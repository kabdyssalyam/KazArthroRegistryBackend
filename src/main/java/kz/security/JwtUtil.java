package kz.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "supersecureyourSecretKeyHere12345andevenmoresecure!@%";  // Use a stronger key in production

    private static Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build();
        return parser.parseClaimsJws(token).getBody();
    }

    public static String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 1000 * 60 * 60 * 3); // 1 hour

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private static <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }

    public static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new java.util.Date());
    }

    private static java.util.Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    private interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
}
