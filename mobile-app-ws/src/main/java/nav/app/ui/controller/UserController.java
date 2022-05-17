package nav.app.ui.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nav.app.ui.model.request.UpdateUserEntityReq;
import nav.app.ui.model.request.UserEntityReq;
import nav.app.ui.model.response.UserEntityResp;
import nav.app.userservice.UserService;
import nav.app.userservice.impl.UserServiceImpl;

@RestController
@RequestMapping("/users") // http://localhost:8080/users
public class UserController {
	
	Map<String, UserEntityResp> users;
	
	@Autowired              // By this Spring will inject UserServiceImpl class in to this class
	UserService userService;
	
//	http://localhost:8080/users?page=1&limit=15  <= Query String path parameter
	@GetMapping
	public String getUsers(@RequestParam(value="page", defaultValue="1") int page, 
						   @RequestParam(value="limit", defaultValue="15") int limit, 
						   @RequestParam(value="sort", required=false) String sort
						  ) {
		return "get user was called with page = " + page + " and Limit = " + limit + " and sort = " + sort;
	}
	
//	http://localhost:8080/users/2  <= Path Variable
/*	public UserRest getUser(@PathVariable String userId) {
		UserRest userRest = new UserRest();
		userRest.setEmail("test@test.com");
		userRest.setFisrtName("Navneet");
		userRest.setLastName("Mishra");
		return new ResponseEntity<UserRest>(userRest, HttpStatus.OK);
	}*/
	
	@GetMapping(path="/{userId}", 
			produces = { MediaType.APPLICATION_XML_VALUE, 
						 MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserEntityResp> getUser(@PathVariable String userId) {   // To get custom http status codes, use ResponseEntity	
//		String firstName = null;
//		int firstNameLength = firstName.length();
//		System.out.println("printing firstNameLength: " + firstNameLength);
		
//		if(true) throw new UserServiceException("A User Service Exception is Thrown.");
		
		if (!users.containsKey(userId)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		}		
		return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
	}
	

//	@PostMapping    OR,
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, 
					 		  MediaType.APPLICATION_JSON_VALUE },
			     produces = { MediaType.APPLICATION_XML_VALUE, 
				 		  	  MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserEntityResp> createUser(@Valid @RequestBody UserEntityReq userEntityReq) {
		
		System.out.println("Autowired userService class is:  " + userService.getClass());
		UserEntityResp userEntityResp = userService.createUser(userEntityReq);
		
		if(users==null) users = new HashMap<>();
		users.put(userEntityResp.getUserId(), userEntityResp);
		
		return new ResponseEntity<UserEntityResp>(userEntityResp, HttpStatus.OK);
	}
	
//	@PutMapping
	@PutMapping(path="/{userId}",
			    consumes = { MediaType.APPLICATION_XML_VALUE, 
	 		  				 MediaType.APPLICATION_JSON_VALUE },
				produces = { MediaType.APPLICATION_XML_VALUE, 
							 MediaType.APPLICATION_JSON_VALUE })
	public UserEntityResp updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserEntityReq updateUserEntityReq) {

		UserEntityResp storedUserEntityResp = users.get(userId);
		storedUserEntityResp.setFisrtName(updateUserEntityReq.getFirstName());
		storedUserEntityResp.setLastName(updateUserEntityReq.getLastName());
		
		users.put(userId, storedUserEntityResp);
		
		return storedUserEntityResp;
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id) {
		
		users.remove(id);		
		return ResponseEntity.noContent().build();
	}
}
