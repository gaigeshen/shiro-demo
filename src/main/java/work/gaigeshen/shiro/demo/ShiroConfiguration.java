package work.gaigeshen.shiro.demo;

import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author gaigeshen
 */
@Configuration
public class ShiroConfiguration {

  @Bean
  public BearerTokenCreator bearerTokenCreator() {
    return new DefaultBearerTokenCreator("secret", 1800);
  }

  @Bean
  public PasswordService passwordService() {
    return new PasswordService() {
      @Override
      public String encryptPassword(Object plaintextPassword) throws IllegalArgumentException {
        return (String) plaintextPassword;
      }

      @Override
      public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
        char[] plainTextChars = (char[]) submittedPlaintext;
        return Objects.equals(new String(plainTextChars), encrypted);
      }
    };
  }

  @Bean
  public Realm realm(BearerTokenCreator bearerTokenCreator,
                     UserService userService, PasswordService passwordService) {
    return new DefaultAuthorizingRealm(bearerTokenCreator, userService, passwordService);
  }

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
    factoryBean.setSecurityManager(securityManager);

    Map<String, String> filterChainDefinitions = new HashMap<>();
    filterChainDefinitions.put("/users/register", "anon");
    filterChainDefinitions.put("/users/login", "anon");
    filterChainDefinitions.put("/**", "authcBearer");
    factoryBean.setFilterChainDefinitionMap(filterChainDefinitions);

    factoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
    factoryBean.setGlobalFilters(Collections.singletonList("noSessionCreation"));

    return factoryBean;
  }

}
