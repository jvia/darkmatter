package sanbox.net;

import java.io.*;
import java.net.*;

public class MultiSocketServer {

    static int portNo = 3807;

    public static void main(String[] args) throws IOException {
        System.out.println("opening socket");
        ServerSocket serverSocket = new ServerSocket(portNo);
        while (true) {
            System.out.println("listening on port " + portNo);
            Socket socket = serverSocket.accept();
            System.out.println("got a connection from: " + socket.getInetAddress().toString());

            ServerThread task = new ServerThread(socket);
            task.start();
        }

        //serverSocket.close();

    }
}
