package models;

import java.io.Serializable;
import java.util.ArrayList;

public class PokerVariables implements Serializable {
	protected static final long serialVersionUID = 1112122330L;
	public boolean cheatMode = false;
	public int follow = 0;
	public int start = 0;
	public int play = 0;
	public ArrayList<String> userNames = new ArrayList<String>();
	public ArrayList<String> updatedUsers = new ArrayList<String>();
	public ArrayList<String> actions = new ArrayList<String>();
	public ArrayList<String> hand = new ArrayList<String>();
	public int phase;
	public int day;

	public PokerVariables() {
		userNames = new ArrayList<String>();
		updatedUsers = new ArrayList<String>();
		actions = new ArrayList<String>();
		hand = new ArrayList<String>();
		phase = 1;
		day = 1;

	}

	public int getPlay() {
		return play;
	}


}
