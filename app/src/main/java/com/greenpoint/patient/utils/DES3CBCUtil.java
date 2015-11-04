package com.greenpoint.patient.utils;

import android.util.Base64;
import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES3CBCUtil
{
  public DES3CBCUtil()
    throws Exception
  {
    String str1 = Base64.encodeToString("abcdefghijklmnopqrstuvwx".getBytes(), 0);
    String str2 = Base64.encodeToString("goocanok".getBytes(), 0);
    byte[] arrayOfByte1 = Base64.decode(str1, 0);
    byte[] arrayOfByte2 = Base64.decode(str2, 0);
    byte[] arrayOfByte3 = "{\"v\":\"v2.01\",\"o\":\"area.list\",\"p\":{\"area\":\"\"},\"a\":{\"id\":\"4c4793ba-7152-40b5-a23f-5d324341936c\",\"cs\":\"DoYK3jkGSDHhTKhx8jHLpQ==\"}}".getBytes("UTF-8");
    LogUtil.i("原始数据:" + "{\"v\":\"v2.01\",\"o\":\"area.list\",\"p\":{\"area\":\"\"},\"a\":{\"id\":\"4c4793ba-7152-40b5-a23f-5d324341936c\",\"cs\":\"DoYK3jkGSDHhTKhx8jHLpQ==\"}}");
    LogUtil.i("CBC加密解密");
    String str3 = Base64.encodeToString(des3EncodeCBC(arrayOfByte1, arrayOfByte2, arrayOfByte3), 0);
    LogUtil.i("加密的数据是:" + str3);
    String str4 = new String(des3DecodeCBC(arrayOfByte1, arrayOfByte2, Base64.decode(str3, 0)), "UTF-8");
    LogUtil.i("解密后的数据是:" + str4);
    LogUtil.i("ECB加密解密");
    String str5 = Base64.encodeToString(des3EncodeECB(arrayOfByte1, arrayOfByte3), 0);
    LogUtil.i("加密的数据是:" + str5);
    String str6 = new String(des3DecodeECB(arrayOfByte1, Base64.decode(str5, 0)), "UTF-8");
    LogUtil.i("解密后的数据是:" + str6);
  }

  public static String base64Decode(String paramString)
  {
    LogUtil.i("base64解码前: " + paramString);
    byte[] arrayOfByte = Base64.decode(paramString, 0);
    try
    {
      String str1 = new String(arrayOfByte, "UTF-8");
      LogUtil.i("base64解码后: " + str1);
      return str1;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
      {
        localUnsupportedEncodingException.printStackTrace();
        String str2 = null;
      }
    }
  }

  public static String base64Encode(String paramString)
  {
    LogUtil.i("base64编码前: " + paramString);
    String str = Base64.encodeToString(paramString.getBytes(), 0);
    LogUtil.i("base64编码后: " + str);
    return str;
  }

  public static String des3DecodeCBC(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    String str1 = Base64.encodeToString(paramString2.getBytes(), 0);
    String str2 = Base64.encodeToString(paramString3.getBytes(), 0);
    byte[] arrayOfByte = Base64.decode(paramString1, 0);
    return Base64.encodeToString(des3DecodeCBC(Base64.decode(str1, 0), Base64.decode(str2, 0), arrayOfByte), 0);
  }

  public static byte[] des3DecodeCBC(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
    throws Exception
  {
    DESedeKeySpec localDESedeKeySpec = new DESedeKeySpec(paramArrayOfByte1);
    SecretKey localSecretKey = SecretKeyFactory.getInstance("desede").generateSecret(localDESedeKeySpec);
    Cipher localCipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
    localCipher.init(2, localSecretKey, new IvParameterSpec(paramArrayOfByte2));
    return localCipher.doFinal(paramArrayOfByte3);
  }

  @Deprecated
  public static byte[] des3DecodeECB(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    DESedeKeySpec localDESedeKeySpec = new DESedeKeySpec(paramArrayOfByte1);
    SecretKey localSecretKey = SecretKeyFactory.getInstance("desede").generateSecret(localDESedeKeySpec);
    Cipher localCipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
    localCipher.init(2, localSecretKey);
    return localCipher.doFinal(paramArrayOfByte2);
  }

  public static String des3EncodeCBC(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    String str1 = Base64.encodeToString(paramString2.getBytes(), 0);
    String str2 = Base64.encodeToString(paramString3.getBytes(), 0);
    byte[] arrayOfByte = paramString1.getBytes("UTF-8");
    return Base64.encodeToString(des3EncodeCBC(Base64.decode(str1, 0), Base64.decode(str2, 0), arrayOfByte), 0);
  }

  public static byte[] des3EncodeCBC(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
    throws Exception
  {
    DESedeKeySpec localDESedeKeySpec = new DESedeKeySpec(paramArrayOfByte1);
    SecretKey localSecretKey = SecretKeyFactory.getInstance("desede").generateSecret(localDESedeKeySpec);
    Cipher localCipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
    localCipher.init(1, localSecretKey, new IvParameterSpec(paramArrayOfByte2));
    return localCipher.doFinal(paramArrayOfByte3);
  }

  @Deprecated
  public static byte[] des3EncodeECB(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    DESedeKeySpec localDESedeKeySpec = new DESedeKeySpec(paramArrayOfByte1);
    SecretKey localSecretKey = SecretKeyFactory.getInstance("desede").generateSecret(localDESedeKeySpec);
    Cipher localCipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
    localCipher.init(1, localSecretKey);
    return localCipher.doFinal(paramArrayOfByte2);
  }
}