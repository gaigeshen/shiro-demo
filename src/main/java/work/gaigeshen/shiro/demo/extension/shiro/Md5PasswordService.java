package work.gaigeshen.shiro.demo.extension.shiro;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.util.ByteSource;

import java.util.Objects;

/**
 *
 * @author gaigeshen
 */
public class Md5PasswordService implements PasswordService {

    public static final Md5PasswordService INSTANCE = new Md5PasswordService();

    @Override
    public String encryptPassword(Object plaintextPassword) {
        return DigestUtils.md5Hex(ByteSource.Util.bytes(plaintextPassword).getBytes());
    }

    @Override
    public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
        String encryptedPassword = encryptPassword(submittedPlaintext);
        return Objects.equals(encryptedPassword, encrypted);
    }
}
