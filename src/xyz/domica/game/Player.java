package xyz.domica.game;

import java.net.InetAddress;

import com.sun.glass.events.KeyEvent;

import javafx.scene.input.KeyCode;
import xyz.domica.engine.GameContainer;
import xyz.domica.engine.Renderer;

public class Player extends GameObject {

	
	private float speed = 100;
	private String username;
	public InetAddress ipAdress;
	public int port;
	
	public Player(int posX, int posY, String username, InetAddress ipAddress, int port) 
	{
		this.tag = "Igrac";
		this.posX = posX * 16;
		this.posY = posY * 16;
		this.height = 16;
		this.width = 16;
		this.username = username;
		this.ipAdress = ipAddress;
		this.port = port;
	}
	
	public void update(GameContainer gc, float dt) 
	{
		if(gc.getInput().isKey(KeyEvent.VK_W)) 
		{
			posY -= dt*speed;
		}
		if(gc.getInput().isKey(KeyEvent.VK_S)) 
		{
			posY += dt*speed;
		}
		if(gc.getInput().isKey(KeyEvent.VK_D)) 
		{
			posX += dt*speed;
		}
		if(gc.getInput().isKey(KeyEvent.VK_A)) 
		{
			posX -= dt*speed;
		}
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawFillRect((int)posX, (int)posY, width, height, 0xff00ff00);
		if(username != null) 
		{
			r.drawText(username, (int) posX - (username.length()),(int) posY- 10, 0xffffffff);
		}
	}

}
