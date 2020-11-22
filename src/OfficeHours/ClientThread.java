package OfficeHours;

import models.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread extends Thread
{
	private BufferedReader din;
	//private PrintWriter dout;
	//private String name;
	private Socket s;
	private String link;
	private Student student;

	public ClientThread(Student student, String hostname, int port)
	{
		this.student = student;
		try
		{
			s = new Socket(hostname, port);
			din = new BufferedReader(new InputStreamReader(s.getInputStream()));
//			dout = new PrintWriter(s.getOutputStream(), true);
			start();
//			Scanner scan = new Scanner(System.in);
//			name = student.getFullName();
//			while(student.getInMeetingStatus()) { //while student is in meeting
//				String line = scan.nextLine();
//				dout.println(name +": " + line);
//			}
//			dout.close();
			Thread.sleep(5000);
		}
		catch (IOException ex) {}
		finally
		{
			try {
				din.close();
				s.close();
			} catch (IOException e) {}
		}
	}
	
	public String getLink()
	{
		return link;
	}

	public void run()
	{
		try {
			link = din.readLine();
			student.getClientLink(link);
			//System.out.println(line);
		}
		catch (IOException ex) {}
	}
}
