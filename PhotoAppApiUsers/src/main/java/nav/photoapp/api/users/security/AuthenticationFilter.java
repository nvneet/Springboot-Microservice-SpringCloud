package nav.photoapp.api.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nav.photoapp.api.users.db.helper.UserDTO;
import nav.photoapp.api.users.service.UsersService;
import nav.photoapp.api.users.ui.model.LoginRequestModel;

/*
 * This class to be used as a user authentication filter for api to perform authentication
 * with username and password 
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	//below 2 fields defined at later stage for successfulAuthentication message
	private UsersService usersService;
	private Environment environment;
	
	public AuthenticationFilter(UsersService usersService, 
								Environment environment,
								AuthenticationManager authenticationManager) {
		this.usersService = usersService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			
			LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);
			return getAuthenticationManager()
					.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) 
			throws IOException, ServletException {
		
		String userName = ((User)auth.getPrincipal()).getUsername();
		UserDTO userDetails = usersService.getUserDetailsByEmail(userName);
		
		String token = Jwts.builder()
				.setSubject(userDetails.getUserId())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secretkey"))
				.compact();
		
		res.addHeader("token", token);
		res.addHeader("userId", userDetails.getUserId());
	}
}
