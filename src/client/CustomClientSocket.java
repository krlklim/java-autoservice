package client;

import server.ClientServerConfigs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class CustomClientSocket implements Runnable{
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket = null;

    @Override
    public void run() {
        try {
            InetAddress ipAddress = InetAddress.getByName(ClientServerConfigs.adress);
            socket = new Socket(ipAddress, ClientServerConfigs.port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() { return objectInputStream; }

    public Socket getSocket() {
        return socket;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

 public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
