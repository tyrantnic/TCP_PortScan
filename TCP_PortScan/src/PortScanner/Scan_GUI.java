package PortScanner;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Scan_GUI extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;//自动生成的序列化机制
	JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);//创建分页窗格，分页标签置于顶
	//第一页主要控件
	JPanel ScanOnejp = new JPanel();//分页窗格第一页的单个端口扫描面板（主面板1）
	JButton scanjb1;//开始扫描按钮
	JButton save1;//保存按钮
	JTextField IPjtf1 = new JTextField(16);//输入ip地址的单行文本编辑框
	JTextField Portjtf = new JTextField(16);//输入端口号的单行文本编辑框
	JTextArea resultjta1 =new JTextArea();//显示结果的文本区
	//第二页主要控件
	JPanel ScanMorejp = new JPanel();//分页窗格第二页的个窗口扫描面板（主面板2）
	JButton scanjb2;
	JButton save2;
	JTextField IPjtf2 = new JTextField(11);
	JTextField threadnumjtf = new JTextField(5);//线程数
	JTextField SPortjtf = new JTextField(5);//开始端口
	JTextField EPortjtf = new JTextField(5);//结束端口
	JTextArea resultjta2 =new JTextArea();
	String[] result;
	public Scan_GUI() 
	{   //第一页
		JPanel inputjp1 = new JPanel();//存放输入和开始按钮的面板
		resultjta1.setEditable(false);//设置为不可被编辑
	    resultjta1.setLineWrap(true);  //设置为自动换行
		JScrollPane Showjsp1 = new JScrollPane(resultjta1);//显示扫描结果的面板
		JLabel ip1 = new JLabel("IP号：");//ip标签
		JLabel port =new JLabel("端口：");//端口标签
		JPanel jp1 = new JPanel();//第一行面板
		jp1.add(ip1);
		jp1.add(IPjtf1);
		JPanel jp2 = new JPanel();//第二行面板
		jp2.add(port);
		jp2.add(Portjtf);
		JPanel jp3 = new JPanel();//第三行面板
		scanjb1 = new JButton("扫描");
		save1 = new JButton("保存");
		jp3.add(scanjb1);
		jp3.add(save1);
		
		inputjp1.setLayout(new GridLayout(3, 1));//设为网格式布局分三份
		inputjp1.add(jp1);
		inputjp1.add(jp2);
		inputjp1.add(jp3);
		
		ScanOnejp.setLayout(new BorderLayout());//边界式布局
		ScanOnejp.add(inputjp1, BorderLayout.NORTH);
		ScanOnejp.add(Showjsp1,BorderLayout.CENTER);
		
		//第二页
		JPanel inputjp2 = new JPanel();
		resultjta2.setEditable(false);
		resultjta2.setLineWrap(true);
		JScrollPane Showjsp2 = new JScrollPane(resultjta2);
		JPanel jp21 = new JPanel();
		Label ip2 = new Label("IP");
		Label thread = new Label("线程数");
		jp21.add(ip2);
		jp21.add(IPjtf2);
		jp21.add(thread);
		jp21.add(threadnumjtf);
		JPanel jp22 = new JPanel();
		Label sport = new Label("开始端口");
		Label eport = new Label("结束端口");
		jp22.add(sport);
		jp22.add(SPortjtf);
		jp22.add(eport);
		jp22.add(EPortjtf);
		
		JPanel jp23 = new JPanel();
		scanjb2 = new JButton("扫描");
		save2 = new JButton("保存");
		jp23.add(scanjb2);
		jp23.add(save2);
		
		inputjp2.setLayout(new GridLayout(3, 1));
		inputjp2.add(jp21);
		inputjp2.add(jp22);
		inputjp2.add(jp23);
		
		ScanMorejp.setLayout(new BorderLayout());//边界式布局
		ScanMorejp.add(inputjp2, BorderLayout.NORTH);
		ScanMorejp.add(Showjsp2,BorderLayout.CENTER);
		
		//加上两个分页
		jTabbedPane.add("单个扫描",ScanOnejp);
		jTabbedPane.add("多个扫描",ScanMorejp);
		this.add(jTabbedPane);
		this.Monitor();//放置监听器
	}
	public void LaunchFrame() {
		IPjtf1.setText("127.0.0.1");
		IPjtf2.setText("127.0.0.1");
		threadnumjtf.setText("500");
		SPortjtf.setText("1");
		EPortjtf.setText("65535");
		this.setTitle("TCP端口扫描"); //设置标题
	    this.setSize(300, 480); //设置窗口大小
	    this.setResizable(false); //固定窗口大小
	    this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-getSize().width)/2,
		         (Toolkit.getDefaultToolkit().getScreenSize().height-getSize().height)/2); 
	    //让窗口启动时显示在屏幕中央
	    this.setVisible(true);	//让窗口可视
	}
	public void Monitor() {//给按钮注册监听
		scanjb1.addActionListener(new Controller());
		save1.addActionListener(new Controller());
		scanjb2.addActionListener(new Controller());
		save2.addActionListener(new Controller());
	}
	public class Controller implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if((e.getSource())instanceof JButton)//如果这个操作是按钮
			{
				JButton button = (JButton)e.getSource(); //强制转换为按钮
				if(button == scanjb1)
				{
					Scan1();
				}else if (button == save1) {//将resultjta1文本区的内容保存至d:\\TCP端口扫描结果1.txt
					SavePort(resultjta1, "d:\\TCP端口扫描结果1.txt");
				}else if (button == scanjb2) {
					Scan2();
				}else if (button == save2) {
					SavePort(resultjta2, "d:\\TCP端口扫描结果2.txt");
				}
			}
		}
		
	}
	//第一页的扫描
	public void Scan1() {
		String IPAddress = IPjtf1.getText().trim();//从文本框获取IP地址
		int Port = 0;
		boolean flag = true;
		try {
			Port = Integer.parseInt(Portjtf.getText().trim());//获取端口号
		} catch (NumberFormatException e) {//若输入不是整形数则产生异常
			flag = false;
			JOptionPane.showMessageDialog(null, "端口号格式错误请重新输入！");
		}
		if (Port<1 || Port>65535) {//端口号范围有误
			JOptionPane.showMessageDialog(null, "端口号范围：1~65535");
		}
		else if (flag) {//输入无误则扫描
			ScanOnePort scanOnePort = new ScanOnePort();
			scanOnePort.ScanOne(IPAddress, Port);
			if (scanOnePort.isOpen()) {//扫描结果来输出在文本区里
				resultjta1.append(scanOnePort.getIP()+"的"+scanOnePort.getPort()+"端口"+"开放\n");
			}else if (!scanOnePort.isOpen()) {
				resultjta1.append(scanOnePort.getIP()+"的"+scanOnePort.getPort()+"端口"+"未开放\n");
			}
		}	
	}
	//第一页的保存
	public void SavePort(JTextArea resultjta12,String SavePlace) {
		boolean flag = true;//是否保存成功
		try {
			FileWriter filterWriter = new FileWriter(SavePlace);//新建文件
			BufferedWriter bufferedWriter = new BufferedWriter(filterWriter);//缓冲IO流
			String[] strings = resultjta12.getText().split("\n");//获取文本区内容并且按换行符切割，写入字符数组中
			for(String string : strings)//逐行写入缓冲区
			{
				bufferedWriter.write(string);
				bufferedWriter.newLine();
			}
			bufferedWriter.flush();//刷入文件中
			bufferedWriter.close();//关闭IO流
		} catch (IOException e) {
			flag = false;//捕捉到异常则保存失败
			JOptionPane.showMessageDialog(null, "保存失败！");
		}
		if (flag) {//未捕捉到异常则提示保存成功
			JOptionPane.showMessageDialog(null, "保存成功，已保存至"+SavePlace);
		}
	}
	//第二页实现多线程高效扫描
	public void Scan2() {
		boolean flag = true;//格式错误标志
		int StartPort = 0;
		int EndPort = 0;
		int ThreadNum = 0;
		String IPAddress = IPjtf2.getText().trim();//从文本框获取IP地址
		try {
			StartPort = Integer.parseInt(SPortjtf.getText().trim());
			EndPort = Integer.parseInt(EPortjtf.getText().trim());
			ThreadNum = Integer.parseInt(threadnumjtf.getText().trim());
		} catch (NumberFormatException e) {
			flag = false;
			JOptionPane.showMessageDialog(null, "格式错误请重新输入！");
		}
		if (StartPort<1 || EndPort>65535) {
			JOptionPane.showMessageDialog(null, "端口范围：1~65535");
		}else if (StartPort>=EndPort) {
			JOptionPane.showMessageDialog(null, "端口号从小至大输入");
		}else if (flag) {
			resultjta2.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(System.currentTimeMillis())+" 扫描开始\n");//获取系统时间
			ThreadOfScan threadOfScan = new ThreadOfScan(IPAddress, StartPort, EndPort,ThreadNum);
			threadOfScan.AllScanner();//实例化一个threadofscan并调用它的AllScanner（）方法
			for (String string : threadOfScan.getResult()) {
				resultjta2.append(string);//遍历通过getResult（）方法获得的结果列表并将其加入文本区
			}
		}
		
	}
//	//系统的main（）方法
//	public static void main(String args[]) {
//		Scan_GUI scan_GUI =new Scan_GUI();//实例化
//		scan_GUI.LaunchFrame();//显示窗体
//	}
}
