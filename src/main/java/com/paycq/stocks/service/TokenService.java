package com.paycq.stocks.service;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

	private final JwtEncoder encoder;

	public TokenService(JwtEncoder encoder) {
		this.encoder = encoder;
	}
	
	public String generateToken(Authentication authentication) {
		
		Instant now = Instant.now();
		String scope = authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(""));
		JwtClaimsSet claims = 	JwtClaimsSet.builder()
								.issuer("self")
								.issuedAt(now)
								.expiresAt(now.plusSeconds(3600))
								.claim("scope", scope)
								.subject(authentication.getName())
								.build();
				
		
		return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

}
