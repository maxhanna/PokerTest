package Testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import gui.*;
import models.*;
import Network.*;

public class ClientTest {

	@Test
	public void test() {

		//creating necessary files to run client code

		//testing PokerVariables
		PokerVariables model = new PokerVariables();

		assertTrue(model.phase == 1);
		assertTrue(model.day == 1);
		assertTrue(model.start == 0);
		assertTrue(model.play == 0);
		assertTrue(model.userNames.isEmpty());
		assertTrue(model.updatedUsers.isEmpty());
		assertTrue(model.actions.isEmpty());
		assertTrue(model.hand.isEmpty());
		assertTrue(model.userHands.isEmpty());

		//testing ClientMessage
		String hand = "Ace of spades,Ace of clubs,Ace of hearts,Ace of diamonds,10 of hearts";
		ChatMessage msg = new ChatMessage(ChatMessage.MESSAGE,hand,"test");

		assert(msg.WHOISIN == 0);
		assertTrue(msg.MESSAGE == 1);
		assertTrue(msg.LOGOUT == 2);
		assertTrue(msg.VICTORY == 3);
		assertTrue(msg.CONNECT == 4);
		assertTrue(msg.NULL == -1);

		assertTrue(msg.type == ChatMessage.MESSAGE);
		assertTrue(msg.message == hand);
		assertTrue(msg.userName == "test");

		//testing Client and Game Rules

		//create client for the user, give it test as username
		model.userNames.add("test");
		assert(model.userNames.contains("test"));

		model.userNames.add("Unknown");
		assert(model.userNames.contains("Unknown"));

		Client client = new Client("localhost", 24884, model.userNames.get(0), model);
		//check if client has a user named test in memory.
		assert(client.model.userNames.contains("test"));
		assert(client.model.userNames.contains("Unknown"));

		//testing game rules
		hand = "2 of spades,2 of clubs,2 of hearts,2 of diamonds,3 of hearts";
		assertTrue(3 == client.checkHigh(hand));
		hand = "4 of spades,2 of clubs,2 of hearts,2 of diamonds,3 of hearts";
		assertTrue(4 == client.checkHigh(hand));
		hand = "4 of spades,5 of clubs,2 of hearts,2 of diamonds,3 of hearts";
		assertFalse(4 == client.checkHigh(hand));
		hand = "8 of spades,5 of clubs,2 of hearts,2 of diamonds,3 of hearts";
		assertTrue(8 == client.checkHigh(hand));
		hand = "9 of spades,5 of clubs,2 of hearts,6 of diamonds,3 of hearts";
		assertTrue(9 == client.checkHigh(hand));
		hand = "10 of spades,5 of clubs,2 of hearts,2 of diamonds,3 of hearts";
		assertTrue(10 == client.checkHigh(hand));
		hand = "Jack of spades,5 of clubs,2 of hearts,2 of diamonds,3 of hearts";
		assertTrue(11 == client.checkHigh(hand));
		hand = "8 of spades,5 of clubs,Queen of hearts,2 of diamonds,3 of hearts";
		assertTrue(12 == client.checkHigh(hand));
		hand = "8 of spades,5 of clubs,2 of hearts,King of diamonds,3 of hearts";
		assertTrue(13 == client.checkHigh(hand));
		hand = "Ace of spades,5 of clubs,2 of hearts,2 of diamonds,3 of hearts";
		assertTrue(14 == client.checkHigh(hand));
		hand = "2 of spades,2 of clubs,2 of hearts,2 of diamonds,Ace of hearts";
		assertTrue(14 == client.checkHigh(hand));

		hand = "Ace of spades,Ace of clubs,Ace of hearts,Ace of diamonds,10 of hearts";
		assertTrue(client.checkPair(hand)>0);
		hand = "Ace of spades,Ace of clubs,Queen of hearts,Queen of diamonds,10 of hearts";
		assertTrue(client.checkPair(hand)>0);
		hand = "Ace of spades,Ace of clubs,Jack of hearts,3 of diamonds,10 of hearts";
		assertTrue(client.checkPair(hand)>0);
		hand = "Ace of spades,Jack of clubs,10 of hearts,9 of diamonds,8 of hearts";
		assertFalse(client.checkPair(hand)>0);
		hand = "Ace of spades,9 of clubs,3 of hearts,5 of diamonds,Ace of hearts";
		assertTrue(client.checkPair(hand)>0);

		hand = "Ace of spades,Ace of clubs,Ace of hearts,Ace of diamonds,10 of hearts";
		assertFalse(client.checkTwoPair(hand)>0);
		hand = "Ace of spades,Ace of clubs,10 of diamonds,Jack of diamonds,10 of hearts";
		assertTrue(client.checkTwoPair(hand)>0);
		hand = "2 of spades,10 of clubs,10 of diamonds,Jack of diamonds,2 of hearts";
		assertTrue(client.checkTwoPair(hand)>0);
		hand = "2 of spades,Ace of clubs,10 of diamonds,Jack of diamonds,2 of hearts";
		assertFalse(client.checkTwoPair(hand)>0);

		hand = "2 of spades,Ace of clubs,10 of diamonds,Jack of diamonds,2 of hearts";
		assertFalse(client.checkThreeOfAKind(hand)>0);
		hand = "2 of spades,Ace of clubs,2 of diamonds,Jack of diamonds,2 of hearts";
		assertTrue(client.checkThreeOfAKind(hand)>0);
		hand = "2 of spades,2 of clubs,2 of diamonds,Jack of diamonds,2 of hearts";
		assertTrue(client.checkThreeOfAKind(hand)>0);
		hand = "Ace of spades,Ace of clubs,Ace of diamonds,Jack of diamonds,2 of hearts";
		assertTrue(client.checkThreeOfAKind(hand)>0);

		hand = "2 of spades,Ace of clubs,10 of diamonds,Jack of diamonds,2 of hearts";
		assertFalse(client.checkFourOfAKind(hand)>0);
		hand = "2 of spades,2 of clubs,2 of diamonds,Jack of diamonds,2 of hearts";
		assertTrue(client.checkFourOfAKind(hand)>0);
		hand = "King of spades,King of clubs,King of diamonds,Jack of diamonds,King of hearts";
		assertTrue(client.checkFourOfAKind(hand)>0);
		hand = "Ace of spades,Ace of clubs,Ace of diamonds,Jack of diamonds,Ace of hearts";
		assertTrue(client.checkFourOfAKind(hand)>0);

		hand = "Ace of spades,Ace of clubs,Ace of hearts,Ace of diamonds,10 of hearts";
		assertTrue(client.checkFourOfAKind(hand)>0);
		hand = "Ace of spades,4 of clubs,Ace of hearts,Ace of diamonds,Ace of clubs";
		assertTrue(client.checkFourOfAKind(hand)>0);
		hand = "2 of spades,2 of clubs,2 of hearts,2 of diamonds,10 of hearts";
		assertTrue(client.checkFourOfAKind(hand)>0);

		hand = "Ace of spades,10 of spades,2 of spades,3 of spades,4 of spades";
		assertTrue(client.checkFlush(hand)>0);
		hand = "King of spades,Ace of spades,Jack of spades,3 of spades,4 of spades";
		assertTrue(client.checkFlush(hand)>0);
		hand = "Ace of spades,10 of hearts,2 of spades,3 of spades,4 of spades";
		assertFalse(client.checkFlush(hand)>0);

		hand = "Ace of spades,2 of spades,3 of spades,4 of spades,5 of spades";
		assertTrue(client.checkStraight(hand)>0);
		hand = "5 of spades,7 of hearts,6 of spades,8 of spades,9 of hearts";
		assertTrue(client.checkStraight(hand)>0);
		hand = "Ace of spades,King of hearts,Queen of spades,Jack of spades,10 of hearts";
		assertTrue(client.checkStraight(hand)>0);
		hand = "Ace of spades,2 of hearts,3 of spades,4 of spades,6 of hearts";
		assertFalse(client.checkStraight(hand)>0);


		hand = "Ace of spades,2 of hearts,3 of spades,4 of spades,6 of hearts";
		assertFalse(client.checkFullHouse(hand));
		hand = "Ace of spades,5 of hearts,6 of spades,6 of clubs,6 of hearts";
		assertFalse(client.checkFullHouse(hand));
		hand = "Ace of spades,Ace of hearts,6 of spades,6 of clubs,6 of hearts";
		assertTrue(client.checkFullHouse(hand));
		hand = "Ace of spades,6 of clubs,6 of spades,Ace of clubs,6 of hearts";
		assertTrue(client.checkFullHouse(hand));
		hand = "King of spades,6 of clubs,6 of spades,King of clubs,6 of hearts";
		assertTrue(client.checkFullHouse(hand));
		hand = "10 of spades,6 of clubs,6 of spades,10 of clubs,6 of hearts";
		assertTrue(client.checkFullHouse(hand));

		hand = "Ace of spades,3 of spades,4 of spades,5 of spades,2 of spades";
		assertTrue(client.checkStraightFlush(hand)>0);
		hand = "4 of spades,6 of spades,2 of spades,5 of spades,3 of spades";
		assertTrue(client.checkStraightFlush(hand)>0);
		hand = "2 of spades,3 of spades,4 of spades,5 of spades,6 of spades";
		assertTrue(client.checkStraightFlush(hand)>0);
		hand = "7 of spades,8 of spades,9 of spades,10 of spades,Jack of spades";
		assertTrue(client.checkStraightFlush(hand)>0);
		hand = "Queen of spades,8 of spades,9 of spades,10 of spades,Jack of spades";
		assertTrue(client.checkStraightFlush(hand)>0);
		hand = "2 of spades,3 of spades,4 of spades,5 of spades,7 of spades";
		assertFalse(client.checkStraightFlush(hand)>0);
		hand = "2 of spades,4 of spades,6 of spades,5 of spades,7 of spades";
		assertFalse(client.checkStraightFlush(hand)>0);

		hand = "Ace of spades,King of spades,Queen of spades,Jack of spades,10 of spades";
		assertTrue(client.checkRoyalFlush(hand));
		hand = "Ace of hearts,King of hearts,Queen of hearts,Jack of hearts,10 of hearts";
		assertTrue(client.checkRoyalFlush(hand));
		hand = "Ace of hearts,King of spades,Queen of spades,Jack of spades,10 of spades";
		assertFalse(client.checkRoyalFlush(hand));
		hand = "Ace of spades,King of spades,Queen of spades,Jack of spades,9 of spades";
		assertFalse(client.checkRoyalFlush(hand));
		hand = "Ace of spades,9 of spades,Queen of spades,Jack of spades,10 of spades";
		assertFalse(client.checkRoyalFlush(hand));

		//testing Client.calculateWinner();
		//creating 3 hands
		String hand1 = "Ace of hearts,King of hearts,Queen of hearts,Jack of hearts,10 of hearts";
		String hand2 = "2 of spades,3 of spades,4 of spades,5 of spades,6 of spades";
		String hand3 = "2 of spades,10 of spades,4 of spades,5 of spades,Ace of spades";
		String hand4 = "2 of clubs,10 of hearts,3 of clubs,5 of clubs,7 of spades";
		assertTrue(client.checkRoyalFlush(hand1));
		assertTrue(client.checkStraightFlush(hand2)>0);
		assertTrue(client.checkFlush(hand3)>0);
		assertTrue(client.checkHigh(hand4) == 10);

		client.model.userHands.put("test",hand1);
		assert(client.model.userHands.containsKey("test"));

		//create test2 user
		client.model.userNames.add("test2");
		assert(client.model.userNames.contains("test2"));

		//create test2's hand
		client.model.userHands.put("test2", hand2);
		assertTrue(client.model.userHands.containsKey("test2"));
		assertTrue(client.model.userHands.get("test2").equals(hand2));
		assertTrue(client.model.userHands.get("test").equals(hand1));
		//testing calculateWinner() with 2 contestants
		assertTrue(client.calculateWinner().contains("test won the game with Royal Flush"));
		assertTrue(client.calculateWinner().contains("test2 followed with Straight Flush"));

		//testing calculateWinner() with 3 contestants
		client.model.userHands.put("test3",hand3);
		//	System.out.println(client.calculateWinner());

		assertTrue(client.model.userHands.containsKey("test3"));
		assertTrue(client.calculateWinner().contains("test3 came in third with Flush"));

		//create test4 user
		client.model.userNames.add("test4");
		assert(client.model.userNames.contains("test4"));
		client.model.userHands.put("test4", hand4);
		assertTrue(client.model.userHands.containsKey("test4"));
		assertTrue(client.model.userHands.get("test4").contains(hand4));
		assertFalse(client.calculateWinner().contains("test4"));


		//System.out.println(client.calculateWinner());

		client.model.userHands.clear();
		hand3 = "8 of clubs,8 of spades,8 of hearts,8 of diamonds,10 of clubs";
		hand4 = "7 of clubs,7 of spades,2 of hearts,3 of spades,10 of clubs";
		hand2 = "King of clubs,King of spades,King of diamonds,3 of hearts,10 of clubs";
		hand1 = "Ace of clubs,Ace of spades,2 of spades,3 of hearts,10 of clubs";

		client.model.userHands.put("test1",hand1);
		client.model.userHands.put("test2",hand2);
		client.model.userHands.put("test3",hand3);
		client.model.userHands.put("test4",hand4);
		//System.out.println(client.calculateWinner());

		assertTrue(client.calculateWinner().contains("test3 won the game with Four of a Kind"));
		assertTrue(client.calculateWinner().contains("test2 followed with Three of a Kind"));
		assertTrue(client.calculateWinner().contains("test1 came in third with Pair"));

		client.model.userHands.clear();
		hand3 = "8 of clubs,8 of spades,2 of spades,3 of hearts,10 of clubs";
		hand4 = "7 of clubs,7 of spades,2 of hearts,3 of spades,10 of clubs";
		hand2 = "King of clubs,King of spades,2 of spades,3 of hearts,10 of clubs";
		hand1 = "Ace of clubs,Ace of spades,2 of spades,3 of hearts,10 of clubs";


		client.model.userHands.put("test1",hand1);
		client.model.userHands.put("test2",hand2);
		client.model.userHands.put("test3",hand3);
		client.model.userHands.put("test4",hand4);
		//System.out.println(client.calculateWinner());
		assertTrue(client.calculateWinner().contains("test1 won the game with Pair"));
		assertTrue(client.calculateWinner().contains("test2 followed with Pair"));
		assertTrue(client.calculateWinner().contains("test3 came in third with Pair"));

		client.model.userHands.clear();
		hand1 = "8 of clubs,8 of spades,Ace of hearts,Ace of diamonds,10 of clubs";
		hand2 = "7 of clubs,7 of spades,2 of hearts,2 of spades,10 of clubs";
		hand3 = "King of clubs,King of spades,Queen of diamonds,Queen of hearts,10 of clubs";
		hand4 = "10 of clubs,Jack of spades,Jack of spades,Ace of hearts,Ace of clubs";

		assertTrue(client.checkTwoPair(hand3)>0);
		client.model.userHands.put("test1",hand1);
		client.model.userHands.put("test2",hand2);
		client.model.userHands.put("test3",hand3);
		client.model.userHands.put("test4",hand4);
		//System.out.println(client.calculateWinner());


		assertTrue(client.calculateWinner().contains("test4 won the game with Two Pair"));
		assertTrue(client.calculateWinner().contains("test1 followed with Two Pair"));
		assertTrue(client.calculateWinner().contains("test3 came in third with Two Pair"));

		client.model.userHands.clear();
		hand1 = "Ace of clubs,Ace of spades,Ace of hearts,Ace of diamonds,10 of clubs";
		hand2 = "Ace of clubs,Ace of spades,Ace of hearts,2 of spades,10 of clubs";
		hand3 = "King of clubs,King of spades,King of diamonds,Queen of hearts,10 of clubs";
		hand4 = "Jack of clubs,Jack of spades,Jack of spades,Ace of hearts,Ace of clubs";

		assertTrue(client.checkThreeOfAKind(hand3)>0);
		client.model.userHands.put("test1",hand1);
		client.model.userHands.put("test2",hand2);
		client.model.userHands.put("test3",hand3);
		client.model.userHands.put("test4",hand4);
		//System.out.println(client.calculateWinner());


		assertTrue(client.calculateWinner().contains("test1 won the game with Four of a Kind"));
		assertTrue(client.calculateWinner().contains("test4 followed with Full House"));
		assertTrue(client.calculateWinner().contains("test2 came in third with Three of a Kind"));
		client.model.userHands.clear();
		hand1 = "Ace of clubs,Ace of spades,Ace of hearts,3 of diamonds,10 of clubs";
		hand2 = "Queen of clubs,Queen of spades,Queen of hearts,2 of spades,10 of clubs";
		hand3 = "King of clubs,King of spades,King of diamonds,Queen of hearts,10 of clubs";
		hand4 = "Jack of clubs,Jack of spades,Jack of spades,10 of hearts,Ace of clubs";

		assertTrue(client.checkThreeOfAKind(hand3)>0);
		client.model.userHands.put("test1",hand1);
		client.model.userHands.put("test2",hand2);
		client.model.userHands.put("test3",hand3);
		client.model.userHands.put("test4",hand4);
		//System.out.println(client.calculateWinner());


		assertTrue(client.calculateWinner().contains("test1 won the game with Three of a Kind"));
		assertTrue(client.calculateWinner().contains("test3 followed with Three of a Kind"));
		assertTrue(client.calculateWinner().contains("test2 came in third with Three of a Kind"));
		client.model.userHands.clear();

		hand2 = "Ace of clubs,Ace of spades,2 of hearts,3 of diamonds,10 of clubs";
		hand1 = "Queen of clubs,Queen of spades,2 of hearts,5 of spades,10 of clubs";
		hand3 = "King of clubs,King of spades,King of diamonds,Queen of hearts,10 of clubs";

		assertTrue(client.checkThreeOfAKind(hand3)>0);
		client.model.userHands.put("test1",hand1);
		client.model.userHands.put("test2",hand2);
		client.model.userHands.put("test3",hand3);
		//System.out.println(client.calculateWinner());


		assertTrue(client.calculateWinner().contains("test3 won the game with Three of a Kind"));
		assertTrue(client.calculateWinner().contains("test2 followed with Pair"));
		assertTrue(client.calculateWinner().contains("test1 came in third with Pair"));
		client.model.userHands.clear();

		hand1 = "King of clubs,Queen of clubs,2 of clubs,3 of clubs,10 of clubs";
		hand2 = "Ace of spades,Jack of spades,2 of spades,5 of spades,10 of spades";
		hand3 = "King of clubs,King of spades,King of diamonds,Queen of hearts,10 of clubs";

		assertTrue(client.checkThreeOfAKind(hand3)>0);
		client.model.userHands.put("test1",hand1);
		client.model.userHands.put("test2",hand2);
		client.model.userHands.put("test3",hand3);
		//System.out.println(client.calculateWinner());


		assertTrue(client.calculateWinner().contains("test2 won the game with Flush"));
		assertTrue(client.calculateWinner().contains("test1 followed with Flush"));
		assertTrue(client.calculateWinner().contains("test3 came in third with Three of a Kind"));
		client.model.userHands.clear();

		hand1 = "King of spades,Queen of clubs,Jack of spades,10 of clubs,9 of clubs";
		hand2 = "Ace of spades,Queen of spades,King of spades,Jack of hearts,10 of hearts";
		hand3 = "10 of clubs,9 of spades,8 of diamonds,6 of hearts,7 of clubs";

		assertTrue(client.checkStraight(hand3)>0);
		client.model.userHands.put("test1",hand1);
		client.model.userHands.put("test2",hand2);
		client.model.userHands.put("test3",hand3);
		System.out.println(client.calculateWinner());


		assertTrue(client.calculateWinner().contains("test2 won the game with Straight"));
		assertTrue(client.calculateWinner().contains("test1 followed with Straight"));
		assertTrue(client.calculateWinner().contains("test3 came in third with Straight"));
		client.model.userHands.clear();

		hand1 = "4 of spades,5 of spades,6 of spades,7 of spades,8 of spades";
		hand2 = "5 of spades,6 of spades,7 of spades,8 of spades,9 of spades";
		hand3 = "9 of diamonds,8 of diamonds,10 of diamonds,Jack of diamonds,7 of diamonds";
		hand4 = "3 of diamonds,4 of diamonds,5 of diamonds,6 of diamonds,7 of diamonds";

		assertTrue(client.checkStraight(hand3)>0);
		client.model.userHands.put("test1",hand1);
		client.model.userHands.put("test2",hand2);
		client.model.userHands.put("test3",hand3);
		client.model.userHands.put("test4",hand4);
		System.out.println(client.calculateWinner());


		assertTrue(client.calculateWinner().contains("test3 won the game with Straight Flush"));
		assertTrue(client.calculateWinner().contains("test2 followed with Straight Flush"));
		assertTrue(client.calculateWinner().contains("test1 came in third with Straight Flush"));
		client.model.userHands.clear();

		//Testing client.checkCard, a function which checks if a card entered is valid.
		String card = "2 of spades";
		assertTrue(client.checkCard(card));
		card = "Ace of spades";
		assertTrue(client.checkCard(card));
		card = "Ace of Spades";
		assertFalse(client.checkCard(card));
		card = "2 of spadess";
		assertFalse(client.checkCard(card));
		card = "2 ofspades";
		assertFalse(client.checkCard(card));
		card = "2ofspades";
		assertFalse(client.checkCard(card));
		card = "2 ofspades";
		assertFalse(client.checkCard(card));
		
		//Test game progression
		assertTrue(client.model.day>1);
		assertTrue(client.model.phase == 1);

		//Testing Server functions
		Server server = new Server(24884);

		ArrayList<String> deck = new ArrayList<String>();
		server.createDeck(deck);
		System.out.println(deck.toString());
		assertTrue(deck.size()==52);
		assertTrue(deck.contains("Ace of spades"));
		assertTrue(deck.contains("Ace of hearts"));
		assertTrue(deck.contains("Ace of clubs"));
		assertTrue(deck.contains("Ace of diamonds"));
		assertTrue(deck.contains("King of spades"));
		assertTrue(deck.contains("King of hearts"));
		assertTrue(deck.contains("King of clubs"));
		assertTrue(deck.contains("King of diamonds"));
		assertTrue(deck.contains("Queen of spades"));
		assertTrue(deck.contains("Queen of hearts"));
		assertTrue(deck.contains("Queen of clubs"));
		assertTrue(deck.contains("Queen of diamonds"));
		assertTrue(deck.contains("Jack of spades"));
		assertTrue(deck.contains("Jack of hearts"));
		assertTrue(deck.contains("Jack of clubs"));
		assertTrue(deck.contains("Jack of diamonds"));
		assertTrue(deck.contains("10 of spades"));
		assertTrue(deck.contains("10 of hearts"));
		assertTrue(deck.contains("10 of clubs"));
		assertTrue(deck.contains("10 of diamonds"));
		assertTrue(deck.contains("9 of spades"));
		assertTrue(deck.contains("9 of hearts"));
		assertTrue(deck.contains("9 of clubs"));
		assertTrue(deck.contains("9 of diamonds"));
		assertTrue(deck.contains("8 of spades"));
		assertTrue(deck.contains("8 of hearts"));
		assertTrue(deck.contains("8 of clubs"));
		assertTrue(deck.contains("8 of diamonds"));
		assertTrue(deck.contains("7 of spades"));
		assertTrue(deck.contains("7 of hearts"));
		assertTrue(deck.contains("7 of clubs"));
		assertTrue(deck.contains("7 of diamonds"));
		assertTrue(deck.contains("6 of spades"));
		assertTrue(deck.contains("6 of hearts"));
		assertTrue(deck.contains("6 of clubs"));
		assertTrue(deck.contains("6 of diamonds"));
		assertTrue(deck.contains("5 of spades"));
		assertTrue(deck.contains("5 of hearts"));
		assertTrue(deck.contains("5 of clubs"));
		assertTrue(deck.contains("5 of diamonds"));
		assertTrue(deck.contains("4 of spades"));
		assertTrue(deck.contains("4 of hearts"));
		assertTrue(deck.contains("4 of clubs"));
		assertTrue(deck.contains("4 of diamonds"));
		assertTrue(deck.contains("3 of spades"));
		assertTrue(deck.contains("3 of hearts"));
		assertTrue(deck.contains("3 of clubs"));
		assertTrue(deck.contains("3 of diamonds"));
		assertTrue(deck.contains("2 of spades"));
		assertTrue(deck.contains("2 of hearts"));
		assertTrue(deck.contains("2 of clubs"));
		assertTrue(deck.contains("2 of diamonds"));
		assertTrue(deck.contains("2 of spades"));
		assertTrue(deck.contains("2 of hearts"));
		assertTrue(deck.contains("2 of clubs"));
		assertTrue(deck.contains("2 of diamonds"));


		server.createDeck(deck);
		//System.out.println(deck.size());
		assertTrue(deck.size()==52);

		server.shuffleDeck(deck);
		assertTrue(deck.size()==52);

		assertTrue(deck.contains("Ace of spades"));
		assertTrue(deck.contains("Ace of hearts"));
		assertTrue(deck.contains("Ace of clubs"));
		assertTrue(deck.contains("Ace of diamonds"));
		assertTrue(deck.contains("King of spades"));
		assertTrue(deck.contains("King of hearts"));
		assertTrue(deck.contains("King of clubs"));
		assertTrue(deck.contains("King of diamonds"));
		assertTrue(deck.contains("Queen of spades"));
		assertTrue(deck.contains("Queen of hearts"));
		assertTrue(deck.contains("Queen of clubs"));
		assertTrue(deck.contains("Queen of diamonds"));
		assertTrue(deck.contains("Jack of spades"));
		assertTrue(deck.contains("Jack of hearts"));
		assertTrue(deck.contains("Jack of clubs"));
		assertTrue(deck.contains("Jack of diamonds"));
		assertTrue(deck.contains("10 of spades"));
		assertTrue(deck.contains("10 of hearts"));
		assertTrue(deck.contains("10 of clubs"));
		assertTrue(deck.contains("10 of diamonds"));
		assertTrue(deck.contains("9 of spades"));
		assertTrue(deck.contains("9 of hearts"));
		assertTrue(deck.contains("9 of clubs"));
		assertTrue(deck.contains("9 of diamonds"));
		assertTrue(deck.contains("8 of spades"));
		assertTrue(deck.contains("8 of hearts"));
		assertTrue(deck.contains("8 of clubs"));
		assertTrue(deck.contains("8 of diamonds"));
		assertTrue(deck.contains("7 of spades"));
		assertTrue(deck.contains("7 of hearts"));
		assertTrue(deck.contains("7 of clubs"));
		assertTrue(deck.contains("7 of diamonds"));
		assertTrue(deck.contains("6 of spades"));
		assertTrue(deck.contains("6 of hearts"));
		assertTrue(deck.contains("6 of clubs"));
		assertTrue(deck.contains("6 of diamonds"));
		assertTrue(deck.contains("5 of spades"));
		assertTrue(deck.contains("5 of hearts"));
		assertTrue(deck.contains("5 of clubs"));
		assertTrue(deck.contains("5 of diamonds"));
		assertTrue(deck.contains("4 of spades"));
		assertTrue(deck.contains("4 of hearts"));
		assertTrue(deck.contains("4 of clubs"));
		assertTrue(deck.contains("4 of diamonds"));
		assertTrue(deck.contains("3 of spades"));
		assertTrue(deck.contains("3 of hearts"));
		assertTrue(deck.contains("3 of clubs"));
		assertTrue(deck.contains("3 of diamonds"));
		assertTrue(deck.contains("2 of spades"));
		assertTrue(deck.contains("2 of hearts"));
		assertTrue(deck.contains("2 of clubs"));
		assertTrue(deck.contains("2 of diamonds"));
		assertTrue(deck.contains("2 of spades"));
		assertTrue(deck.contains("2 of hearts"));
		assertTrue(deck.contains("2 of clubs"));
		assertTrue(deck.contains("2 of diamonds"));
		System.out.println(deck.toString());

		assertFalse(server.returnCards("2 of spades", deck));
		deck.remove("2 of spades");
		assertTrue(deck.size()==51);
		assertFalse(deck.contains("2 of spades"));
		assertTrue(server.returnCards("2 of spades", deck));
		assertTrue(deck.size()==52);
		assertTrue(deck.contains("2 of spades"));
		server.serveCards(deck);
		assertTrue(deck.size()==47);
		server.serveCards(deck);
		assertTrue(deck.size()==42);
		server.createDeck(deck);
		assertTrue(deck.size()==52);

		server.takeCards(2, deck);
		assertTrue(deck.size()==50);
		server.shuffleDeck(deck);
		assertTrue(deck.size()==50);
		server.takeCards(5, deck);
		assertTrue(deck.size()==45);
		server.takeCards(10, deck);
		assertTrue(deck.size()==40);
		server.takeCards(-10, deck);
		assertTrue(deck.size()==40);

	}

}
