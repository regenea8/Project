package com.irc.bot;

public class Timer extends Thread {

	MyBot bot;
	String channel;
	String sender;
	String login;
	String hostname;
	String message;
	int second;
	
	public Timer(MyBot bot, String channel, String sender, String login, String hostname, String message, 
			int time, String flag) {
		this.bot = bot;
		this.channel = channel;
		this.sender = sender;
		this.login = login;
		this.hostname = hostname;
		this.message = message;
		
		if ("hour".equals(flag)) {
			this.second = time * (1000 * 60 * 60);
		} 
		else if ("min".equals(flag)) {
			this.second = time * (1000 * 60);
		}
		else if ("second".equals(flag)) {
			this.second = time * 1000;
		}
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
			sleep(second);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		bot.sendMessage(channel, "[" + sender + "] 말씀하신 " + (second / 1000) + "초가 지났습니다.");
	}

}
