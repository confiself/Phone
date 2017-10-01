package com.web;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import com.ui.Mobile;

public class Method extends CommonInfo implements Runnable{
//public class Method extends Thread{
	public int id;
	private JButton btnSet;
	private JTable table;
	private JLabel labelLogin,labelProcess,labelStock;
	private String phone,psw;
	private String picPath;
	public void run(){
		synchronized(Mobile.lock){
			table.setValueAt("登录中...", id, 3);
		}
		
		//String result=modifyAddressWeb();
		String result=buyGoods();
		if(result.indexOf("成功")!=-1)CommonInfo.successNum++;
		//String result=modifyAddress();
		synchronized(Mobile.lock){
			table.setValueAt(result, id, 3);
			Mobile.loginFinish++;
		}
		synchronized(Mobile.lockFile){
			try {
				Mobile.bufferWriterLogin.write(phone+"----"+result+"\r\n");
				Mobile.bufferWriterLogin.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		loginStartNew();
		if(Mobile.startGuessFlag)processStartNew();
	}
	public Method(int ID,JButton btnSet,JTable table,JLabel labelLogin,JLabel labelProcess,JLabel labelStock){
		id=ID;
		this.btnSet=btnSet;
		this.table=table;
		this.labelLogin=labelLogin;
		this.labelProcess=labelProcess;
		this.labelStock=labelStock;
		phone=CommonInfo.loginInfo.get(id).phone;
		psw=CommonInfo.loginInfo.get(id).psw;
		picPath=Mobile.localPath+"pic";
	}
	public String buyGoodsWeb(){//下单未成功
		System.out.println("线程"+id+"登录");	
		String result="";
		String url="https://login.10086.cn/captchazh.htm?type=05";
		HttpRequest httpRequest=new HttpRequest(url);
		String picFileName=picPath+"\\"+id+".jpg";
		if(!httpRequest.sendGet(picFileName))return "下载验证码失败";
		String cookie=httpRequest.getCookie();	
		
		
		String recResult=UUAPI.uu_easyRec(picFileName,5006);
		int pos;
		if((pos=recResult.indexOf("_"))==-1)return "验证码返回有误";
		recResult=recResult.substring(pos+1).trim();
		System.out.println("recResult= "+recResult);	
		
		String refer="";
		url="https://login.10086.cn/verifyCaptcha?inputCode="+recResult.trim();
		httpRequest=new HttpRequest(url);
		refer="https://login.10086.cn/html/login/login.html?channelID=12002&backUrl=http%3A%2F%2Fshop.10086.cn%2Fmall_771_771.html%3Fforcelogin%3D1";
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("Cookie",cookie);
		result=httpRequest.sendGet();
		System.out.println(result);
		
		StringBuilder strBuilder=new StringBuilder();
		url="https://login.10086.cn/login.htm?accountType=01";
		strBuilder.append(url);
		strBuilder.append("&account="+phone);
		strBuilder.append("&password="+psw);
		strBuilder.append("+&pwdType=01");
		strBuilder.append("&inputCode="+recResult);
		strBuilder.append("&backUrl=http%3A%2F%2Fshop.10086.cn%2Fmall_200_200.html%3Fforcelogin%3D1&rememberMe=0&channelID=12002&protocol=https%3A&isNew=1");

		url=strBuilder.toString();
		httpRequest=new HttpRequest(url);
		refer=" https://login.10086.cn/html/login/login.html?channelID=12002&backUrl=http%3A%2F%2Fshop.10086.cn%2Fmall_200_200.html%3Fforcelogin%3D1";
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("Host", "login.10086.cn");
		httpRequest.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
		httpRequest.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
		httpRequest.setRequestProperty("Cookie",cookie);
		httpRequest.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
		result=httpRequest.sendGet();
		String show="";
		try {
			show = new String(result.getBytes("gbk"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("登录返回"+show);
		if(show.indexOf("系统繁忙")!=-1)return "系统繁忙";
		else if(show.indexOf("验证码")!=-1)return "验证码错误";
		String artifact="";
		if(show.indexOf("认证成功")!=-1){
		cookie=httpRequest.getCookie();
		int i=show.indexOf("fact\":");
		show=show.substring(i+7);
		i=show.indexOf("\"");
		show=show.substring(0,i);
		artifact=show;
		System.out.println("artifact="+artifact);	
		}			


		url="http://shop.10086.cn/sso/getartifact.php?backUrl=http%3A%2F%2Fshop.10086.cn%2Fmall_200_200.html%3Fforcelogin%3D1&artifact="+artifact;
		httpRequest=new HttpRequest(url);
		httpRequest.setRequestProperty("cookie", cookie);
		httpRequest.setRequestProperty("Host", "login.10086.cn");
		result=httpRequest.sendGet();
		System.out.println(result);
		
		url="http://shop.10086.cn/mall_200_200.html";
		httpRequest=new HttpRequest(url);
		httpRequest.setRequestProperty("cookie", cookie);
		httpRequest.setRequestProperty("Host", "login.10086.cn");
		result=httpRequest.sendGet();

		url="http://shop.10086.cn/ajax/user/userinfo.json?province_id=200&city_id=200&callback=initUser";
		httpRequest=new HttpRequest(url);
		refer="http://shop.10086.cn/mall_200_200.html";
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("cookie", cookie);
		httpRequest.setRequestProperty("Host", "login.10086.cn");
		result=httpRequest.sendGet();
		System.out.println(result);
		
		url="http://shop.10086.cn/ajax/detail/getstock.json?goods_id=1025815&merchant_id=1000049&sale_type=1&sku_id=1017870 ";
		httpRequest=new HttpRequest(url);
		refer="http://shop.10086.cn/goods/200_200_1025815_1017870.html";
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("cookie", cookie);
		result=httpRequest.sendGet();
		System.out.println(result);
		
		url="http://shop.10086.cn/ajax/cart/add.json";
		httpRequest=new HttpRequest(url);
		String param="sku%5B0%5D%5Bmodel_id%5D=1017870&sku%5B0%5D%5Bgoods_id%5D=1025815&sku%5B0%5D%5Bnum%5D=1&sku%5B0%5D%5Bdetail_id%5D=0&province_id=200&city_id=200";
		refer="http://shop.10086.cn/goods/200_200_1025815_1017870.html";
		httpRequest.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("cookie", cookie);
		result=httpRequest.sendPost(param);
		System.out.println(result);
		String cart_code="";
		result=result.substring(result.indexOf("cart_code"));
		cart_code=result.substring(0,result.indexOf("\""));
		
		url="http://shop.10086.cn/order/checkorder.php?"+cart_code;
		httpRequest=new HttpRequest(url);
		refer="http://shop.10086.cn/goods/200_200_1025815_1017870.html";
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("cookie", cookie);
		result=httpRequest.sendGet();
		System.out.println(result);
		
		
		url="http://shop.10086.cn/i/v1/cust/recaddr/"+phone+"?time=2015102175917944";
		httpRequest=new HttpRequest(url);
		refer="http://shop.10086.cn/i/?f=recaddrmag&welcome=1443780249404";
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("cookie", cookie);
		result=httpRequest.sendGet();
		System.out.println(result);
		result=result.substring(result.indexOf("recAddrId")+12);
		String recAddrId=result.substring(0,result.indexOf("\""));
		
		
		url="http://shop.10086.cn/ajax/order/addorder.json";
		httpRequest=new HttpRequest(url);
		strBuilder.delete(0, strBuilder.length());
		strBuilder.append("address_id="+recAddrId);
		strBuilder.append("&invoice_id=personal&pay_type=1&coupon_type=&eticket_number_mall=");
		strBuilder.append("&"+cart_code);
		strBuilder.append("&"+cart_code);
		param=strBuilder.toString();
		refer=" http://shop.10086.cn/order/checkorder.php?"+cart_code;
		httpRequest.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("cookie", cookie);
		result=httpRequest.sendPost(param);
		result=unicode2String(result);
		System.out.println(result);		
		if(result.indexOf("下单成功")!=-1)
			result="下单成功";
		else
			result="下单失败";

		return result;
	}
	public String modifyAddressWeb(){
		System.out.println("线程"+id+"登录");	
		String result="";
		String url="https://login.10086.cn/captchazh.htm?type=05";
		HttpRequest httpRequest=new HttpRequest(url);
		String picFileName=picPath+"\\"+id+".jpg";
		if(!httpRequest.sendGet(picFileName))return "下载验证码失败";
		String cookie=httpRequest.getCookie();	
		
		
		String recResult=UUAPI.uu_easyRec(picFileName,5006);
		int pos;
		if((pos=recResult.indexOf("_"))==-1)return "验证码返回有误";
		recResult=recResult.substring(pos+1).trim();
		System.out.println("recResult= "+recResult);	
		
		String refer="";
		url="https://login.10086.cn/verifyCaptcha?inputCode="+recResult.trim();
		httpRequest=new HttpRequest(url);
		refer="https://login.10086.cn/html/login/login.html?channelID=12002&backUrl=http%3A%2F%2Fshop.10086.cn%2Fmall_771_771.html%3Fforcelogin%3D1";
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("Cookie",cookie);
		result=httpRequest.sendGet();
		System.out.println(result);
		
		StringBuilder strBuilder=new StringBuilder();
		url="https://login.10086.cn/login.htm?accountType=01";
		strBuilder.append(url);
		strBuilder.append("&account="+phone);
		strBuilder.append("&password="+psw);
		strBuilder.append("+&pwdType=01");
		strBuilder.append("&inputCode="+recResult);
		strBuilder.append("&backUrl=http%3A%2F%2Fshop.10086.cn%2Fmall_771_771.html%3Fforcelogin%3D1&rememberMe=0&channelID=12002&protocol=https%3A");
	
		url=strBuilder.toString();
		httpRequest=new HttpRequest(url);
		refer="https://login.10086.cn/html/login/login.html?channelID=12002&backUrl=http%3A%2F%2Fshop.10086.cn%2Fmall_771_771.html%3Fforcelogin%3D1";
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("Host", "login.10086.cn");
		httpRequest.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
		httpRequest.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
		httpRequest.setRequestProperty("Cookie",cookie);
		httpRequest.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
		result=httpRequest.sendGet();
		String show="";
		try {
			show = new String(result.getBytes("gbk"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("登录返回"+show);
		if(show.indexOf("系统繁忙")!=-1)return "系统繁忙";
		else if(show.indexOf("验证码")!=-1)return "验证码错误";
		String artifact="";
		if(show.indexOf("认证成功")!=-1){
		cookie=httpRequest.getCookie();
		int i=show.indexOf("fact\":");
		show=show.substring(i+7);
		i=show.indexOf("\"");
		show=show.substring(0,i);
		artifact=show;
		System.out.println("artifact="+artifact);	
		}			
		String urlLoginInfo="http://shop.10086.cn/i/v1/auth/loginfo?time=2015102153753867";
		httpRequest=new HttpRequest(urlLoginInfo);
		refer="http://shop.10086.cn/i/?f=recaddrmag";
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("cookie", cookie);
		result=httpRequest.sendGet();
		cookie+=httpRequest.getCookie();
		
		url="http://shop.10086.cn/i/v1/auth/getArtifact?backUrl=http%3A%2F%2Fshop.10086.cn%2Fi%2F%3Ff%3Drecaddrmag&artifact="+artifact;
		httpRequest=new HttpRequest(url);
		refer="http://shop.10086.cn/i/?f=recaddrmag";
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("cookie", cookie);
		result=httpRequest.sendGet();
		cookie+=httpRequest.getCookie();
		
		url="http://shop.10086.cn/i/?f=recaddrmag&welcome=1443771768490";
		httpRequest=new HttpRequest(url);
		refer="http://shop.10086.cn/i/?f=recaddrmag";
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("cookie", cookie);
		result=httpRequest.sendGet();
		
		url="http://shop.10086.cn/i/v1/auth/loginfo?time=2015102153756304";
		httpRequest=new HttpRequest(url);
		refer="http://shop.10086.cn/i/?f=recaddrmag&welcome=1443771768490";
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("cookie", cookie);
		result=httpRequest.sendGet();
		
		url="http://shop.10086.cn/i/v1/cust/recaddr/"+phone+"?time=201510216031932";
		httpRequest=new HttpRequest(url);
		String param="{\"recName\":\"1234561\",\"recPhoneNo\":\"13945454545\",\"zipCode\":\"123456\",\"recTel\":\"\",\"provinceCode\":\"440000\",\"cityCode\":\"440200\",\"regionCode\":\"440204\",\"street\":\"abcdefg\",\"isDefault\":\"1\"}";
		refer="http://shop.10086.cn/i/?f=recaddrmag&welcome=1443771768490";
		httpRequest.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		httpRequest.setRequestProperty("Referer", refer);
		httpRequest.setRequestProperty("cookie", cookie);
		result=httpRequest.sendPost(param);
		try {
			show = new String(result.getBytes("gbk"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(show);
		if(show.indexOf("添加常用收货地址成功")!=-1)
			result="修改地址成功";
		else
			result="修改地址失败";

		return result;
	}	
	public String modifyAddress(){
		System.out.println("线程"+id+"登录");	
		String url="https://clientaccess.10086.cn:9043/biz-orange/LN/uamlogin/login";
		String param="{\"cid\":\"\",\"cv\":\"2.2.0\",\"en\":\"3\",\"reqBody\":{\"ccPasswd\":\"[pass]\",\"cellNum\":\"[phone]\",\"sendSmsFlag\":\"1\"},\"sn\":\"CHM-TL00H\",\"sp\":\"720x1280\",\"st\":\"1\",\"sv\":\"4.4.2\",\"t\":\"\"}";	
		param=param.replace("[phone]", phone);
		param=param.replace("[pass]",psw);
		HttpRequest httpRequest=new HttpRequest(url);
		httpRequest.setRequestProperty("Content-Type","application/Json");
		httpRequest.setRequestProperty("x-forwarded-for", getIP());
		httpRequest.setRequestProperty("client_ip", getIP());
		String result=httpRequest.sendPost(param);
		String cookie=httpRequest.getCookie();			
		try {
			result = new String(result.getBytes("gbk"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("result1="+result);
		
		if(cookie.equals(""))return "";
		cookie=cookie.substring(0, cookie.length()-1);
		System.out.println("cookie"+cookie);
		
		if(cookie.indexOf("JSESSIONID") == -1)return "登录失败";		
		url="https://clientaccess.10086.cn/biz-orange/SHG/receiveAdmin/addReceivingAddrInfo?";//+cookie;
		param="{\"cid\":\"\",\"cv\":\"2.1.0\",\"en\":\"3\",\"t\":\"[cookie]\",\"reqBody\":{\"cellNum\":\"[phone]\",\"provinceCode\":\"440000\",\"cityCode\":\"440100\",\"regionCode\":\"440107\",\"street\":\"[street]\",\"zipCode\":null,\"recName\":\"[name]\",\"recPhoneNo\":\"[phone]\",\"recTel\":null,\"isDefault\":\"1\"},\"sn\":\"\",\"sp\":\"\",\"st\":\"1\",\"sv\":\"4.4.2\",\"t\":\"\"}";
		param=param.replace("[cookie]", cookie);
		param=param.replace("[phone]", phone);
		String street="华工北三";
		String name="胡1";
		param=param.replace("[street]", street);
		param=param.replace("[name]", name);
		httpRequest=new HttpRequest(url);
		httpRequest.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		httpRequest.setRequestProperty("Referer","https://clientdispatch.10086.cn:9043/leadeon-cmcc-static/v2.0/pages/mall/address/c_address_add.html?operator=add&actionPage=&goodsId=&skuId=&num=&payWay=&contractId=&contractPhone=&contractName=&recAddrId=&invoiceId=&invoiceAddr=&invoiceType=");
		httpRequest.setRequestProperty("Host","clientaccess.10086.cn");
		httpRequest.setRequestProperty("Origin","https://clientdispatch.10086.cn:9043");
		httpRequest.setRequestProperty("Cookie",cookie);
		result=httpRequest.sendPost(param);
		System.out.println(result);
		if(result.indexOf("SUCCESS")!=-1)return "修改地址成功";		
		return "修改地址失败";
	}	
	private String buyGoods(){
		System.out.println("线程"+id+"登录");	
		String url="https://clientaccess.10086.cn:9043/biz-orange/LN/uamlogin/login";
		String param="{\"cid\":\"\",\"cv\":\"2.2.0\",\"en\":\"3\",\"reqBody\":{\"ccPasswd\":\"[pass]\",\"cellNum\":\"[phone]\",\"sendSmsFlag\":\"1\"},\"sn\":\"CHM-TL00H\",\"sp\":\"720x1280\",\"st\":\"1\",\"sv\":\"4.4.2\",\"t\":\"\"}";	
		param=param.replace("[phone]", phone);
		param=param.replace("[pass]",psw);
		HttpRequest httpRequest=new HttpRequest(url);
		httpRequest.setRequestProperty("Content-Type","application/Json");
		httpRequest.setRequestProperty("x-forwarded-for", getIP());
		httpRequest.setRequestProperty("client_ip", getIP());
		String result=httpRequest.sendPost(param);
		String cookie=httpRequest.getCookie();			

		System.out.println("result1="+result);
		
		if(cookie.equals(""))return "";
		cookie=cookie.substring(0, cookie.length()-1);
		System.out.println("cookie"+cookie);
		
		if(cookie.indexOf("JSESSIONID") == -1)return "登录失败";	
		/*
		//获取库存		
		url="https://clientaccess.10086.cn/biz-orange/SHS/goodsSku/skuStock"; 
		param="{\"cid\":\"\",\"en\":\"0\",\"t\":\"[cookie]\",\"sn\":\"iPhone7,2\",\"cv\":\"2.2.0\",\"st\":\"2\",\"sv\":\"9.0.2\",\"sp\":\"750x1334\",\"reqBody\":{\"skuId\":[skuId]}}";
		param=param.replace("[cookie]", cookie);
		param=param.replace("[skuId]", String.valueOf(Mobile.modelID));
		httpRequest=new HttpRequest(url);
		httpRequest.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		httpRequest.setRequestProperty("Referer"," https://clientdispatch.10086.cn:9043/leadeon-cmcc-static/v2.0/pages/mall/productwith/productwith.html?prodId=1022557&adverType=7&adverLocation=6");
		httpRequest.setRequestProperty("Host","clientaccess.10086.cn");
		httpRequest.setRequestProperty("Origin","https://clientdispatch.10086.cn:9043");
		httpRequest.setRequestProperty("Cookie",cookie);
		result=httpRequest.sendPost(param);
		System.out.println(result);
		String stockInfo=result.substring(result.indexOf("stock\""));
		stockInfo=stockInfo.substring(0, stockInfo.indexOf("}"));
		String stock=stockInfo.substring(stockInfo.indexOf(":")+1,stockInfo.indexOf(","));		
		stock=stock.replace("\"", "");
		System.out.println("stock----"+stock);
		if(stock.equals("0"))return "库存为空";
		String lock=stockInfo.substring(stockInfo.indexOf("lock")+6);
		lock=lock.replace("\"", "");
		synchronized(Mobile.lock){
			labelStock.setText(stock+"/"+lock);
		}		
		//查看地址	
		url="https://clientaccess.10086.cn/biz-orange/SHG/receiveAdmin/getDefaultsReceivingAddrInfo"; 
		param="{\"cid\":\"\",\"en\":\"0\",\"t\":\"[cookie]\",\"sn\":\"iPhone7,2\",\"cv\":\"2.2.0\",\"st\":\"2\",\"sv\":\"9.0.2\",\"sp\":\"750x1334\",\"reqBody\":{\"cellNum\":\"[phone]\"}}";
		param=param.replace("[cookie]", cookie);
		param=param.replace("[phone]", phone);
		httpRequest=new HttpRequest(url);
		httpRequest.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		httpRequest.setRequestProperty("Referer"," https://clientdispatch.10086.cn:9043/leadeon-cmcc-static/v2.0/pages/mall/order/c_orderDetail.html?goodsId=1022546&skuId=1015922&num=1&contractPhone=13430301964&contractName=undefined");
		httpRequest.setRequestProperty("Host","clientaccess.10086.cn");
		httpRequest.setRequestProperty("Origin","https://clientdispatch.10086.cn:9043");
		httpRequest.setRequestProperty("Cookie",cookie);
		result=httpRequest.sendPost(param);
		System.out.println(result);
		String recAddrId=result.substring(result.indexOf("recAddrId"));
	    recAddrId=recAddrId.substring(recAddrId.indexOf(":")+1,recAddrId.indexOf(","));		
	    recAddrId=recAddrId.replace("\"", "").trim();	 
	    System.out.println("recAddrId----"+recAddrId);
	    if(recAddrId.equals(""))return "地址为空";	
	    
		//下单		
		url="https://clientaccess.10086.cn/biz-orange/SHS/submitorder/submitOrder"; 
		param="{\"cid\":\"\",\"en\":\"0\",\"t\":\"[cookie]\",\"sn\":\"iPhone7,2\",\"cv\":\"2.2.0\",\"st\":\"2\",\"sv\":\"9.0.2\",\"sp\":\"750x1334\",\"reqBody\":{\"provinceCode\":\"200\",\"cityCode\":\"020\",\"cellNum\":\"[phone]\",\"addressId\":\"[addressId]\",\"payType\":1,\"invoiceId\":\"\",\"skuList\":[{\"prodId\":\"[prodId]\",\"modelId\":\"[modelId]\",\"num\":\"1\",\"contractId\":null,\"detailId\":null,\"parentId\":null,\"packageId\":null}]}}";
		param=param.replace("[cookie]", cookie);
		param=param.replace("[phone]", phone);
		param=param.replace("[addressId]",recAddrId);
		param=param.replace("[prodId]", String.valueOf(Mobile.prodID));
		param=param.replace("[modelId]", String.valueOf(Mobile.modelID));
		httpRequest=new HttpRequest(url);
		httpRequest.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		httpRequest.setRequestProperty("Referer","https://clientdispatch.10086.cn:9043/leadeon-cmcc-static/v2.0/pages/mall/order/c_orderDetail.html?goodsId=1022546&skuId=1015922&num=1&contractPhone=13430301964&contractName=undefined");
		httpRequest.setRequestProperty("Host","clientaccess.10086.cn");
		httpRequest.setRequestProperty("Origin","https://clientdispatch.10086.cn:9043");
		httpRequest.setRequestProperty("Cookie",cookie);
		result=httpRequest.sendPost(param);		
		try {
			result = new String(result.getBytes("gbk"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		if(result.indexOf("SUCCESS")==-1)return "下单失败";	*/
		return "下单成功";
	}
	private void loginStartThread(){		
		if(Mobile.loginCurrent<CommonInfo.accountNum){			
			Mobile.methodThread[Mobile.loginCurrent].start();	
			Mobile.loginCurrent++;		
		}
		String result=Mobile.loginFinish+"/"+CommonInfo.accountNum;
		synchronized(Mobile.lock){
			Mobile.labelLogin.setText(result);
			Mobile.labelProcess.setText("0/"+String.valueOf(CommonInfo.successNum));
		}
		if(Mobile.loginFinish==CommonInfo.accountNum){
			labelLogin.setText("登录完毕!");
		}
	}
	private void loginStartNew(){
		int diff=Mobile.loginThreadNum-(Mobile.loginCurrent-Mobile.loginFinish);
		while(Mobile.loginCurrent<CommonInfo.accountNum && diff>0){
			Mobile.methodThread[Mobile.loginCurrent].start();
			Mobile.loginCurrent++;
			diff--;
		}
		String result=Mobile.loginFinish+"/"+CommonInfo.accountNum;
		synchronized(Mobile.lock){
			Mobile.labelLogin.setText(result);
			Mobile.labelProcess.setText("0/"+String.valueOf(CommonInfo.successNum));
		}
		if(Mobile.loginFinish==CommonInfo.accountNum){
			labelLogin.setText("登录完毕!");
		}
		
	}
	private void processStartNew(){
		int diff=Mobile.processThreadNum-(Mobile.processCurrent-Mobile.processFinish);
		while(Mobile.processCurrent<CommonInfo.successNum && diff>0){
			CommonInfo.processThread.get(Mobile.processCurrent).start();
			Mobile.processCurrent++;	
		}
	}
	private String getIP(){
		Random random=new Random(id);
		String ip=String.valueOf(Math.abs(random.nextInt())%254)+1;
		ip+="."+String.valueOf(Math.abs(random.nextInt())%254)+1;
		ip+="."+String.valueOf(Math.abs(random.nextInt())%254)+1;
		ip+="."+String.valueOf(Math.abs(random.nextInt())%254)+1;
		return ip;
	}
	private String getCid(){
		String url="https://clientaccess.10086.cn:9043/leadeon-cmcc-biz/createCid/getCreateCid";
		//String url="https://clientaccess.10086.cn:9043/biz-orange/createCid/getCreateCid";
		String param="{\"cid\":\"\",\"cv\":\"2.0.0\",\"en\":\"0\",\"reqBody\":{\"imei\":\"#\",\"macAdd\":\"@\"},\"sn\":\"\",\"sp\":\"\",\"st\":\"1\",\"sv\":\"4.4.2\",\"t\":\"\"}";		
		param=param.replace("#",getImei());
		param=param.replace("@",getMac());
		HttpRequest httpRequest=new HttpRequest(url);
		httpRequest.setRequestProperty("Content-Type","application/Json");
		httpRequest.setRequestProperty("x-forwarded-for", getIP());
		httpRequest.setRequestProperty("client_ip", getIP());
		String result=httpRequest.sendPost(param);
		//System.out.println(result);
		int index=result.indexOf("cid");
		result=result.substring(index+6);
		int end=result.indexOf("\"}");
		result=result.substring(0,end);
		return result;
	}
	private String getImei(){
		String imei=phone;//"62282616187021";//		
		imei+=imei.substring(8);//14位
		int n=0;
		for(int i=0;i<14;i++){
			int a=Integer.valueOf(imei.substring(i, i+1));
			if(i%2==1){
				a=a*2;
				if(a>=10){
					n=n+a%10+a/10;
				}
				else{
					n=n+a;
				}
			}
			else{
				n+=a;
			}
		}
		//System.out.println("n is"+n);
		n=n%10;
		if(n==0){
			imei+="0";
		}
		else{
			imei+=String.valueOf(10-n);
		}	
		//System.out.println(imei);
		return imei;
	}
	private String getMac(){
		String mac="";
		int count=0;
		for(int i=0;i<psw.length();i++){			
			if(Character.isAlphabetic(psw.charAt(i))){
				mac+=psw.substring(i,i+1).toLowerCase();
				count++;
				if(count==12)break;
				if(count%2==0)mac+=":";
			}
		}	
		//System.out.println(mac);
		return mac;
	}
	
	private String unicode2String(String unicodeStr){
	    StringBuffer sb = new StringBuffer();
	    String str[] = unicodeStr.toUpperCase().split("U");
	    for(int i=0;i<str.length;i++){
	      if(str[i].equals("")) continue;
	      char c = (char)Integer.parseInt(str[i].trim(),16);
	      sb.append(c);
	    }
	    return sb.toString();
	  }
	
}

