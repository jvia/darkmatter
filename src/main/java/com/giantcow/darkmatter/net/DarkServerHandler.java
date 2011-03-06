//package com.giantcow.darkmatter.net;
//
//import com.giantcow.darkmatter.player.Matter;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
// */
//public class DarkServerHandler extends Thread {
//
//    private Socket clientSock;
//    private String address;
//    private Set<Matter> matters = Collections.synchronizedSet(new HashSet<Matter>());
//    private boolean finished;
//
//    DarkServerHandler(Socket socket, Set<Matter> matters) {
//        clientSock = socket;
//        this.matters = matters;
//        boolean finished = false;
//        DarkServer.players++;
//    }
//
//    @Override
//    public void run() {
//        try {
//
//            ObjectInputStream input = new ObjectInputStream(clientSock.getInputStream());
//            ObjectOutputStream output = new ObjectOutputStream(clientSock.getOutputStream());
//
//            processClient(input, output);
//
//            // Close all streams and connections
//            input.close();
//            output.close();
//            clientSock.close();
//        } catch (IOException ex) {
//        }
//    }
//
//    private void processClient(ObjectInputStream input, ObjectOutputStream output) {
//        GameMessage msg;
//        while (!finished) {
//            try {
//                // read request
//                msg = (GameMessage) input.readObject();
//
//                // determine its type and process accordingly
//                switch (msg.type) {
//                    case Data:
//                        Set<Matter> set = msg.data;
//                        matters.addAll(set);
//                        msg.type = msg.type.Data;
//                        msg.data = matters;
//                        break;
//                    case String:
//                        if (msg.message.equals("playercount")) {
//                            msg.type = msg.type.String;
//                            msg.message = String.valueOf(DarkServer.players);
//                        } else if (msg.message.equals("goodbye")) {
//                            finished = true;
//                            DarkServer.players--;
//                            return;
//                        }
//                        break;
//                }
//
//
//                // send the data back down
//                output.writeObject(msg);
//            } catch (IOException ex) {
//                Logger.getLogger(DarkServerHandler.class.getName()).log(Level.SEVERE, "Could not read object", ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(DarkServerHandler.class.getName()).log(Level.SEVERE, "Could not cast object to Message", ex);
//            }
//        }
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//}
