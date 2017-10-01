package com.ui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;


public class HttpClientTest {
	public String getLogin(){
		String url = "https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/public/login.do";
		HttpGet httpGet = new HttpGet(url);
		String cookies = "";
		httpGet.setHeader("Connection","keep-alive");
		httpGet.setHeader("Host","ecom.satair.com");
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		System.setProperty("http.proxySet", "true"); 
		System.setProperty("http.proxyHost", "127.0.0.1"); 
		System.setProperty("http.proxyPort", "8888");
		 
		HttpClient httpClient = null;  
        String result = null;  
	
		try{
			httpClient = new SSLClient();
			HttpResponse  httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity =  httpResponse.getEntity();
			String content = EntityUtils.toString(httpEntity);
			cookies = getCookies(httpResponse);
//			System.out.println(cookies);
			System.out.println(content);
		}catch(Exception e){
			System.out.println(e);
		}
		return cookies;
	}
	public String getCookies(HttpResponse httpResponse){
    	Header[] headers = httpResponse.getHeaders("set-cookie");
    	String headerstr=headers.toString();
        if (headers == null)
            return "";
        String cookies = "";
        for(int i=0;i<headers.length;i++){
        	cookies += headers[i].getValue();
        }
        return cookies;
    }
	public String getCookie(HttpsURLConnection connection){
		/*String cookieVal = connection.getHeaderField("Set-Cookie");
		String sessionId = null;
		if(cookieVal != null){
			sessionId = cookieVal.substring(0, cookieVal.indexOf(";"));
		}*/
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
	public void postTest(){
		String url = "http://rapdb.dna.affrc.go.jp/tools/converter/run";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Connection","keep-alive");
//		httpPost.setHeader("Content-Type", "multipart/form-data;boundary=--sssssss");
		httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");

		HttpClient httpClient = null;  
        String result = null;  
	
		try{
//			httpClient = new SSLClient();
			httpClient = new DefaultHttpClient();
			MultipartEntity mutiEntity = new MultipartEntity();
			mutiEntity.addPart("keyword", new StringBody("Os12g0614600", Charset.forName("utf-8")));  
			mutiEntity.addPart("submit", new StringBody("Convert", Charset.forName("utf-8"))); 
			mutiEntity.addPart("selectDB", new StringBody("RtM", Charset.forName("utf-8")));  
			
			httpPost.setEntity(mutiEntity);
			
			HttpResponse  httpResponse = httpClient.execute(httpPost);
			
			HttpEntity httpEntity =  httpResponse.getEntity();
			String content = EntityUtils.toString(httpEntity);
			System.out.println(content);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	 /** 
     * Http URL重定向 
     */  
    private  void redirect02() {  
        DefaultHttpClient httpclient = null;  
//        String url = "https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/public/login.do";
        String url = "http://hotels.ctrip.com/hotel/hong-kong58";
        try {  
//            httpclient = new SSLClient();  
        	httpclient = new DefaultHttpClient();  
            httpclient.setRedirectStrategy(new RedirectStrategy() { //设置重定向处理方式  
				@Override
				public HttpUriRequest getRedirect(HttpRequest arg0,
						HttpResponse arg1, HttpContext arg2)
						throws ProtocolException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public boolean isRedirected(HttpRequest arg0,
						HttpResponse arg1, HttpContext arg2)
						throws ProtocolException {
					// TODO Auto-generated method stub
					return false;
				}  
            });  
            
    		HttpPost httpPost = new HttpPost(url);
    		httpPost.setHeader("Connection","keep-alive");
    		httpPost.setHeader("Host","ecom.satair.com");
    		httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
    		MultipartEntity mutiEntity = new MultipartEntity();
			mutiEntity.addPart("htmlbevt_ty", new StringBody("htmlb:button:click:null", Charset.forName("utf-8")));  
			mutiEntity.addPart("htmlbevt_frm", new StringBody("LoginForm", Charset.forName("utf-8"))); 
			mutiEntity.addPart("htmlbevt_oid", new StringBody("button_login", Charset.forName("utf-8")));  
			mutiEntity.addPart("htmlbevt_id", new StringBody("button_login", Charset.forName("utf-8")));  
//			mutiEntity.addPart("htmlbevt_ty", new StringBody("htmlb:checkbox:click:null", Charset.forName("utf-8")));  
//			mutiEntity.addPart("htmlbevt_frm", new StringBody("LoginForm", Charset.forName("utf-8"))); 
//			mutiEntity.addPart("htmlbevt_oid", new StringBody("agreeChkBox", Charset.forName("utf-8")));  
//			mutiEntity.addPart("htmlbevt_id", new StringBody("event_agg_chkbox", Charset.forName("utf-8"))); 
			
			mutiEntity.addPart("htmlbevt_cnt", new StringBody("0", Charset.forName("utf-8")));  
			mutiEntity.addPart("onInputProcessing", new StringBody("htmlb", Charset.forName("utf-8")));   
			mutiEntity.addPart("sap-htmlb-design", new StringBody("", Charset.forName("utf-8"))); 
			mutiEntity.addPart("user_model_customer_nr", new StringBody("517257", Charset.forName("utf-8")));   
			mutiEntity.addPart("user_model_username", new StringBody("CASSIE", Charset.forName("utf-8")));  
			mutiEntity.addPart("user_model_password", new StringBody("C1a2s3s4i5e6", Charset.forName("utf-8"))); 
			mutiEntity.addPart("user_model_tnc_check", new StringBody("X", Charset.forName("utf-8"))); 
			mutiEntity.addPart("user_model_tnc_check__cb", new StringBody("X", Charset.forName("utf-8"))); 
			
//			httpPost.setEntity(mutiEntity);
    		
            HttpResponse response = httpclient.execute(httpPost);  
  
            int statusCode = response.getStatusLine().getStatusCode();  
            if (statusCode == HttpStatus.SC_OK) {  
                // 获取响应实体  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    // 打印响应内容长度  
                    System.out.println("Response content length: "  
                            + entity.getContentLength());  
                    // 打印响应内容  
                    System.out.println("Response content: "  
                            + EntityUtils.toString(entity));  
                }  
            } else if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY  
                    || statusCode == HttpStatus.SC_MOVED_PERMANENTLY) {  
                  
                System.out.println("当前页面发生重定向了---");  
                  
                Header[] headers = response.getHeaders("Location");  
                if(headers!=null && headers.length>0){  
                    String redirectUrl = headers[0].getValue();  
                    System.out.println("重定向的URL:"+redirectUrl);  
                      
                    redirectUrl = redirectUrl.replace(" ", "%20");  
//                    get(redirectUrl);  
                }  
            }  
  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch(Exception e){
        	e.printStackTrace();
        }finally {  
            // 关闭连接,释放资源  
            httpclient.getConnectionManager().shutdown();  
        }  
    }  
	public void postLogin(){
		String url = "https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/public/login.do";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Connection","keep-alive");
		httpPost.setHeader("Host","ecom.satair.com");
		httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.setHeader("Content-Type", "multipart/form-data;boundary=--sssssss");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
//		httpPost.setHeader("Cookie", "SATAIR_WEBSHOP_USERNAME=CASSIE; SATAIR_WEBSHOP_CUSTOMER_NR=517257;");
//		httpPost.setHeader("Cookie", "sap-contextid=SID%3aANON%3aWSSATWPR01_WPR_02%3atCShTxf7NgtB4CObUESTmONJ8xCoPNWvzkeXl1yo-NEW; sap-appcontext=c2FwLXNlc3Npb25pZD1TSUQlM2FBTk9OJTNhV1NTQVRXUFIwMV9XUFJfMDIlM2F0Q1NoNkR5UE9jZVhvRWM1a1dhMWEyeW1Pc19vUE5VejhVZjJZeHJtLUFUVA%3d%3d; sap-usercontext=sap-client=370");

		
		 
		HttpClient httpClient = null;  
        String result = null;  
	
		try{
			httpClient = new SSLClient();
			
			MultipartEntity mutiEntity = new MultipartEntity();
//			mutiEntity.addPart("htmlbevt_ty", new StringBody("htmlb:button:click:null", Charset.forName("utf-8")));  
//			mutiEntity.addPart("htmlbevt_frm", new StringBody("LoginForm", Charset.forName("utf-8"))); 
//			mutiEntity.addPart("htmlbevt_oid", new StringBody("button_login", Charset.forName("utf-8")));  
//			mutiEntity.addPart("htmlbevt_id", new StringBody("button_login", Charset.forName("utf-8")));  
			mutiEntity.addPart("htmlbevt_ty", new StringBody("htmlb:checkbox:click:null", Charset.forName("utf-8")));  
			mutiEntity.addPart("htmlbevt_frm", new StringBody("LoginForm", Charset.forName("utf-8"))); 
			mutiEntity.addPart("htmlbevt_oid", new StringBody("agreeChkBox", Charset.forName("utf-8")));  
			mutiEntity.addPart("htmlbevt_id", new StringBody("event_agg_chkbox", Charset.forName("utf-8"))); 
			
			mutiEntity.addPart("htmlbevt_cnt", new StringBody("0", Charset.forName("utf-8")));  
			mutiEntity.addPart("onInputProcessing", new StringBody("htmlb", Charset.forName("utf-8")));   
			mutiEntity.addPart("sap-htmlb-design", new StringBody("", Charset.forName("utf-8"))); 
			mutiEntity.addPart("user_model_customer_nr", new StringBody("517257", Charset.forName("utf-8")));   
			mutiEntity.addPart("user_model_username", new StringBody("CASSIE", Charset.forName("utf-8")));  
			mutiEntity.addPart("user_model_password", new StringBody("C1a2s3s4i5e6", Charset.forName("utf-8"))); 
			mutiEntity.addPart("user_model_tnc_check", new StringBody("X", Charset.forName("utf-8"))); 
			mutiEntity.addPart("user_model_tnc_check__cb", new StringBody("X", Charset.forName("utf-8"))); 
			
			httpPost.setEntity(mutiEntity);
			
			HttpResponse  httpResponse = httpClient.execute(httpPost);
			
			HttpEntity httpEntity =  httpResponse.getEntity();
			String cookies = getCookies(httpResponse);
			String content = EntityUtils.toString(httpEntity);
			System.out.println(cookies);
			System.out.println(content);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public void postQuery(){
		String url = "https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/private/main.do";
		String boundary = "----WebKitFormBoundary3pUKoAwUA8jDxPH4";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Connection","keep-alive");
		httpPost.setHeader("Host","ecom.satair.com");
		httpPost.setHeader("Referer","https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/private/main.do");
		httpPost.setHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundarykYNa0jxXIOmQpBmW");
		httpPost.setHeader("Origin", "https://ecom.satair.com");
		httpPost.setHeader("Upgrade-Insecure-Requests", "1");
		httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
		httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate");
		httpPost.setHeader("Cookie", "sap-contextid=SID%3aANON%3aWSSATWPR01_WPR_02%3atCShMLxEuryVHr-GP6_ApLiZr_KoPNUazEeAU-RW-NEW; sap-appcontext=c2FwLXNlc3Npb25pZD1TSUQlM2FBTk9OJTNhV1NTQVRXUFIwMV9XUFJfMDIlM2F0Q1NoNkR5UE9jZVhvRWM1a1dhMWEyeW1Pc19vUE5VejhVZjJZeHJtLUFUVA%3d%3d; sap-usercontext=sap-client=370");
//		httpPost.setHeader("Cookie", "sap-contextid=SID%3aANON%3aWSSATWPR01_WPR_02%3atCShTxf7NgtB4CObUESTmONJ8xCoPNWvzkeXl1yo-NEW; sap-appcontext=c2FwLXNlc3Npb25pZD1TSUQlM2FBTk9OJTNhV1NTQVRXUFIwMV9XUFJfMDIlM2F0Q1NoNkR5UE9jZVhvRWM1a1dhMWEyeW1Pc19vUE5VejhVZjJZeHJtLUFUVA%3d%3d; sap-usercontext=sap-client=370");

		
		 
		HttpClient httpClient = null;  
        String result = null;  
	
		try{
			httpClient = new SSLClient();
			
			MultipartEntity mutiEntity = new MultipartEntity();
			
			mutiEntity.addPart("htmlbevt_ty", new StringBody("htmlb:inputField:click:null", Charset.forName("utf-8")));  
			mutiEntity.addPart("htmlbevt_frm", new StringBody("MainForm", Charset.forName("utf-8"))); 
			mutiEntity.addPart("htmlbevt_oid", new StringBody("order_ctr_matnr", Charset.forName("utf-8")));  
			mutiEntity.addPart("htmlbevt_id", new StringBody("submitonenter", Charset.forName("utf-8")));   
			mutiEntity.addPart("htmlbevt_cnt", new StringBody("0", Charset.forName("utf-8")));  
			mutiEntity.addPart("onInputProcessing", new StringBody("htmlb", Charset.forName("utf-8")));   
			
			mutiEntity.addPart("billto_partner", new StringBody("0000517257", Charset.forName("utf-8")));  
			mutiEntity.addPart("order_model_req_date", new StringBody("24.12.2016", Charset.forName("utf-8")));   
			mutiEntity.addPart("material_model_qty", new StringBody("1", Charset.forName("utf-8")));  
			mutiEntity.addPart("material_model_mfrpn", new StringBody("42FLW720", Charset.forName("utf-8")));   
			mutiEntity.addPart("user_model_shipto_partner", new StringBody("517257", Charset.forName("utf-8"))); 
			
			mutiEntity.addPart("order_model_add_postal_code", new StringBody("00000", Charset.forName("utf-8")));  
			mutiEntity.addPart("order_model_add_city", new StringBody("Kowloon", Charset.forName("utf-8")));   
			mutiEntity.addPart("order_model_add_name", new StringBody("Betterair Trade Co Ltd", Charset.forName("utf-8")));  
			mutiEntity.addPart("order_model_add_country", new StringBody("HK", Charset.forName("utf-8")));   
			mutiEntity.addPart("user_model_terms.zterm", new StringBody("Net 30 days", Charset.forName("utf-8"))); 
			
			
			mutiEntity.addPart("order_model_add_street", new StringBody("Unit 04, 7/F Bright Way Tower", Charset.forName("utf-8")));   
			mutiEntity.addPart("user_model_terms.inco2", new StringBody("EXW .", Charset.forName("utf-8")));  
			mutiEntity.addPart("order_ctr_basket_table_pager_Index", new StringBody("0", Charset.forName("utf-8"))); 
			mutiEntity.addPart("order_ctr_basket_table_RowCount", new StringBody("0", Charset.forName("utf-8"))); 
			mutiEntity.addPart("order_ctr_basket_table_AllColumnNames", new StringBody("EXT_MATERIAL/MATERIAL/CAGE/MAKTX/QTY/UNIT/LINE_AMOUNT/ADD_COST/CURRENCY/P_UNIT/DLV_DAYS/AOG_CRITICAL/TEXTZ002/PLANTNAME/KURZTEXT/ATP/DELETE_MATNR/", Charset.forName("utf-8")));
			
			
			mutiEntity.addPart("order_ctr_basket_table-TS", new StringBody("NONE", Charset.forName("utf-8")));   
			mutiEntity.addPart("order_ctr_basket_table_VisibleFirstRow", new StringBody("0", Charset.forName("utf-8")));  
			mutiEntity.addPart("order_ctr_basket_table_pager_Index", new StringBody("0", Charset.forName("utf-8"))); 
			mutiEntity.addPart("order_ctr_basket_table_RowCount", new StringBody("0", Charset.forName("utf-8"))); 

			mutiEntity.addPart("order_ctr_basket_amount", new StringBody("0.00", Charset.forName("utf-8")));   
			mutiEntity.addPart("order_ctr_use_creditcard__cb", new StringBody("X", Charset.forName("utf-8")));  
			
			httpPost.setEntity(mutiEntity);
			
			HttpResponse  httpResponse = httpClient.execute(httpPost);
			
			HttpEntity httpEntity =  httpResponse.getEntity();
			String content = EntityUtils.toString(httpEntity);
			System.out.println(content);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static void main(String[] arg){
		HttpClientTest test = new HttpClientTest();
		test.getLogin();
//		String cookies = test.getLogin();
//		test.postTest();
//		test.postLogin();
//		test.redirect02();
//		test.postQuery();
	}
}
