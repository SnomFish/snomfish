package io.github.snomfish.security.jwt;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtCookieService {
    
    
    public final static String ACCESS_TOKEN_COOKIE = "ACCESS";
    public final static String REFRESH_TOKEN_COOKIE = "REFRESH";


    public void addAccessTokenCookie(HttpServletResponse response, String token) {
        response.addCookie(buildCookie(
            ACCESS_TOKEN_COOKIE,
            token,
            15 * 60
        ));
    }
    public void addRefreshTokenCookie(HttpServletResponse response, String token) {
        response.addCookie(buildCookie(
            REFRESH_TOKEN_COOKIE,
            token,
            24 * 60 * 60
        ));
    }


    public void clearAccessTokenCookie(HttpServletResponse response) {
        response.addCookie(deleteCookie(ACCESS_TOKEN_COOKIE));
    }
    public void clearCookies(HttpServletResponse response) {
        response.addCookie(deleteCookie(ACCESS_TOKEN_COOKIE));
        response.addCookie(deleteCookie(REFRESH_TOKEN_COOKIE));
    }


    public Cookie buildCookie(String name, String value, int maxAgeSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeSeconds);
        return cookie;
    }


    public Cookie deleteCookie(String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }
}
