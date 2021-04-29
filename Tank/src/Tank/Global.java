package Tank;
import java.net.InetAddress;
import java.util.*;

public class Global{
	public volatile static Queue<String> webPlayerActions;
	public volatile static int localplayer = 3;
	public volatile static int totalPlayer = 1;
	public volatile static Queue<InetAddress> IPAddresses;
	public static InetAddress addr1 = null;
	public static InetAddress addr2 = null;
}