package br.gov.mapa.gateway.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.gov.mapa.core.config.JwtConfiguration;
import br.gov.mapa.gateway.filter.GatewayJwtTokenAuthorizationFilter;
import br.gov.mapa.token.config.SecurityTokenConfig;
import br.gov.mapa.token.converter.TokenConverter;

//@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends SecurityTokenConfig {
    private final TokenConverter tokenConverter;

    public SecurityConfig(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.tokenConverter = tokenConverter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//	        .authorizeRequests()
//			.antMatchers(jwtConfiguration.getLoginUrl()).permitAll()
//			.antMatchers(PUBLIC_MATCHERS).permitAll()
//			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
//		.and()
	        .addFilterAfter(new GatewayJwtTokenAuthorizationFilter(jwtConfiguration, tokenConverter), 
	        		UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }
    
	/*@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers(jwtConfiguration.getLoginUrl())
		.antMatchers(PUBLIC_MATCHERS)
		.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST);
	}*/
}
