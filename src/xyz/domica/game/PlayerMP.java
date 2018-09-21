package xyz.domica.game;

import java.net.InetAddress;

import xyz.domica.engine.GameContainer;

public class PlayerMP extends Player{


	public GameContainer gc;
	public float dt;
	
	public PlayerMP(int posX, int posY, String username, InetAddress ipAddress, int port) {
		super(posX, posY, username, ipAddress, port);

		
	}
	
	public void update() 
	{
		super.update(gc, dt);
	}

}
