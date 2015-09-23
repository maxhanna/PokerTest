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
		
		assertTrue(msg.WHOISIN == 0);
		assertTrue(msg.MESSAGE == 1);
		assertTrue(msg.LOGOUT == 2);
		assertTrue(msg.VICTORY == 3);
		assertTrue(msg.CONNECT == 4);
		assertTrue(msg.NULL == -1);

		assertTrue(msg.type == ChatMessage.MESSAGE);
		assertTrue(msg.message == hand);
		assertTrue(msg.userName == "test");

		//testing Client and Game Rules
		model.userNames.add("test");
		Client client = new Client("localhost", 24442, model.userNames.get(0), model);

		hand = "2 of spades,2 of clubs,2 of hearts,2 of diamonds,3 of hearts";
		assertTrue(3 == client.checkHigh(hand));
		hand = "4 of spades,2 of clubs,2 of hearts,2 of diamonds,3 of hearts";
		assertTrue(4 == client.checkHigh(hand));
		hand = "4 of spades,5 of clubs,2 of hearts,2 of diamonds,3 of hearts";
		assertFalse(4 == client.checkHigh(hand));

		hand = "2 of spades,2 of clubs,2 of hearts,2 of diamonds,Ace of hearts";
		assertTrue(14 == client.checkHigh(hand));
		
		hand = "Ace of spades,Ace of clubs,Ace of hearts,Ace of diamonds,10 of hearts";
		assertTrue(client.checkFourOfAKind(hand));

		hand = "Ace of spades,10 of spades,2 of spades,3 of spades,4 of spades";
		assertTrue(client.checkFlush(hand));
		hand = "Ace of spades,10 of hearts,2 of spades,3 of spades,4 of spades";
		assertFalse(client.checkFlush(hand));

		hand = "4 of spades,6 of spades,2 of spades,5 of spades,3 of spades";
		assertTrue(client.checkStraightFlush(hand));
		hand = "2 of spades,3 of spades,4 of spades,5 of spades,6 of spades";
		assertTrue(client.checkStraightFlush(hand));
		hand = "7 of spades,8 of spades,9 of spades,10 of spades,Jack of spades";
		assertTrue(client.checkStraightFlush(hand));
		hand = "Queen of spades,8 of spades,9 of spades,10 of spades,Jack of spades";
		assertTrue(client.checkStraightFlush(hand));
		hand = "2 of spades,3 of spades,4 of spades,5 of spades,7 of spades";
		assertFalse(client.checkStraightFlush(hand));
		
		hand = "Ace of spades,King of spades,Queen of spades,Jack of spades,10 of spades";
		assertTrue(client.checkRoyalFlush(hand));
		hand = "Ace of hearts,King of spades,Queen of spades,Jack of spades,10 of spades";
		assertFalse(client.checkRoyalFlush(hand));
		
		
	}

}
