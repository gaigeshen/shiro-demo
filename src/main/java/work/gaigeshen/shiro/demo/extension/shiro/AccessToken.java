package work.gaigeshen.shiro.demo.extension.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;

/**
 *
 * @author gaigeshen
 */
public class AccessToken implements HostAuthenticationToken {

    private final String token;

    private final String host;

    public AccessToken(String token, String host) {
        this.token = token;
        this.host = host;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
