package com.ui;

import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.io.Serializable;  
import java.net.HttpURLConnection;  
import java.net.URL;  
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;  
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

  
public class AvioDiepen {
	private static String xpsen = "";
	private static String rowid = "";
	private static String value = "";
	public final String userName = "purchaser@betterairgroup.com";
	public final String password = "DnU9h2F4";
	public final String PN = "ABS0951B3LM018";
	public final String quantity = "1";
	
	public static void main(String[] arg){
		AvioDiepen avioDiepen = new AvioDiepen();
        System.setProperty("https.proxyHost", "localhost");
        System.setProperty("https.proxyPort", "8888");
		
		try{
			//登录
			String indexResponse = avioDiepen.IndexPage();
			setCookieValue(indexResponse);
			
			String login1Response = avioDiepen.Login1();
			setCookieValue(login1Response);

			//查询
			//part search
			String xpsenTmp =xpsen; 
			String search1Response = avioDiepen.search1();
			setXpsenValue(search1Response);
			System.out.println(search1Response);
			String search2Response = avioDiepen.search2();
			setXpsenValue(search2Response);
			System.out.println(search2Response);
			xpsen = xpsenTmp;
			String search3Response = avioDiepen.search3();
			setXpsenValue(search3Response);
			System.out.println(search3Response);
	        
		}catch(Exception e){
			
		}
	}
	private static void setXpsenValue(String response) {
		// TODO Auto-generated method stub
		JSONObject obj = JSONObject.fromObject(response);
        obj = obj.getJSONObject("xeh5");
        JSONArray xpsenmaster = obj.getJSONArray("xpsenmaster");
        xpsen = xpsenmaster.getJSONObject(0).getString("value");
        System.out.println(xpsen);
	}
	private static void setCookieValue(String response) {
		// TODO Auto-generated method stub
		JSONObject obj = JSONObject.fromObject(response);
        obj = obj.getJSONObject("xeh5");
        JSONArray cookie = obj.getJSONArray("cookie");
        value = cookie.getJSONObject(0).getString("value");
        System.out.println(value);
        xpsen = cookie.getJSONObject(0).getString("xpsen");
        System.out.println(xpsen);
        JSONArray events = obj.getJSONArray("events");
        rowid = events.getJSONObject(0).getString("rowid");
        System.out.println(rowid);
        
	}
	public String IndexPage() throws Exception{
		String serverUrl = "https://adcommerce3.avio-diepen.com/adcomsvc";
        URL url = new URL(serverUrl);  
  
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        connection.setSSLSocketFactory(sc.getSocketFactory());
        connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
        // 发送POST请求必须设置如下两行  
        connection.setDoOutput(true);  
        connection.setDoInput(true);  
        connection.setRequestMethod("POST");  
        connection.setRequestProperty("Connection", "Keep-Alive");  
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
        connection.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.addRequestProperty("Host", "adcommerce3.avio-diepen.com");
        connection.addRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        StringBuffer contentBody = new StringBuffer();  
        String content = "{\"tt-xml\":[{\"num\":1,\"parent\":0,\"nodename\":\"xeml\",\"val\":\"\",\"attlist\":\"\"},{\"num\":2,\"parent\":1,\"nodename\":\"sub\",\"val\":\"p-init\",\"attlist\":\"\"},{\"num\":3,\"parent\":1,\"nodename\":\"v-cookie\",\"val\":\"xelogin\",\"attlist\":\"\"}]}";
        contentBody.append(content);
        OutputStream out = connection.getOutputStream();  

        String contentMessage = contentBody.toString();  
        out.write(contentMessage.getBytes("utf-8"));  
        out.flush();  
        out.close();  
  
        //返回
        String strLine = "";  
        String strResponse = "";  
        InputStream in = connection.getInputStream();  
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
        while ((strLine = reader.readLine()) != null)  {  
            strResponse += strLine + "\n";  
        }  
		return strResponse;
	}
	public String Login1() throws Exception{
		String serverUrl = "https://adcommerce3.avio-diepen.com/adcomsvc";
        URL url = new URL(serverUrl);  
  
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        connection.setSSLSocketFactory(sc.getSocketFactory());
        connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
        // 发送POST请求必须设置如下两行  
        connection.setDoOutput(true);  
        connection.setDoInput(true);  
        connection.setRequestMethod("POST");  
        connection.setRequestProperty("Connection", "Keep-Alive");  
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
        connection.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.addRequestProperty("Host", "adcommerce3.avio-diepen.com");
        connection.addRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        StringBuffer contentBody = new StringBuffer();  
        String content = "{\"tt-xml\":[{\"num\":1,\"parent\":0,\"nodename\":\"xeml\",\"val\":\"\",\"attlist\":\"\"},{\"num\":2,\"parent\":1,\"nodename\":\"v-cookie\",\"val\":\""+value+"\",\"attlist\":\"\"},{\"num\":3,\"parent\":1,\"nodename\":\"xpseccookie\",\"val\":\"\",\"attlist\":\"\"},{\"num\":4,\"parent\":1,\"nodename\":\"xpsen\",\"val\":\""+xpsen+"\",\"attlist\":\"\"},{\"num\":5,\"parent\":1,\"nodename\":\"xpsenmaster\",\"val\":\""+xpsen+"\",\"attlist\":\"\"},{\"num\":6,\"parent\":1,\"nodename\":\"mode\",\"attlist\":\"\"},{\"num\":7,\"parent\":1,\"nodename\":\"sub\",\"val\":\"s_event\",\"attlist\":\"\"},{\"num\":8,\"parent\":1,\"nodename\":\"event\",\"val\":\"xemlnextevent\",\"attlist\":\"\"},{\"num\":9,\"parent\":1,\"nodename\":\"nextevent\",\"val\":\""+rowid+"\",\"attlist\":\"\"},{\"num\":10,\"parent\":1,\"val\":\"\",\"attlist\":\"\"},{\"num\":11,\"parent\":1,\"nodename\":\"values\",\"val\":\"\",\"attlist\":\"\"},{\"num\":12,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-remember\"},{\"num\":13,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-register\"},{\"num\":14,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001yes\\u0001[value]\\u0001"+ userName +"\\u0001[xwidc]\\u0001.username\"},{\"num\":15,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001yes\\u0001[value]\\u0001"+ password +"\\u0001[xwidc]\\u0001.password\"},{\"num\":16,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.department\"},{\"num\":17,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-button01\"},{\"num\":18,\"parent\":1,\"nodename\":\"enabledFields\",\"val\":\"null,.v-remember,.username,.password,.department\",\"attlist\":\"\"},{\"num\":19,\"parent\":1,\"nodename\":\"eventData\",\"attlist\":\"\"}]}";
        contentBody.append(content);
        OutputStream out = connection.getOutputStream();  

        String contentMessage = contentBody.toString();  
        out.write(contentMessage.getBytes("utf-8"));  
        out.flush();  
        out.close();  
  
        //返回
        String strLine = "";  
        String strResponse = "";  
        InputStream in = connection.getInputStream();  
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
        while ((strLine = reader.readLine()) != null)  {  
            strResponse += strLine + "\n";  
        }  
		return strResponse;
	}
	
	public String search1() throws Exception{
		String serverUrl = "https://adcommerce3.avio-diepen.com/adcomsvc";
        URL url = new URL(serverUrl);  
  
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        connection.setSSLSocketFactory(sc.getSocketFactory());
        connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
        // 发送POST请求必须设置如下两行  
        connection.setDoOutput(true);  
        connection.setDoInput(true);  
        connection.setRequestMethod("POST");  
        connection.setRequestProperty("Connection", "Keep-Alive");  
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
        connection.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.addRequestProperty("Host", "adcommerce3.avio-diepen.com");
        connection.addRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        StringBuffer contentBody = new StringBuffer(); 
        String content = "{\"tt-xml\":[{\"num\":8,\"parent\":1,\"nodename\":\"xwidid\",\"val\":\"0x00000000000054c0\",\"attlist\":\"\"},{\"num\":1,\"parent\":0,\"nodename\":\"xeml\",\"val\":\"\",\"attlist\":\"\"},{\"num\":2,\"parent\":1,\"nodename\":\"sub\",\"val\":\"s_event\",\"attlist\":\"\"},{\"num\":3,\"parent\":1,\"nodename\":\"event\",\"val\":\"menustartevent\",\"attlist\":\"\"},{\"num\":4,\"parent\":1,\"nodename\":\"v-cookie\",\"val\":\""+value+"\",\"attlist\":\"\"},{\"num\":5,\"parent\":1,\"nodename\":\"xpsenmaster\",\"val\":\""+xpsen+"\",\"attlist\":\"\"},{\"num\":6,\"parent\":1,\"nodename\":\"xpsen\",\"val\":\""+xpsen+"\",\"attlist\":\"\"},{\"num\":7,\"parent\":1,\"nodename\":\"nextevent\",\"val\":\""+rowid+"\",\"attlist\":\"\"},{\"num\":9,\"parent\":1,\"nodename\":\"openScreens\",\"val\":\"\",\"attlist\":\"\"}]}";
        System.out.println(content);
        contentBody.append(content);
        OutputStream out = connection.getOutputStream();  

        String contentMessage = contentBody.toString();  
        out.write(contentMessage.getBytes("utf-8"));  
        out.flush();  
        out.close();  
  
        //返回
        String strLine = "";  
        String strResponse = "";  
        InputStream in = connection.getInputStream();  
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
        while ((strLine = reader.readLine()) != null)  {  
            strResponse += strLine + "\n";  
        }  
		return strResponse;
	}
	public String search2() throws Exception{
		String serverUrl = "https://adcommerce3.avio-diepen.com/adcomsvc";
        URL url = new URL(serverUrl);  
  
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        connection.setSSLSocketFactory(sc.getSocketFactory());
        connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
        // 发送POST请求必须设置如下两行  
        connection.setDoOutput(true);  
        connection.setDoInput(true);  
        connection.setRequestMethod("POST");  
        connection.setRequestProperty("Connection", "Keep-Alive");  
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
        connection.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.addRequestProperty("Host", "adcommerce3.avio-diepen.com");
        connection.addRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        StringBuffer contentBody = new StringBuffer(); 
        String content = "{\"tt-xml\":[{\"num\":1,\"parent\":0,\"nodename\":\"xeml\",\"val\":\"\",\"attlist\":\"\"},{\"num\":2,\"parent\":1,\"nodename\":\"v-cookie\",\"val\":\""+value+"\",\"attlist\":\"\"},{\"num\":3,\"parent\":1,\"nodename\":\"xpseccookie\",\"val\":\"\",\"attlist\":\"\"},{\"num\":4,\"parent\":1,\"nodename\":\"xpsen\",\"val\":\""+xpsen+"\",\"attlist\":\"\"},{\"num\":5,\"parent\":1,\"nodename\":\"xpsenmaster\",\"val\":\""+xpsen+"\",\"attlist\":\"\"},{\"num\":6,\"parent\":1,\"nodename\":\"mode\",\"val\":0,\"attlist\":\"\"},{\"num\":7,\"parent\":1,\"nodename\":\"sub\",\"val\":\"s_event\",\"attlist\":\"\"},{\"num\":8,\"parent\":1,\"nodename\":\"event\",\"val\":\"xemlnextevent\",\"attlist\":\"\"},{\"num\":9,\"parent\":1,\"nodename\":\"nextevent\",\"val\":\"0x0000000000202060\",\"attlist\":\"\"},{\"num\":10,\"parent\":1,\"val\":\"\",\"attlist\":\"\"},{\"num\":11,\"parent\":1,\"nodename\":\"values\",\"val\":\"\",\"attlist\":\"\"},{\"num\":12,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-home\"},{\"num\":13,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-prtsearch\"},{\"num\":14,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001In the Part Search menu you can search aircraft parts by entering 1 up to 10 part numbers.<br /><br />\n<h3>Part search </h3>\nType or paste part numbers in the left text box and the quantity in the right text box. \nIf no quantity is entered the system will use the MOQ (Minimum Order Quantity).<br />\nClick the 'Search >>' button and ADcommerce will show you real time stock and pricing information of the part searched.<br /><br />\\u0001[xwidc]\\u0001.v-helpContent\"},{\"num\":15,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-help\"},{\"num\":16,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001yes\\u0001[value]\\u0001"+PN+"\\u0001[xwidc]\\u0001.v-partno01\"},{\"num\":17,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001yes\\u0001[value]\\u0001"+quantity+"\\u0001[xwidc]\\u0001.v-partqty01\"},{\"num\":18,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partno02\"},{\"num\":19,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partqty02\"},{\"num\":20,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partno03\"},{\"num\":21,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partqty03\"},{\"num\":22,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partno04\"},{\"num\":23,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partqty04\"},{\"num\":24,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partno05\"},{\"num\":25,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partqty05\"},{\"num\":26,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partno06\"},{\"num\":27,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partqty06\"},{\"num\":28,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partno07\"},{\"num\":29,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partqty07\"},{\"num\":30,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partno08\"},{\"num\":31,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partqty08\"},{\"num\":32,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partno09\"},{\"num\":33,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partqty09\"},{\"num\":34,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partno10\"},{\"num\":35,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-partqty10\"},{\"num\":36,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-submitFrmJs\"},{\"num\":37,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-submit\"},{\"num\":38,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-jsclick\"},{\"num\":39,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-search\"},{\"num\":40,\"parent\":11,\"nodename\":\"xwidvalue\",\"val\":\"\",\"attlist\":\"[attlist]\\u0001changed,value,xwidc\\u0001[changed]\\u0001no\\u0001[value]\\u0001\\u0001[xwidc]\\u0001.v-empty\"},{\"num\":41,\"parent\":1,\"nodename\":\"enabledFields\",\"val\":\"null,.v-helpContent,.v-partno01,.v-partno02,.v-partqty02,.v-partno03,.v-partqty03,.v-partno04,.v-partqty04,.v-partno05,.v-partqty05,.v-partno06,.v-partqty06,.v-partno07,.v-partqty07,.v-partno08,.v-partqty08,.v-partno09,.v-partqty09,.v-partno10,.v-partqty10\",\"attlist\":\"\"},{\"num\":42,\"parent\":1,\"nodename\":\"lastField\",\"val\":\".v-partqty01\",\"attlist\":\"\"},{\"num\":43,\"parent\":1,\"nodename\":\"openScreens\",\"val\":\"753743\",\"attlist\":\"\"},{\"num\":44,\"parent\":1,\"nodename\":\"eventData\",\"val\":\"eventData\",\"attlist\":\"\"}]}";
        System.out.println(content);
        contentBody.append(content);
        OutputStream out = connection.getOutputStream();  

        String contentMessage = contentBody.toString();  
        out.write(contentMessage.getBytes("utf-8"));  
        out.flush();  
        out.close();  
  
        //返回
        String strLine = "";  
        String strResponse = "";  
        InputStream in = connection.getInputStream();  
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
        while ((strLine = reader.readLine()) != null)  {  
            strResponse += strLine + "\n";  
        }  
		return strResponse;
	}
	public String search3() throws Exception{
		String serverUrl = "https://adcommerce3.avio-diepen.com/adcomsvc";
        URL url = new URL(serverUrl);  
  
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        connection.setSSLSocketFactory(sc.getSocketFactory());
        connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
        // 发送POST请求必须设置如下两行  
        connection.setDoOutput(true);  
        connection.setDoInput(true);  
        connection.setRequestMethod("POST");  
        connection.setRequestProperty("Connection", "Keep-Alive");  
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
        connection.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.addRequestProperty("Host", "adcommerce3.avio-diepen.com");
        connection.addRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        StringBuffer contentBody = new StringBuffer(); 
        String content = "{\"tt-xml\":[{\"num\":1,\"parent\":0,\"nodename\":\"xeml\",\"val\":\"\",\"attlist\":\"\"},{\"num\":2,\"parent\":1,\"nodename\":\"v-cookie\",\"val\":\""+value+"\",\"attlist\":\"\"},{\"num\":3,\"parent\":1,\"nodename\":\"xpsenparent\",\"val\":\""+xpsen+"\",\"attlist\":\"\"},{\"num\":4,\"parent\":1,\"nodename\":\"sub\",\"val\":\"p-init\",\"attlist\":\"\"},{\"num\":5,\"parent\":1,\"nodename\":\"v-xproc\",\"val\":\"0x00000000000054c1\",\"attlist\":\"\"}]}";
        System.out.println(content);
        contentBody.append(content);
        OutputStream out = connection.getOutputStream();  

        String contentMessage = contentBody.toString();  
        out.write(contentMessage.getBytes("utf-8"));  
        out.flush();  
        out.close();  
  
        //返回
        String strLine = "";  
        String strResponse = "";  
        InputStream in = connection.getInputStream();  
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
        while ((strLine = reader.readLine()) != null)  {  
            strResponse += strLine + "\n";  
        }  
		return strResponse;
	}
	public String getCookie(HttpsURLConnection connection){
		String cookieVal="";
		List<String> list=new ArrayList<String>();
		 Map<String, List<String>> map = connection.getHeaderFields();
         // 遍历所有的响应头字段
        for (String key : map.keySet()) {
            // System.out.println(key + "--->" + map.get(key));
        	if(key==null)continue;
        	if(key.toLowerCase().equals("set-cookie")){
        		list=map.get(key);
        		break;
        	}
         }
        for(int i=0;i<list.size();i++)
        	cookieVal+=list.get(i)+";";
		return cookieVal;
	}
	
	private static class TrustAnyTrustManager implements X509TrustManager {
	    
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
    
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}  