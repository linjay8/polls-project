package OfficeHours;
import models.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

public class ServerThread extends Thread {

	private PrintWriter dout;
//	private BufferedReader din;
	private LinkRoom lr;
	private static Semaphore semaphore = new Semaphore(2); // static is important

	public ServerThread(Socket s, LinkRoom lr)
	{
		try
		{
			this.lr = lr;
			dout = new PrintWriter(s.getOutputStream(), true);
//			din = new BufferedReader(new InputStreamReader(s.getInputStream()));
			start();
		}
		catch (IOException ex) {ex.printStackTrace();}
	}

	public void sendMessage(String message)
	{
		dout.println(message);
	}

	public void run()
	{
		try {
			semaphore.acquire();
//			System.out.println("acquire a permit");
			lr.sendLink();   // Send link to new student that should join meeting
//			while(true)
//			{
//				String line = din.readLine();
//				lr.sendMessage(line);
//			}
		}
		catch (Exception ex) {}
		finally {
			semaphore.release(); // release semaphore when client quits
//			System.out.println("release a permit");
			try {
				dout.close();
//				din.close();
			}
			catch (Exception ex) {}
		}
	}