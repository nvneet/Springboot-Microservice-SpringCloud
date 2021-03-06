package nav.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import nav.photoapp.api.users.db.helper.UserDTO;

                    
public interface UsersService extends UserDetailsService{
	UserDTO createUser(UserDTO userDetail);
	UserDTO getUserDetailsByEmail(String email);
}








//<extends UserDetailsService> needed for Authentication purpose in WebSecurity class.

// NOTE: UserDetailsService:
//       springframework.security.core.userdetails.UserDetailsService is Core interface which loads 
//       user-specific data. 
//       It is used throughout the framework as a user DAO and is the strategy used by the
//       DaoAuthenticationProvider. 