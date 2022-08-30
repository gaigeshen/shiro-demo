package work.gaigeshen.shiro.demo.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import work.gaigeshen.shiro.demo.extension.shiro.AccessTokenFilter;
import work.gaigeshen.shiro.demo.extension.shiro.AccessTokenRealm;
import work.gaigeshen.shiro.demo.extension.shiro.Md5PasswordService;
import work.gaigeshen.shiro.demo.user.UserService;
import work.gaigeshen.shiro.demo.user.accesstoken.AccessTokenCreator;
import work.gaigeshen.shiro.demo.user.accesstoken.DefaultAccessTokenCreator;

import java.util.Collections;
import java.util.List;

/**
 * @author gaigeshen
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition filterChainDefinition = new DefaultShiroFilterChainDefinition();
        filterChainDefinition.addPathDefinition("/users/register", "anon");
        filterChainDefinition.addPathDefinition("/users/login", "anon");
        filterChainDefinition.addPathDefinition("/**", "accessToken");
        return filterChainDefinition;
    }

    @Bean
    public AccessTokenFilter accessToken() {
        return new AccessTokenFilter();
    }

    @Bean
    public List<String> globalFilters() {
        return Collections.singletonList(DefaultFilter.noSessionCreation.name());
    }

    @Bean
    public Realm realm(UserService userService, AccessTokenCreator accessTokenCreator) {
        return AccessTokenRealm.create(Md5PasswordService.INSTANCE, userService, accessTokenCreator);
    }

    @Bean
    public AccessTokenCreator accessTokenCreator() {
        return DefaultAccessTokenCreator.create();
    }

    @Bean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }
}