package com.example.tw_thainguyen.security.jwt;

import com.example.tw_thainguyen.security.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret:defaultSecretKey}")
    private String SECRET_KEY;

    @Value("${jwt.expiration:86400000}")
    private Long EXPIRED;

    public String generateToken(UserPrinciple userPrinciple) {
        Date dateExpiration = new Date(System.currentTimeMillis() + EXPIRED);
        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setExpiration(dateExpiration)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("Token đã hết hạn: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Token không hợp lệ: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Lỗi khi xác thực token: {}", e.getMessage());
        }
        return false;
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}