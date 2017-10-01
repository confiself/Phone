package com.web;
import java.io.IOException;
import java.util.Random;

import javax.script.Invocable; 
import javax.script.ScriptEngine; 
import javax.script.ScriptEngineManager; 
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;

import com.ui.Mobile;
public class Process extends CommonInfo implements Runnable{
	private int id,guessRow;
	private String cookie;
	private String cid;
	private String phone;
	private JTable table;
	private JButton btnSet;
	private JLabel labelProcess;
	Process(int id,JButton btnSet,JTable table,JLabel labelProcess){
		this.id=id;
		this.btnSet=btnSet;
		this.table=table;
		this.labelProcess=labelProcess;	
		phone=CommonInfo.successInfo.get(id).phone;
		cookie=CommonInfo.successInfo.get(id).cookie;
		cid=CommonInfo.successInfo.get(id).cid;
		guessRow=CommonInfo.successInfo.get(id).guessRow;
	}
	public void run(){
		String result="";
		String tmp;
		for(int i=0;i<4;i++){
			tmp=guess();
			if(tmp!="")
				result+=tmp+"|";
		}
		if(result=="")tmp="猜完|没一个中！";
		else tmp="猜中了|"+result;
		synchronized(Mobile.lock){
			table.setValueAt(tmp, guessRow, 3);
			Mobile.processFinish++;
		}
	//	if(result!=""){
			synchronized(Mobile.lockFile){
				try {
					Mobile.bufferWriterGuess.write(phone+"----"+tmp+"\r\n");
					Mobile.bufferWriterGuess.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		//}
		
		processStartNew();
		
	}
	private void processStartNew(){
		int diff=Mobile.processThreadNum-(Mobile.processCurrent-Mobile.processFinish);
		while(Mobile.processCurrent<CommonInfo.successNum && diff>0){
			CommonInfo.processThread.get(Mobile.processCurrent).start();
			Mobile.processCurrent++;	
			diff--;
		}
		String result=Mobile.processFinish+"/"+CommonInfo.successNum;
		Mobile.labelProcess.setText(result);
		if(Mobile.loginFinish==CommonInfo.accountNum && Mobile.processFinish==CommonInfo.successNum){
			labelProcess.setText("猜完!");			
			btnSet.setEnabled(false);
		}
	}
	public String guess(){	
		String num=getNum();			
		String url="https://clientaccess.10086.cn:9043/leadeon-cmcc-events/api/guess/lottery?"+cookie+";Comment=SessionServer-cmcc;Path=/;Secure";
		String param="{\"cid\":\"[cid]\",\"en\":\"0\",\"t\":\"[cook];Comment=SessionServer-cmcc;Path=/;Secure\",\"sn\":\"\",\"cv\":\"2.1.0\",\"st\":\"1\",\"sv\":\"4.4.2\",\"sp\":\"\",\"reqBody\":{\"numb\":#}}";
		param=param.replace("[cook]", cookie);
		param=param.replace("[cid]", cid);
		param=param.replace("#", num);
		HttpRequest httpRequest=new HttpRequest(url);
		httpRequest.setRequestProperty("Content-Type","application/Json");
		httpRequest.setRequestProperty("x-forwarded-for", getIP());
		httpRequest.setRequestProperty("client_ip", getIP());
		String result=httpRequest.sendPost(param);
		
		if(result.indexOf("恭喜您获得") != -1){
			int pos=result.indexOf("msg");
			result=result.substring(pos+6);
			pos=result.indexOf("\"");
			result=result.substring(0,pos);
			result.replace("恭喜您获得：", "");
		}
		else{
			result="";
		}		
		System.out.println(result);
		return result;
	}
	private String  getNum(){
		ScriptEngineManager mgr = new ScriptEngineManager(); 
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String js="function getNumber() {var c = 99;var b = 3;var a = 10;var d = generateGuessNumber(c, b, a);return d.start+\"|\"+d.end;}";
		js+="function generateGuessNumber(c, b, a) {var d = {};d.start = getRandomNum(10, c - a);d.end = getRandomNum(d.start + b, d.start + a); d.end = getRandomNum(d.start + b, d.start + a);return d;}";
		js+="function getRandomNum(c, a) { var b = a - c;var d = Math.random();return (c + Math.round(d * b));}";
		String num="";
		try {
			engine.eval(js);
			Invocable inv = (Invocable) engine; 
			num=(String)inv.invokeFunction("getNumber");
			System.out.println(num);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int pos=num.indexOf("|");
		int value=Integer.valueOf(num.substring(0,pos));
		num=String.valueOf(value+1);
		return num;
	}
	private String getIP(){
		Random random=new Random(id);
		String ip=String.valueOf(Math.abs(random.nextInt())%254)+1;
		ip+="."+String.valueOf(Math.abs(random.nextInt())%254)+1;
		ip+="."+String.valueOf(Math.abs(random.nextInt())%254)+1;
		ip+="."+String.valueOf(Math.abs(random.nextInt())%254)+1;
		return ip;
}

}
