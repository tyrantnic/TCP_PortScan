package PortScanner;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class ScanOnePort {
	boolean flag;
	int Port;
	String IPAddress;
	public boolean isOpen() {
		return flag;
	}
	public int getPort() {
		return Port;
	}
	public String getIP() {
		return IPAddress;
	}
	public void ScanOne(String ip,int port) {
		this.Port = port;
		this.IPAddress = ip;
		Socket socket = null;
		try {
			socket=new Socket();//建立套接字
			SocketAddress socketAddress = new InetSocketAddress(IPAddress, Port);
			socket.connect(socketAddress, 100);//建立连接，设置超时时间以提高效率
			flag = true;//若连接成功则执行
		} catch (IOException e) {
			flag = false;//若未连接上则捕获异常
		}finally {
			if (socket !=null) {
				try {
					socket.close();//若成功建立连接，则扫描端口后关闭连接，节省资源
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		}
	}
}
