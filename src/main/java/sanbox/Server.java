/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sanbox;

import com.giantcow.darkmatter.DarkMatter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Yukun_Wang
 */
public class Server {



    public static void main(String[] args) {
        

        ServerSocket ss = null;
        while (true){
            try {
            ss = new ServerSocket(8888);
            Socket socket = ss.accept();
            //send message to client
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            //reserve message
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            System.out.println("reserve:" + dis.readUTF());

            dos.writeUTF("this is a message send from servers");
            
            socket.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        }
        
    }
}
