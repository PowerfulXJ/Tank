package Tank;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Init extends Thread {
	
	private DatagramSocket ds1 = null;
	private DatagramSocket ds2 = null;
	private DatagramSocket ds3 = null;
	//@SuppressWarnings("resource")
	@Override
	public void run() {
		String host1 = "10.0.0.5";
		String host2 = "10.0.0.7";
		String host3 = "10.0.0.8";
		int port = 9999;
		try {
			sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			InetAddress adds1 = InetAddress.getByName(host1);
			InetAddress adds2 = InetAddress.getByName(host2);
			InetAddress adds3 = InetAddress.getByName(host3);
			ds1 = new DatagramSocket();
			ds2 = new DatagramSocket();
			ds3 = new DatagramSocket();
			String message = "Hello";
			DatagramPacket dp1 = new DatagramPacket(message .getBytes(),
					message.length(), adds1, port);
			DatagramPacket dp2 = new DatagramPacket(message .getBytes(),
					message.length(), adds2, port);
			DatagramPacket dp3 = new DatagramPacket(message .getBytes(),
					message.length(), adds3, port);
			ds1.connect(adds1, port);
			ds1.send(dp1);
			ds1.disconnect();
			
			ds2.connect(adds2, port);
			ds2.send(dp2);
			ds2.disconnect();
			
			ds3.connect(adds3, port);
			ds3.send(dp3);
			ds3.disconnect();
			
			System.out.println("Broardcast message sent.");
			ds1.close();
			ds2.close();
			ds3.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		
	}
	
}