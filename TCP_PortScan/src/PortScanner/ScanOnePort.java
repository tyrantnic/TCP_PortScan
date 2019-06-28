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
			socket=new Socket();//�����׽���
			SocketAddress socketAddress = new InetSocketAddress(IPAddress, Port);
			socket.connect(socketAddress, 100);//�������ӣ����ó�ʱʱ�������Ч��
			flag = true;//�����ӳɹ���ִ��
		} catch (IOException e) {
			flag = false;//��δ�������򲶻��쳣
		}finally {
			if (socket !=null) {
				try {
					socket.close();//���ɹ��������ӣ���ɨ��˿ں�ر����ӣ���ʡ��Դ
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		}
	}
}
