package Testing;

import static org.junit.Assert.*;

import org.junit.Test;

import gui.*;
import models.*;
import Network.*;

public class ClientTest {

	@Test
	public void test() {

		PokerVariables variables = new PokerVariables();

		assertTrue(variables.phase == 1);
		assertTrue(variables.start == 0);
		assertTrue(variables.play == 0);
		
		//testing server communication and return types
		
	}

}
