package Client;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/** The SocketClient class is a simple example of a TCP/IP Socket Client. */


public class Client {
	public static void main(String[] args) {
		ArrayList<String> actions = new ArrayList<String>();
		ArrayList<String> hand = new ArrayList<String>();
		
		while(true)
		{

			Scanner keyboard;
			keyboard = new Scanner(System.in);
			System.out.println("Join a game room? (type join)");
			String myMsg = keyboard.nextLine();
			if (myMsg.equals("join"))
			{
				actions.add("join");
			}
			else if (myMsg.equals("leave"))
			{
				actions.add("leave");
			}
			else if (myMsg.equals("quit"))
			{
				actions.add("leave");
				System.exit(0);
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
			keyboard.close();
		}
	}
	
}
