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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.entity.mime.content.StringBody;

  
public class Satair{
	private String userName = "";
	private String password = "";
	private String customerNr = "";
	private String cookie = "";
    private static final String BOUNDARY = "----WebKitFormBoundary3pUKoAwUA8jDxPH4";  
	public static void main(String[] arg){
		Satair satair = new Satair();
		try{
	        System.setProperty("https.proxyHost", "localhost");
	        System.setProperty("https.proxyPort", "8888");
			satair.login("CASSIE", "C1a2s3s4i5e6","517257");
			satair.query("42FLW720", 1, "2.12.2016");


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
	
	private String  indexPage(){
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
  
    public String userlogin() throws Exception {
    	String serverUrl = "https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/public/login.do";
        ArrayList<FormFieldKeyValuePair> ffkvps = new ArrayList<FormFieldKeyValuePair>();  
        //以下两种方式都不行
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_ty", "htmlb:button:click:null"));  
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_frm", "LoginForm"));  
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_oid", "button_login")); 
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_id", "button_login")); 
        
        ffkvps.add(new FormFieldKeyValuePair("htmlbevt_cnt", "0")); 
        ffkvps.add(new FormFieldKeyValuePair("onInputProcessing", "htmlb"));  
        ffkvps.add(new FormFieldKeyValuePair("sap-htmlb-design", ""));
        ffkvps.add(new FormFieldKeyValuePair("user_model_customer_nr", customerNr));  
        ffkvps.add(new FormFieldKeyValuePair("user_model_username", userName));  
        ffkvps.add(new FormFieldKeyValuePair("user_model_password", password));
        ffkvps.add(new FormFieldKeyValuePair("user_model_tnc_check", "X"));
        ffkvps.add(new FormFieldKeyValuePair("user_model_tnc_check__cb", "X"));
  
        Satair hpe = new Satair();   
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
        connection.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
        connection.addRequestProperty("Content-Type", "multipart/form-data; boundary="+BOUNDARY);
        connection.addRequestProperty("Cookie", cookie);

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
        String contextid = getMyCookie(cookie, "sap-contextid");
        return contextid;  
  
    } 
    public String queryPN(String pn, int quantity, String reqDate)throws Exception{
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

 
        ffkvps.add(new FormFieldKeyValuePair("billto_partner", "0000" + customerNr));
        ffkvps.add(new FormFieldKeyValuePair("order_model_req_date", reqDate)); 
        ffkvps.add(new FormFieldKeyValuePair("order_model_ponum", "")); 
        ffkvps.add(new FormFieldKeyValuePair("material_model_qty", String.valueOf(quantity))); 
        ffkvps.add(new FormFieldKeyValuePair("material_model_mfrpn", pn)); 
        ffkvps.add(new FormFieldKeyValuePair("user_model_shipto_partner", customerNr)); 

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


  
        Satair hpe = new Satair();   
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
        connection.addRequestProperty("Cookie", cookie);
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
        return strResponse;  
    }
	public boolean login(String userName, String password, String customerNr) {
		// TODO Auto-generated method stub
		try{
			this.userName = userName;
			this.password = password;
			this.customerNr = customerNr;
			cookie = indexPage();
			System.out.println(cookie);
			String contextid = userlogin();
			System.out.println(contextid);
			if(contextid != null && contextid != ""){
				cookie += contextid;
			}
			System.out.println(cookie);

		}catch(Exception e){
			
		}
		return false;
	}

	public boolean query(String pn, int quantity, String reqDate) {
		// TODO Auto-generated method stub
		try {
			queryPN("", quantity, reqDate);
			String result = queryPN(pn, quantity, reqDate);
//			System.out.println(result);
			praseResult(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	private void praseResult(String result) {
		// TODO Auto-generated method stub
		//获取结果字符串，从material到tbody
		//没有找到material,直接返回,否则截取最后一个image.
		if(result.indexOf("material") == -1){
			return;
		}

		result = result.substring(result.indexOf("order_ctr_basket_table\""));
		result = result.substring(0, result.lastIndexOf("ct=\"Image"));
		int totalCount = result.split(".material").length;
		System.out.println(totalCount);
		System.out.println(result);
		String resultArr[] = result.split("</span>");
		for(String str: resultArr){
			if(! str.contains(">")){
				continue;
			}
			System.out.println(str.substring(str.lastIndexOf(">") + 1));
		}
//		Pattern pattern = Pattern.compile("(<span.*>.*</span>)");
//		Matcher matcher= pattern.matcher(result);
//		while(matcher.find()){
//			String resultRaw = matcher.group(0); 
//			System.out.println(resultRaw);
////			System.out.println(resultRaw.substring(1, resultRaw.length() - 7));
//		}
		
		
	}

	private String getMyCookie(String cookieStr, String cookieName){
		String cookieArr[] = cookieStr.split(";");
		String cookie = "";
		for(String cookieValue:cookieArr){
			if(cookieValue.contains(cookieName)){
				cookie = cookieValue;
				break;
			}
		}
		return cookie;
	}

}  