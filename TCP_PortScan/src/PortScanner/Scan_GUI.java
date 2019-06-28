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
	private static final long serialVersionUID = 1L;//�Զ����ɵ����л�����
	JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);//������ҳ���񣬷�ҳ��ǩ���ڶ�
	//��һҳ��Ҫ�ؼ�
	JPanel ScanOnejp = new JPanel();//��ҳ�����һҳ�ĵ����˿�ɨ����壨�����1��
	JButton scanjb1;//��ʼɨ�谴ť
	JButton save1;//���水ť
	JTextField IPjtf1 = new JTextField(16);//����ip��ַ�ĵ����ı��༭��
	JTextField Portjtf = new JTextField(16);//����˿ںŵĵ����ı��༭��
	JTextArea resultjta1 =new JTextArea();//��ʾ������ı���
	//�ڶ�ҳ��Ҫ�ؼ�
	JPanel ScanMorejp = new JPanel();//��ҳ����ڶ�ҳ�ĸ�����ɨ����壨�����2��
	JButton scanjb2;
	JButton save2;
	JTextField IPjtf2 = new JTextField(11);
	JTextField threadnumjtf = new JTextField(5);//�߳���
	JTextField SPortjtf = new JTextField(5);//��ʼ�˿�
	JTextField EPortjtf = new JTextField(5);//�����˿�
	JTextArea resultjta2 =new JTextArea();
	String[] result;
	public Scan_GUI() 
	{   //��һҳ
		JPanel inputjp1 = new JPanel();//�������Ϳ�ʼ��ť�����
		resultjta1.setEditable(false);//����Ϊ���ɱ��༭
	    resultjta1.setLineWrap(true);  //����Ϊ�Զ�����
		JScrollPane Showjsp1 = new JScrollPane(resultjta1);//��ʾɨ���������
		JLabel ip1 = new JLabel("IP�ţ�");//ip��ǩ
		JLabel port =new JLabel("�˿ڣ�");//�˿ڱ�ǩ
		JPanel jp1 = new JPanel();//��һ�����
		jp1.add(ip1);
		jp1.add(IPjtf1);
		JPanel jp2 = new JPanel();//�ڶ������
		jp2.add(port);
		jp2.add(Portjtf);
		JPanel jp3 = new JPanel();//���������
		scanjb1 = new JButton("ɨ��");
		save1 = new JButton("����");
		jp3.add(scanjb1);
		jp3.add(save1);
		
		inputjp1.setLayout(new GridLayout(3, 1));//��Ϊ����ʽ���ַ�����
		inputjp1.add(jp1);
		inputjp1.add(jp2);
		inputjp1.add(jp3);
		
		ScanOnejp.setLayout(new BorderLayout());//�߽�ʽ����
		ScanOnejp.add(inputjp1, BorderLayout.NORTH);
		ScanOnejp.add(Showjsp1,BorderLayout.CENTER);
		
		//�ڶ�ҳ
		JPanel inputjp2 = new JPanel();
		resultjta2.setEditable(false);
		resultjta2.setLineWrap(true);
		JScrollPane Showjsp2 = new JScrollPane(resultjta2);
		JPanel jp21 = new JPanel();
		Label ip2 = new Label("IP");
		Label thread = new Label("�߳���");
		jp21.add(ip2);
		jp21.add(IPjtf2);
		jp21.add(thread);
		jp21.add(threadnumjtf);
		JPanel jp22 = new JPanel();
		Label sport = new Label("��ʼ�˿�");
		Label eport = new Label("�����˿�");
		jp22.add(sport);
		jp22.add(SPortjtf);
		jp22.add(eport);
		jp22.add(EPortjtf);
		
		JPanel jp23 = new JPanel();
		scanjb2 = new JButton("ɨ��");
		save2 = new JButton("����");
		jp23.add(scanjb2);
		jp23.add(save2);
		
		inputjp2.setLayout(new GridLayout(3, 1));
		inputjp2.add(jp21);
		inputjp2.add(jp22);
		inputjp2.add(jp23);
		
		ScanMorejp.setLayout(new BorderLayout());//�߽�ʽ����
		ScanMorejp.add(inputjp2, BorderLayout.NORTH);
		ScanMorejp.add(Showjsp2,BorderLayout.CENTER);
		
		//����������ҳ
		jTabbedPane.add("����ɨ��",ScanOnejp);
		jTabbedPane.add("���ɨ��",ScanMorejp);
		this.add(jTabbedPane);
		this.Monitor();//���ü�����
	}
	public void LaunchFrame() {
		IPjtf1.setText("127.0.0.1");
		IPjtf2.setText("127.0.0.1");
		threadnumjtf.setText("500");
		SPortjtf.setText("1");
		EPortjtf.setText("65535");
		this.setTitle("TCP�˿�ɨ��"); //���ñ���
	    this.setSize(300, 480); //���ô��ڴ�С
	    this.setResizable(false); //�̶����ڴ�С
	    this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-getSize().width)/2,
		         (Toolkit.getDefaultToolkit().getScreenSize().height-getSize().height)/2); 
	    //�ô�������ʱ��ʾ����Ļ����
	    this.setVisible(true);	//�ô��ڿ���
	}
	public void Monitor() {//����ťע�����
		scanjb1.addActionListener(new Controller());
		save1.addActionListener(new Controller());
		scanjb2.addActionListener(new Controller());
		save2.addActionListener(new Controller());
	}
	public class Controller implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if((e.getSource())instanceof JButton)//�����������ǰ�ť
			{
				JButton button = (JButton)e.getSource(); //ǿ��ת��Ϊ��ť
				if(button == scanjb1)
				{
					Scan1();
				}else if (button == save1) {//��resultjta1�ı��������ݱ�����d:\\TCP�˿�ɨ����1.txt
					SavePort(resultjta1, "d:\\TCP�˿�ɨ����1.txt");
				}else if (button == scanjb2) {
					Scan2();
				}else if (button == save2) {
					SavePort(resultjta2, "d:\\TCP�˿�ɨ����2.txt");
				}
			}
		}
		
	}
	//��һҳ��ɨ��
	public void Scan1() {
		String IPAddress = IPjtf1.getText().trim();//���ı����ȡIP��ַ
		int Port = 0;
		boolean flag = true;
		try {
			Port = Integer.parseInt(Portjtf.getText().trim());//��ȡ�˿ں�
		} catch (NumberFormatException e) {//�����벻��������������쳣
			flag = false;
			JOptionPane.showMessageDialog(null, "�˿ںŸ�ʽ�������������룡");
		}
		if (Port<1 || Port>65535) {//�˿ںŷ�Χ����
			JOptionPane.showMessageDialog(null, "�˿ںŷ�Χ��1~65535");
		}
		else if (flag) {//����������ɨ��
			ScanOnePort scanOnePort = new ScanOnePort();
			scanOnePort.ScanOne(IPAddress, Port);
			if (scanOnePort.isOpen()) {//ɨ������������ı�����
				resultjta1.append(scanOnePort.getIP()+"��"+scanOnePort.getPort()+"�˿�"+"����\n");
			}else if (!scanOnePort.isOpen()) {
				resultjta1.append(scanOnePort.getIP()+"��"+scanOnePort.getPort()+"�˿�"+"δ����\n");
			}
		}	
	}
	//��һҳ�ı���
	public void SavePort(JTextArea resultjta12,String SavePlace) {
		boolean flag = true;//�Ƿ񱣴�ɹ�
		try {
			FileWriter filterWriter = new FileWriter(SavePlace);//�½��ļ�
			BufferedWriter bufferedWriter = new BufferedWriter(filterWriter);//����IO��
			String[] strings = resultjta12.getText().split("\n");//��ȡ�ı������ݲ��Ұ����з��ид���ַ�������
			for(String string : strings)//����д�뻺����
			{
				bufferedWriter.write(string);
				bufferedWriter.newLine();
			}
			bufferedWriter.flush();//ˢ���ļ���
			bufferedWriter.close();//�ر�IO��
		} catch (IOException e) {
			flag = false;//��׽���쳣�򱣴�ʧ��
			JOptionPane.showMessageDialog(null, "����ʧ�ܣ�");
		}
		if (flag) {//δ��׽���쳣����ʾ����ɹ�
			JOptionPane.showMessageDialog(null, "����ɹ����ѱ�����"+SavePlace);
		}
	}
	//�ڶ�ҳʵ�ֶ��̸߳�Чɨ��
	public void Scan2() {
		boolean flag = true;//��ʽ�����־
		int StartPort = 0;
		int EndPort = 0;
		int ThreadNum = 0;
		String IPAddress = IPjtf2.getText().trim();//���ı����ȡIP��ַ
		try {
			StartPort = Integer.parseInt(SPortjtf.getText().trim());
			EndPort = Integer.parseInt(EPortjtf.getText().trim());
			ThreadNum = Integer.parseInt(threadnumjtf.getText().trim());
		} catch (NumberFormatException e) {
			flag = false;
			JOptionPane.showMessageDialog(null, "��ʽ�������������룡");
		}
		if (StartPort<1 || EndPort>65535) {
			JOptionPane.showMessageDialog(null, "�˿ڷ�Χ��1~65535");
		}else if (StartPort>=EndPort) {
			JOptionPane.showMessageDialog(null, "�˿ںŴ�С��������");
		}else if (flag) {
			resultjta2.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(System.currentTimeMillis())+" ɨ�迪ʼ\n");//��ȡϵͳʱ��
			ThreadOfScan threadOfScan = new ThreadOfScan(IPAddress, StartPort, EndPort,ThreadNum);
			threadOfScan.AllScanner();//ʵ����һ��threadofscan����������AllScanner��������
			for (String string : threadOfScan.getResult()) {
				resultjta2.append(string);//����ͨ��getResult����������õĽ���б���������ı���
			}
		}
		
	}
//	//ϵͳ��main��������
//	public static void main(String args[]) {
//		Scan_GUI scan_GUI =new Scan_GUI();//ʵ����
//		scan_GUI.LaunchFrame();//��ʾ����
//	}
}
