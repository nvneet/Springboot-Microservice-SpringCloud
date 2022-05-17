package nav.photoapp.api.users.db.helper;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByEmail (String email);
}
