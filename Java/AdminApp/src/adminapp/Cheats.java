/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminapp;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Usuari
 */
public class Cheats {
    
    public static String convertToMD5(String input) throws NoSuchAlgorithmException {
    String result = input;
    if(input != null) {
        MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
        md.update(input.getBytes());
        BigInteger hash = new BigInteger(1, md.digest());
        result = hash.toString(16);
        while(result.length() < 32) { //40 for SHA-1
            result = "0" + result;
        }
    }
    return result;
}
    
    public static boolean matching(String orig, String compare){
        String md5 = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(compare.getBytes());
            byte[] digest = md.digest();
            md5 = new BigInteger(1, md.digest()).toString(16);
            return md5.equals(orig);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
}
