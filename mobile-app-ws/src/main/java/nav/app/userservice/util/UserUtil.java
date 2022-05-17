package nav.app.userservice.util;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UserUtil {

	public String getUserId() {
		return UUID.randomUUID().toString();
	}
}
