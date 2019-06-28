package PortScanner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadOfScan {
	String IPAddress;
	int StartPort;
	int EndPort;
	int ThreadNum;
	int allDealPort;
	int oneThreadWork;
	int lastThreadWork;
	ArrayList<String> result = new ArrayList<>();//�������ɨ�����Ŀɱ䳤�ַ����б�
	public ThreadOfScan(String ip,int startport,int endport,int threadnumber) {
		this.IPAddress=ip;//���췽����ɳ�Ա�����ĸ�ֵ
		this.StartPort=startport;
		this.EndPort=endport;
		this.ThreadNum=threadnumber;
	}
	public ArrayList<String> getResult() {
		return result;//����ɨ����
	}
	public void AllScanner() {
		allDealPort = EndPort - StartPort;//������Ҫɨ�账��Ķ˿���
		oneThreadWork = allDealPort / ThreadNum;//ǰThreadNum-1���߳���Ҫ���������
		lastThreadWork = allDealPort - ((ThreadNum-1)*oneThreadWork);//���һ���̶߳ദ��һЩɨ������
		ExecutorService threadPool = Executors.newCachedThreadPool();
		for (int i = 1; i <= ThreadNum; i++) {//�����iʵ�����̵߳����
			ThreadScanMethod threadScanMethod = new ThreadScanMethod(i);
			threadPool.execute(threadScanMethod);//�¼����߳�
		}
		threadPool.shutdown();//over
		while (true) {
			if (threadPool.isTerminated()) {//ɨ����ɣ���ȡϵͳʱ�䣬��ʱ
				result.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(System.currentTimeMillis())+" ɨ�����\n");
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public class ThreadScanMethod implements Runnable {
		
		int ThreadSerial;//�߳����
		public ThreadScanMethod(int threadserial) {
			this.ThreadSerial=threadserial;
		}
		@Override
		public void run() {
			//ǰthreadnum-1���߳�Ҫɨ��oneThreadWork���˿�
			if (ThreadSerial>0&&ThreadSerial<ThreadNum) {
				for (int i = 1; i <= oneThreadWork; i++) {
					int port = oneThreadWork*(ThreadSerial-1)+StartPort+i;
					ScanOnePort scanOnePort = new ScanOnePort();
					scanOnePort.ScanOne(IPAddress, port);//����ɨ��һ���˿ڵķ���
					if (scanOnePort.isOpen()) {//���˿ڿ�����д����Ϣ
						result.add(scanOnePort.getIP()+"��"+scanOnePort.getPort()+"�˿�"+"����\n");
					} 
				}
			}else if (ThreadSerial==ThreadNum) {//���һ���߳�
				for (int i = 1; i <= lastThreadWork; i++) {
					int port = oneThreadWork*(ThreadSerial-1)+StartPort+i;
					ScanOnePort scanOnePort = new ScanOnePort();
					scanOnePort.ScanOne(IPAddress, port);
					if (scanOnePort.isOpen()) {
						result.add(scanOnePort.getIP()+"��"+scanOnePort.getPort()+"�˿�"+"����\n");
					}
				}
			}
			
		}
		
	}
}
