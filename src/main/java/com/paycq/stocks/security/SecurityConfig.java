package com.paycq.stocks.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console; 

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final RsaKeyProperties keyProperties;
	
	public SecurityConfig(RsaKeyProperties keyProperties) {
		this.keyProperties = keyProperties;
	}
	
	@Bean
	public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.headers().frameOptions().sameOrigin();
		
		http.authorizeHttpRequests().requestMatchers(toH2Console(), 
				AntPathRequestMatcher.antMatcher("/swagger-ui/**"),
				AntPathRequestMatcher.antMatcher("/swagger-resources/**"),
				AntPathRequestMatcher.antMatcher("/webjars/**"),
				AntPathRequestMatcher.antMatcher("/v3/api-docs/**"),
				AntPathRequestMatcher.antMatcher("favicon.ico"),
				AntPathRequestMatcher.antMatcher("/api/v1/stock/signup")
				)
				.permitAll();
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		
	 	return http.
	 			csrf(csrf -> csrf.disable()).
	 			oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt).
	 			sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
	 			httpBasic(withDefaults()).
	 			build();
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(this.keyProperties.publicKey()).build();
	}
	
	@Bean
	public JwtEncoder jwtEndoder() {
		JWK jwk = new RSAKey.Builder(this.keyProperties.publicKey()).
									privateKey(this.keyProperties.privatekey()).
									build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
	

}
