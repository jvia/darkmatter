/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sanbox;

import com.giantcow.darkmatter.DarkMatter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author yxw999
 */
public class client {

    public static void main(String[] args) {


        Socket socket = null;
        while (true) {
            try {
                socket = new Socket("localhost", 8888);

                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                //send message to server
                dos.writeUTF("this is a message send from client");
                

                System.out.println("reserve: " + dis.readUTF());

                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
