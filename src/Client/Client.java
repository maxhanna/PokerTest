package Client;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
/* The java.io package contains the basics needed for IO operations. */
import java.io.*;
import java.util.Scanner;
/** The SocketClient class is a simple example of a TCP/IP Socket Client. */


public class Client {
	public static void main(String[] args) {
		boolean inGame = false;
		ArrayList<String> actions = new ArrayList<String>();
		ArrayList<String> hand = new ArrayList<String>();
		
		while(true)
		{
			Scanner keyboard = new Scanner(System.in);
			System.out.println("Join a game room? (type join)");
			String myMsg = keyboard.next();
			if (myMsg.equals("join"))
			{
				inGame = true;
				actions.add("join");
			}
			ClientMessage msg = new ClientMessage();
			
			//Send a message to the server "myMsg"
			//and print string returned from function sendMessage.
			String serverMessage = msg.sendMessage(myMsg + " ");
			
			System.out.println("Server Message : " + serverMessage);
			
			if (serverMessage.contains("take card"))
			{
				//split server message, and convert it into the player's hand
			     String[] result = serverMessage.split(":");
			     String[] results = result[1].split(",");
			     ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(results));
			     
			     for (int i = 0 ; i < stringList.size() ; i++){
			    	 hand.add(stringList.get(i));
			    	 System.out.println(hand.get(i));
			     }
			}
		}
	}
	
}
