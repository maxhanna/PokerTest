package gui;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import models.ChatMessage;
import models.PokerVariables;

public class Client  {

	// for I/O	
	String scores;
	// Variables needed to maintain game
	PokerVariables model;
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;


	// the server, the port and the username
	private String server;
	private int port;
	/*
	 *  Constructor called by console mode
	 *  server: the server address
	 *  port: the port number
	 *  username: the username
	 */

	/*
	 * Constructor call when used from a GUI
	 * in console mode the ClienGUI parameter is null
	 */
	public Client(String server, int port, String username, PokerVariables model) {
		this.server = server;
		this.port = port;
		this.model = model;
		this.model.userNames.set(0, username);
	}

	/*
	 * To start the dialog
	 */
	public boolean start() {
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		} 
		// if it failed not much I can so
		catch(Exception ec) {
			display("Error connectiong to server:" + ec);
			return false;
		}

		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);

		/* Creating both Data Stream */
		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		// creates the Thread to listen from the server 
		new ListenFromServer().start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be ChatMessage objects
		try
		{
			sOutput.writeObject(model.userNames.get(0));
		}
		catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		// success we inform the caller that it worked
		return true;
	}

	/*
	 * To send a message to the console or the GUI
	 */
	private void display(String msg) {
		System.out.println(msg);      // println in console
	}

	/*
	 * To send a message to the server
	 */
	void sendMessage(ChatMessage msg) {
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}
	}

	/*
	 * When something goes wrong
	 * Close the Input/Output streams and disconnect not much to do in the catch clause
	 */
	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {} // not much else I can do
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {} // not much else I can do
		try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {} // not much else I can do




	}
	public boolean checkFourOfAKind(String hand)
	{
		String findStr = "Ace";
		int count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;

		findStr = "King";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;

		findStr = "Queen";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;

		findStr = "Jack";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;

		findStr = "10";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;

		findStr = "9";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;

		findStr = "8";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;

		findStr = "7";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;

		findStr = "6";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;

		findStr = "5";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;

		findStr = "4";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;

		findStr = "3";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;

		findStr = "2";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return true;
		
		return false;
	}

	public boolean checkThreeOfAKind(String hand)
	{
		String findStr = "Ace";
		int count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;

		findStr = "King";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;

		findStr = "Queen";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;

		findStr = "Jack";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;

		findStr = "10";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;

		findStr = "9";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;

		findStr = "8";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;

		findStr = "7";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;

		findStr = "6";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;

		findStr = "5";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;

		findStr = "4";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;

		findStr = "3";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;

		findStr = "2";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return true;
		
		return false;
	}
	public boolean checkPair(String hand)
	{
		String findStr = "Ace";
		int count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;

		findStr = "King";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;

		findStr = "Queen";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;

		findStr = "Jack";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;

		findStr = "10";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;

		findStr = "9";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;

		findStr = "8";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;

		findStr = "7";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;

		findStr = "6";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;

		findStr = "5";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;

		findStr = "4";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;

		findStr = "3";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;

		findStr = "2";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return true;
		
		return false;
	}
	public int checkHigh(String hand)
	{
		String findStr = "Ace";
		int count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 14;

		findStr = "King";
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 13;
		
		findStr = "Queen";
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 12;
		
		findStr = "Jack";
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 11;
		
		findStr = "10";
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 10;
		
		findStr = "9";
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 9;
		
		findStr = "8";
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 8;
		
		findStr = "7";
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 7;
		
		findStr = "6";
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 6;
		
		findStr = "5";
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 5;
		
		findStr = "4";
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 4;
		
		findStr = "3";
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 3;
		
		findStr = "2";
		count = hand.split(findStr, -1).length-1;
		if (count>0)
			return 2;
		
		return 0;
	}
	public boolean checkTwoPair(String hand)
	{
		int num;
		int numPairs = 0;
		for (num = 1; num<13; num++)
		{
			String pair = num+"";
			String s = hand;
			if (num==1)
				pair = "Ace";
			else if (num==11)
				pair = "Jack";
			else if (num==12)
				pair = "Queen";
			else if (num==13)
				pair = "King";
				
			int count = 0;
			int index;
			while(true) {
			    index = s.indexOf(pair);
			    if(index == -1) break;
			    s = s.substring(index + pair.length());
			    count++;
			    if (count == 2){
			    	numPairs++;
			    }
			}
			if (numPairs>1)
			{
				return true;
			}
		}
		return false;
	}
	public boolean checkStraight(String hand)
	{
		if (hand.contains("Ace") && hand.contains("3") && hand.contains("4") && hand.contains("5") && hand.contains("2"))
		  {
		    return true;
		  }
		if (hand.contains("2") && hand.contains("3") && hand.contains("4") && hand.contains("5") && hand.contains("6"))
		  {
		    return true;
		  }
		  if (hand.contains("3") && hand.contains("4") && hand.contains("5") && hand.contains("6") && hand.contains("7"))
		  {
		    return true;
		  }
		  if (hand.contains("4") && hand.contains("5") && hand.contains("6") && hand.contains("7") && hand.contains("8"))
		  {
		    return true;
		  }
		  if (hand.contains("5") && hand.contains("6") && hand.contains("7") && hand.contains("8") && hand.contains("9"))
		  {
		    return true;
		  }
		  if (hand.contains("10") && hand.contains("6") && hand.contains("7") && hand.contains("8") && hand.contains("9"))
		  {
		    return true;
		  }
		  if (hand.contains("10") && hand.contains("Jack") && hand.contains("7") && hand.contains("8") && hand.contains("9"))
		  {
		    return true;
		  }
		  if (hand.contains("10") && hand.contains("Jack") && hand.contains("Queen") && hand.contains("8") && hand.contains("9"))
		  {
		    return true;
		  }
		  if (hand.contains("10") && hand.contains("Jack") && hand.contains("Queen") && hand.contains("King") && hand.contains("9"))
		  {
		    return true;
		  }
		  if (hand.contains("10") && hand.contains("Jack") && hand.contains("Queen") && hand.contains("King") && hand.contains("Ace"))
		  {
		    return true;
		  }
		return false;
	}
	public boolean checkFlush(String hand)
	{
		String string = hand;
		String[] cards = string.split(",");
		String card1 = cards[0];
		String[] cardParts = card1.split(" ");
		String suit = cardParts[2];
	    int lastIndex = 0;
	    int count = 0;
	    String str = hand;
	    String findStr = suit;
	    while ((lastIndex = str.indexOf(findStr, lastIndex)) != -1) {
	        count++;
	        lastIndex += findStr.length() - 1;
	    }
		if (count>4)
			return true;
		return false;
	}
	public boolean checkRoyalFlush(String hand)
	{
		if (hand.contains("Ace") && hand.contains("King") && hand.contains("Queen") && hand.contains("Jack") && hand.contains("10"))
		  {
			if (checkFlush(hand))
				return true;
		  }
		return false;
	}
	public boolean checkFullHouse(String hand)
	{
		if (checkThreeOfAKind(hand))
		{
			for (int cardNum=1;cardNum<14;cardNum++)
			{
				String str = hand;
			    String findStr = cardNum+"";
			    if (findStr.equals("1"))
			    	findStr = "Ace";
			    else if (findStr.equals("11"))
			    	findStr = "Jack";
			    else if (findStr.equals("12"))
			    	findStr = "Queen";
			    else if (findStr.equals("13"))
			    	findStr = "King";
			    int lastIndex = 0;
			    int count = 0;

			    while(lastIndex != -1){

			        lastIndex = str.indexOf(findStr,lastIndex);

			        if(lastIndex != -1){
			            count ++;
			            lastIndex += findStr.length();
			        }
			    }
			    //System.out.println("found " + count + " " + findStr);
			    if (count==2)
				   return true;
			}
			
		}
		return false;
	}
	public boolean checkStraightFlush(String hand)
	{
		if (checkStraight(hand))
			if (checkFlush(hand))
				return true;
		return false;
	}
	
	public String calculateWinner()
	{

		String winningHand = "";
		String secondHand = "";
		String thirdHand = "";
		if (model.userHands.keySet().size()==0)
			return "No Winner";
		else{
			String victor;
			String second;
			String third;
			int victorPoints = 0;
			int secondPoints = 0;
			int thirdPoints = 0;
			int userPoints = 0;
			victor = (String) model.userHands.keySet().toArray()[0];
			second = (String) model.userHands.keySet().toArray()[0];
			third = (String) model.userHands.keySet().toArray()[0];
			for (String user : model.userHands.keySet())
			{
				//14 max for ace high
				userPoints = checkHigh(model.userHands.get(user));

				//23 points for a royal flush
				if (checkRoyalFlush(model.userHands.get(user)))
					userPoints = userPoints + 23;
				//22 points for a straight flush
				else if (checkStraightFlush(model.userHands.get(user)))
					userPoints = userPoints + 22;
				//21 points for four of a kind
				else if (checkFourOfAKind(model.userHands.get(user)))
					userPoints = userPoints + 21;
				//20 points for full house
				else if (checkFullHouse(model.userHands.get(user)))
					userPoints = userPoints + 20;
				//19 points for flush
				else if (checkFlush(model.userHands.get(user)))
					userPoints = userPoints + 19;
				//18 points for straight
				else if (checkStraight(model.userHands.get(user)))
					userPoints = userPoints + 18;
				//17 points for three of a kind
				else if (checkThreeOfAKind(model.userHands.get(user)))
					userPoints = userPoints + 17;
				//16 points for two pair
				else if (checkTwoPair(model.userHands.get(user)))
					userPoints = userPoints + 16;
				//15 points for pair
				else if (checkPair(model.userHands.get(user)))
					userPoints = userPoints + 15;
				
				
				// point system takes into account high cards.
				// a pair with ace high will beat a pair with king high.
				if (userPoints > victorPoints){
					victor = user;
					victorPoints = userPoints;
					if (victorPoints > 10)
					{
						if (checkHigh(model.userHands.get(user)) == 14)
							winningHand = "Ace high";
						else if (checkHigh(model.userHands.get(user)) == 13)
							winningHand = "King high";
						else if (checkHigh(model.userHands.get(user)) == 12)
							winningHand = "Queen high";
						else if (checkHigh(model.userHands.get(user)) == 11)
							winningHand = "Jack high";
						else {
							winningHand = checkHigh(model.userHands.get(user))+" high";
						}
						
					}
					if (checkPair(model.userHands.get(user)))
						winningHand = "Pair";
					if (checkTwoPair(model.userHands.get(user)))
						winningHand = "Two Pair";
					if (checkThreeOfAKind(model.userHands.get(user)))
						winningHand = "Three of a kind";
					if (checkStraight(model.userHands.get(user)))
						winningHand = "Straight";
					if (checkFlush(model.userHands.get(user)))
						winningHand = "Flush";
					if (checkFullHouse(model.userHands.get(user)))
						winningHand = "Full House";
					if (checkFourOfAKind(model.userHands.get(user)))
						winningHand = "Four of a kind";
					if (checkStraightFlush(model.userHands.get(user)))
						winningHand = "Straight Flush";
					if (checkRoyalFlush(model.userHands.get(user)))
						winningHand = "Royal Flush";
				}
				else if (userPoints > secondPoints){
					second = user;
					secondPoints = userPoints;
					if (secondPoints > 10)
					{
						if (checkHigh(model.userHands.get(user)) == 14)
							secondHand = "Ace high";
						else if (checkHigh(model.userHands.get(user)) == 13)
							secondHand = "King high";
						else if (checkHigh(model.userHands.get(user)) == 12)
							secondHand = "Queen high";
						else if (checkHigh(model.userHands.get(user)) == 11)
							secondHand = "Jack high";
						else {
							secondHand = checkHigh(model.userHands.get(user))+" high";
						}
						
					}
					if (checkPair(model.userHands.get(user)))
						secondHand = "Pair";
					if (checkTwoPair(model.userHands.get(user)))
						secondHand = "Two Pair";
					if (checkThreeOfAKind(model.userHands.get(user)))
						secondHand = "Three of a kind";
					if (checkStraight(model.userHands.get(user)))
						secondHand = "Straight";
					if (checkFlush(model.userHands.get(user)))
						secondHand = "Flush";
					if (checkFullHouse(model.userHands.get(user)))
						secondHand = "Full House";
					if (checkFourOfAKind(model.userHands.get(user)))
						secondHand = "Four of a kind";
					if (checkStraightFlush(model.userHands.get(user)))
						secondHand = "Straight Flush";
					if (checkRoyalFlush(model.userHands.get(user)))
						secondHand = "Royal Flush";
				}
				else if (userPoints > thirdPoints){
					third = user;
					thirdPoints = userPoints;
					if (thirdPoints > 10)
					{
						if (checkHigh(model.userHands.get(user)) == 14)
							thirdHand = "Ace high";
						else if (checkHigh(model.userHands.get(user)) == 13)
							thirdHand = "King high";
						else if (checkHigh(model.userHands.get(user)) == 12)
							thirdHand = "Queen high";
						else if (checkHigh(model.userHands.get(user)) == 11)
							thirdHand = "Jack high";
						else {
							thirdHand = checkHigh(model.userHands.get(user))+" high";
						}
						
					}
					if (checkPair(model.userHands.get(user)))
						thirdHand = "Pair";
					if (checkTwoPair(model.userHands.get(user)))
						thirdHand = "Two Pair";
					if (checkThreeOfAKind(model.userHands.get(user)))
						thirdHand = "Three of a kind";
					if (checkStraight(model.userHands.get(user)))
						thirdHand = "Straight";
					if (checkFlush(model.userHands.get(user)))
						thirdHand = "Flush";
					if (checkFullHouse(model.userHands.get(user)))
						thirdHand = "Full House";
					if (checkFourOfAKind(model.userHands.get(user)))
						thirdHand = "Four of a kind";
					if (checkStraightFlush(model.userHands.get(user)))
						thirdHand = "Straight Flush";
					if (checkRoyalFlush(model.userHands.get(user)))
						thirdHand = "Royal Flush";
				}
			}
			model.phase = 1;
			if (model.userHands.keySet().size()==1)
				return (victor + " with " + winningHand + " " + victorPoints ) ;
			else if (model.userHands.keySet().size()==2)
				return (victor + " with " + winningHand + " " + victorPoints + " Followed by " + second + " with " + secondHand) ;
			else
				return (victor + " with " + winningHand + " " + victorPoints + " Followed by " + second + " with " + secondHand + ", and " + third + " with " + thirdHand) ;
		}
		
	}
	/*
	 * To start the Client in console mode use one of the following command
	 * > java Client
	 * > java Client username
	 * > java Client username portNumber
	 * > java Client username portNumber serverAddress
	 * at the console prompt
	 * If the portNumber is not specified 28442 is used
	 * If the serverAddress is not specified "localHost" is used
	 * If the username is not specified "Anonymous" is used
	 * > java Client 
	 * is equivalent to
	 * > java Client Anonymous 28442 localhost 
	 * are eqquivalent
	 * 
	 * In console mode, if an error occurs the program simply stops
	 * when a GUI id used, the GUI is informed of the disconnection
	 */
	public static void main(String[] args) {
		// default values	
		PokerVariables model  = new PokerVariables();


		int portNumber = 28442;
		String serverAddress = "localhost";
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter a Username");
		

		String userName  = keyboard.nextLine();
		model.userNames.add(userName);

		// depending of the number of arguments provided we fall through
		switch(args.length) {
		// > javac Client username portNumber serverAddr
		case 3:
			serverAddress = args[2];
			// > javac Client username portNumber
		case 2:
			try {
				portNumber = Integer.parseInt(args[1]);
			}
			catch(Exception e) {
				System.out.println("Invalid port number.");
				System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
				keyboard.close();
				return;
			}
			// > javac Client username
		case 1: 
			model.userNames.set(0, args[0]);
			// > java Client
		case 0:
			break;
			// invalid number of arguments
		default:
			System.out.println("Usage is: > java Client [player number] [portNumber] {serverAddress]");
			keyboard.close();
			return;
		}
		// create the Client object
		Client client = new Client(serverAddress, portNumber, model.userNames.get(0), model);
		// test if we can start the connection to the Server
		// if it failed nothing we can do
		if(!client.start()) {
			keyboard.close();
			return;
		}

		// wait for messages from user
		//Scanner scan = new Scanner(System.in);
		String msg = "";
		// loop forever for message from the user
		while(true) {
			// read message from user


			System.out.print(": ");
			msg = keyboard.nextLine( );
			
			// logout if message is LOGOUT
			if(msg.equalsIgnoreCase("LOGOUT")) {
					client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, "" + ",disconnected\n", model.userNames.get(0)));
					// break to do the disconnect
					break;
				}
				// message WhoIsIn
				else if(msg.equalsIgnoreCase("WHOISIN")) {
					client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, "", model.userNames.get(0)));				
				}
	
				else {				// default to ordinary message
	
					if (msg.contains("serve"))
					{
						if (model.phase > 2)
						{
							msg = "";
							System.out.println("You have already been served.");
						}
						else
							model.phase = 2;
					}
					if (msg.contains("return"))
					{
						if (model.phase<2)
						{
							System.out.println("No cards to return!");
							msg = "";
						}
						else {
							model.phase = 3;
							String cards = msg.replace("return ", "");
							cards = msg.replace("ace", "Ace");
							cards = msg.replace("jack", "Jack");
							cards = msg.replace("queen", "Queen");
							cards = msg.replace("king", "King");
							cards = cards.replace(", ", ",");
	
							String delims2 = "[,]+";
							String[] hand = cards.split(delims2);
							for (String card : hand){
								if (model.hand.remove(card))
									System.out.println("Removed "+ card + " from hand.");
							}
							System.out.println("Returning cards to server");
							model.day++;
						}
					}
					client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg, model.userNames.get(0)));				
					
					System.out.println("Sending ordinary message to server");
					
	
				}
			
		}
		// done disconnect
		client.disconnect();
		keyboard.close();
	}

	/*
	 * a class that waits for the message from the server and append them to the JTextArea
	 * if we have a GUI or simply use System.out.println() if in console mode
	 */
	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					ChatMessage msg1 = (ChatMessage) sInput.readObject();
					//If you want console to print every message client receives from server, uncomment next line.
					String msg = "";
					if (msg1.getMessage().getClass().equals(String.class))
					{
						msg = (String)msg1.getMessage();
						//System.out.println(msg);

					}
					
					String phrase = msg;
					String delims = "[ ]+";
					String[] tokens = phrase.split(delims);

					for (int i = 0; i<tokens.length-1; i++)
					{
						if (tokens[i].contains(model.userNames.get(0)))
						{
							if (tokens[i+1].contains("takes"))
							{	
								System.out.println("youre taking cards");
								String cards = msg;
								cards = cards.replace(model.userNames.get(0) + " takes ","");

								String delims2 = "[,]+";
								String[] hand = cards.split(delims2);
								if (!model.hand.isEmpty())
									model.hand.clear();
								System.out.println("your current hand:");
								for(String s : hand)
								{
									model.hand.add(s);
									System.out.println(s);
								}
								
								
							}
							if (tokens[i+1].contains("gets"))
							{	
								String cards = msg;
								if (!cards.contains("gets nothing"))
								{
									cards = cards.replace(model.userNames.get(0) + " gets ", "");
	
									System.out.println("youre getting cards " + cards);
									String delims2 = "[,]+";
									String[] received = cards.split(delims2);
									
									for(String s : received)
									{
										model.hand.add(s);
										System.out.println("Added " + s + " to hand");
									}
								}
								System.out.println("Current hand: ");
								String hand = "";
								for(String s : model.hand)
								{
									if (!s.equals(model.hand.get(model.hand.size()-1)))
										hand = hand + s + ",";
									else 
										hand = hand + s;
								}
								System.out.println(hand);
								sendMessage(new ChatMessage(ChatMessage.MESSAGE, "Current Hand " + hand, model.userNames.get(0)));				
								
							}
						}
						else if (tokens[i].contains("Calculate"))
						{
							String winner = "";
							System.out.println("Winner : ");
							winner = calculateWinner();
							System.out.println(winner);
							model.userHands = new HashMap<String,String>();
						}
						else if (tokens[i].contains("Current"))
						{
							String hand = msg1.getMessage();
							hand = hand.replace("Current Hand ", "");
							System.out.println("Received " + msg1.getUserName() + "'s hand.");
							model.userHands.put(msg1.getUserName(), hand);
							if (model.cheatMode)
								System.out.println(msg1.getUserName() + "'s hand:\n"+hand);
						}

						else if (tokens[i+1].contains("disconnected"))
						{
							model.userHands.remove(tokens[i]);
						}
						else {
							//System.out.println(msg);
						}
					}


				}

				catch(IOException e) {
					display("Server has closed the connection: " + e);
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}

