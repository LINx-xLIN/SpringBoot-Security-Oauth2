package com.lin.security.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 功能描述: 资源服务器配置
 */
@Configuration
@EnableResourceServer
/*@EnableGlobalMethodSecurity(prePostEnabled = true)*/
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "resId";


    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // 配置资源id，这里的资源id和授权服务器中的资源id一致
        resources.resourceId(RESOURCE_ID)
                /*.tokenServices(tokenService() )//远程认证*/
                .tokenStore(tokenStore)
                // 设置这些资源仅基于令牌认证
                .stateless(true);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**")
                .access("#oauth2.hasScope('all')")
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }




    /*//远程认证资源
    //资源服务令牌解析服务  
    @Bean
    public ResourceServerTokenServices tokenService() {      
        //使用远程服务请求授权服务器校验token,必须指定校验token 的url、client_id，client_secret      
        RemoteTokenServices service=new RemoteTokenServices();      
        service.setCheckTokenEndpointUrl("http://localhost:53020/uaa/oauth/check_token");      
        service.setClientId("c1");      
        service.setClientSecret("secret");      
        return service;  
    }  */
}
