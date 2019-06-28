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
	ArrayList<String> result = new ArrayList<>();//用来存放扫描结果的可变长字符串列表
	public ThreadOfScan(String ip,int startport,int endport,int threadnumber) {
		this.IPAddress=ip;//构造方法完成成员变量的赋值
		this.StartPort=startport;
		this.EndPort=endport;
		this.ThreadNum=threadnumber;
	}
	public ArrayList<String> getResult() {
		return result;//返回扫描结果
	}
	public void AllScanner() {
		allDealPort = EndPort - StartPort;//所有需要扫描处理的端口数
		oneThreadWork = allDealPort / ThreadNum;//前ThreadNum-1个线程需要处理的任务
		lastThreadWork = allDealPort - ((ThreadNum-1)*oneThreadWork);//最后一个线程多处理一些扫描任务
		ExecutorService threadPool = Executors.newCachedThreadPool();
		for (int i = 1; i <= ThreadNum; i++) {//这里的i实则是线程的序号
			ThreadScanMethod threadScanMethod = new ThreadScanMethod(i);
			threadPool.execute(threadScanMethod);//新加入线程
		}
		threadPool.shutdown();//over
		while (true) {
			if (threadPool.isTerminated()) {//扫描完成，获取系统时间，计时
				result.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(System.currentTimeMillis())+" 扫描完成\n");
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
		
		int ThreadSerial;//线程序号
		public ThreadScanMethod(int threadserial) {
			this.ThreadSerial=threadserial;
		}
		@Override
		public void run() {
			//前threadnum-1个线程要扫描oneThreadWork个端口
			if (ThreadSerial>0&&ThreadSerial<ThreadNum) {
				for (int i = 1; i <= oneThreadWork; i++) {
					int port = oneThreadWork*(ThreadSerial-1)+StartPort+i;
					ScanOnePort scanOnePort = new ScanOnePort();
					scanOnePort.ScanOne(IPAddress, port);//调用扫描一个端口的方法
					if (scanOnePort.isOpen()) {//若端口开放则写入信息
						result.add(scanOnePort.getIP()+"的"+scanOnePort.getPort()+"端口"+"开放\n");
					} 
				}
			}else if (ThreadSerial==ThreadNum) {//最后一个线程
				for (int i = 1; i <= lastThreadWork; i++) {
					int port = oneThreadWork*(ThreadSerial-1)+StartPort+i;
					ScanOnePort scanOnePort = new ScanOnePort();
					scanOnePort.ScanOne(IPAddress, port);
					if (scanOnePort.isOpen()) {
						result.add(scanOnePort.getIP()+"的"+scanOnePort.getPort()+"端口"+"开放\n");
					}
				}
			}
			
		}
		
	}
}
