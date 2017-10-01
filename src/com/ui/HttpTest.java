package com.ui;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

import com.web.HttpRequest;

import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;  

import javax.net.ssl.SSLContext;  
import javax.net.ssl.TrustManager;  
import javax.net.ssl.X509TrustManager;  

import org.apache.http.conn.ClientConnectionManager;  
import org.apache.http.conn.scheme.Scheme;  
import org.apache.http.conn.scheme.SchemeRegistry;  
import org.apache.http.conn.ssl.SSLSocketFactory;  
import org.apache.http.impl.client.DefaultHttpClient; 
import org.apache.http.impl.execchain.MainClientExec;

import java.util.ArrayList;  
import java.util.HashMap;
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;  

import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils; 

public class HttpTest {
	public class SSLClient extends DefaultHttpClient{  
	    public SSLClient() throws Exception{  
	        super();  
	        SSLContext ctx = SSLContext.getInstance("TLS");  
	        X509TrustManager tm = new X509TrustManager() {  
	                @Override  
	                public void checkClientTrusted(X509Certificate[] chain,  
	                        String authType) throws CertificateException {  
	                }  
	                @Override  
	                public void checkServerTrusted(X509Certificate[] chain,  
	                        String authType) throws CertificateException {  
	                }  
	                @Override  
	                public X509Certificate[] getAcceptedIssuers() {  
	                    return null;  
	                }  
	        };  
	        ctx.init(null, new TrustManager[]{tm}, null);  
	        SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
	        ClientConnectionManager ccm = this.getConnectionManager();  
	        SchemeRegistry sr = ccm.getSchemeRegistry();  
	        sr.register(new Scheme("https", 443, ssf));  
	    }  
	}  
	  public String doGet(String url){  
	        HttpClient httpClient = null;  
	        HttpGet httpGet = null;  
	        String result = null;  
	        try{  
	            httpClient = new SSLClient();  
	            httpGet = new HttpGet(url);
	           
	    		String refer=" https://www.shopklx.com/be/?_requestid=19320";
	    		httpGet.setHeader("Referer", refer);
	    		httpGet.setHeader("Host", "www.shopklx.com");
	    		httpGet.setHeader("Upgrade-Insecure-Requests", "1");
	    		httpGet.setHeader("Connection", "keep-alive");
	    		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	    		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
	    		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
	    		httpGet.setHeader("Accept-Encoding", "gzip, deflate");
	    	
	            HttpResponse response = httpClient.execute(httpGet);  
	            if(response != null){  
	                HttpEntity resEntity = response.getEntity();  
	                if(resEntity != null){  
	                    result = EntityUtils.toString(resEntity);  
	                }  
	            }
	        }catch(Exception ex){  
	            ex.printStackTrace();  
	        }  
	        return result;  
	    }
	  public String doPost(String url,Map<String,String> map,String charset){  
	        HttpClient httpClient = null;  
	        HttpPost httpPost = null;  
	        String result = null;  
	        try{  
	            httpClient = new SSLClient();  
	            httpPost = new HttpPost(url);
	            
	    		String refer=" https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/public/login.do";
	    		httpPost.setHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundarypRUFJiSSHo67rrhT");
	    		httpPost.setHeader("Referer", refer);
	    		httpPost.setHeader("Host", "ecom.satair.com");
	    		httpPost.setHeader("Connection", "keep-alive");
	    		httpPost.setHeader("Upgrade-Insecure-Requests", "1");
	    		httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	    		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
	    		httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
	    		httpPost.setHeader("Accept-Encoding", "gzip, deflate");
	    		MultipartEntity mutiEntity = new MultipartEntity();
	    		mutiEntity.addPart("htmlbevt_ty",new StringBody("htmlb:button:click:null"));  
	    		mutiEntity.addPart("htmlbevt_frm",new StringBody("LoginForm"));  
	    		mutiEntity.addPart("htmlbevt_oid",new StringBody("button_login"));  
	    		mutiEntity.addPart("htmlbevt_id",new StringBody("button_login"));  
	            
	    		mutiEntity.addPart("htmlbevt_cnt",new StringBody("0"));  
	    		mutiEntity.addPart("onInputProcessing",new StringBody("htmlb"));  
	    		mutiEntity.addPart("sap-htmlb-design",new StringBody(""));  
	    		mutiEntity.addPart("user_model_customer_nr",new StringBody("517257"));  
	    	
	    		mutiEntity.addPart("user_model_username",new StringBody("CASSI"));  
	    		mutiEntity.addPart("user_model_password",new StringBody("C1a2s3s4i5e6"));  
	    		mutiEntity.addPart("user_model_tnc_check",new StringBody("X"));  
	    		mutiEntity.addPart("user_model_tnc_check__cb",new StringBody("X"));
	    		httpPost.setEntity(mutiEntity);
//	            //…Ë÷√≤Œ ˝  
//	            List<NameValuePair> list = new ArrayList<NameValuePair>();  
//	            Iterator iterator = map.entrySet().iterator();  
//	            while(iterator.hasNext()){  
//	                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
//	                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
//	            }  
//	            if(list.size() > 0){  
//	                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
//	                httpPost.setEntity(entity);  
//	            }  
	            HttpResponse response = httpClient.execute(httpPost); 
	            HttpGet get = new HttpGet("https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/private/main.do");
	            response = httpClient.execute(get);
	            if(response != null){  
	                HttpEntity resEntity = response.getEntity();  
	                if(resEntity != null){  
	                    result = EntityUtils.toString(resEntity,charset);  
	                }  
	            }
	        }catch(Exception ex){  
	            ex.printStackTrace();  
	        }  
	        return result;  
	    } 
	public void test(){
		String url="https://ecom.satair.com/satair(bD1lbiZjPTM3MA==)/public/login.do";
        Map<String,String> createMap = new HashMap<String,String>();  
	    createMap.put("htmlbevt_ty","htmlb:button:click:null");  
        createMap.put("htmlbevt_frm","LoginForm");  
        createMap.put("htmlbevt_oid","button_login");  
        createMap.put("htmlbevt_id","button_login");  
        
        createMap.put("htmlbevt_cnt","0");  
        createMap.put("onInputProcessing","htmlb");  
        createMap.put("sap-htmlb-design","");  
        createMap.put("user_model_customer_nr","517257");  
	
        createMap.put("user_model_username","CASSIE");  
        createMap.put("user_model_password","C1a2s3s4i5e6");  
        createMap.put("user_model_tnc_check","X");  
        createMap.put("user_model_tnc_check__cb","X");  
        String result = doPost(url,createMap,"utf-8");  
        System.out.println("result:"+result);  
		
//		String url="https://www.shopklx.com/be/index.jsp?_dyncharset=UTF-8&_dynSessConf=-1948041798661900619&%2Fatg%2Fuserprofiling%2FProfileFormHandler.value.login=BETTERAIRGROUP.MQIU&_D%3A%2Fatg%2Fuserprofiling%2FProfileFormHandler.value.login=+&%2Fatg%2Fuserprofiling%2FProfileFormHandler.value.password=affvTmXr&_D%3A%2Fatg%2Fuserprofiling%2FProfileFormHandler.value.password=+&%2Fcom%2Fklx%2Fprofile%2FSessionBean.loginFlyout=false&_D%3A%2Fcom%2Fklx%2Fprofile%2FSessionBean.loginFlyout=+&%2Fcom%2Fklx%2Fprofile%2FSessionBean.anonymousSkUId=&_D%3A%2Fcom%2Fklx%2Fprofile%2FSessionBean.anonymousSkUId=+&%2Fcom%2Fklx%2Fprofile%2FSessionBean.anonymousQuantity=&_D%3A%2Fcom%2Fklx%2Fprofile%2FSessionBean.anonymousQuantity=+&%2Fcom%2Fklx%2Fprofile%2FSessionBean.addAllToQuote=false&_D%3A%2Fcom%2Fklx%2Fprofile%2FSessionBean.addAllToQuote=+&%2Fatg%2Fuserprofiling%2FProfileFormHandler.loginErrorURL=index.jsp&_D%3A%2Fatg%2Fuserprofiling%2FProfileFormHandler.loginErrorURL=+&%2Fatg%2Fuserprofiling%2FProfileFormHandler.loginSuccessURL=index.jsp&_D%3A%2Fatg%2Fuserprofiling%2FProfileFormHandler.loginSuccessURL=+&Login=submit&_D%3ALogin=+&_DARGS=%2Fbe%2Fglobal%2Fincludes%2Fnav%2FnavLogin.jsp";
//		Map<String,String> createMap = new HashMap<String,String>();  
//		String result = doGet(url);  
//		System.out.println(result); 
	}
	 public static void main(String[] arg){
     	HttpTest httpTest = new HttpTest();
     	httpTest.test();
     }

}
