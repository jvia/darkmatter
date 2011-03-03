package sanbox.net;

//This is a client that connects to on ip "ipAddy" on  port "portNo" 
//and sends an object of "type Message".
import com.giantcow.darkmatter.player.Matter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;

public class ObjectClient {

    // ipAddy must be changed to the ip address of the server
    static int portNo = 3802;
//	static String ipAddy = "127.0.0.1";
    static String ipAddy = "localhost";

    // instead of using try and catch, you can just state that the method might throw an exception
    public static void main(String[] args) throws IOException {

        // Open the connection to the remote computer
        Socket socket = new Socket(ipAddy, portNo);

        //Wrapping a ObjectOutputStream  around the  output stream lets us send Objects
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        // Make the message object to send
        com.giantcow.darkmatter.net.Message m = new com.giantcow.darkmatter.net.Message();
        m.type = m.type.String;
        m.message = "Hello";
        out.writeObject(m);

        HashSet<Matter> matters =new HashSet<Matter>();
        matters.add(new Matter(0.0, 0.0, 0.0, 0.0, 0.0));
        matters.add(new Matter(1.0, 1.0, 1.0, 0.0, 0.0));
        m = new com.giantcow.darkmatter.net.Message();
        m.type = m.type.Data;
        m.data = matters;
        out.writeObject(m);

        // close the connection
        out.close();
        socket.close();
    }
}
