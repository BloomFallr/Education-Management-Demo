package cn.edu.ustc.sscs.utils;

import cn.edu.ustc.sscs.common.constant.JwtClaimsConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * JWT令牌工具类
 */
public class JWTUtil {

    public static String createJWT(String secret, Long ttlMillis, Integer id, String identity) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        String uuid = UUID.randomUUID().toString();
        Date exprireDate = Date.from(Instant.now().plusMillis(ttlMillis));
        SecureDigestAlgorithm<SecretKey, SecretKey> algorithm = Jwts.SIG.HS256;
        return Jwts.builder()
                .claim(JwtClaimsConstant.ID, id)
                .claim(JwtClaimsConstant.IDENTITY, identity)
                .id(uuid)
                .expiration(exprireDate)
                .issuedAt(new Date())
                .signWith(key, algorithm)
                .compact();
    }

    public static Claims parseJWT(String secret, String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static Integer getIdFromToken(String secret, String token) {
        return parseJWT(secret, token).get(JwtClaimsConstant.ID, Integer.class);
    }

    public static String getIdentityFromToken(String secret, String token) {
        return parseJWT(secret, token).get(JwtClaimsConstant.IDENTITY, String.class);
    }
}
