package work.gaigeshen.shiro.demo;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gaigeshen
 */
@Configuration
public class ShiroConfiguration {

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
    factoryBean.setSecurityManager(securityManager);

    factoryBean.setLoginUrl("/login");

    Map<String, String> filterChainDefinitions = new HashMap<>();
    filterChainDefinitions.put("/register", "anon");
    filterChainDefinitions.put("/login", "anon");
    filterChainDefinitions.put("/**", "authcBearer");
    factoryBean.setFilterChainDefinitionMap(filterChainDefinitions);

    factoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
    factoryBean.setGlobalFilters(Collections.singletonList("noSessionCreation"));

    return factoryBean;
  }

}
