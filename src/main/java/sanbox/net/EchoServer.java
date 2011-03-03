/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sanbox.net;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author jxv911
 */
public class EchoServer {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(5776);
        System.out.println(server.getInetAddress());
        System.out.println(server.getLocalSocketAddress());
        
        while (true) {
            Socket connection = server.accept();
            System.out.println(connection.getLocalAddress());
            System.out.println(connection.getInetAddress());
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            System.out.println("here");
            out.write("You've connected to this server. Bye-bye now.\r\n");
            System.out.println("now here");
            connection.close();
        }
    }
}
