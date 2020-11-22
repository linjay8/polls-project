package OfficeHours;

import models.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread extends Thread
{
	private BufferedReader din;
	private PrintWriter dout;
	private String name;
	private Socket s;

	public ClientThread(Student student, String hostname, int port)
	{
		try
		{
			s = new Socket(hostname, port);
			din = new BufferedReader(new InputStreamReader(s.getInputStream()));
			dout = new PrintWriter(s.getOutputStream(), true);
			start();
			Scanner scan = new Scanner(System.in);
			name = student.getFullName();
			while(student.getInMeetingStatus()) { //while student is in meeting
				String line = scan.nextLine();
				dout.println(name +": " + line);
			}
			dout.close();
			din.close();
			s.close();
		}
		catch (IOException ex) {ex.printStackTrace();}
	}

	public void run()
	{
		try {
			while(true)
			{
				String line = din.readLine();
				System.out.println(line);
			}
		}
		catch (IOException ex) {System.out.println("Connection closed");}
	}
}
