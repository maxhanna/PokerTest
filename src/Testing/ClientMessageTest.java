package Testing;

import static org.junit.Assert.*;

import org.junit.Test;

import Client.ClientMessage;

public class ClientMessageTest {

	@Test
	public void test() {

		ClientMessage clientTester = new ClientMessage();
		String contents = clientTester.sendMessage("test");
		assert(contents.equals("test"));
	}

}
