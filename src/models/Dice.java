package models;
import java.util.Random;

public class Dice{
	
	private int value;
	Random rand = new Random();
	
	public Dice(){
		roll();
	}
	
	public Dice(int newVal){
		value=newVal;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public void reset(){
		roll();
	}
	
	public int roll(){
		return rand.nextInt(6)+1;
	}
}
