package xyz.domica.net;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import xyz.domica.game.GameManager;

public class GameClient extends Thread {

	private InetAddress ipAddress;
	private DatagramSocket socket;
	private GameManager gm;
	
	public GameClient(GameManager gm, String ipAddress) 
	{
		this.gm = gm;
		try 
		{
		this.socket = new DatagramSocket();
		this.ipAddress = InetAddress.getByName(ipAddress);
		} 
		catch (SocketException | UnknownHostException e) 
		{
			e.printStackTrace();
		}

	}
	
	public void run() 
	{
		while(true) 
		{
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try 
			{
				socket.receive(packet);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			String message = new String(packet.getData());
			System.out.println("Server >"+ message);
		}
	}
	
	public void sendData(byte[] data) 
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
		try 
		{
			socket.send(packet);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
