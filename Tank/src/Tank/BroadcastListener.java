package Tank;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class BroadcastListener extends Thread {
	
	private int ACK = 0;
	private int port = 9999;
	@Override
	public void run() {
		DatagramSocket ds = null;
		DatagramPacket dp = null;
		try {
			ds = new DatagramSocket(port);
			//ds.setSoTimeout(1000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (Global.totalPlayer < 3) {
			byte[] buffer = new byte[1024];
			DatagramPacket recvpacket = new DatagramPacket(buffer, buffer.length);
		    try {
				ds.receive(recvpacket);
			    String s = new String(recvpacket.getData(), recvpacket.getOffset(), recvpacket.getLength(), StandardCharsets.UTF_8);
				if (s.equals("Hello")) {
					Global.totalPlayer += 1;
					Global.localplayer =- 1;
					InetAddress ipaddr = recvpacket.getAddress();
					ds = new DatagramSocket();
					ds.connect(ipaddr, port);
					byte[] data = "Recognized".getBytes();
					dp = new DatagramPacket(data, data.length);
					ds.send(dp);
					dp = null;
					System.out.println("Recieved HELLO!");
				}
				if (s.equals("Recognized")) {
					Global.totalPlayer += 1;
					System.out.println("Recognized New Player!");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ds.close();
		System.out.println("player number: " + Global.localplayer);
	}
}