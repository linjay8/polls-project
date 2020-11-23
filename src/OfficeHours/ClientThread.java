package OfficeHours;

import models.*;
import java.io.*;
import java.net.*;
import java.time.ZonedDateTime;
import java.util.*;

public class ClientThread extends Thread
{
	private BufferedReader din;
	private Socket s;
	private String link;
	private Student student;

	public ClientThread(String hostname, int port) //Student student, 
	{
		//this.student = student;
		try
		{
			s = new Socket(hostname, port);
			din = new BufferedReader(new InputStreamReader(s.getInputStream()));
			start();
		}
		catch (IOException ex) {}
	}
	
	public String getLink()
	{
		return link;
	}

	public void run()
	{
		while (true)
		{
			try {
				link = din.readLine();
				System.out.println(" Received link from server: " + link); //ZonedDateTime.now(student.getTimezone()) +
				din.close();
				s.close();
				break;
			}
			catch (IOException ex) {}
		}
		
	}
	
	public static void main (String [] args)
	{
		new ClientThread("localhost", 6789);
	}
}
