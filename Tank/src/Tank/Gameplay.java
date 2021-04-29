package Tank;
import java.util.*;
import java.util.Queue;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

import javax.swing.Timer;



public class Gameplay  extends JPanel implements ActionListener 
{
	private brick br;
	
	private ImageIcon player1;	
	private int player1X = 0;
	private int player1Y = 250;	
	private boolean player1right = true;
	private boolean player1left = false;
	private boolean player1down = false;
	private boolean player1up = false;	
	private int player1score = 0;
	private int player1lives = 5;
	private boolean player1Shoot = false;
	private String bulletShootDir1 = "";
	
	private ImageIcon player2;	
	private int player2X = 300;
	private int player2Y = 550;	
	private boolean player2right = false;
	private boolean player2left = false;
	private boolean player2down = false;
	private boolean player2up = true;
	private int player2score = 0;
	private int player2lives = 5;
	private boolean player2Shoot = false;
	private String bulletShootDir2 = "";
	
	private ImageIcon player3;	
	private int player3X = 600;
	private int player3Y = 250;	
	private boolean player3right = false;
	private boolean player3left = true;
	private boolean player3down = false;
	private boolean player3up = false;
	private int player3score = 0;
	private int player3lives = 5;
	private boolean player3Shoot = false;
	private String bulletShootDir3 = "";
	
	private Timer timer;
	private int delay=8;
	
	private PlayerListener playerListener = null;
	//private Player2Listener player2Listener = null;
	
	private Player1Bullet player1Bullet = null;
	private Player2Bullet player2Bullet = null;
	private Player3Bullet player3Bullet = null;
	
	private boolean play = true;
	
	//public static Queue<String> Player2Action;
	
	public Gameplay()
	{				
		br = new brick();
		playerListener = new PlayerListener();
		//player2Listener = new Player2Listener();
		setFocusable(true);
		//addKeyListener(this);
		addKeyListener(playerListener);
		//addKeyListener(player2Listener);
		setFocusTraversalKeysEnabled(false);
		
		
		webPlayerListener webplayerListener = new webPlayerListener();
		webplayerListener.start();
		
        timer=new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{    		
		// play background
		g.setColor(Color.black);
		g.fillRect(0, 0, 650, 600);
		
		// right side background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(660, 0, 140, 600);
		
		// draw solid bricks
		br.drawSolids(this, g);
		
		// draw Breakable bricks	
		br.draw(this, g);
		
		if(play)
		{
			// draw player 1
			if (player1lives > 0) {
				if(player1up)
					player1=new ImageIcon("player1_tank_up.png");	
				else if(player1down)
					player1=new ImageIcon("player1_tank_down.png");
				else if(player1right)
					player1=new ImageIcon("player1_tank_right.png");
				else if(player1left)
					player1=new ImageIcon("player1_tank_left.png");
					
				player1.paintIcon(this, g, player1X, player1Y);
			}
			// draw player 2
			if (player2lives > 0) {
				if(player2up)
					player2=new ImageIcon("player2_tank_up.png");	
				else if(player2down)
					player2=new ImageIcon("player2_tank_down.png");
				else if(player2right)
					player2=new ImageIcon("player2_tank_right.png");
				else if(player2left)
					player2=new ImageIcon("player2_tank_left.png");
				
				player2.paintIcon(this, g, player2X, player2Y);
			}
			// draw player 3
			if(player3lives > 0){
				if(player3up)
					player3=new ImageIcon("player3_tank_up.png");	
				else if(player3down)
					player3=new ImageIcon("player3_tank_down.png");
				else if(player3right)
					player3=new ImageIcon("player3_tank_right.png");
				else if(player3left)
					player3=new ImageIcon("player3_tank_left.png");
							
				player3.paintIcon(this, g, player3X, player3Y);
			}
			
			//draw bullets
			if(player1Bullet != null && player1Shoot)
			{
				if(bulletShootDir1.equals(""))
				{
					if(player1up)
					{					
						bulletShootDir1 = "up";
					}
					else if(player1down)
					{					
						bulletShootDir1 = "down";
					}
					else if(player1right)
					{				
						bulletShootDir1 = "right";
					}
					else if(player1left)
					{			
						bulletShootDir1 = "left";
					}
				}
				else
				{
					player1Bullet.move(bulletShootDir1);
					player1Bullet.draw(g);
				}
				
				
				if(new Rectangle(player1Bullet.getX(), player1Bullet.getY(), 10, 10)
				.intersects(new Rectangle(player2X, player2Y, 50, 50)))
				{
					player1score += 10;
					player2lives -= 1;
					player1Bullet = null;
					player1Shoot = false;
					bulletShootDir1 = "";
				}
				if(new Rectangle(player1Bullet.getX(), player1Bullet.getY(), 10, 10)
						.intersects(new Rectangle(player3X, player3Y, 50, 50)))
						{
							player1score += 10;
							player3lives -= 1;
							player1Bullet = null;
							player1Shoot = false;
							bulletShootDir1 = "";
						}
				
				if(br.checkCollision(player1Bullet.getX(), player1Bullet.getY())
						|| br.checkSolidCollision(player1Bullet.getX(), player1Bullet.getY()))
				{
					player1Bullet = null;
					player1Shoot = false;
					bulletShootDir1 = "";				
				}
	
				if(player1Bullet.getY() < 1 
						|| player1Bullet.getY() > 580
						|| player1Bullet.getX() < 1
						|| player1Bullet.getX() > 630)
				{
					player1Bullet = null;
					player1Shoot = false;
					bulletShootDir1 = "";
				}
			}
			
			if(player2Bullet != null && player2Shoot)
			{
				if(bulletShootDir2.equals(""))
				{
					if(player2up)
					{					
						bulletShootDir2 = "up";
					}
					else if(player2down)
					{					
						bulletShootDir2 = "down";
					}
					else if(player2right)
					{				
						bulletShootDir2 = "right";
					}
					else if(player2left)
					{			
						bulletShootDir2 = "left";
					}
				}
				else
				{
					player2Bullet.move(bulletShootDir2);
					player2Bullet.draw(g);
				}
				
				
				if(new Rectangle(player2Bullet.getX(), player2Bullet.getY(), 10, 10)
				.intersects(new Rectangle(player1X, player1Y, 50, 50)))
				{
					player2score += 10;
					player1lives -= 1;
					player2Bullet = null;
					player2Shoot = false;
					bulletShootDir2 = "";
				}
				if(new Rectangle(player2Bullet.getX(), player2Bullet.getY(), 10, 10)
						.intersects(new Rectangle(player3X, player3Y, 50, 50)))
						{
							player2score += 10;
							player3lives -= 1;
							player2Bullet = null;
							player2Shoot = false;
							bulletShootDir2 = "";
						}
				
				if(player2Bullet != null && br.checkCollision(player2Bullet.getX(), player2Bullet.getY())
						|| br.checkSolidCollision(player2Bullet.getX(), player2Bullet.getY()))
				{
					player2Bullet = null;
					player2Shoot = false;
					bulletShootDir2 = "";				
				}
				
				if(player2Bullet != null && player2Bullet.getY() < 1 
						|| player2Bullet.getY() > 580
						|| player2Bullet.getX() < 1
						|| player2Bullet.getX() > 630)
				{
					player2Bullet = null;
					player2Shoot = false;
					bulletShootDir2 = "";
				}
			}
			
			if(player3Bullet != null && player3Shoot)
			{
				if(bulletShootDir3.equals(""))
				{
					if(player3up)
					{					
						bulletShootDir3 = "up";
					}
					else if(player3down)
					{					
						bulletShootDir3 = "down";
					}
					else if(player3right)
					{				
						bulletShootDir3 = "right";
					}
					else if(player3left)
					{			
						bulletShootDir3 = "left";
					}
				}
				else
				{
					player3Bullet.move(bulletShootDir3);
					player3Bullet.draw(g);
				}
				
				
				if(new Rectangle(player3Bullet.getX(), player3Bullet.getY(), 10, 10)
				.intersects(new Rectangle(player1X, player1Y, 50, 50)))
				{
					player3score += 10;
					player1lives -= 1;
					player3Bullet = null;
					player3Shoot = false;
					bulletShootDir3 = "";
				}
				if(new Rectangle(player3Bullet.getX(), player3Bullet.getY(), 10, 10)
						.intersects(new Rectangle(player2X, player2Y, 50, 50)))
						{
							player3score += 10;
							player2lives -= 1;
							player3Bullet = null;
							player3Shoot = false;
							bulletShootDir3 = "";
						}
				
				if(player3Bullet != null && br.checkCollision(player3Bullet.getX(), player3Bullet.getY())
						|| br.checkSolidCollision(player3Bullet.getX(), player3Bullet.getY()))
				{
					player3Bullet = null;
					player3Shoot = false;
					bulletShootDir3 = "";				
				}
				
				if(player3Bullet != null && player3Bullet.getY() < 1 
						|| player3Bullet.getY() > 580
						|| player3Bullet.getX() < 1
						|| player3Bullet.getX() > 630)
				{
					player3Bullet = null;
					player3Shoot = false;
					bulletShootDir3 = "";
				}
			}
			
		}
	
		
		// the scores 		
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD, 15));
		g.drawString("Scores", 700,30);
		g.drawString("Player 1:  "+player1score, 670,60);
		g.drawString("Player 2:  "+player2score, 670,90);
		g.drawString("Player 3:  "+player3score, 670,120);
		
		g.drawString("Lives", 700,180);
		g.drawString("Player 1:  "+player1lives, 670,210);
		g.drawString("Player 2:  "+player2lives, 670,240);
		g.drawString("Player 3:  "+player3lives, 670,270);
		
		if(Global.localplayer == 1)
		{
			if (player1lives == 0) {
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 60));
				g.drawString("Game Over", 200,300);
				g.drawString("You lose", 180,380);
				play = false;
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 30));
				//g.drawString("(Space to Restart)", 230,430);
				player1X = 0;
				player1Y = 0;
			}
			if (player1lives > 0 && player2lives <= 0 && player3lives <= 0) {
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 60));
				g.drawString("Game Over", 200,300);
				g.drawString("You win", 180,380);
				play = false;
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 30));
				//g.drawString("(Space to Restart)", 230,430);
			}
		}
		if(Global.localplayer == 2)
		{
			if (player2lives == 0) {
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 60));
				g.drawString("Game Over", 200,300);
				g.drawString("You lose", 180,380);
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 30));
				//g.drawString("(Space to Restart)", 230,430);
				play = false;
				player2X = 0;
				player2Y = 0;
			}
			if (player2lives > 0 && player1lives <= 0 && player3lives <= 0) {
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 60));
				g.drawString("Game Over", 200,300);
				g.drawString("You win", 180,380);
				play = false;
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 30));
				//g.drawString("(Space to Restart)", 230,430);
			}
		}
		if(Global.localplayer == 3)
		{
			if (player3lives == 0) {
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 60));
				g.drawString("Game Over", 200,300);
				g.drawString("You lose", 180,380);
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 30));
				//g.drawString("(Space to Restart)", 230,430);
				play = false;
				player3X = 0;
				player3Y = 0;
			}
			if (player3lives > 0 && player1lives <= 0 && player2lives <= 0) {
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 60));
				g.drawString("Game Over", 200,300);
				g.drawString("You win", 180,380);
				play = false;
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 30));
				//g.drawString("(Space to Restart)", 230,430);
			}
		}
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
	
		repaint();
	}

	private class PlayerListener implements KeyListener
	{
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}		
		public void keyPressed(KeyEvent e) {	
//			InetAddress addr1 = Global.IPAddresses.remove();
//			InetAddress addr2 = Global.IPAddresses.remove();
			DatagramSocket ds1 = null;
			DatagramSocket ds2 = null;
			try {
				ds1 = new DatagramSocket();
				ds2 = new DatagramSocket();
				ds1.setSoTimeout(1000);
				ds2.setSoTimeout(1000);
				ds1.connect(Global.addr1, 6666); // set target ip and port number
				ds2.connect(Global.addr2, 6666);
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
			if(e.getKeyCode()== KeyEvent.VK_SPACE && ((player1lives == 0 && player2lives == 0) || (player1lives == 0 && player3lives == 0) || (player2lives == 0 && player3lives == 0)))
			{
				br = new brick();
				player1X = 0;
				player1Y = 250;	
				player1right = true;
				player1left = false;
				player1down = false;
				player1up = true;	
				
				player2X = 300;
				player2Y = 550;	
				player2right = false;
				player2left = false;
				player2down = false;
				player2up = true;	
				
				player3X = 600;
				player3Y = 250;	
				player3right = false;
				player3left = true;
				player3down = false;
				player3up = false;	
				
				player1score = 0;
				player1lives = 5;
				player2score = 0;
				player2lives = 5;
				player3score = 0;
				player3lives = 5;
				
				play = true;
				repaint();
			}
			if(e.getKeyCode()== KeyEvent.VK_F)
			{
				if (Global.localplayer == 1) {
					byte[] data = "1shoot".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(!player1Shoot)
					{
						if(player1up)
						{					
							player1Bullet = new Player1Bullet(player1X + 20, player1Y);
						}
						else if(player1down)
						{					
							player1Bullet = new Player1Bullet(player1X + 20, player1Y + 40);
						}
						else if(player1right)
						{				
							player1Bullet = new Player1Bullet(player1X + 40, player1Y + 20);
						}
						else if(player1left)
						{			
							player1Bullet = new Player1Bullet(player1X, player1Y + 20);
						}
						
						player1Shoot = true;
					}
				}
				if (Global.localplayer == 2) {
					byte[] data = "2shoot".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(!player2Shoot)
					{
						if(player2up)
						{					
							player2Bullet = new Player2Bullet(player2X + 20, player2Y);
						}
						else if(player2down)
						{					
							player2Bullet = new Player2Bullet(player2X + 20, player2Y + 40);
						}
						else if(player2right)
						{				
							player2Bullet = new Player2Bullet(player2X + 40, player2Y + 20);
						}
						else if(player2left)
						{			
							player2Bullet = new Player2Bullet(player2X, player2Y + 20);
						}
						
						player2Shoot = true;
					}
				}
				if (Global.localplayer == 3) {
					byte[] data = "3shoot".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(!player3Shoot)
					{
						if(player3up)
						{					
							player3Bullet = new Player3Bullet(player3X + 20, player3Y);
						}
						else if(player3down)
						{					
							player3Bullet = new Player3Bullet(player3X + 20, player3Y + 40);
						}
						else if(player3right)
						{				
							player3Bullet = new Player3Bullet(player3X + 40, player3Y + 20);
						}
						else if(player3left)
						{			
							player3Bullet = new Player3Bullet(player3X, player3Y + 20);
						}
						
						player3Shoot = true;
					}
				}
			}
			if(e.getKeyCode()== KeyEvent.VK_UP)
			{
				if (Global.localplayer == 1) {
					byte[] data = "1up".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player1right = false;
					player1left = false;
					player1down = false; 
					player1up = true;		
					
					if(!(player1Y < 10))
						player1Y-=10;
				}
				if (Global.localplayer == 2) {
					byte[] data = "2up".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player2right = false;
					player2left = false;
					player2down = false; 
					player2up = true;		
					
					if(!(player2Y < 10))
						player2Y-=10;
				}
				if (Global.localplayer == 3) {
					byte[] data = "3up".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player3right = false;
					player3left = false;
					player3down = false; 
					player3up = true;		
					
					if(!(player3Y < 10))
						player3Y-=10;
				}
				

			}
			if(e.getKeyCode()== KeyEvent.VK_LEFT)
			{
				if (Global.localplayer == 1) {
					byte[] data = "1left".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player1right = false;
					player1left = true;
					player1down = false;
					player1up = false;
					
					if(!(player1X < 10))
						player1X-=10;
				}
				
				if (Global.localplayer == 2) {
					byte[] data = "2left".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player2right = false;
					player2left = true;
					player2down = false;
					player2up = false;
					
					if(!(player2X < 10))
						player2X-=10;
				}
				
				if (Global.localplayer == 3) {
					byte[] data = "3left".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player3right = false;
					player3left = true;
					player3down = false;
					player3up = false;
					
					if(!(player3X < 10))
						player3X-=10;
				}
				
			}
			if(e.getKeyCode()== KeyEvent.VK_DOWN)
			{
				if (Global.localplayer == 1) {
					byte[] data = "1down".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player1right = false;
					player1left = false;
					player1down = true;
					player1up = false;
					
					if(!(player1Y > 540))
						player1Y+=10;
				}
				if (Global.localplayer == 2) {
					byte[] data = "2down".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player2right = false;
					player2left = false;
					player2down = true;
					player2up = false;
					
					if(!(player2Y > 540))
						player2Y+=10;
				}
				if (Global.localplayer == 3) {
					byte[] data = "3down".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player3right = false;
					player3left = false;
					player3down = true;
					player3up = false;
					
					if(!(player3Y > 540))
						player3Y+=10;
				}
				
			}
			if(e.getKeyCode()== KeyEvent.VK_RIGHT)
			{
				if (Global.localplayer == 1) {
					byte[] data = "1right".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player1right = true;
					player1left = false;
					player1down = false;
					player1up = false;
					
					if(!(player1X > 590))
						player1X+=10;
				}
				if (Global.localplayer == 2) {
					byte[] data = "2right".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player2right = true;
					player2left = false;
					player2down = false;
					player2up = false;
					
					if(!(player2X > 590))
						player2X+=10;
				}
				if (Global.localplayer == 3) {
					byte[] data = "3right".getBytes();
		    		DatagramPacket packet1 = new DatagramPacket(data, data.length);
		    		DatagramPacket packet2 = new DatagramPacket(data, data.length);
		    		try {
						ds1.send(packet1);
						ds2.send(packet2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player3right = true;
					player3left = false;
					player3down = false;
					player3up = false;
					
					if(!(player3X > 590))
						player3X+=10;
				}
				
			}
		}
	}
	
//	private class Player2Listener implements KeyListener
//	{
//		public void keyTyped(KeyEvent e) {}
//		public void keyReleased(KeyEvent e) {}		
//		public void keyPressed(KeyEvent e) 
//		{	
//			if(e.getKeyCode()== KeyEvent.VK_M)
//			{
//				if(!player2Shoot)
//				{
//					if(player2up)
//					{					
//						player2Bullet = new Player2Bullet(player2X + 20, player2Y);
//					}
//					else if(player2down)
//					{					
//						player2Bullet = new Player2Bullet(player2X + 20, player2Y + 40);
//					}
//					else if(player2right)
//					{				
//						player2Bullet = new Player2Bullet(player2X + 40, player2Y + 20);
//					}
//					else if(player2left)
//					{			
//						player2Bullet = new Player2Bullet(player2X, player2Y + 20);
//					}
//					
//					player2Shoot = true;
//				}
//			}
//			if(e.getKeyCode()== KeyEvent.VK_W)
//			{
//				player2right = false;
//				player2left = false;
//				player2down = false;
//				player2up = true;		
//				
//				if(!(player2Y < 10))
//					player2Y-=10;
//
//			}
//			if(e.getKeyCode()== KeyEvent.VK_A)
//			{
//				player2right = false;
//				player2left = true;
//				player2down = false;
//				player2up = false;
//				
//				if(!(player2X < 10))
//					player2X-=10;
//			}
//			if(e.getKeyCode()== KeyEvent.VK_S)
//			{
//				player2right = false;
//				player2left = false;
//				player2down = true;
//				player2up = false;
//				
//				if(!(player2Y > 540))
//					player2Y+=10;
//			}
//			if(e.getKeyCode()== KeyEvent.VK_D)
//			{
//				player2right = true;
//				player2left = false;
//				player2down = false;
//				player2up = false;
//				
//				if(!(player2X > 590))
//					player2X+=10;
//			}
//			
//		}
//	}
	
	
	class webPlayerListener extends Thread {
	    @Override
	    public void run() {
	    	System.out.println("Listening to actions");
	    	while(true) {
	    		if (Global.webPlayerActions.size() > 0) {
			    	System.out.println(Global.webPlayerActions.size());
	    			System.out.println("recieved action");
	    			String s = Global.webPlayerActions.remove();
	    			System.out.println(s);
	    			
	    			if(s.equals("1shoot"))
	    			{
	    				if(!player1Shoot)
	    				{
	    					if(player1up)
	    					{					
	    						player1Bullet = new Player1Bullet(player1X + 20, player1Y);
	    					}
	    					else if(player1down)
	    					{					
	    						player1Bullet = new Player1Bullet(player1X + 20, player1Y + 40);
	    					}
	    					else if(player1right)
	    					{				
	    						player1Bullet = new Player1Bullet(player1X + 40, player1Y + 20);
	    					}
	    					else if(player1left)
	    					{			
	    						player1Bullet = new Player1Bullet(player1X, player1Y + 20);
	    					}
	    					
	    					player1Shoot = true;
	    				}
	    			}
	    			if(s.equals("1up"))
	    			{
	    				player1right = false;
	    				player1left = false;
	    				player1down = false;
	    				player1up = true;		
	    				
	    				if(!(player1Y < 10))
	    					player1Y-=10;

	    			}
	    			if(s.equals("1left"))
	    			{
	    				player1right = false;
	    				player1left = true;
	    				player1down = false;
	    				player1up = false;
	    				
	    				if(!(player1X < 10))
	    					player1X-=10;
	    			}
	    			if(s.equals("1down"))
	    			{
	    				player1right = false;
	    				player1left = false;
	    				player1down = true;
	    				player1up = false;
	    				
	    				if(!(player1Y > 540))
	    					player1Y+=10;
	    			}
	    			if(s.equals("1right"))
	    			{
	    				player1right = true;
	    				player1left = false;
	    				player1down = false;
	    				player1up = false;
	    				
	    				if(!(player1X > 590))
	    					player1X+=10;
	    			}
	    			
	    			
	    			if(s.equals("2shoot"))
	    			{
	    				if(!player2Shoot)
	    				{
	    					if(player2up)
	    					{					
	    						player2Bullet = new Player2Bullet(player2X + 20, player2Y);
	    					}
	    					else if(player2down)
	    					{					
	    						player2Bullet = new Player2Bullet(player2X + 20, player2Y + 40);
	    					}
	    					else if(player2right)
	    					{				
	    						player2Bullet = new Player2Bullet(player2X + 40, player2Y + 20);
	    					}
	    					else if(player2left)
	    					{			
	    						player2Bullet = new Player2Bullet(player2X, player2Y + 20);
	    					}
	    					
	    					player2Shoot = true;
	    				}
	    			}
	    			if(s.equals("2up"))
	    			{
	    				player2right = false;
	    				player2left = false;
	    				player2down = false;
	    				player2up = true;		
	    				
	    				if(!(player2Y < 10))
	    					player2Y-=10;

	    			}
	    			if(s.equals("2left"))
	    			{
	    				player2right = false;
	    				player2left = true;
	    				player2down = false;
	    				player2up = false;
	    				
	    				if(!(player2X < 10))
	    					player2X-=10;
	    			}
	    			if(s.equals("2down"))
	    			{
	    				player2right = false;
	    				player2left = false;
	    				player2down = true;
	    				player2up = false;
	    				
	    				if(!(player2Y > 540))
	    					player2Y+=10;
	    			}
	    			if(s.equals("2right"))
	    			{
	    				player2right = true;
	    				player2left = false;
	    				player2down = false;
	    				player2up = false;
	    				
	    				if(!(player2X > 590))
	    					player2X+=10;
	    			}
	    			
	    			if(s.equals("3shoot"))
	    			{
	    				if(!player3Shoot)
	    				{
	    					if(player3up)
	    					{					
	    						player3Bullet = new Player3Bullet(player3X + 20, player3Y);
	    					}
	    					else if(player3down)
	    					{					
	    						player3Bullet = new Player3Bullet(player3X + 20, player3Y + 40);
	    					}
	    					else if(player3right)
	    					{				
	    						player3Bullet = new Player3Bullet(player3X + 40, player3Y + 20);
	    					}
	    					else if(player3left)
	    					{			
	    						player3Bullet = new Player3Bullet(player3X, player3Y + 20);
	    					}
	    					
	    					player3Shoot = true;
	    				}
	    			}
	    			if(s.equals("3up"))
	    			{
	    				player3right = false;
	    				player3left = false;
	    				player3down = false;
	    				player3up = true;		
	    				
	    				if(!(player3Y < 10))
	    					player3Y-=10;

	    			}
	    			if(s.equals("3left"))
	    			{
	    				player3right = false;
	    				player3left = true;
	    				player3down = false;
	    				player3up = false;
	    				
	    				if(!(player3X < 10))
	    					player3X-=10;
	    			}
	    			if(s.equals("3down"))
	    			{
	    				player3right = false;
	    				player3left = false;
	    				player3down = true;
	    				player3up = false;
	    				
	    				if(!(player3Y > 540))
	    					player3Y+=10;
	    			}
	    			if(s.equals("3right"))
	    			{
	    				player3right = true;
	    				player3left = false;
	    				player3down = false;
	    				player3up = false;
	    				
	    				if(!(player3X > 590))
	    					player3X+=10;
	    			}
	    			
	    		}
	    	}
	    }
	}
}