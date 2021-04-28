package Tank;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Init extends Thread {
	
	private DatagramSocket ds = null;
	//@SuppressWarnings("resource")
	@Override
	public void run() {
		String host1 = "10.0.0.6";
		String host2 = "10.0.0.6";
		String host3 = "10.0.0.6";
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
			ds = new DatagramSocket();
			String message = "Hello";
			DatagramPacket dp1 = new DatagramPacket(message .getBytes(),
					message.length(), adds1, port);
			DatagramPacket dp2 = new DatagramPacket(message .getBytes(),
					message.length(), adds2, port);
			DatagramPacket dp3 = new DatagramPacket(message .getBytes(),
					message.length(), adds3, port);
			ds.connect(adds1, port);
			ds.send(dp1);
			ds.disconnect();
			
			ds.connect(adds2, port);
			ds.send(dp2);
			ds.disconnect();
			
			ds.connect(adds3, port);
			ds.send(dp3);
			ds.disconnect();
			
			System.out.println("Broardcast message sent.");
			ds.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		
	}
	
}