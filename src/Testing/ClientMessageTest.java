package Testing;

import static org.junit.Assert.*;

import org.junit.Test;

import gui.*;
import models.*;
import Network.*;

public class ClientMessageTest {

	@Test
	public void test() {
		Server testServer = new Server(8888);
		PokerVariables variables = new PokerVariables();
		Client clientTester = new Client("localhost",8888,"test",variables);
		
		String contents;
		
		//testing server communication and return types
		
	}

}
