package forceman.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Igor on 23.12.2016.
 * ����� ��������� ����� + ���� ��� �������
 * �������������� ��������� ���������������:
 * MD5
 * SHA-1
 * SHA-256
 */
public class PasswordHash implements IPasswordHash {
    /**
     * �������������� ������������ ����
     */
    private String algoritm;

    /**
     * ������� ��������� �������� ���� {@link PasswordHash#algoritm}
     * @return ������������ ������ ��������� ������������ ����
     */
    public String getAlgoritm(){
        return algoritm;
    }

    /**
     * ����������� ������ {@link PasswordHash}
     * @param algoritm �������� ������������ ���� ������ (MD5, SHA-1� SHA-256)
     */
    public PasswordHash(String algoritm){
        this.algoritm = algoritm;
    }
    /**
     * �������� ���� ��� ������
     * @return ���� ��� ������
     */
    public String getNextSalt(){
        SecureRandom sRandom =  new SecureRandom();
        byte[] bytes = new byte[20];
        sRandom.nextBytes(bytes);
        return new BigInteger(bytes).toString(16);
    }

    /**
     *  �������� ���� �� ��������� ������� � ����
     * @param password ������
     * @param salt ��������������� ����
     * @return ��� ������ + ����
     * @throws NoSuchAlgorithmException ��� �������� ������������� �������� ��������� (����������� ��������: MD5, SHA-1� SHA-256)
     * @throws IllegalArgumentException ��� �������� � �������� ���������� ������� ������ �������� null
     */
    public String createHash(String password, String salt) throws NoSuchAlgorithmException, IllegalArgumentException {
        if(password == null || salt == null)
            throw new IllegalArgumentException();
        MessageDigest messageDigest=null;
        messageDigest = MessageDigest.getInstance(algoritm);
        messageDigest.update((password+salt).getBytes());

        String encryptedPassword = (new BigInteger(messageDigest.digest())).toString(16);
        System.out.println("Encrypted Password: " + encryptedPassword);
        return encryptedPassword;
    }

    /*
    public static boolean authenticate(String password, String oldHash, String salt, String algoritm)
                                                                            throws NoSuchAlgorithmException{
        String newHash = createHash(password, salt, algoritm);
        return newHash.equals(oldHash);
    }
    */


}
