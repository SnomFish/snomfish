package io.github.snomfish.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    

    private String secret;
    private long accessTokenExpirationMs;
    private long refreshTokenExpirationMs;


    public String getSecret() {return secret;}
    public long getAccessTokenExpirationMs() {return accessTokenExpirationMs;}
    public long getRefreshTokenExpirationMs() {return refreshTokenExpirationMs;}


    public void setSecret(String secret) {this.secret = secret;}
    public void setAccessTokenExpirationMs(long accessTokenExpirationMs) {this.accessTokenExpirationMs = accessTokenExpirationMs;}
    public void setRefreshTokenExpirationMs(long refreshTokenExpirationMs) {this.refreshTokenExpirationMs = refreshTokenExpirationMs;}
}
