package forceman.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Igor on 23.12.2016.
 */
public class PasswordHash {
    public static byte[] getNextSalt(){
        SecureRandom sRandom =  new SecureRandom();
        byte[] bytes = new byte[20];
        sRandom.nextBytes(bytes);
        return bytes;
    }

    public static String createHash(String password, String salt) {
        /*
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update( new String(password + salt).getBytes() );
        byte[] byteData = md.digest();
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println("Hex format : " + sb.toString());
        return sb.toString();
        */
        MessageDigest messageDigest=null;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update((password+salt).getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String encryptedPassword = (new BigInteger(messageDigest.digest())).toString(16);
        System.out.println("Encrypted Password: " + encryptedPassword);
        return encryptedPassword;
    }

    public static boolean authenticate(String password, String oldHash, String salt){
        String newHash = createHash(password, salt);
        return newHash.equals(oldHash);
    }


}
