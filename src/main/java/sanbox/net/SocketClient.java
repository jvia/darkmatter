package sanbox.net;

//This is a simple client that connects to on ip "ipAddy" on  port "portNo" 
// and sends two strings.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class SocketClient {

	// ipAddy must be changed to the ip address of the server
	static String ipAddy = "localhost";//10.1.154.188";
	static int portNo = 3801;

	// instead of using try and catch, you can just state that the method might throw an exception
	public static void main(String[] args) throws IOException {
		
		System.out.println("opening socket");
		Socket socket = new Socket(ipAddy,portNo);
		
		//Wrapping a PrintWriter and a BufferedReader around the input and outputs streams makes it easy for us to read and write strings
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		System.out.println("writing data");
		out.write("Hello \n");
		out.write("World\n");


		System.out.println("closing socket");		
		out.close();
		in.close();
		socket.close();

		System.out.println("done");
	}
}













