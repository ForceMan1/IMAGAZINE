package forceman.security;

import java.security.NoSuchAlgorithmException;

/**
 * Created by 1 on 26.12.2016.
 *  Интерфейс получения соленого хеша пароля
 */
public interface IPasswordHash {
    /**
     * Создание соли для пароля
     * @return соль для пароля
     */
    public String getNextSalt();

    /**
     *  Создание хеша по заданному парролю и соли
     * @param password Пароль
     * @param salt Сгенерированная соль
     * @return Хеш пароля + соли
     */
    String createHash(String password, String salt) throws NoSuchAlgorithmException;
}
