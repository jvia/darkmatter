package com.giantcow.darkmatter.net;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;
import java.util.List;

/**
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class ClientTest extends TestCase {

    Server server;
    Client client;

    public void testPass() {
        assertTrue(true);
    }

//     public ClientTest(String testName) {
//         super(testName);
//         System.out.println("Starting server...");
//         server = new Server();
//         server.start();
//         try {
//             Thread.sleep(1000);
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//         client = new Client();
//     }

//     public void shutdown() {
//         server.shutdown();
//     }

     public static Test suite() {
         return new TestSuite(ClientTest.class);
     }


//     /** Test of run method, of class Client.
//     public void testRun() {
//         System.out.println("run");
//         client.start();
//         assertTrue(client.isConnected());
//         client.shutdown();
//     }
//     */

//     /** Test of getGameState method, of class Client. */
//     public void testGetGameState() {
//         System.out.println("getGameState");
//         client = new Client();
//         List result = client.getGameState();
//         assertNotNull(result);
//     }

//     /** Test of sendClick method, of class Client.
//     public void testSendClick() {
//         System.out.println("sendClick");
//         Point click = new Point(4, 5);
//         client.sendClick(click);
//         assertTrue(true);
//     }
//     */

// //    /** Test of whoAmI method, of class Client. */
// //    public void testWhoAmI() {
// //        System.out.println("whoAmI");
// //        int expResult = 0;
// //        int result = client.whoAmI();
// //        assertEquals(expResult, result);
// //    }


//     /** Test of isConnected method, of class Client. */
//     public void testIsConnected() {
//         System.out.println("isConnected");
//         boolean expResult = true;
//         boolean result = client.isConnected();
//         assertEquals(expResult, result);
//     }

//     /** Test of shutdown method, of class Client. */
//     public void testShutdown() {
//         System.out.println("shutdown");
//         client.shutdown();
//         try {
//             Thread.sleep(500);
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//         assertFalse(client.isConnected());
//     }
}
