package security;

import forceman.security.IPasswordHash;
import forceman.security.PasswordHash;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Igor on 23.12.2016.
 */
public class HashSaltedPasswordJUnitTest extends Assert{
    @Test
    public void testHash() throws NoSuchAlgorithmException {
        IPasswordHash passwordHashImpl = new PasswordHash("SHA-256");
        String salt = passwordHashImpl.getNextSalt(); //Arrays.toString( passwordHashImpl.getNextSalt() );
        String hash =  passwordHashImpl.createHash("qwerty123456", salt);
        System.out.println("salt " + salt);
        System.out.println("hash: " + hash);


        String newHash = passwordHashImpl.createHash("qwerty123456", salt);
        assertTrue(hash.equals(newHash));
        //assertTrue(PasswordHash.authenticate("qwerty123458", hash, salt));
    }


}
