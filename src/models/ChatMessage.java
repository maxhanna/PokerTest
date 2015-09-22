package models;
import java.io.Serializable;

public class ChatMessage implements Serializable
{
	protected static final long serialVersionUID = 1112122200L;
	public static final int WHOISIN = 0;
	public static final int MESSAGE = 1;
	public static final int LOGOUT = 2;
	public static final int VICTORY = 3;
	public static final int CONNECT = 4;
	public static final int NULL = -1;
	public int type;
	public String message;
	public String userName;
	
	public ChatMessage(int type, String message, String userName)
	{
		this.type = type;
		this.message = message;
		this.userName = userName;
	}
	public String toString()
	{
		return userName + "'s message";
	}
	public int getType()
	{
		return this.type;
	}
	public String getMessage()
	{
		return this.message;
	}
	public void setMessage(String msg)
	{
		this.message = msg;;
	}

	public String getUserName()
	{
		return this.userName;
	}
	public void setUserName(String msg)
	{
		this.userName = msg;;
	}

}
