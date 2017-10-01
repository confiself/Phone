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

import org.apache.http.client.HttpClient;
import org.apache.http.entity.mime.content.StringBody;

  
public class HttpPostEmulator {
	public static void main(String[] arg){
		HttpPostEmulator emulator = new HttpPostEmulator();
		String cookie = emulator.getLogin();
		try{
			String cookieLogin = emulator.postLogin(cookie);
			System.out.println(cookieLogin);
			String cookieAll = cookieLogin + cookie;
			String arr[] = cookieAll.split(";");
			System.out.println(arr[0] + ";" + arr[6]);
			emulator.postMain(arr[0] + ";" + arr[6]);
			emulator.postMain(arr[0] + ";" + arr[6]);
		}catch(Exception e){
			
		}
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

	private void postTest() throws Exception {
		String serverUrl = "http://rapdb.dna.affrc.go.jp/tools/converter/run";
        ArrayList<FormFieldKeyValuePair> ffkvps = new ArrayList<FormFieldKeyValuePair>();  

        ffkvps.add(new FormFieldKeyValuePair("keyword", "Os12g0614600"));  
        ffkvps.add(new FormFieldKeyValuePair("submit", "Convert"));  
        ffkvps.add(new FormFieldKeyValuePair("selectDB", "RtM")); 
  
        HttpPostEmulator hpe = new HttpPostEmulator();   
        URL url = new URL(serverUrl);  
  
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
        // 发送POST请求必须设置如下两行  
        connection.setDoOutput(true);  
        connection.setDoInput(true);  
//        connection.setUseCaches(false);  
        connection.setRequestMethod("POST");  
        connection.setRequestProperty("Connection", "Keep-Alive");  
        connection.setRequestProperty("Host","rapdb.dna.affrc.go.jp");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
        connection.addRequestProperty("Content-Type", "multipart/form-data; boundary="+BOUNDARY);
        String boundary = BOUNDARY;  
        // 传输内容  
        StringBuffer contentBody = new StringBuffer("--" + boundary);  
        String endBoundary = "\r\n--" + boundary + "--\r\n";  
        OutputStream out = connection.getOutputStream();  
        for (FormFieldKeyValuePair ffkvp : ffkvps)  {  
            contentBody.append("\r\n")  
            .append("Content-Disposition: form-data; name=\"")  
            .append(ffkvp.getKey() + "\"")  
            .append("\r\n")  
            .append("\r\n")  
            .append(ffkvp.getValue())  
            .append("\r\n")  
            .append("--")  
            .append(boundary);  
        }  
        String boundaryMessage1 = contentBody.toString();  
        out.write(boundaryMessage1.getBytes("utf-8"));  
        out.write(endBoundary.getBytes("utf-8"));  
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
        System.out.print(strResponse);  
        
	}
	private String  getLogin(){
		String cookie = "";
		try{
			String serverUrl = "https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/public/login.do";//上传地址  
			URL url = new URL(serverUrl);  
		  
	        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
	        connection.setSSLSocketFactory(sc.getSocketFactory());
	        connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
	        connection.setRequestProperty("Connection", "Keep-Alive");  
	        connection.setRequestMethod("GET");  
	        connection.setRequestProperty("Host","ecom.satair.com");
	        connection.setRequestProperty("Referer", serverUrl);
	        connection.setRequestProperty("Origin", "https://ecom.satair.com");
	        connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
	        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
	        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
	        connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
	        connection.setRequestProperty("Charset", "UTF-8");  
	        String strResString = "";
	        String strLine = "";  
	        String strResponse = "";  
	  
	        InputStream in = connection.getInputStream();  
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
	  
	        while ((strLine = reader.readLine()) != null)  {  
	            strResponse += strLine + "\n";  
	        }
	        cookie = getCookie(connection);
//	        System.out.print(cookie);
//	        System.out.print(strResponse);
		}catch(Exception e){
			System.out.println(e);
		}
		return cookie;
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
    // 每个post参数之间的分隔。随意设定，只要不会和其他的字符串重复即可。  
    private static final String BOUNDARY = "----WebKitFormBoundary3pUKoAwUA8jDxPH4";  
  
    public String postLogin(String cookieStr) throws Exception {
    	String serverUrl = "https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/public/login.do";
        ArrayList<FormFieldKeyValuePair> ffkvps = new ArrayList<FormFieldKeyValuePair>();  
        //以下两种方式都不行
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_ty", "htmlb:button:click:null"));  
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_frm", "LoginForm"));  
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_oid", "button_login")); 
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_id", "button_login")); 
//	     ffkvps.add(new FormFieldKeyValuePair("htmlbevt_ty", "htmlb:checkbox:click:null"));  
//	     ffkvps.add(new FormFieldKeyValuePair("htmlbevt_frm", "LoginForm"));  
//	     ffkvps.add(new FormFieldKeyValuePair("htmlbevt_oid", "agreeChkBox")); 
//	     ffkvps.add(new FormFieldKeyValuePair("htmlbevt_id", "event_agg_chkbox")); 
        
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_cnt", "0")); 
        ffkvps.add(new FormFieldKeyValuePair("onInputProcessing", "htmlb"));  
        ffkvps.add(new FormFieldKeyValuePair("sap-htmlb-design", ""));
        ffkvps.add(new FormFieldKeyValuePair("user_model_customer_nr", "517257"));  
        ffkvps.add(new FormFieldKeyValuePair("user_model_username", "CASSIE"));  
        ffkvps.add(new FormFieldKeyValuePair("user_model_password", "C1a2s3s4i5e6"));
        ffkvps.add(new FormFieldKeyValuePair("user_model_tnc_check", "X"));
        ffkvps.add(new FormFieldKeyValuePair("user_model_tnc_check__cb", "X"));
  
        HttpPostEmulator hpe = new HttpPostEmulator();   
        URL url = new URL(serverUrl);  
  
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        connection.setSSLSocketFactory(sc.getSocketFactory());
        connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
        connection.setInstanceFollowRedirects(false);
        // 发送POST请求必须设置如下两行  
        connection.setDoOutput(true);  
        connection.setDoInput(true);  
//        connection.setUseCaches(false);  
        connection.setRequestMethod("POST");  
        connection.setRequestProperty("Connection", "Keep-Alive");  
        connection.setRequestProperty("Host","ecom.satair.com");
        connection.setRequestProperty("ContentType","text/xml;charset=utf-8");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
        connection.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
        connection.addRequestProperty("Content-Type", "multipart/form-data; boundary="+BOUNDARY);
//        connection.addRequestProperty("Cookie", "SATAIR_WEBSHOP_USERNAME=CASSIE; SATAIR_WEBSHOP_CUSTOMER_NR=517257; sap-appcontext=c2FwLXNlc3Npb25pZD1TSUQlM2FBTk9OJTNhV1NTQVRXUFIwMV9XUFJfMDIlM2F0Q1NoaXdGYm44OUxHbjE3WkJmcFhhYlhPRGVvUE5WbGdVZllBaEV2LUFUVA%3d%3d");
        System.setProperty("https.proxyHost", "localhost");
        System.setProperty("https.proxyPort", "8888");
        if(cookieStr != null && !cookieStr.equals("")){
        	System.out.println("getcookie:"+ cookieStr.split(";")[0]);
        	connection.addRequestProperty("Cookie", "SATAIR_WEBSHOP_USERNAME=CASSIE; SATAIR_WEBSHOP_CUSTOMER_NR=517257; " + cookieStr.split(";")[0]);
        }
        String boundary = BOUNDARY;  
        // 传输内容  
        StringBuffer contentBody = new StringBuffer("--" + boundary);  
        String endBoundary = "\r\n--" + boundary + "--\r\n";  
        OutputStream out = connection.getOutputStream();  
        for (FormFieldKeyValuePair ffkvp : ffkvps)  {  
            contentBody.append("\r\n")  
            .append("Content-Disposition: form-data; name=\"")  
            .append(ffkvp.getKey() + "\"")  
            .append("\r\n")  
            .append("\r\n")  
            .append(ffkvp.getValue())  
            .append("\r\n")  
            .append("--")  
            .append(boundary);  
        }  
        String boundaryMessage1 = contentBody.toString();  
        out.write(boundaryMessage1.getBytes("utf-8"));  
        out.write("--\r\n".getBytes("utf-8")); 
//        out.write(endBoundary.getBytes("utf-8"));  
        out.flush();  
        out.close();  
  
        //返回
        String strLine = "";  
        String strResponse = "";  
        InputStream in = null;
        try{
        	in = connection.getInputStream();  
        }catch(Exception e){
        	System.out.println(e);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
        while ((strLine = reader.readLine()) != null)  {  
            strResponse += strLine + "\n";  
        }  
        String cookie = getCookie(connection);
        System.out.print(cookie);  
        System.out.print(strResponse);  
  
        return cookie;  
  
    } 
    public String postMain(String cookieStr)throws Exception{
    	String serverUrl = "https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/private/main.do";
        ArrayList<FormFieldKeyValuePair> ffkvps = new ArrayList<FormFieldKeyValuePair>();  
        //以下两种方式都不行
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_ty", "htmlb:inputField:click:null"));
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_frm", "MainForm")); 
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_oid", "order_ctr_matnr")); 
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_id", "submitonenter")); 
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_cnt", "0")); 
        ffkvps.add(new FormFieldKeyValuePair("onInputProcessing", "htmlb")); 
        ffkvps.add(new FormFieldKeyValuePair("sap-htmlb-design", ""));

 
        ffkvps.add(new FormFieldKeyValuePair("billto_partner", "0000517257"));
        ffkvps.add(new FormFieldKeyValuePair("order_model_req_date", "2.12.2016")); 
        ffkvps.add(new FormFieldKeyValuePair("order_model_ponum", "")); 
        ffkvps.add(new FormFieldKeyValuePair("material_model_qty", "1")); 
        ffkvps.add(new FormFieldKeyValuePair("material_model_mfrpn", "42FLW720")); 
        ffkvps.add(new FormFieldKeyValuePair("user_model_shipto_partner", "517257")); 

        ffkvps.add(new FormFieldKeyValuePair("order_model_add_postal_code", "00000"));
        ffkvps.add(new FormFieldKeyValuePair("order_model_add_city", "Kowloon")); 
        ffkvps.add(new FormFieldKeyValuePair("order_model_add_name", "Betterair Trade Co Ltd")); 
        ffkvps.add(new FormFieldKeyValuePair("order_model_add_region", "")); 
        ffkvps.add(new FormFieldKeyValuePair("order_model_add_country", "HK")); 
        ffkvps.add(new FormFieldKeyValuePair("order_model_add_name2", ""));
        ffkvps.add(new FormFieldKeyValuePair("user_model_terms.zterm", "Net 30 days")); 
		
        ffkvps.add(new FormFieldKeyValuePair("order_model_add_street", "Unit 04, 7/F Bright Way Tower"));
        ffkvps.add(new FormFieldKeyValuePair("user_model_terms.inco2", "EXW .")); 
        ffkvps.add(new FormFieldKeyValuePair("order_model_add_street4", "")); 
        ffkvps.add(new FormFieldKeyValuePair("order_model_carrier", ""));
        ffkvps.add(new FormFieldKeyValuePair("order_model_ship_method", ""));
        ffkvps.add(new FormFieldKeyValuePair("order_model_acct_num", ""));
        ffkvps.add(new FormFieldKeyValuePair("order_model_add_street5", ""));
        
        ffkvps.add(new FormFieldKeyValuePair("order_ctr_basket_table_pager_Index", "0")); 
        ffkvps.add(new FormFieldKeyValuePair("order_ctr_basket_table_RowCount", "0")); 
        ffkvps.add(new FormFieldKeyValuePair("order_ctr_basket_table_AllColumnNames", "EXT_MATERIAL/MATERIAL/CAGE/MAKTX/QTY/UNIT/LINE_AMOUNT/ADD_COST/CURRENCY/P_UNIT/DLV_DAYS/AOG_CRITICAL/TEXTZ002/PLANTNAME/KURZTEXT/ATP/DELETE_MATNR/")); 

        ffkvps.add(new FormFieldKeyValuePair("order_ctr_basket_table-TS", "NONE"));
        ffkvps.add(new FormFieldKeyValuePair("order_ctr_basket_table_VisibleFirstRow", "0")); 
        ffkvps.add(new FormFieldKeyValuePair("order_ctr_basket_amount", "0.00")); 
        ffkvps.add(new FormFieldKeyValuePair("order_ctr_use_creditcard__cb", "X")); 


  
        HttpPostEmulator hpe = new HttpPostEmulator();   
        URL url = new URL(serverUrl);  
  
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        connection.setSSLSocketFactory(sc.getSocketFactory());
        connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
        connection.setInstanceFollowRedirects(false);
        // 发送POST请求必须设置如下两行  
        connection.setDoOutput(true);  
        connection.setDoInput(true);  
//        connection.setUseCaches(false);  
        connection.setRequestMethod("POST");  
        connection.setRequestProperty("Connection", "Keep-Alive");  
        connection.setRequestProperty("Host","ecom.satair.com");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
        connection.addRequestProperty("Content-Type", "multipart/form-data; boundary="+BOUNDARY);
        connection.addRequestProperty("Referer", "https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/private/main.do");
        connection.addRequestProperty("Cookie", cookieStr);
        System.setProperty("https.proxyHost", "localhost");
        System.setProperty("https.proxyPort", "8888");
//        if(cookieStr != null && !cookieStr.equals("")){
//        	System.out.println("getcookie:"+ cookieStr.split(";")[0]);
//        	connection.addRequestProperty("Cookie", "SATAIR_WEBSHOP_USERNAME=CASSIE; SATAIR_WEBSHOP_CUSTOMER_NR=517257; " + cookieStr.split(";")[0]);
//        }
        String boundary = BOUNDARY;  
        // 传输内容  
        StringBuffer contentBody = new StringBuffer("--" + boundary);  
        String endBoundary = "\r\n--" + boundary + "--\r\n";  
        OutputStream out = connection.getOutputStream();  
        for (FormFieldKeyValuePair ffkvp : ffkvps)  {  
            contentBody.append("\r\n")  
            .append("Content-Disposition: form-data; name=\"")  
            .append(ffkvp.getKey() + "\"")  
            .append("\r\n")  
            .append("\r\n")  
            .append(ffkvp.getValue())  
            .append("\r\n")  
            .append("--")  
            .append(boundary);  
        }  
        String boundaryMessage1 = contentBody.toString();  
        out.write(boundaryMessage1.getBytes("utf-8"));  
        out.write("--".getBytes("utf-8")); 
//        out.write(endBoundary.getBytes("utf-8"));  
        out.flush();  
        out.close();  
  
        //返回
        String strLine = "";  
        String strResponse = "";  
        InputStream in = null;
        try{
        	in = connection.getInputStream();  
        }catch(Exception e){
        	System.out.println(e);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
        while ((strLine = reader.readLine()) != null)  {  
            strResponse += strLine + "\n";  
        }  
        String cookie = getCookie(connection);
        System.out.print(cookie);  
        System.out.print(strResponse);  
  
        return cookie;  
    }

}  