package com.ui;

import com.web.HttpsRequest;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.*;

import javax.net.ssl.*;

public class IPhoneX {
    private String cookie = "";
    private String cellNum;
    private String cellNumEnc;
    private OkHttpClient okHttpClient;
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    public IPhoneX(){
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        mBuilder.sslSocketFactory(createSSLSocketFactory());
        mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
        mBuilder.cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        cookieStore.put(httpUrl.host(), list);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                });
        okHttpClient = mBuilder.build();
        System.out.println("create client success");
    }

    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
    public  boolean sendMsg(){
        String url="https://clientaccess.10086.cn/biz-orange/LN/uamrandcode/sendMsgLogin";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,"\n" +
                "{\"ak\":\"F4AA34B89513F0D087CA0EF11A3277469DC74905\",\"cid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"city\":\"0755\",\"ctid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"cv\":\"4.0.0\",\"en\":\"0\",\"imei\":\"358811074939040\",\"nt\":\"3\",\"prov\":\"200\",\"reqBody\":{\"cellNum\":\"15013894358\"},\"sb\":\"samsung\",\"sn\":\"SM-C7000\",\"sp\":\"1080x1920\",\"st\":\"1\",\"sv\":\"6.0.1\",\"t\":\"\",\"tel\":\"99999999999\",\"xc\":\"A2061\",\"xk\":\"8134206949ee8bbc89e534902056abc3b91c333ac8f5eb629e36cb0a3b37825736bf236e\"}");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Host", "clientaccess.10086.cn")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Accept-Encoding", "gzip")
                .addHeader("User-Agent", "okhttp/3.8.1")
                .addHeader("xs", "8f516654520c7e439a66e9cff7e3e232")

                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String result = response.body().string();
            System.out.println(result);
            if (result.contains("\"retDesc\":\"SUCCESS\"")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    public void login(){
        String url = "https://clientaccess.10086.cn/biz-orange/LN/uamrandcodelogin/login";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,"{\"ak\":\"F4AA34B89513F0D087CA0EF11A3277469DC74905\",\"cid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"city\":\"0755\",\"ctid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"cv\":\"4.0.0\",\"en\":\"0\",\"imei\":\"358811074939040\",\"nt\":\"3\",\"prov\":\"200\",\"reqBody\":{\"cellNum\":\"d0DHUrIOS9BKmxv5yVNYHhDkquHdpWn5yQ0JV2GPK+xrfOZzZNtdbvXXuAbuVOBdLR/urES+1bFvjjwBTzzFLPxbgmqp05ZA33ilOPBfFqRNYBFBf4JE3wmiQsMEAqftdVAug0QwbSwpGgdAi2gfLCPg9bIo6/jr7NgFbgTFnhs=\",\"imei\":\"358811074939040\",\"sendSmsFlag\":\"1\",\"verifyCode\":\"241821\"},\"sb\":\"samsung\",\"sn\":\"SM-C7000\",\"sp\":\"1080x1920\",\"st\":\"1\",\"sv\":\"6.0.1\",\"t\":\"\",\"tel\":\"99999999999\",\"xc\":\"A2061\",\"xk\":\"8134206949ee8bbc89e534902056abc3b91c333ac8f5eb629e36cb0a3b37825736bf236e\"}");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]){
        IPhoneX phone = new IPhoneX();
        phone.cellNum = "15013894358";
        phone.cellNumEnc = "d0DHUrIOS9BKmxv5yVNYHhDkquHdpWn5yQ0JV2GPK+xrfOZzZNtdbvXXuAbuVOBdLR/urES+1bFvjjwBTzzFLPxbgmqp05ZA33ilOPBfFqRNYBFBf4JE3wmiQsMEAqftdVAug0QwbSwpGgdAi2gfLCPg9bIo6/jr7NgFbgTFnhs=";
        int count = 0;
        while (! phone.sendMsg()) {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){

            }
            count += 1;

        }
        System.out.print("try send msg: " + String.valueOf(count));
//        while (true) {
//            phone.login();
//            try {
//                Thread.sleep(1000);
//            }catch (InterruptedException e){
//
//            }
//        }

    }
    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }
    public  void getAddressList(){
        String url="https://app.10086.cn/leadeon-cmcc-static/v2.0/pages/mall/address/c_addresslist.html";
        HttpsRequest httpsRequest=new HttpsRequest(url);
        httpsRequest.setRequestProperty("Host","app.10086.cn");
        httpsRequest.setRequestProperty("Connection", "keep-alive");
        httpsRequest.setRequestProperty("Upgrade-Insecure-Requests", "1");
        httpsRequest.setRequestProperty("X-Requested-With", "com.greenpoint.android.mc10086.activity");
        httpsRequest.setRequestProperty("User-Agent", "okhttp/3.8.1");
        String result=httpsRequest.sendGet();
        System.out.println(result);
    }
    public  void getAddressAddHtml(){
        String url="https://app.10086.cn/leadeon-cmcc-static/v2.0/pages/mall/address/c_address_add.html?operator=add&actionPage=&goodsId=&skuId=&num=&payWay=&prodType=&orderPayPrice=&contractId=&contractPhone=&modelIds=&packageModelIds=&recAddrId=&address=&recName=&recPhoneNo=&invoiceId=&invoiceAddr=&invoiceType=";
        HttpsRequest httpsRequest=new HttpsRequest(url);
        httpsRequest.setRequestProperty("Host","app.10086.cn");
        httpsRequest.setRequestProperty("Connection", "keep-alive");
        httpsRequest.setRequestProperty("Upgrade-Insecure-Requests", "1");
        httpsRequest.setRequestProperty("Referer", "https://app.10086.cn/leadeon-cmcc-static/v2.0/pages/mall/address/c_addresslist.html");
        httpsRequest.setRequestProperty("X-Requested-With", "com.greenpoint.android.mc10086.activity");
        httpsRequest.setRequestProperty("Accept-Encoding", "gzip");
        httpsRequest.setRequestProperty("User-Agent", "okhttp/3.8.1");
        String result=httpsRequest.sendGet();
        System.out.println(result);
    }
    public  void getRealFee(){
        String url="https://clientaccess.10086.cn/biz-orange/BN/realFeeQuery/getRealFee";
        HttpsRequest httpsRequest=new HttpsRequest(url);
        httpsRequest.setRequestProperty("Host","clientaccess.10086.cn");
        httpsRequest.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        httpsRequest.setRequestProperty("Connection", "keep-alive");
//    	httpsRequest.setRequestProperty("Upgrade-Insecure-Requests", "1");
        httpsRequest.setRequestProperty("xs", "882ada5955ee5445375db7cf05b9bb4e");
        httpsRequest.setRequestProperty("Accept-Encoding", "gzip");
        httpsRequest.setRequestProperty("User-Agent", "okhttp/3.8.1");
        if (this.cookie != null && this.cookie != ""){
            httpsRequest.setRequestProperty("Cookie", this.cookie);
        }
        StringBuilder strBuilder=new StringBuilder();
        String txt = "{\"ak\":\"F4AA34B89513F0D087CA0EF11A3277469DC74905\",\"cid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"city\":\"0755\",\"ctid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"cv\":\"4.0.0\",\"en\":\"0\",\"imei\":\"358811074939040\",\"nt\":\"3\",\"prov\":\"200\",\"reqBody\":{\"cellNum\":\"CELL_NUM\"},\"sb\":\"samsung\",\"sn\":\"SM-C7000\",\"sp\":\"1080x1920\",\"st\":\"1\",\"sv\":\"6.0.1\",\"t\":\"ddce2b4618ed023b5b9be3a972119852\",\"tel\":\"TELL\",\"xc\":\"A2061\",\"xk\":\"8134206949ee8bbc89e534902056abc3b91c333ac8f5eb629e36cb0a3b37825736bf236e\"}";
        txt = txt.replace("CELL_NUM", this.cellNum);
        txt = txt.replace("TELL", this.cellNum);

        strBuilder.append(txt);
        String param=strBuilder.toString();
        System.out.println(param);
        String result=httpsRequest.sendPost(param);
        System.out.println(result);
    }
    public  void addRecvAddrInfo(){
        String url="https://app.10086.cn/biz-orange/SHG/receiveAdmin/addReceivingAddrInfo?" + this.cookie;
        url = url.replaceAll(" ", "");
        System.out.println(url);
        HttpsRequest httpsRequest=new HttpsRequest(url);
        httpsRequest.setRequestProperty("Host","app.10086.cn");
        httpsRequest.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        httpsRequest.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpsRequest.setRequestProperty("Connection", "keep-alive");
        httpsRequest.setRequestProperty("Origin", "https://app.10086.cn");
        httpsRequest.setRequestProperty("Referer", "https://app.10086.cn/leadeon-cmcc-static/v2.0/pages/mall/address/c_address_add.html?operator=add&actionPage=&goodsId=&skuId=&num=&payWay=&prodType=&orderPayPrice=&contractId=&contractPhone=&modelIds=&packageModelIds=&recAddrId=&address=&recName=&recPhoneNo=&invoiceId=&invoiceAddr=&invoiceType=");
        httpsRequest.setRequestProperty("Accept-Encoding", "gzip");
        httpsRequest.setRequestProperty("User-Agent", "okhttp/3.8.1");
//        if (this.cookie != null && this.cookie != ""){
//            httpsRequest.setRequestProperty("Cookie", this.cookie);
//        }
        StringBuilder strBuilder=new StringBuilder();
        String txt = "{\"cid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"en\":\"0\",\"t\":\"T_COOKIE\",\"sn\":\"SM-C7000\",\"cv\":\"4.0.0\",\"st\":\"1\",\"sv\":\"6.0.1\",\"sp\":\"1080x1920\",\"xc\":\"A2061\",\"imei\":\"\",\"nt\":\"\",\"sb\":\"\",\"prov\":\"200\",\"city\":\"0755\",\"tel\":\"15013894358\",\"reqBody\":{\"cellNum\":\"CELL_NUM\",\"provinceCode\":\"130000\",\"cityCode\":\"130100\",\"regionCode\":\"130133\",\"street\":\"STREET\",\"zipCode\":null,\"recName\":\"REC_NAME\",\"recPhoneNo\":\"REC_PHONE_NO\",\"recTel\":null,\"isDefault\":\"0\"}}";
        txt = txt.replace("CELL_NUM", this.cellNum);
        txt = txt.replace("T_COOKIE", this.cookie);
        txt = txt.replace("STREET", "fdsafasffffffffffffffffffffff");
        txt = txt.replace("REC_NAME", "wda");
        txt = txt.replace("REC_PHONE_NO", "15012121111");

        strBuilder.append(txt);
        String param=strBuilder.toString();
        System.out.println(param);
        String result=httpsRequest.sendPost(param);
        System.out.println(result);
    }
    public  void getorderDetail(){
        String url= "https://app.10086.cn/leadeon-cmcc-static/v2.0/pages/mall/order/c_orderDetail.html?goodsId=1044585&skuId=1039418&num=1&prodType=2&o2oNum=null&orderPayPrice=739.00";
        url = url.replaceAll(" ", "");
        System.out.println(url);
        HttpsRequest httpsRequest=new HttpsRequest(url);
        httpsRequest.setRequestProperty("Host","app.10086.cn");
        httpsRequest.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpsRequest.setRequestProperty("Connection", "keep-alive");
        httpsRequest.setRequestProperty("User-Agent", "okhttp/3.8.1");
        httpsRequest.setRequestProperty("X-Requested-With", "com.greenpoint.android.mc10086.activity");
        String result=httpsRequest.sendGet();
        System.out.println(result);
    }
    public  void getDefaultsReceivingAddrInfo(){
        String url="https://app.10086.cn/biz-orange/SHG/receiveAdmin/getDefaultsReceivingAddrInfo?" + this.cookie;
        url = url.replaceAll(" ", "");
        System.out.println(url);
        HttpsRequest httpsRequest=new HttpsRequest(url);
        httpsRequest.setRequestProperty("Host","app.10086.cn");
        httpsRequest.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        httpsRequest.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpsRequest.setRequestProperty("Connection", "keep-alive");
        httpsRequest.setRequestProperty("Origin", "https://app.10086.cn");
        httpsRequest.setRequestProperty("Referer", "https://app.10086.cn/leadeon-cmcc-static/v2.0/pages/mall/order/c_orderDetail.html?goodsId=1044585&skuId=1039418&num=1&prodType=2&o2oNum=null&orderPayPrice=739.00");
//        httpsRequest.setRequestProperty("Accept-Encoding", "gzip");
        httpsRequest.setRequestProperty("User-Agent", "okhttp/3.8.1");
        StringBuilder strBuilder=new StringBuilder();
        String txt = "{\"cid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"en\":\"0\",\"t\":\"ticketID=B;JSESSIONID=f7c63270-2c6b-4230-8443-1a96299b9056;UID=c8ab4dec06de4ffba0bccacc2f8c9dfa;Comment=SessionServer-unity;Path=/;Secure\",\"sn\":\"SM-C7000\",\"cv\":\"4.0.0\",\"st\":\"1\",\"sv\":\"6.0.1\",\"sp\":\"1080x1920\",\"xc\":\"A2061\",\"imei\":\"\",\"nt\":\"\",\"sb\":\"\",\"prov\":\"200\",\"city\":\"0755\",\"tel\":\"15013894358\",\"reqBody\":{\"cellNum\":\"15013894358\"}}";
        strBuilder.append(txt);
        String param=strBuilder.toString();
        System.out.println(param);
        String result=httpsRequest.sendPost(param);
        System.out.println(result);
    }
    public  void submitOrder(){
        String url="https://app.10086.cn/biz-orange/SHS/submitorder/submitOrder?" + this.cookie;
        System.out.println(url);
        HttpsRequest httpsRequest=new HttpsRequest(url);
        httpsRequest.setRequestProperty("Host","app.10086.cn");
        httpsRequest.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        httpsRequest.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpsRequest.setRequestProperty("Connection", "keep-alive");
        httpsRequest.setRequestProperty("Origin", "https://app.10086.cn");
        httpsRequest.setRequestProperty("Referer", "https://app.10086.cn/leadeon-cmcc-static/v2.0/pages/mall/order/c_orderDetail.html?goodsId=1044585&skuId=1039418&num=1&prodType=2&o2oNum=null&orderPayPrice=739.00");
//        httpsRequest.setRequestProperty("Accept-Encoding", "gzip");
        httpsRequest.setRequestProperty("User-Agent", "okhttp/3.8.1");
        StringBuilder strBuilder=new StringBuilder();
        String txt = "{\"cid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"en\":\"0\",\"t\":\"T_COOKIE\",\"sn\":\"SM-C7000\",\"cv\":\"4.0.0\",\"st\":\"1\",\"sv\":\"6.0.1\",\"sp\":\"1080x1920\",\"xc\":\"A2061\",\"imei\":\"\",\"nt\":\"\",\"sb\":\"\",\"prov\":\"200\",\"city\":\"0755\",\"tel\":\"15013894358\",\"reqBody\":{\"cellNum\":\"15013894358\",\"payWay\":1,\"ticketNo\":\"\",\"invoiceInfo\":{\"invoiceType\":1,\"title\":\"个人\"},\"deliveryInfo\":{\"consignee\":\"胡静\",\"addr\":\"1111\",\"provinceId\":\"130000\",\"provinceName\":\"1212\",\"cityId\":\"130100\",\"cityName\":\"石家庄市\",\"townId\":\"130133\",\"townName\":\"1111\",\"areaId\":\"130133\",\"mobilePhone\":\"15013894358\"},\"skuList\":[{\"prodId\":\"1044585\",\"provinceId\":\"200\",\"cityId\":\"0755\",\"modelId\":\"1039418\",\"num\":\"1\",\"packageId\":-1,\"detailId\":-1,\"assistantInfo\":\"\",\"mobile\":\"\",\"mobileProvince\":0}]}}";
        txt = txt.replace("T_COOKIE", this.cookie);
        strBuilder.append(txt);
        String param=strBuilder.toString();
        System.out.println(param);
        String result=httpsRequest.sendPost(param);
        System.out.println(result);
    }
}
