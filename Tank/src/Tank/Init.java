package Tank;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Init extends Thread {
	
	private int ACK = 0;
	private DatagramSocket ds = null;
	//@SuppressWarnings("resource")
	@Override
	public void run() {
		String bdcast = "10.0.0.255";
		int port = 9999;
		try {
			InetAddress adds = InetAddress.getByName(bdcast);
			DatagramSocket ds = new DatagramSocket();
			String message = "Hello";
			DatagramPacket dp = new DatagramPacket(message .getBytes(),
					message.length(), adds, port);
			ds.send(dp);
			ds.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		// 发送:
		//DatagramPacket packet = new DatagramPacket(data, data.length);
//		try {
//			ds.send(packet);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		DatagramSocket ds = null;
		DatagramPacket dp = null;
		try {
			ds = new DatagramSocket(port);
			ds.setSoTimeout(1000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < 4; i++) {
			byte[] buffer = new byte[1024];
			DatagramPacket recvpacket = new DatagramPacket(buffer, buffer.length);
		    try {
				ds.receive(recvpacket);
			    String s = new String(recvpacket.getData(), recvpacket.getOffset(), recvpacket.getLength(), StandardCharsets.UTF_8);
				if (s.equals("HELLO")) {
					ACK += 1;
					System.out.println("Recieved HELLO!");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ds.close();
		Global.totalPlayer = ACK;
		Global.localplayer = ACK + 1;
		
		
	}
	
}