package com.lin.security.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 功能描述: 使用Jwt存储token的配置
 */
@Configuration
public class JwtTokenStoreConfig {

    //对称密钥加密
    private final static String  SIGNING_KEY = "test_key";


    //通过对称加密认证token
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        //配置JWT使用的秘钥
        accessTokenConverter.setSigningKey(SIGNING_KEY);
        return accessTokenConverter;
    }



    /*//内存管理token
    //通过/oauth/check_token认证token
    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }*/

}
