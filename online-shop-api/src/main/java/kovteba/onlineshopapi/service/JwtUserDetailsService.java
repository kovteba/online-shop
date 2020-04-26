package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(JwtUserDetailsService.class);

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		log.info("loadUserByUsername, " + this.getClass());

		UserEntity userEntity = (UserEntity)userService.getUserByEmail(email).getObject();

		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}

		Set<GrantedAuthority> roles = new HashSet();
		roles.add(new SimpleGrantedAuthority(userEntity.getRoleUser().getRoleValue()));

		return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(),
				roles);
	}


}