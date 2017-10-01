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
			// �򿪺�URL֮�������
			connection = realUrl.openConnection();
	        // ����ͨ�õ���������
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
	                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");			
		} catch (MalformedURLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
        
		
	}


	HttpRequest(String url,String param){
		try {
			 String urlNameString = url + "?" + param;
            realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		} catch (MalformedURLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
        
		
	}
	public void setRequestProperty(String property,String value){
		connection.setRequestProperty(property, value);
	}
    /**
     * ��ָ��URL����GET����������
     * 
     * @param url
     *            ���������URL
     * @param param
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return URL ������Զ����Դ����Ӧ���
     */
    public  String sendGet() {
        String result = "";
        BufferedReader in = null;
        try {           
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            /*   Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
           for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
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
     * ����ͼƬ
     * @param fileName
     * 	����ͼƬ�����ƣ�����·��
     * 
     */
     public boolean sendGet(String fileName){
     	String result = "";
     	InputStream inputStream=null;
     	OutputStream outputStream=null;
     	boolean flag=false;
         try {           
             // ����ʵ�ʵ�����
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
             System.out.println("����GET��������쳣��" + e);
             e.printStackTrace();
         }
         // ʹ��finally�����ر�������
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
     * ��ָ�� URL ����POST����������
     * 
     * @param url
     *            ��������� URL
     * @param param
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return ������Զ����Դ����Ӧ���
     */
    public  String sendPost(String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {            
            // ����POST�������������������
        	connection.setDoOutput(true);
        	connection.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(connection.getOutputStream());

            // �����������
            out.print(param);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("���� POST ��������쳣��"+e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر��������������
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
         // �������е���Ӧͷ�ֶ�
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