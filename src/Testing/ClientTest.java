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

		//testing server communication and return types
		
	}

}
