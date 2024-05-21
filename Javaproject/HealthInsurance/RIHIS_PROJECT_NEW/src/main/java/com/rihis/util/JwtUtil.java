package com.rihis.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${rahul.secret}")
	private String SECRET_KEY;
	
	private String generateToken(Map<String, Object> claims , String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(200)))
				.setIssuer("rahul")
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}
	
	public String generateToken(String subject) {
		Map<String, Object> claims = new HashMap<>();
		return generateToken(claims, subject);
	}
	
	private Claims getClaims(String token) {
		return Jwts.parser()
					.setSigningKey(SECRET_KEY)
					.parseClaimsJws(token)
					.getBody();
	}
	
	public String getUsername(String token) {
		return getClaims(token).getSubject();	
	}
}
