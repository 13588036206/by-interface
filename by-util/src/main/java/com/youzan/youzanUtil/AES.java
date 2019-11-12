package com.youzan.youzanUtil;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;

/**
 * @Author fengyuhao
 * @Date 2019-04-09 20:42
 * https://blog.csdn.net/u011514810/article/details/72725398
 */
public class AES {
    /**
          * AES解密
          * @param strKey
          * @param content
          * @return
          * @throws Exception
          */
    public static String AESDecrypt(byte[] content, String strKey) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] original = cipher.doFinal(content);
        String originalString = new String(original);
        return originalString;
    }

    private static SecretKeySpec getKey(String strKey) {
        byte[] arrBTmp = strKey.getBytes();
        // 创建一个空的16位字节数组（默认值为0）
        byte[] arrB = new byte[16];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
        return skeySpec;
    }

    public static String decrypt(String message,String key){
        String decryResult = null;
        try {
            message = URLDecoder.decode(message, "GBK");
            byte[] bytes = new BASE64Decoder().decodeBuffer(message);
            decryResult= AES.AESDecrypt(bytes, key);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return decryResult;
    }

    public static void main(String[] args){
        String decryResult = null;
       String token = "7MCfwcsVib4W3jKMgUChnqcknVTYYVm%2BjHtnNIcBZL%2Brb%2BrT1cKtIHIf2fGxyWLMqOdsJVfByphP%0AjWRmlwM5zg%3D%3D";
        //String token = "itEaKPZPhDH0gumJBUzVvWNnKVpT5Q2wreXhWFn87z4b%2f0USFSmXIZ2RBr%2f2YHJIXUtDJu%2fVn24xuqmgQQXQ2A%3d%3d";
        //1、从map中取出加密过的message，使用urlDecode转 码,
        //String message = "y1cfJmonkFPrjc2zE23R3Brp0DEWvexcd1GjdSie61Wmbkvd3cjruQ76 KApHx3pGMuWOl2hur6K";
        try {
            String message = URLDecoder.decode(token,"GBK");
            //7MCfwcsVib4W3jKMgUChnqcknVTYYVm+jHtnNIcBZL+rb+rT1cKtIHIf2fGxyWLMqOdsJVfByphPjWRmlwM5zg==
            //2、解密消息内容,密钥为client_secret注意修改
            byte[] bytes = new BASE64Decoder().decodeBuffer(message);
            decryResult = AES.AESDecrypt(bytes, "8c0c629c95c9bcb36269634788f504fc");
            //3、解密后的数据
            System.out.println("解密后的数据>>"+decryResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
