package sanbox.net;


//This is a simple object that can be passed across a Socket connection 

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = -8584149296031996410L;
	public static final int VALUE = 1;
	public static final int STRING = 2;
	
	// kind = VALUE if the message uses the value field
	// kind = STRING if the message stores a string
	int kind;
	int messageValue;
	String messageString; 

	public Message () {}
}
