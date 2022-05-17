package nav.app.userservice.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nav.app.ui.model.request.UserEntityReq;
import nav.app.ui.model.response.UserEntityResp;
import nav.app.userservice.UserService;
import nav.app.userservice.util.UserUtil;

@Service
public class UserServiceImpl implements UserService{

	private UserUtil userUtil;
	Map<String, UserEntityResp> users;
	
	public  UserServiceImpl() {
		
	}
	
	@Autowired
	public  UserServiceImpl (UserUtil userUtil) {
		this.userUtil = userUtil;
	}
	
	@Override
	public UserEntityResp createUser(UserEntityReq userEntityReq) {

		UserEntityResp userEntityResp = new UserEntityResp();
		userEntityResp.setEmail(userEntityReq.getEmail());
		userEntityResp.setFisrtName(userEntityReq.getFirstName());
		userEntityResp.setLastName(userEntityReq.getLastName());
		
		userEntityResp.setUserId(userUtil.getUserId());
//		
//		if(users==null) users = new HashMap<>();
//		users.put(userUtil.getUserId(), userEntityResp);
		
		return userEntityResp;
	}

}
