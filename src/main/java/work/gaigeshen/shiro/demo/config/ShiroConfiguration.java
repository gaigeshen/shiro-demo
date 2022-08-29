package work.gaigeshen.shiro.demo.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gaigeshen
 */
@Configuration
public class ShiroConfiguration {

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

    factoryBean.setGlobalFilters(Collections.singletonList("noSessionCreation"));

    Map<String, String> filterDefinitions = new HashMap<>();
    filterDefinitions.put("/users/login", "anon");
    filterDefinitions.put("/users/register", "anon");
    filterDefinitions.put("/**", "authcBearer");
    factoryBean.setFilterChainDefinitionMap(filterDefinitions);

    factoryBean.setSecurityManager(securityManager);
    return factoryBean;
  }

}