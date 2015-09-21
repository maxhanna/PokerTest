package Testing;

import static org.junit.Assert.*;

import org.junit.Test;

import Client.ClientMessage;

public class ClientMessageTest {

	@Test
	public void test() {

		ClientMessage clientTester = new ClientMessage();
		String contents;
		contents = clientTester.sendMessage("test");
		System.out.println(contents);
		assertTrue(contents.equals("test"));

		clientTester = new ClientMessage();
		contents = clientTester.sendMessage("test test");
		System.out.println(contents);
		assertTrue(contents.equals("test test"));
	}

}
