package xyz.domica.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import xyz.domica.game.GameManager;
import xyz.domica.game.Player;
import xyz.domica.game.PlayerMP;
import xyz.domica.net.packets.Packet;
import xyz.domica.net.packets.Packet.PacketTypes;
import xyz.domica.net.packets.Packet00Login;

public class GameServer extends Thread {
	
	private DatagramSocket socket;
	private GameManager gm;
	private List<Player> connectedPlayers = new ArrayList<Player>();
	public Player player;
	
	public GameServer(GameManager gm) 
	{
		this.gm = gm;
		try 
		{
		this.socket = new DatagramSocket(1331);
		} 
		catch (SocketException e) 
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
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
			/*
			String message = new String(packet.getData());
			System.out.println("Client >["+ packet.getAddress().getHostAddress() + ":" + packet.getPort() + "]"+ message);
			if(message.trim().equalsIgnoreCase("ping")) 
			{
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			}
			*/

		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
		switch(type) 
		{
			default:
			case INVALID:
				break;
			case LOGIN:
				Packet00Login packet = new Packet00Login(data);
				System.out.println("["+ address.getHostAddress()+":"+port+"] " +packet.getUsername() + "se connecto..");
				player = new Player(5, 5, packet.getUsername(), address, port); // This line creates the player
				gm.objects.add(player);
				if (player != null) 
				{
					this.connectedPlayers.add(player);
				}
				break;
			case DISCONNECT:
				break;
		}
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) 
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try 
		{
			socket.send(packet);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void sendDataToAllClients(byte[] data) {
		for(Player p : connectedPlayers) 
		{
			sendData(data, p.ipAdress, p.port);
		}
	}
}
