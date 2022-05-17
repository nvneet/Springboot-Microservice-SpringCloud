package nav.app.ui.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateUserEntityReq {
	
	@NotNull(message="First name can not be null.")
	@Size(min=2)
	private String firstName;
	
	@NotNull(message="Last name can not be null.")
	@Size(min=2)
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
