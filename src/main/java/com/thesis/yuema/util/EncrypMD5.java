package com.thesis.yuema.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class EncrypMD5 {
	
	public static String eccrypt(String info){		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(info.getBytes());
			byte[]byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			//32位加密
			return buf.toString();
			// 16位的加密
			//return buf.toString().substring(8, 24); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
		
    public static void md5() {       
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();       
        // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版       
        md5.setEncodeHashAsBase64(false);       
        String pwd = md5.encodePassword("1234", null);       
        System.out.println("MD5: " + pwd + " len=" + pwd.length());  
    }
    
    public static void main(String[] args) {
    	System.out.println(eccrypt("1234"));
		md5();
	}
	
}
