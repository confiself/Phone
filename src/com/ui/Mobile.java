package com.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.web.*;

public class Mobile extends JFrame{
	public static JLabel labelLogin,labelProcess,labelSave,labelStock;
	public static JTable table;
	public volatile static int methodMode,prodID,modelID;
	private JComboBox combox;
	public static JButton btnLoad,btnLogin,btnSet,btnProcess,btnSave;
	private JTextField inputLoginThread,inputProcessThread,inputProdID,inputModelID;
	public volatile static int loginFinish,loginCurrent,processFinish,processCurrent;
	public volatile static int loginThreadNum,processThreadNum;
	public volatile static boolean startGuessFlag=false;
	public  volatile static String localPath;
	public static FileWriter fileWriterLogin,fileWriterGuess;
	public static BufferedWriter bufferWriterLogin,bufferWriterGuess;
	public static Object lock=new Object();//表格锁
	public static Object lockFile=new Object();//文件锁
	public static Thread[] methodThread;
	private String selectedFileName;	
	private static DefaultTableModel tableModel;
	private Vector<String> columnVector;
	public static void main(String arg[]){
		Mobile mobile=new Mobile();
		mobile.setVisible(true);
		
	}	
	public Mobile(){
		super();
		initUI();
		//创建两个临时文件
		String path=Mobile.class.getResource("/").toString();
		path=path.replace("file:/", "");
		localPath=path;
		try {
			fileWriterLogin=new FileWriter(path+"登录临时文件.txt");
			fileWriterGuess=new FileWriter(path+"下单临时文件.txt");
			bufferWriterLogin=new BufferedWriter(fileWriterLogin);
			bufferWriterGuess=new BufferedWriter(fileWriterGuess);
			String picPath=localPath+"pic";
			File picFile=new File(picPath);
			if(!picFile.exists() && !picFile.isDirectory()){
				picFile.mkdir();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void initUI(){
		setTitle("移动活动手机助手");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸 
		int width=(int)screenSize.getWidth();
		
		setBounds(width/2-325,100,650,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		//getContentPane().add(label,BorderLayout.NORTH);
		String method[]={"下单","修改地址"};
		combox=new JComboBox(method);
		combox.setBounds(40, 5, 90, 20);
		combox.setFont(new Font("微软雅黑",Font.PLAIN,12));
		
		btnLoad=new JButton();
		btnLoad.setBounds(40, 25, 90, 25);
		btnLoad.setText("导入账号");
		btnLoad.setFont(new Font("微软雅黑",Font.PLAIN,14));
		btnLogin=new JButton();
		btnLogin.setBounds(40, 50, 90, 25);
		btnLogin.setText("登录");
		btnLogin.setFont(new Font("微软雅黑",Font.PLAIN,14));
		btnProcess=new JButton();
		btnProcess.setBounds(40, 75, 90, 25);
		btnProcess.setFont(new Font("微软雅黑",Font.PLAIN,14));
		btnProcess.setText("购买");
		btnSet=new JButton();
		btnSet.setBounds(40, 180, 90, 25);
		btnSet.setFont(new Font("微软雅黑",Font.PLAIN,14));
		btnSet.setText("设置线程");
		
		inputLoginThread=new JTextField();
		inputProcessThread=new JTextField();
		inputLoginThread.setBounds(40, 140, 90, 20);
		inputProcessThread.setBounds(40, 160, 90, 20);
		inputLoginThread.setText("30");
		inputLoginThread.setHorizontalAlignment(JTextField.CENTER);
		inputProcessThread.setText("30");
		inputProcessThread.setHorizontalAlignment(JTextField.CENTER);
		
		
		labelLogin=new JLabel();
		labelLogin.setText("0/0");
		labelLogin.setHorizontalAlignment(JLabel.CENTER);
		labelLogin.setFont(new Font("微软雅黑",Font.PLAIN,12));
		labelLogin.setBounds(40, 100, 90, 20);
		labelProcess=new JLabel();
		labelProcess.setText("0/0");
		labelProcess.setHorizontalAlignment(JLabel.CENTER);
		labelProcess.setFont(new Font("微软雅黑",Font.PLAIN,12));
		labelProcess.setBounds(40, 120, 90, 20);
		
		btnSave=new JButton();
		btnSave.setBounds(40, 205, 90, 25);
		btnSave.setFont(new Font("微软雅黑",Font.PLAIN,14));
		btnSave.setText("保存结果");

		labelSave=new JLabel();
		labelSave.setText("");
		labelSave.setHorizontalAlignment(JLabel.CENTER);
		labelSave.setFont(new Font("微软雅黑",Font.PLAIN,12));
		labelSave.setBounds(40, 230, 90, 20);
		
		inputProdID=new JTextField();
		inputProdID.setBounds(40, 250, 90, 20);
		inputProdID.setHorizontalAlignment(JTextField.CENTER);
		inputProdID.setText("1022546");
		
		inputModelID=new JTextField();
		inputModelID.setBounds(40, 270, 90, 20);
		inputModelID.setHorizontalAlignment(JTextField.CENTER);
		inputModelID.setText("1015922");
		
		labelStock=new JLabel();
		labelStock.setText("库存：");
		labelStock.setHorizontalAlignment(JLabel.CENTER);
		labelStock.setFont(new Font("微软雅黑",Font.PLAIN,12));
		labelStock.setBounds(40, 290, 90, 20);
		
		JPanel panel=new JPanel();		
		panel.setLayout(null);
		panel.add(btnLogin,null);
		panel.add(btnLoad);
		panel.add(btnLogin,null);
		panel.add(btnSet,null);	
		panel.add(btnProcess,null);	
		panel.add(inputLoginThread,null);		
		panel.add(inputProcessThread,null);	
		panel.add(labelLogin,null);	
		panel.add(labelProcess,null);	
		panel.add(btnSave,null);
		panel.add(labelSave,null);
		panel.add(combox,null);
		panel.add(inputProdID,null);
		panel.add(inputModelID,null);
		panel.add(labelStock,null);
	
		tableModel=new DefaultTableModel();
		columnVector=new Vector();
		columnVector.add("序号");
		columnVector.add("账号");
		columnVector.add("密码");
		columnVector.add("状态");
		Vector dataVector=new Vector();
		for(int i=0;i<11;i++){
			Vector<String> rowVector=new Vector();	
			rowVector.add("");
			rowVector.add("");
			rowVector.add("");
			rowVector.add("");
			dataVector.add(rowVector);
		}	
		table=new JTable(tableModel);
		tableModel.setDataVector(dataVector, columnVector);		
		table.setRowHeight(20);
		table.setBounds(0, 0, 300, 200);			
		JScrollPane scrollPane=new JScrollPane();
		scrollPane.setViewportView(table);

		Container c=getContentPane();
		c.setLayout(new BorderLayout());//为相对布局
		c.add(scrollPane,BorderLayout.WEST);
		c.add(panel,BorderLayout.CENTER);
	
		btnLoad.addMouseListener(new MouseAdapter(){
			public void mouseClicked(final MouseEvent arg0){
				if(btnLoadClicked())btnLoad.setEnabled(false);
			}
		});
		btnSet.addMouseListener(new MouseAdapter(){
			public void mouseClicked(final MouseEvent arg0){
				btnSetClicked();				
			}	
			});
		btnLogin.addMouseListener(new MouseAdapter(){
			public void mouseClicked(final MouseEvent arg0){
				String loginText=inputLoginThread.getText().trim();
				loginThreadNum=Integer.valueOf(loginText);
				methodMode=combox.getSelectedIndex();
				if(methodMode==0){
					prodID=Integer.valueOf(inputProdID.getText().trim());
					modelID=Integer.valueOf(inputModelID.getText().trim());
				}
				else if(methodMode==1){
					addressInfo();
				}
				if(btnLoginClicked()){		
					inputLoginThread.setEditable(false);	
					inputProcessThread.setEditable(false);
				};
				
			}
		});
		btnProcess.addMouseListener(new MouseAdapter(){
			public void mouseClicked(final MouseEvent arg0){
				btnProcessClicked();
			}
		});
		inputLoginThread.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			}
			@Override
			public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
				String value=inputLoginThread.getText().trim();				
				if(value.equals("")){
					inputLoginThread.setText("30");
					return;
				}
				if(Integer.valueOf(value)<0){
					inputLoginThread.setText("30");
				}
			}
		});
		inputProcessThread.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			}
			@Override
			public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
				String value=inputProcessThread.getText().trim();				
				if(value.equals("")){
					inputProcessThread.setText("30");
					return;
				}
				if(Integer.valueOf(value)<0){
					inputProcessThread.setText("30");
				}
			}
		});
		btnSave.addMouseListener(new MouseAdapter(){
			public void mouseClicked(final MouseEvent arg0){
				btnSaveClicked();
			}
		});
		combox.addItemListener(new ItemListener() {
	           public void itemStateChanged(ItemEvent e) 
	           {
	               if(e.getStateChange() == ItemEvent.SELECTED)
	               {
	            	   String seleMethod=e.getItem().toString();
	            	   if(seleMethod.equals("下单")){	            		   
	            	   }
	               	JOptionPane.showMessageDialog(null, "您选择了"+e.getItem().toString()); 
	               }
	           }
	       });
		
	}
	
	private boolean btnLoadClicked(){
		JFileChooser fileChooser=new JFileChooser(localPath);
		FileNameExtensionFilter filter=new FileNameExtensionFilter("文本文件","txt");
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle("选择一个文件");  
		fileChooser.setFont(new Font("宋体",Font.PLAIN,20));
		int returnVal=fileChooser.showOpenDialog(null);
		if(returnVal == JFileChooser.CANCEL_OPTION){ //APPROVE_OPTION 
            return false;  
        }  
		String fileName=fileChooser.getSelectedFile().getAbsolutePath();	
		selectedFileName=fileName;
		if(fileName!=""){
			try {				
				LoadInfo(fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("error");
				e.printStackTrace();
			}
		}
		
		if(CommonInfo.accountNum==0)return false;	
		Vector dataVector=new Vector();
		methodThread=new Thread[CommonInfo.accountNum];
		for(int i=0;i<CommonInfo.accountNum;i++){
			Method method=new Method(i,btnSet,table, labelLogin,labelProcess,labelStock);
			Info info=CommonInfo.loginInfo.get(i);
			Vector rowVector=new Vector();
			rowVector.add(String.valueOf(i));
			rowVector.add(info.phone);
			rowVector.add(info.psw);
			rowVector.add("...");
			dataVector.add(rowVector);
			methodThread[i]=new Thread(method);
		}	
		
		tableModel.setDataVector(dataVector, columnVector);
		//设置第一列对齐方式
		DefaultTableCellRenderer d = new DefaultTableCellRenderer(); 
		d.setHorizontalAlignment(JLabel.CENTER); 
		TableColumn col = table.getColumn(table.getColumnName(0)); 
		col.setCellRenderer(d); 
		labelLogin.setText("0/"+String.valueOf(CommonInfo.accountNum));
		return true;
	}
	private boolean btnLoginClicked(){				
		boolean status;
		try {
			status = UUAPI.checkAPI();
			if(!status){
				JOptionPane.showConfirmDialog(null, "校验打码API错误！");
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		btnLogin.setEnabled(false);
		loginThreadNum=Integer.valueOf(inputProcessThread.getText().trim());
		int diff=loginThreadNum-(loginCurrent-loginFinish);
		while(loginCurrent<CommonInfo.accountNum && diff>0){
			methodThread[loginCurrent].start();
			loginCurrent++;
			diff--;
		}		
		return true;
	}
	private void btnSetClicked(){	
		if(btnSet.getText().toString().equals("设置线程")){
			btnSet.setText("保存设置");
			inputLoginThread.setEditable(true);
			inputProcessThread.setEditable(true);
			return;
		}
		if(btnSet.getText().toString().equals("保存设置")){
			loginThreadNum=Integer.valueOf(inputLoginThread.getText().trim());
			btnSet.setText("设置线程");
			processThreadNum=Integer.valueOf(inputProcessThread.getText().trim());
			inputLoginThread.setEditable(false);
			inputProcessThread.setEditable(false);
		}
		
	}
	private void btnProcessClicked(){
		processThreadNum=Integer.valueOf(inputProcessThread.getText().trim());
		if(CommonInfo.successNum==0)return;
		startGuessFlag=true;		
		btnProcess.setEnabled(false);
		int diff=processThreadNum-(processCurrent-processFinish);
		while(processCurrent<CommonInfo.successNum && diff>0){
			CommonInfo.processThread.get(processCurrent).start();
			processCurrent++;
			diff--;
		}			

	}
	private void btnSaveClicked(){
		try {	
			int pos=selectedFileName.lastIndexOf("\\");
			if(pos==-1)return;
			String selectedPath=selectedFileName.substring(0, pos+1);
			JFileChooser fileChooser=new JFileChooser(selectedPath);
			FileNameExtensionFilter filter=new FileNameExtensionFilter("文本文件","txt");
			fileChooser.setFileFilter(filter);
			fileChooser.setDialogTitle("保存"); 
			fileChooser.setFont(new Font("微软雅黑",Font.PLAIN,14));
			File fileSelected=new File(selectedFileName);
			fileChooser.setSelectedFile(fileSelected);
			int returnVal=fileChooser.showSaveDialog(null);
			if(returnVal == JFileChooser.CANCEL_OPTION){  
	            return ;  
	        }  
			fileWriterLogin.close();
			fileWriterGuess.close();
			bufferWriterLogin.close();
			bufferWriterGuess.close();
			String fileName=fileChooser.getSelectedFile().getAbsolutePath();
			if(fileName.indexOf(".txt")==-1){
				labelSave.setText("文件格式错误！");	
				return;
			}
			File fileGuessTmp=new File(localPath+"登录临时文件.txt");
			File fileGuess=new File(fileName);
			FileInputStream fileStreamGuessTmp=new FileInputStream(fileGuessTmp);
			FileOutputStream fileStreamGuess=new FileOutputStream(fileGuess);
			byte b[]=new byte[(int) fileGuessTmp.length()];
			fileStreamGuessTmp.read(b);
			fileStreamGuess.write(b);	
			labelSave.setText("保存成功！");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private void LoadInfo(String filename) throws IOException{
		 
		File srcFile = new File(filename);  
		FileReader ins;
		ins = new FileReader(srcFile);
		BufferedReader readBuf = new BufferedReader(ins);
		String s = null;
		CommonInfo commonInfo=new CommonInfo();
		while((s=readBuf.readLine())!=null){	   
			s=s.trim();
			if(s.equals(""))continue;
			int pos=s.indexOf("-");
			Info info=new Info();	
			info.phone=s.substring(0,pos);
			info.psw=s.substring(pos+4);
			commonInfo.loginInfo.add(info);
			commonInfo.accountNum++;
		  }
		  readBuf.close();				
	}

	private boolean addressInfo(){

		return true;
	}

}
