package OfficeHours;

import models.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class LinkRoom {

	private Vector<ServerThread> serverThreads;
	private ServerSocket ss;
	private String link;

	public LinkRoom(int port, String link)
	{
		try
		{
			//System.out.println("Binding to port " + port);
			ServerSocket ss = new ServerSocket(port);
			serverThreads = new Vector<ServerThread>();
			while(true) // while OH is open
			{
				Socket s = ss.accept();   //  Accept the incoming request
				System.out.println("Connection from " + s + " at " + new Date());
				// each ClientThread creates a ServerThread which serves this connection
				ServerThread st = new ServerThread(s, this);
				//System.out.println("Inviting this student to meeting...");
				serverThreads.add(st);
			}
		}
		catch (Exception ex) {}

	}

	public void sendLink()
	{
		//System.out.println("Sending link ..." + link);
		ServerThread newThread = serverThreads.lastElement();
		newThread.sendMessage(link);
	}
	
//	public void sendMessage(String message)
//	{
//		ServerThread newThread = serverThreads.lastElement();
//		newThread.sendMessage(message);
//	}
}


