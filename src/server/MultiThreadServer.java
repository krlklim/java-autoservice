package server;

import client.MonoThreadClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;


public class MultiThreadServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(ClientServerConfigs.port);
            System.out.println("("+ Calendar.getInstance().getTime()+")"+"Server started: IP-adress= " + ClientServerConfigs.adress + "  PORT = " + ClientServerConfigs.port + " ");

            while (true) {
                Socket client = null;

                while (client == null) {
                    client = serverSocket.accept();
                }
                new MonoThreadClientHandler(client);
                System.out.println("Client connected Successfully");
            }
        } catch (SocketException e) {
            System.err.println("Socket exception Raised");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O exception");
            e.printStackTrace();
        }
        System.out.println("("+ Calendar.getInstance().getTime()+")"+"Сервер остановлен");
    }
}
