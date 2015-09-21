package Testing;

import static org.junit.Assert.*;

import org.junit.Test;

import Client.ClientMessage;

public class ClientMessageTest {

	@Test
	public void test() {

		ClientMessage clientTester = new ClientMessage();
		String contents;
		
		//testing server communication and return types
		contents = clientTester.sendMessage("test");
		System.out.println(contents);
		assertTrue(contents.equals("test"));

		clientTester = new ClientMessage();
		contents = clientTester.sendMessage("test test");
		System.out.println(contents);
		assertTrue(contents.equals("test test"));

		clientTester = new ClientMessage();
		contents = clientTester.sendMessage("test\\/test");
		System.out.println(contents);
		assertTrue(contents.equals("test\\/test"));

		// testing some of the more specific server commands
		clientTester = new ClientMessage();
		contents = clientTester.sendMessage("join");
		System.out.println(contents);
		assertTrue(contents.contains("take card"));
	}

}
