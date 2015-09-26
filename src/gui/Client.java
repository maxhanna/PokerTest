package gui;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import models.ChatMessage;
import models.PokerVariables;

public class Client  {

	// for I/O	
	String scores;
	// Variables needed to maintain game
	public PokerVariables model;
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

		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort()
				+ "\n Available commands: \n"
				+ "serve : tell the dealer you want cards \n"
				+ "return Ace of spades, 10 of hearts : returns the Ace of spades and 10 of hearts to the dealer\n"
				+ "pass : do not return any cards to the dealer";
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
	public boolean checkCard(String card)
	{
		if (card.contains("of ") && (card.contains("spades") || card.contains("hearts") 
				|| card.contains("diamonds") || card.contains("clubs")))
		{
			card = card + card.replace("of ", "");
			card = card + card.replace("hearts", "");
			card = card + card.replace("diamonds", "");
			card = card + card.replace("clubs", "");
			card = card + card.replace("spades", "");
			if (card.equals("Ace")||card.equals("Jack")||card.equals("Queen")||
					card.equals("King")||card.equals("10")||card.equals("9")||
					card.equals("8")||card.equals("7")||card.equals("6")||
					card.equals("5")||card.equals("4")||card.equals("3")||card.equals("2"))
			{
				return true;
			}
		}
		return false;
	}
	public int checkFourOfAKind(String hand)
	{
		String findStr = "Ace";
		int count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 633;

		findStr = "King";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 632;

		findStr = "Queen";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 631;

		findStr = "Jack";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 630;

		findStr = "10";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 629;

		findStr = "9";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 628;

		findStr = "8";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 627;

		findStr = "7";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 626;

		findStr = "6";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 625;

		findStr = "5";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 624;

		findStr = "4";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 623;

		findStr = "3";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 622;

		findStr = "2";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>3)
			return 621;

		return 0;
	}

	public int checkThreeOfAKind(String hand)
	{
		String findStr = "Ace";
		int count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 14+417;

		findStr = "King";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 13+417;

		findStr = "Queen";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 12+417;

		findStr = "Jack";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 11+417;

		findStr = "10";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 10+417;

		findStr = "9";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 9+417;

		findStr = "8";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 8+417;

		findStr = "7";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 7+417;

		findStr = "6";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 6+417;

		findStr = "5";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 5+417;

		findStr = "4";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 4+417;

		findStr = "3";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 3+417;

		findStr = "2";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>2)
			return 2+417;

		return 0;
	}
	public int checkPair(String hand)
	{
		String findStr = "Ace";
		int count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 314;

		findStr = "King";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 312;

		findStr = "Queen";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 311;

		findStr = "Jack";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 310;

		findStr = "10";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 309;

		findStr = "9";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 308;

		findStr = "8";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 307;

		findStr = "7";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 306;

		findStr = "6";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 305;

		findStr = "5";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 304;

		findStr = "4";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 303;

		findStr = "3";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 302;

		findStr = "2";
		count = 0;
		count = hand.split(findStr, -1).length-1;
		if (count>1)
			return 301;

		return 0;
	}
	public int checkHigh(String hand)
	{

		if (hand.contains("Ace"))
			return 14;

		if (hand.contains("King"))
			return 13;

		if (hand.contains("Queen"))
			return 12;

		if (hand.contains("Jack"))
			return 11;
		if (hand.contains("10"))
			return 10;
		if (hand.contains("9"))
			return 9;
		if (hand.contains("8"))
			return 8;
		if (hand.contains("7"))
			return 7;
		if (hand.contains("6"))
			return 6;
		if (hand.contains("5"))
			return 5;
		if (hand.contains("4"))
			return 4;
		if (hand.contains("3"))
			return 3;
		if (hand.contains("2"))
			return 2;

		return 0;
	}
	public int checkTwoPair(String hand)
	{
		int num;
		int numPairs = 0;
		int highPair = 0;
		boolean setHighPair = false;
		for (num = 1; num<14; num++)
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
					if (num==1){
						highPair = 14;
						setHighPair = true;
					}
					else if (num==13 && !setHighPair)
					{
						highPair = 13;
						setHighPair = true;
					}
					else if (num==12 && !setHighPair)
					{
						highPair = 12;
						setHighPair = true;
					}
					else if (num==11 && !setHighPair)
					{
						highPair = 11;
						setHighPair = true;
					}
					else if (num==10 && !setHighPair)
					{
						highPair = 10;
						setHighPair = true;
					}
					else if (num==9 && !setHighPair)
					{
						highPair = 9;
						setHighPair = true;
					}
					else if (num==8 && !setHighPair)
					{
						highPair = 8;
						setHighPair = true;
					}
					else if (num==7 && !setHighPair)
					{
						highPair = 7;
						setHighPair = true;
					}
					else if (num==6 && !setHighPair)
					{
						highPair = 6;
						setHighPair = true;
					}
					else if (num==5 && !setHighPair)
					{
						highPair = 5;
						setHighPair = true;
					}
					else if (num==4 && !setHighPair)
					{
						highPair = 4;
						setHighPair = true;
					}
					else if (num==3 && !setHighPair)
					{
						highPair = 3;
						setHighPair = true;
					}
					else if (num==2 && !setHighPair)
					{
						highPair = 2;
						setHighPair = true;
					}
				}
			}
			if (numPairs>1)
			{
				return highPair+360;
			}
		}
		return 0;
	}
	public int checkStraight(String hand)
	{
		if (hand.contains("Ace") && hand.contains("3") && hand.contains("4") && hand.contains("5") && hand.contains("2"))
		{
			return 468;
		}
		if (hand.contains("2") && hand.contains("3") && hand.contains("4") && hand.contains("5") && hand.contains("6"))
		{
			return 469;
		}
		if (hand.contains("3") && hand.contains("4") && hand.contains("5") && hand.contains("6") && hand.contains("7"))
		{
			return 470;
		}
		if (hand.contains("4") && hand.contains("5") && hand.contains("6") && hand.contains("7") && hand.contains("8"))
		{
			return 471;
		}
		if (hand.contains("5") && hand.contains("6") && hand.contains("7") && hand.contains("8") && hand.contains("9"))
		{
			return 472;
		}
		if (hand.contains("10") && hand.contains("6") && hand.contains("7") && hand.contains("8") && hand.contains("9"))
		{
			return 473;
		}
		if (hand.contains("10") && hand.contains("Jack") && hand.contains("7") && hand.contains("8") && hand.contains("9"))
		{
			return 474;
		}
		if (hand.contains("10") && hand.contains("Jack") && hand.contains("Queen") && hand.contains("8") && hand.contains("9"))
		{
			return 475;
		}
		if (hand.contains("10") && hand.contains("Jack") && hand.contains("Queen") && hand.contains("King") && hand.contains("9"))
		{
			return 476;
		}
		if (hand.contains("10") && hand.contains("Jack") && hand.contains("Queen") && hand.contains("King") && hand.contains("Ace"))
		{
			return 477;
		}
		return 0;
	}
	public int checkFlush(String hand)
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
			return 519+checkHigh(hand);
		return 0;
	}
	public boolean checkRoyalFlush(String hand)
	{
		if (hand.contains("Ace") && hand.contains("King") && hand.contains("Queen") && hand.contains("Jack") && hand.contains("10"))
		{
			if (checkFlush(hand)>0)
				return true;
		}
		return false;
	}
	public boolean checkFullHouse(String hand)
	{
		if (checkThreeOfAKind(hand)>0)
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
	public int checkStraightFlush(String hand)
	{
		if (checkStraight(hand)>0)
			if (checkFlush(hand)>0)
				return 622 + checkStraight(hand) - 467;
		return 0;
	}

	public String calculateWinner()
	{

		if (model.userHands.keySet().size()==0)
			return "No Winner";
		else{

			int userPoints = 0;
			HashMap<String,String[]> pointsTable = new HashMap<String, String[]>();

			for (String user : model.userHands.keySet())
			{
				//14 max for ace high
				userPoints = checkHigh(model.userHands.get(user));
				String userHand = "";
				//723 points for a royal flush
				if (checkRoyalFlush(model.userHands.get(user)))
				{
					userPoints =  723;
					userHand = "Royal Flush";
				}
				//672 points for a straight flush
				else if (checkStraightFlush(model.userHands.get(user))>0){
					userHand = "Straight Flush";
					userPoints =  checkStraightFlush(model.userHands.get(user));
				}
				//21 points for four of a kind
				else if (checkFourOfAKind(model.userHands.get(user))>0){
					userPoints = checkFourOfAKind(model.userHands.get(user));
					userHand = "Four of a Kind";
				}
				//20 points for full house
				else if (checkFullHouse(model.userHands.get(user))){
					userPoints =  570 + checkHigh(model.userHands.get(user));
					userHand = "Full House";
				}
				//19 points for flush
				else if (checkFlush(model.userHands.get(user))>0){
					userPoints =  checkFlush(model.userHands.get(user));
					userHand = "Flush";
				}
				//18 points for straight
				else if (checkStraight(model.userHands.get(user))>0){
					userPoints =  checkStraight(model.userHands.get(user));
					userHand = "Straight";
				}
				//17 points for three of a kind
				else if (checkThreeOfAKind(model.userHands.get(user))>0){
					userPoints =  checkThreeOfAKind(model.userHands.get(user));
					userHand = "Three of a Kind";
				}
				//16 points for two pair
				else if (checkTwoPair(model.userHands.get(user))>0){
					userPoints =  checkTwoPair(model.userHands.get(user));
					userHand = "Two Pair";
				}
				//15 points for pair
				else if (checkPair(model.userHands.get(user))>0) {
					userPoints =  checkPair(model.userHands.get(user));
					userHand = "Pair";
				}
				else if (checkHigh(model.userHands.get(user)) == 14){
					userHand = "Ace high";
					userPoints = 14;
				}
				else if (checkHigh(model.userHands.get(user)) == 13){
					userHand = "King high";
					userPoints = 13;
				}
				else if (checkHigh(model.userHands.get(user)) == 12){
					userHand = "Queen high";
					userPoints = 12;
				}
				else if (checkHigh(model.userHands.get(user)) == 11){
					userHand = "Jack high";
					userPoints = 11;
				}
				else {
					userHand = checkHigh(model.userHands.get(user))+" high";
					userPoints = checkHigh(model.userHands.get(user));
				}
				
				pointsTable.put(user, new String[] { userHand, userPoints+"" });
				// point system takes into account high cards.
				// a pair with ace high will beat a pair with king high.

			}


			ArrayList<String> rank = new ArrayList<String>();
			HashMap<String,String[]> pointTable = new HashMap<String,String[]>();
			for (String user: pointsTable.keySet())
			{
				pointTable.put(user,pointsTable.get(user));
			}
			//System.out.println(pointTable.size());
			while (!pointTable.isEmpty())
			{
				int topUserPoints = 0;
				for (String user: pointTable.keySet())
				{
					if (Integer.parseInt(pointTable.get(user)[1]) > topUserPoints)
					{
						topUserPoints = Integer.parseInt(pointTable.get(user)[1]);
						
					}
				}
				String topUser = "";
				for(String user : pointTable.keySet())
				{
					if (Integer.parseInt(pointTable.get(user)[1])==topUserPoints)
					{
						topUser = user;
						rank.add(user);
						break;
					}
				}
				pointTable.remove(topUser);
			}
			String outcome;
			outcome = (rank.get(0) + " won the game with " + pointsTable.get(rank.get(0))[0]);
			if (rank.size()>1)
				outcome = outcome + "\n" + (rank.get(1) + " followed with " + pointsTable.get(rank.get(1))[0]);
			if (rank.size()>2)
				outcome = outcome + "\n" + (rank.get(2) + " came in third with " + pointsTable.get(rank.get(2))[0]);

			model.phase = 1;
			model.day++;
			return outcome;
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
				client.disconnect();
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
				if (msg.contains("pass"))
				{
					if (model.phase<2)
					{
						System.out.println("No cards to return! Try : serve");
						msg = "";
					}
					else
					{
						model.phase = 3;
					}
				}
				if (msg.contains("return"))
				{
					if (model.phase<2)
					{
						System.out.println("No cards to return! Try : serve");
						msg = "";
					}
					else {
						model.phase = 3;
						String cards = msg.replace("return ", "");
						cards = cards.replace("ace", "Ace");
						cards = cards.replace("jack", "Jack");
						cards = cards.replace("queen", "Queen");
						cards = cards.replace("king", "King");
						cards = cards.replace(", ", ",");

						String delims2 = "[,]+";
						String[] hand = cards.split(delims2);
						for (String card : hand){
							if (card.contains("return"))
								card = card.replace("return ", "");
							if (model.hand.contains(card) && client.checkCard(card))
							{
								model.hand.remove(card);
								System.out.println("Removed "+ card + " from hand.");
							}
							else
							{
								System.out.println("Your hand does not contain "+card+"!");
								msg = "";
							}
						}
						if (msg.equals(""))
							System.out.println("example cards: Ace of spades, 2 of diamonds");
						else
							System.out.println("Returning cards to server");
						model.day++;
					}
				}
				client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg, model.userNames.get(0)));				

				//System.out.println("Sending ordinary message to server");


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
							String dscUsr = "";
							for (int z = 0; z<i+1; z++)
								dscUsr = dscUsr + tokens[z];
							model.userHands.remove(dscUsr);
							model.userNames.remove(dscUsr);
							model.updatedUsers.remove(dscUsr);
							System.out.println(dscUsr + " disconnected.");
						}
						else {
							//			System.out.println(msg1.getUserName() +" "+msg1.getMessage() + "\n");
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

