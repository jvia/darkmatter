package sanbox.net;

import java.io.*;
import java.net.*;

public class ServerThread extends Thread {

    Socket mySocket;
    String address;

    public ServerThread(Socket socket) {
        mySocket = socket;
        address = mySocket.getInetAddress().toString();
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(mySocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

            String line = in.readLine();
            while (line != null) {
                System.out.println(address + " says " + line);
                line = in.readLine();
            }

            System.out.println("connection for " + address + " closed");

            out.close();
            in.close();
            mySocket.close();
        }
        catch (IOException e) {
            System.out.print(e);
        }
    }
}
