package work.gaigeshen.shiro.demo;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import work.gaigeshen.shiro.demo.accesstoken.AccessTokenGenerator;
import work.gaigeshen.shiro.demo.accesstoken.JwtAccessTokenGenerator;

/**
 *
 * @author gaigeshen
 */
@Configuration
public class ShiroConfiguration {

  @Bean
  public AccessTokenGenerator accessTokenGenerator() {
    return new JwtAccessTokenGenerator("secret");
  }

  @Bean
  public ShiroFilterChainDefinition shiroFilterChainDefinition() {
    DefaultShiroFilterChainDefinition filterChainDefinition = new DefaultShiroFilterChainDefinition();
    filterChainDefinition.addPathDefinition("/**", "anon");
    return filterChainDefinition;
  }

  @Bean
  public CacheManager cacheManager() {
    return new MemoryConstrainedCacheManager();
  }
}
