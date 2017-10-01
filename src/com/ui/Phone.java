package com.ui;

import com.web.HttpsRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Phone {
    private String cookie = "";
    private String cellNum;
    private String cellNumEnc;
    public static void main(String args[]){
        Phone phone = new Phone();
        phone.cellNum = "15013894358";
//        phone.cellNumEnc = "Kp+EfmC+vWsVCVhpusVdGQA0dk34I2B5N4wTDO1mnAmI6cWrRap6cjRAUZY2GPbTGhgeJrt5RjyvvQFyOIkd3WBQs/Zdr3xeF5NogFNgFFrjM6KgGyR/KQpFRv9qF0gKDqg1wUy4MtUSTRcaeGwcYfZ5yKGNYnwbwdxW7n48/BQ=";
//        phone.cellNumEnc = "f/JN9/LDbj2oFm7rlZxS89JbEsk9G25b/85b6NTU7QtFjoGH5dkgrFYOHELNLjCbNwcAP/l9CQSkyGTlYwqvTvVqsgTenjaE23hvrYXIaa285mloUMaphjymwBf7PLfil0AXCDqSzzQkFR6XWPQVEXOCICD1J60b9czhvCnKMSs=";
//        phone.cellNumEnc = "NkyUw4jDwFNw9grG8iuXwQjFOlcWD7eI6iR0pXd6wpG19oWB+QjzS7r2Nk99RNj/xu19SsJsMN0JrfdeFG4EUijhZ3ykP1p2KOZHoga1GKAZhXeiaxdP5n20yX4uALyFzVo3LIuCJcgi1VplQNZ1qZUheTy3uns2033f3F1h5Z8=";
        phone.cellNumEnc = "d0DHUrIOS9BKmxv5yVNYHhDkquHdpWn5yQ0JV2GPK+xrfOZzZNtdbvXXuAbuVOBdLR/urES+1bFvjjwBTzzFLPxbgmqp05ZA33ilOPBfFqRNYBFBf4JE3wmiQsMEAqftdVAug0QwbSwpGgdAi2gfLCPg9bIo6/jr7NgFbgTFnhs=";
        int count = 0;
        while (! phone.sendMsg()) {
            count += 1;
        }
        System.out.println("send msg retry total:" + String.valueOf(count));

        count = 0;
        while (phone.cookie.equals("")) {
            phone.login("147619");
            count += 1;
        }
        System.out.println("login retry total:" + String.valueOf(count));
        count = 0;
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("start time: " + sdf.format(d));
        while (true){
            phone.getRealFee();
            try {
                Thread.sleep(10000);
                count += 1;
                d = new Date();
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("current time: " + sdf.format(d));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //提交订单
//        phone.getorderDetail();
//        phone.getDefaultsReceivingAddrInfo();
//        phone.submitOrder();


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
    public  boolean sendMsg(){
        String url="https://clientaccess.10086.cn/biz-orange/LN/uamrandcode/sendMsgLogin";
        HttpsRequest httpsRequest=new HttpsRequest(url);
        httpsRequest.setRequestProperty("Host","clientaccess.10086.cn");
        httpsRequest.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        httpsRequest.setRequestProperty("Connection", "keep-alive");
        httpsRequest.setRequestProperty("User-Agent", "okhttp/3.8.1");
        StringBuilder strBuilder=new StringBuilder();
        String txt  = "{\"ak\":\"F4AA34B89513F0D087CA0EF11A3277469DC74905\",\"cid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"city\":\"0755\",\"ctid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"cv\":\"4.0.0\",\"en\":\"0\",\"imei\":\"358811074939040\",\"nt\":\"3\",\"prov\":\"200\",\"reqBody\":{\"cellNum\":\"15013894358\"},\"sb\":\"samsung\",\"sn\":\"SM-C7000\",\"sp\":\"1080x1920\",\"st\":\"1\",\"sv\":\"6.0.1\",\"t\":\"\",\"tel\":\"99999999999\",\"xc\":\"A2061\",\"xk\":\"8134206949ee8bbc89e534902056abc3b91c333ac8f5eb629e36cb0a3b37825736bf236e\"}";
        strBuilder.append(txt);
        String param=strBuilder.toString();
        System.out.println(param);
        String result=httpsRequest.sendPost(param);
        System.out.println(result);
        if (result.contains("\"retDesc\":\"SUCCESS\"")){
            return true;
        }
        return false;
    }
    public  void login(String verifyCode){
        String url="https://clientaccess.10086.cn/biz-orange/LN/uamrandcodelogin/login";
        HttpsRequest httpsRequest=new HttpsRequest(url);
        httpsRequest.setRequestProperty("Host","clientaccess.10086.cn");
        httpsRequest.setRequestProperty("Accept", "text/json, text/javascript, */*; q=0.01");
        httpsRequest.setRequestProperty("Accept-Encoding", "compress");
        httpsRequest.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        httpsRequest.setRequestProperty("Connection", "keep-alive");
        httpsRequest.setRequestProperty("User-Agent", "okhttp/3.8.1");
//        long timeMillis = System.currentTimeMillis();
//        String xs = Phone.getMd5(String.valueOf(timeMillis));
//        httpsRequest.setRequestProperty("xs", "85860ac5987ee2da0611e166b062913f");

        StringBuilder strBuilder=new StringBuilder();
        String txt = "{\"ak\":\"F4AA34B89513F0D087CA0EF11A3277469DC74905\",\"cid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"city\":\"0755\",\"ctid\":\"ZQ7NGnFe+2Ob+ELjX6nA80oNw9raJFK96ckDGM/SJqdKa110jeool++QXR4R/VmoUbYy1yY6S0Tv7LQOgp8OxK/6BUQ0L7PEE0y+VwFEAMA=\",\"cv\":\"4.0.0\",\"en\":\"0\",\"imei\":\"358811074939040\",\"nt\":\"3\",\"prov\":\"200\",\"reqBody\":{\"cellNum\":\"CELL_NUM_ENC\",\"imei\":\"358811074939040\",\"sendSmsFlag\":\"1\",\"verifyCode\":\"VERIFY_CODE\"},\"sb\":\"samsung\",\"sn\":\"SM-C7000\",\"sp\":\"1080x1920\",\"st\":\"1\",\"sv\":\"6.0.1\",\"t\":\"\",\"tel\":\"99999999999\",\"xc\":\"A2290\",\"xk\":\"8134206949ee8bbc89e534902056abc3b91c333ac8f5eb629e36cb0a3b37825736bf236e\"}";
        txt = txt.replace("CELL_NUM_ENC", this.cellNumEnc);
        txt = txt.replace("VERIFY_CODE", verifyCode);

        strBuilder.append(txt);
        String param=strBuilder.toString();
        System.out.println(param);
        String result=httpsRequest.sendPost(param);
        String cookie = httpsRequest.getCookie();
        if (null != cookie && "" != cookie){
            cookie = cookie.replaceAll(" ","");
            this.cookie = cookie;
        }
        System.out.println(cookie);
        System.out.println(result);
    }
}
