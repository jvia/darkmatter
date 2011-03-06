//package sanbox.net;
//
////This is a server that listens on port "portNo" for a client to connect
////and then tries to read Message objects from the client and test their type.
//import com.giantcow.darkmatter.player.Matter;
//import java.io.EOFException;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class ObjectServer {
//
//    // portNo is the port number that the server will listen on
//    static int portNo = 3802;
//
//    public static void main(String[] args) throws IOException {
//
//        System.out.println("opening socket");
//        ServerSocket serverSocket = new ServerSocket(portNo);
//
//        System.out.println("My connection address is " + serverSocket.getLocalSocketAddress().toString());
//
//        // Listen on the socket until someone tries to connect.
//        System.out.println("listening on port " + portNo);
//        Socket socket = serverSocket.accept();
//        System.out.println("got a connection from: " + socket.getInetAddress().toString());
//
//        //Wrapping a ObjectOutputStream around the output stream lets us send Objects
//        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//
//        Object inObj;
//        com.giantcow.darkmatter.net.GameMessage inMess;
//
//        // Loop reads from the input stream until the connection is closed, when
//        // the EOFException is raised and the program ends.
//        try {
//            while (true) {
//                // read an object from the input stream.
//                inObj = in.readObject();
//
//                // check to see if it is a "Message" object
//                if (inObj instanceof com.giantcow.darkmatter.net.GameMessage) {
//                    inMess = (com.giantcow.darkmatter.net.GameMessage) inObj;
//
//
//                    // if it is then print the contains
//                    if (inMess.type == inMess.type.String) {
//                        System.out.println("Message is : " + inMess.message);
//                    } else if (inMess.type == inMess.type.Data) {
//                        System.out.println("Message is a set: ");
//                        for (Matter m : inMess.data)
//                            System.out.println(m);
//                    }
//                } else {
//                    System.out.println("Object of is not a Message");
//                }
//            }
//        } catch (EOFException e) {
//            System.out.println("connection closed");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Recieved object of an unknown class");
//        }
//        in.close();
//        socket.close();
//        serverSocket.close();
//    }
//}
