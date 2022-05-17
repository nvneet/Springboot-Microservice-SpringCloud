package nav.photoapp.api.users.db.helper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="users")
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7299469165951197598L;
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=true, unique=true)
	private String userId;
	
	@Column(nullable=true, length=50)
	private String firstName;
	
	@Column(nullable=true, length=50)
	private String lastName;
	
	@Column(nullable=true, length=120, unique=true)
	private String email;
	
	@Column(nullable=true, length=50)
	private String password;
	
	@Column(nullable=true, unique=true)
	private String encryptedPassword;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

}
