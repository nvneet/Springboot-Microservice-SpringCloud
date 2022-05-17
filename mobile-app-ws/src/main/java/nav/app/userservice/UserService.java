package nav.app.userservice;

import nav.app.ui.model.request.UserEntityReq;
import nav.app.ui.model.response.UserEntityResp;

public interface UserService {
	UserEntityResp createUser(UserEntityReq user);
}
