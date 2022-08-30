package work.gaigeshen.shiro.demo.extension.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import work.gaigeshen.shiro.demo.commons.json.JsonCodec;
import work.gaigeshen.shiro.demo.commons.web.HttpStatusResultCode;
import work.gaigeshen.shiro.demo.commons.web.Result;
import work.gaigeshen.shiro.demo.commons.web.Results;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 *
 * @author gaigeshen
 */
public class AccessTokenFilter extends AuthenticatingFilter {

    public static final String ACCESS_TOKEN_HEADER = "x-auth-token";

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String authTokenHeader = httpRequest.getHeader(ACCESS_TOKEN_HEADER);
        return new AccessToken(authTokenHeader, getHost(request));
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String accessTokenHeader = httpRequest.getHeader(ACCESS_TOKEN_HEADER);
        if (Objects.isNull(accessTokenHeader)) {
            return true;
        }
        if (!executeLogin(request, response)) {
            Result<?> errorResult = Results.create(HttpStatusResultCode.UNAUTHORIZED);
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.getWriter().write(JsonCodec.instance().encode(errorResult));
            return false;
        }
        return true;
    }
}
