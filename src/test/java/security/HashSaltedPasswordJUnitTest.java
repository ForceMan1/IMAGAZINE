package security;

import forceman.security.PasswordHash;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Igor on 23.12.2016.
 */
public class HashSaltedPasswordJUnitTest extends Assert{
    @Test
    public void testHash(){
        String salt = Arrays.toString(PasswordHash.getNextSalt());
        String hash = PasswordHash.createHash("qwerty123456", salt);
        System.out.println("salt " + salt);
        System.out.println("hash: " + hash);
        assertTrue(PasswordHash.authenticate("qwerty123458", hash, salt));
    }


}
