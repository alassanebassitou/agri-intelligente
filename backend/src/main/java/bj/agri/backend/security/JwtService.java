package bj.agri.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import static bj.agri.backend.utils.Constants.USER_ID;

@Service
public class JwtService {

    @Value("${application.api.jwt-secret-key}")
    private String SECRET_KEY;

    public String generateToken(AuthenticatedUser me){
        return Jwts.builder()
                .subject(me.npi())
                .claim(USER_ID, me.userId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (30*60*1000)))
                .signWith(getSignKey())
                .compact();
    }

    /*private String createToken(AuthenticatedUser me) {
        return Jwts.builder()
                .subject(me.username())
                .claim("userId", me.userId())
                .claim("roles", me.authorities())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (10000 * 60 * 30)))
                .signWith(getSignKey())
                .compact();
    }*/

    private Key getSignKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public AuthenticatedUser extractAuthUser(String token){
        return extractClaims(token, claims -> new AuthenticatedUser(
                claimAsLong(claims, USER_ID),
                claims.getSubject(),
                null,
                null
        ));
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignKey())
                .build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        AuthenticatedUser authUser = extractAuthUser(token);
        return (authUser.getUsername().equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Long claimAsLong(Claims claims, String key) {
        Object value =claims.get(key);
        return value == null ? null:((Number)value).longValue();
    }
}
