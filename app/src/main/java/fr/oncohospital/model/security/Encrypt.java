package fr.oncohospital.model.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Assia HACHICHI on 25/03/2021
 * @author Assia HACHICHI
 */

public class Encrypt {

    /**
     * This method encrypt a given string with MD5 message-digest algorithm.
     * @param s is an input string to be encrypt
     * @return the encrypting string
     */
    public static String md5(String s) {
        MessageDigest myDigest;
        byte bufferStringByte[];
        StringBuffer encryptString;
        int i;
        String tmp;
        try {
            myDigest = java.security.MessageDigest.getInstance("MD5");
            myDigest.update(s.getBytes());
            bufferStringByte = myDigest.digest();
            encryptString = new StringBuffer();
            for (i=0; i<bufferStringByte.length; i++){
                tmp = Integer.toHexString(0xFF & bufferStringByte[i]);
                if (tmp.length()==1) tmp = '0'+tmp;
                encryptString.append(tmp);
            }
            return encryptString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
