package com.web;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpRequest {
	private URL realUrl;
	private int id;
	public String cookie;
	public URLConnection connection;
	public HttpRequest(String url){
		try {
			realUrl = new URL(url);
			// 打开和URL之间的连接
			connection = realUrl.openConnection();
	        // 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
	                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");			
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        
		
	}


	HttpRequest(String url,String param){
		try {
			 String urlNameString = url + "?" + param;
            realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        
		
	}
	public void setRequestProperty(String property,String value){
		connection.setRequestProperty(property, value);
	}
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public  String sendGet() {
        String result = "";
        BufferedReader in = null;
        try {           
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            /*   Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
           for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 返回图片
     * @param fileName
     * 	保存图片的名称，绝对路径
     * 
     */
     public boolean sendGet(String fileName){
     	String result = "";
     	InputStream inputStream=null;
     	OutputStream outputStream=null;
     	boolean flag=false;
         try {           
             // 建立实际的连接
             connection.connect(); 
             inputStream=connection.getInputStream();
             outputStream=new FileOutputStream(fileName);
             byte b[]=new byte[1024];
             int len;
             while(((len=inputStream.read(b))!=-1)){
             	outputStream.write(b, 0, len);            	
             }
             flag=true;
            
         } catch (Exception e) {
             System.out.println("发送GET请求出现异常！" + e);
             e.printStackTrace();
         }
         // 使用finally块来关闭输入流
         finally {
             try {
                 if (inputStream != null) {
                 	inputStream.close();
                 }
                 if(outputStream!=null){
                 	outputStream.close();
                 }
             } catch (Exception e2) {
                 e2.printStackTrace();
             }
         }
         return flag;
     }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public  String sendPost(String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {            
            // 发送POST请求必须设置如下两行
        	connection.setDoOutput(true);
        	connection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());

            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    } 

	public String getCookie(){
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
        	if(key.equals("Set-Cookie")){
        		list=map.get(key);
        		break;
        	}
         }
        for(int i=0;i<list.size();i++)
        	cookieVal+=list.get(i)+";";
		//return sessionId;
		return cookieVal;
	}
	public void close(){
		
	}
   
}