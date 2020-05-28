package com.irc.bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBot extends PircBot {

	Logger logger = LoggerFactory.getLogger(MyBot.class);
	public static HashMap<String, String> map;
	
	public MyBot() {
		this.setName("[신병교육대]집사");
		map = new HashMap<String, String>();
		List<String> list = readFile();
		for (int i = 0; i < list.size(); i++) {
			save(list.get(i));
		}		
	}

	public void onMessage(String channel, String sender,
			String login, String hostname, String message) {
		
		logger.info(sender + " : " + message);
		
		String[] arrMsg = message.split(" ");
		
		if ("!기억".equals(arrMsg[0])) {
			save(message);
			makeFile(message);
			sendMessage(channel, /*sender + */ " [" + arrMsg[1] + "] 을(를) 기억하였습니다.");
		}
		else if ("!알려".equals(arrMsg[0])) {
			String content = map.get(arrMsg[1]);
			sendMessage(channel, /* sender + */ "[" + arrMsg[1] + "] " + content);
		}
		else if ("!목록".equals(arrMsg[0])) {
			String msg = "";
			Iterator<String> keys = map.keySet().iterator();
	        while( keys.hasNext() ){
	            String key = keys.next();
	            msg = msg + key + ", ";
	        }
	        sendMessage(channel, "[목록] " + msg);
		}
		else if ("!시".equals(arrMsg[0])) {
			int hour = 0;
			try {
				hour = Integer.parseInt(arrMsg[1]);
				sendMessage(channel, "[시] " + hour + "시간 후 알려드리겠습니다.");
				Timer timer = new Timer(this, channel, sender, login, hostname, message, hour, "hour");
				timer.start();
			} catch (Exception e) {
				e.printStackTrace();
				sendMessage(channel, "[시] 시간을 정확히 입력해주세요.");
			}
		}
		else if ("!분".equals(arrMsg[0])) {
			int min = 0;
			try {
				min = Integer.parseInt(arrMsg[1]);
				sendMessage(channel, "[분] " + min + "분 후 알려드리겠습니다.");
				Timer timer = new Timer(this, channel, sender, login, hostname, message, min, "min");
				timer.start();
			} catch (Exception e) {
				e.printStackTrace();
				sendMessage(channel, "[분] 분을 정확히 입력해주세요.");
			}
		}
		else if ("!초".equals(arrMsg[0])) {
			int second = 0;
			try {
				second = Integer.parseInt(arrMsg[1]);
				sendMessage(channel, "[초] " + second + "초 후 알려드리겠습니다.");
				Timer timer = new Timer(this, channel, sender, login, hostname, message, second, "second");
				timer.start();
			} catch (Exception e) {
				e.printStackTrace();
				sendMessage(channel, "[초] 초를 정확히 입력해주세요.");
			}
		}
		else if (message.indexOf("@") == 0) {
			String name = message.substring(1, 3);
			User[] users = getUsers(channel);
			for (int i = 0; i < users.length; i++) {
				String joinNick = users[i].getNick();
				if (joinNick.indexOf(name) != -1) {
					sendMessage(channel, joinNick);
					return;
				}
			}
			sendMessage(channel, "해당 닉네임은 접속하지 않았습니다.");
		}

		/*
		if (message.equalsIgnoreCase("time")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is now " + time);
        }
		 */
	}

	public static void save(String msg) {
		String[] arrLine = msg.split(" ");
		String content = "";
		for (int i = 2; i < arrLine.length; i++) {
			content = content + arrLine[i] + " ";
		}
		System.out.println("key=" + arrLine[1] + ", value=" + content);
		map.put(arrLine[1], content);
	}
	
	public static void makeFile(String text) {

		//String fileName = "C:/logs/seres/memory.log";
		String fileName = "/home/opc/irc/memory.log";
		
		try{

			// BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
			BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));

			// 엔터
			fw.newLine();
			
			// 파일안에 문자열 쓰기
			fw.write(text);
			fw.flush();

			// 객체 닫기
			fw.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static List<String> readFile() {

		List<String> list = new ArrayList<String>(); 

		try{
            //파일 객체 생성
            //File file = new File("C:/logs/seres/memory.log");
			File file = new File("/home/opc/irc/memory.log");
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null){
                list.add(line);
            }
            //.readLine()은 끝에 개행문자를 읽지 않는다.            
            bufReader.close();
        }catch (FileNotFoundException e) {
            // TODO: handle exception
        }catch(IOException e){
            System.out.println(e);
        }

		return list;
	}
}