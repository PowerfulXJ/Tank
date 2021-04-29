package Tank;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class BroadcastListener extends Thread {
	
	//private int ACK = 0;
	private int port = 9999;
	@Override
	public void run() {
		DatagramSocket ds = null;
		//DatagramPacket dp = null;
		Global.IPAddresses = new LinkedList<InetAddress>();
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
			    InetAddress ipaddr = recvpacket.getAddress();
			    Global.IPAddresses.add(ipaddr);
				if (s.equals("Hello")) {
					Global.totalPlayer += 1;
					Global.localplayer -= 1;
					@SuppressWarnings("resource")
					DatagramSocket ds1 = new DatagramSocket();
					ds1.connect(ipaddr, port);
					byte[] data = "Recognized".getBytes();
					DatagramPacket dp1 = new DatagramPacket(data, data.length);
					ds1.send(dp1);
					System.out.println("Recieved Hello!");
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
		Global.addr1 = Global.IPAddresses.remove();
		Global.addr2 = Global.IPAddresses.remove();
		System.out.println("player number: " + Global.localplayer);
		System.out.println("web player number: " + Global.IPAddresses.size());
	}
}