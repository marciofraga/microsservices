package br.gov.mapa.token.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.SignedJWT;

import br.gov.mapa.core.config.JwtConfiguration;
import br.gov.mapa.token.converter.TokenConverter;
import br.gov.mapa.token.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {
	
	@Autowired
	protected final JwtConfiguration jwtConfiguration;
	@Autowired
    protected final TokenConverter tokenConverter;
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		 String header = request.getHeader(jwtConfiguration.getHeader().getName());

	        if (header == null || !header.startsWith(jwtConfiguration.getHeader().getPrefix())) {
	            chain.doFilter(request, response);
	            return;
	        }

	        String token = header.replace(jwtConfiguration.getHeader().getPrefix(), "").trim();

	        SecurityContextUtil.setSecurityContext(StringUtils.equalsIgnoreCase("signed", jwtConfiguration.getType()) ? validate(token) : decryptValidating(token));

	        chain.doFilter(request, response);
	    }

	    @SneakyThrows
	    private SignedJWT decryptValidating(String encryptedToken) {
	        String signedToken = tokenConverter.decryptToken(encryptedToken);
	        tokenConverter.validateTokenSignature(signedToken);
	        return SignedJWT.parse(signedToken);
	    }

	    @SneakyThrows
	    private SignedJWT validate(String signedToken) {
	        tokenConverter.validateTokenSignature(signedToken);
	        return SignedJWT.parse(signedToken);
	    }
}