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
		
		try {
			ds.connect(InetAddress.getByName("localhost"), 1024);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // 连接指定服务器和端口
		// 发送:
		byte[] data = "Hello".getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try {
			ds.send(packet);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ds = new DatagramSocket(1024);
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
			    String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
				if (s.equals("HELLO")) {
					ACK += 1;
					System.out.println("Recieved HELLO!");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Global.totalPlayer = ACK;
		Global.localplayer = ACK + 1;
		
		
	}
	
}