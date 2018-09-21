package xyz.domica.game;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import xyz.domica.engine.AbstractGame;
import xyz.domica.engine.GameContainer;
import xyz.domica.engine.Renderer;
import xyz.domica.engine.gfx.ImageTile;
import xyz.domica.net.GameClient;
import xyz.domica.net.GameServer;
import xyz.domica.net.packets.Packet00Login;

public class GameManager extends AbstractGame
{

	public ArrayList<GameObject> objects = new ArrayList<GameObject>();
	
	private ImageTile image;
	private GameClient socketClient;
	private GameServer socketServer;
	
	public GameManager() 
	{
		//objects.add(new Player(2,2, JOptionPane.showInputDialog(this, "Unesi username")));
		
		if(JOptionPane.showConfirmDialog(null, "Server?") == 0) 
		{
			socketServer = new GameServer(this);
			socketServer.start();
		}

		
		socketClient = new GameClient(this, "127.0.0.1");
		socketClient.start();
		
		socketClient.sendData("ping".getBytes());
		
		Packet00Login loginPacket = new Packet00Login(JOptionPane.showInputDialog(this, "Unesi username"));
		loginPacket.writeData(socketClient);
	}
	

	@Override
	public void update(GameContainer gc, float dt) 
	{
		for(int i = 0; i < objects.size(); i++) 
		{
			objects.get(i).update(gc, dt);
			if(objects.get(i).isDead()) 
			{
				objects.remove(i);
				i--;
			}
		}
	}

	
	@Override
	public void render(GameContainer gc, Renderer r) 
	{
		for(GameObject obj : objects) 
		{
			obj.render(gc, r);
		}
		
	}
	
	public static void main(String args[]) 
	{
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<GameObject> objects) {
		this.objects = objects;
	}

}
