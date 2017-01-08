package forceman.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Igor on 23.12.2016.
 * Класс получения хешей + соль для паролей
 * Поддерживаются следующие криптоалгоритмы:
 * MD5
 * SHA-1
 * SHA-256
 */
public class PasswordHash implements IPasswordHash {
    /**
     * Криптоалгоритм формирования хеша
     */
    private String algoritm;

    /**
     * Функция получения значения поля {@link PasswordHash#algoritm}
     * @return наименование крипто алгоритма формирования хеша
     */
    public String getAlgoritm(){
        return algoritm;
    }

    /**
     * Конструктор класса {@link PasswordHash}
     * @param algoritm Алгоритм формирования хеша пароля (MD5, SHA-1б SHA-256)
     */
    public PasswordHash(String algoritm){
        this.algoritm = algoritm;
    }
    /**
     * Создание соли для пароля
     * @return соль для пароля
     */
    public String getNextSalt(){
        SecureRandom sRandom =  new SecureRandom();
        byte[] bytes = new byte[20];
        sRandom.nextBytes(bytes);
        return new BigInteger(bytes).toString(16);
    }

    /**
     *  Создание хеша по заданному парролю и соли
     * @param password Пароль
     * @param salt Сгенерированная соль
     * @return Хеш пароля + соль
     * @throws NoSuchAlgorithmException При указании некорректного названия алгоритма (разрешенные значения: MD5, SHA-1б SHA-256)
     * @throws IllegalArgumentException При указании в качестве аргументов данного метода значения null
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
