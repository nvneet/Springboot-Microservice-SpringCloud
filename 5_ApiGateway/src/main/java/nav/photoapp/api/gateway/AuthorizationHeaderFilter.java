package nav.photoapp.api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

	@Autowired
	Environment env;
	
	public AuthorizationHeaderFilter() {
		super(Config.class); // To set, which config class to use when the apply method is called
	}
	
	public static class Config {
		// put configuration properties here
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			
			ServerHttpRequest request = exchange.getRequest();
			
//			if(request.getHeaders().containsKey("Authorization")) {
			if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
			}
			
			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt = authorizationHeader.replace("Bearer", "").trim();
			
			if (!isJwtTokenValid(jwt)) {
				return onError(exchange, "JWT Token not valid", HttpStatus.UNAUTHORIZED);
			}
			
			return chain.filter(exchange);
		};
	}

	private Mono<Void> onError(ServerWebExchange exchange, String string, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		
		return response.setComplete();
	}
	/*
	 * Validating JWT token
	 */
	private boolean isJwtTokenValid(String jwt) {
		boolean isValid = true;
		
		// io.jsonwebtoken.SignatureException: JWT signature does not match locally computed signature. 
		// JWT validity cannot be asserted and should not be trusted.
		String subject = null;
		try {
			subject = Jwts.parser()
						.setSigningKey(env.getProperty("token.secretkey"))
						.parseClaimsJws(jwt)
						.getBody()
						.getSubject();
		} catch (Exception e) {
			isValid=false;
		}
		
		if(subject == null || subject.isEmpty()) {
			isValid = false;
		}
		
		return isValid;
	}
}
