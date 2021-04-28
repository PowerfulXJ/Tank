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
		String bdcast = "10.0.0.255";
		int port = 9999;
		try {
			sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			InetAddress adds = InetAddress.getByName(bdcast);
			ds = new DatagramSocket();
			String message = "Hello";
			DatagramPacket dp = new DatagramPacket(message .getBytes(),
					message.length(), adds, port);
			ds.send(dp);
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