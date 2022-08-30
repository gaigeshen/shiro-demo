package work.gaigeshen.shiro.demo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.gaigeshen.shiro.demo.commons.web.Result;
import work.gaigeshen.shiro.demo.commons.web.Results;
import work.gaigeshen.shiro.demo.user.accesstoken.AccessTokenCreator;

/**
 *
 * @author gaigeshen
 */
@RequestMapping("/users")
@RestController
public class LoginController {

    private final AccessTokenCreator accessTokenCreator;

    public LoginController(AccessTokenCreator accessTokenCreator) {
        this.accessTokenCreator = accessTokenCreator;
    }

    @PostMapping("/login")
    public Result<?> login(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return Results.create(subject.getPrincipal());
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("x-auth-token") String accessToken) {
        accessTokenCreator.invalidate(accessToken);
        return Results.create();
    }
}
