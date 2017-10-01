package com.ui;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher; 
public class PushConstants {
	/*
	 private static byte[] a(byte[] paramArrayOfByte, String paramString, int paramInt)
			    throws Exception
			  {
			    X509EncodedKeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(paramString.getBytes()));
			    PublicKey localPublicKey = KeyFactory.getInstance("RSA").generatePublic(localX509EncodedKeySpec);
			    Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			    localCipher.init(1, localPublicKey);
			    int i = paramInt / 8;
			    int j = i - 11;
			    int k = paramArrayOfByte.length;
			    byte[] arrayOfByte1 = new byte[i * ((-1 + (k + j)) / j)];
			    int m = 0;
			    int n = 0;
			    while (n < k)
			    {
			      int i1 = k - n;
			      if (j < i1)
			        i1 = j;
			      byte[] arrayOfByte2 = new byte[i1];
			      System.arraycopy(paramArrayOfByte, n, arrayOfByte2, 0, i1);
			      n += i1;
			      System.arraycopy(localCipher.doFinal(arrayOfByte2), 0, arrayOfByte1, m, i);
			      m += i;
			    }
			    return arrayOfByte1;
			  }
	 public static String rsaEncrypt(String paramString)
	  {
	    try
	    {
	      String str = Base64.encode(a(paramString.getBytes(), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/7VlVn9LIrZ71PL2RZMbK/Yxc\r\ndb046w/cXVylxS7ouPY06namZUFVhdbUnNRJzmGUZlzs3jUbvMO3l+4c9cw/n9aQ\r\nrm/brgaRDeZbeSrQYRZv60xzJIimuFFxsRM+ku6/dAyYmXiQXlRbgvFQ0MsVng4j\r\nv+cXhtTis2Kbwb8mQwIDAQAB\r\n", 1024), "utf-8");
	      return str;
	    }
	    catch (Exception localException)
	    {
	      return null;
	    }
	    return null;
	  }
	  */
}
