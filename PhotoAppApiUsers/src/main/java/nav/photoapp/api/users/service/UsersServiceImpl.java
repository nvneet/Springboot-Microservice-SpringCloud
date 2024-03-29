package nav.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import nav.photoapp.api.users.db.helper.UserDTO;
import nav.photoapp.api.users.db.helper.UserEntity;
import nav.photoapp.api.users.db.helper.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {

	private UsersRepository usersRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
//	@Autowired
//	public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//		this.usersRepository = usersRepository;
//		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//	}
	
	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	
	@Override
	public UserDTO createUser(UserDTO userDetail) {
		
		userDetail.setUserId(UUID.randomUUID().toString());
//		userDetail.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetail.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDetail, UserEntity.class);

		usersRepository.save(userEntity);
		
		UserDTO returnValue = modelMapper.map(userEntity, UserDTO.class);
		
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = usersRepository.findByEmail(username);		
		if(userEntity == null) throw new UsernameNotFoundException(username);
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true,true,true,true, new ArrayList<>());
	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {
		UserEntity userEntity = usersRepository.findByEmail(email);		
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		return new ModelMapper().map(userEntity,UserDTO.class);
	}

}
