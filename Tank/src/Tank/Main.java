package Tank;
import java.awt.Color;

import javax.swing.JFrame;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;


public class Main {

	public static void main(String[] args) {
		Thread init = new Init();
		Thread t = new MyThread();
		Thread broadcastListener = new BroadcastListener();
		Global.webPlayerActions = new LinkedList<String>();
		broadcastListener.start();
		init.start();
		
		t.start();
		JFrame obj=new JFrame();
		try {
			broadcastListener.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gameplay gamePlay = new Gameplay();
		
		obj.setBounds(10, 10, 800, 630);
		obj.setTitle("Tank 2D");	
		obj.setBackground(Color.gray);
		obj.setResizable(false);
		
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
		obj.setVisible(true);

	}

}

class MyThread extends Thread {
    @SuppressWarnings("resource")
	@Override
	
    public void run() {
        System.out.println("Action listener enabled!");
        DatagramSocket ds = null;
        try {
			ds = new DatagramSocket(6666);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Waiting for Actions...");
        for (;;) {
    	    byte[] buffer = new byte[1024];
    	    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    	    try {
				ds.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    	    //System.out.print("Recieved!");
    	    // 收取到的数据存储在buffer中，由packet.getOffset(), packet.getLength()指定起始位置和长度
    	    // 将其按UTF-8编码转换为String:
    	    String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
    	    Global.webPlayerActions.add(s);
    	    //System.out.println(Global.Player2Actions.size());

    	    byte[] data = "ACK".getBytes(StandardCharsets.UTF_8);
    	    packet.setData(data);
    	    try {
				ds.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}