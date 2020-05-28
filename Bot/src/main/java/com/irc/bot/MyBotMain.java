package com.irc.bot;

public class MyBotMain {
	
	public static void main(String[] args) throws Exception {
		
		// Now start our bot up.
		MyBot bot = new MyBot();

		// Enable debugging output.
		bot.setVerbose(true);

		// Connect to the IRC server. 
		bot.connect("IRC.LUATIC.NET");

		// Join the #pircbot channel.
		bot.joinChannel("#신병교육대");	
		
	}
}