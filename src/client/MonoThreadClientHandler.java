package client;

import models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import static config.Messages.GET_CURRENT_USER;
import static config.Messages.USER_LOGIN;
import static config.Messages.USER_SIGNUP;

public class MonoThreadClientHandler extends Thread {
    private Socket clientSocket;
    private CustomClientMessage clientMessage;
    private String userMessage;

    public ObjectOutputStream outputStream;
    public ObjectInputStream inputStream;

    private User currentUser;

    public MonoThreadClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.start();
    }

    @Override
    public void run() {
        try {
            inputStream = new ObjectInputStream(this.clientSocket.getInputStream());
            outputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
            while(true) {
                this.clientMessage = (CustomClientMessage) inputStream.readObject();
                userMessage = this.clientMessage.getMessage();
                System.out.println("Receiving message " + userMessage);
                switch(userMessage) {
                    //Authorization
                    case USER_LOGIN: {
                        User userPayload = (User) this.clientMessage.getPayload();
                        System.out.println("Attempting to authenticate user " + userPayload.getLogin());

                        User user = User.login(userPayload.getLogin(), userPayload.getPassword());

                        if (user == null) {
                            System.out.println(USER_LOGIN + ": Failure");
                            outputStream.writeObject(new CustomClientMessage("Failure"));
                            return;
                        }

                        this.currentUser = user;
                        System.out.println("Success");
                        outputStream.writeObject(new CustomClientMessage(USER_LOGIN + ": Success", user));
                        break;
                    }
                    case USER_SIGNUP: {
                        User userPayload = (User) this.clientMessage.getPayload();
                        System.out.println("Attempting to signup user " + userPayload.getLogin());


                        User user = new User(
                            userPayload.getLogin(),
                            userPayload.getPassword(),
                            userPayload.getFirstName(),
                            userPayload.getLastName(),
                            userPayload.getMiddleName(),
                            userPayload.getRole()
                        );
                        user.signup();


                        if (user == null) {
                            System.out.println(USER_SIGNUP + ": Failure");
                            outputStream.writeObject(new CustomClientMessage("Failure"));
                            return;
                        }

                        this.currentUser = user;
                        System.out.println("Success");
                        outputStream.writeObject(new CustomClientMessage(USER_SIGNUP + ": Success", user));
                        break;
                    }
                    case GET_CURRENT_USER: {
                        outputStream.writeObject(
                                new CustomClientMessage(USER_SIGNUP + ": Success",
                                this.currentUser));
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

