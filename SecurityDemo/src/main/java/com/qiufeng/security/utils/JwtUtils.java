package com.qiufeng.security.utils;

import com.qiufeng.security.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理Token工具
 */
public class JwtUtils {

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String SUBJECT = "congge";

    public static final long EXPIRITION = 1000 * 24 * 60 * 7;

    public static final String APPSECRET_KEY = "congge_secret";

    private static final String ROLE_CLAIMS = "rol";

    /**
     * @param user 用户
     * @return 令牌
     */
    public static String generateJsonWebToken(User user) {
        if (user.getId() == null || user.getUserName() == null ) {
            return  null;
        }
        Map<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS,"rol");

        String token = Jwts.builder()
                            .setSubject(SUBJECT)
                            .setClaims(map)
                            .claim("id",user.getId())
                            .claim("userName",user.getUserName())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                            .signWith(SignatureAlgorithm.HS256,APPSECRET_KEY).compact();

        return token;
    }

    /**
     * 生产Token
     * @param userName 用户名
     * @param role 角色
     * @return token
     */
    public static String createToken(String userName,String role) {
        Map<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS,role);

        String token = Jwts.builder()
                            .setSubject(userName)
                            .setClaims(map)
                            .claim("userName",userName)
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                            .signWith(SignatureAlgorithm.HS256,APPSECRET_KEY).compact();
        return token;
    }

    /**
     * 获取用户
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
            return claims;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取用户名
     * @param token
     * @return 用户名
     */
    public static String getUserName(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("userName").toString();
    }

    /**
     * 获取用户角色
     * @param token
     * @return 角色
     */
    public static String getUserRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("rol").toString();
    }

    /**
     * 校验是否过期
     * @param token
     * @return false 正常，true 过期
     */
    public static boolean isExpiration(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }
}
