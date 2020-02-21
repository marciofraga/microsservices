package br.gov.mapa.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.gov.mapa.auth.filter.JwtAuthenticationFilter;
import br.gov.mapa.core.config.JwtConfiguration;
import br.gov.mapa.token.config.SecurityTokenConfig;
import br.gov.mapa.token.converter.TokenConverter;
import br.gov.mapa.token.creator.TokenCreator;
import br.gov.mapa.token.filter.JwtTokenAuthorizationFilter;

@EnableWebSecurity
public class SecurityConfig extends SecurityTokenConfig {

	@Autowired
	private final UserDetailsService userDetailsService;
	@Autowired
	private final JwtConfiguration jwtConfiguration;
	@Autowired
	private final TokenCreator tokenCreator;
	@Autowired
	private final TokenConverter tokenConverter;
	
	public SecurityConfig(JwtConfiguration jwtConfiguration, @Qualifier("userMapaDetailsService") UserDetailsService userDetailsService,
	            TokenCreator tokenCreator, TokenConverter tokenConverter) {
			super(jwtConfiguration);
			this.jwtConfiguration = jwtConfiguration;
			this.userDetailsService = userDetailsService;
			this.tokenCreator = tokenCreator;
			this.tokenConverter = tokenConverter;
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
       http
       	.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtConfiguration, tokenCreator))
       	.addFilterAfter(new JwtTokenAuthorizationFilter(jwtConfiguration, tokenConverter), UsernamePasswordAuthenticationFilter.class);
       super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
