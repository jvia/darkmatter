package sanbox.net;

// This is a simple server that listens on port "portNo" for a client to connect 
// and then tries to read strings from the client and print them to stdout

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	// portNo is the port number that the server will listen on
	static int portNo = 3801;

	public static void main(String[] args) {

		//There might be a network error, therefore we run the code in a "try" block 
		try {

			System.out.println("opening socket");
			ServerSocket serverSocket = new ServerSocket(portNo);

			// Listen on the socket until someone tries to connect.
			System.out.println("listening on port "+portNo);
			Socket socket = serverSocket.accept();
			System.out.println("got a connection from: "+socket.getInetAddress().toString());

			//Wrapping a PrintWriter and a BufferedReader around the input and outputs streams makes it easy for us to read and write strings
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			//  Wait for a line of data. 
			String line = in.readLine();
			while ( line != null) {
				// print the data recieved across the socket.
				System.out.println(line); 
				// wait for more data, line == null when the connection is closed.
				line = in.readLine();
			}

			System.out.println("connection closed");
			out.close();
			in.close();
			socket.close();
			serverSocket.close();
		}
		catch (IOException e) {
			// This code is run if a network error occurs, e.g. the Internet connection is lost.
			System.out.println("Network error: "+e);}
	}
}
