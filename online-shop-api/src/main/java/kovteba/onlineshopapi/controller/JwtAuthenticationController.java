package kovteba.onlineshopapi.controller;

import kovteba.onlineshopapi.entity.RecoveryEntity;
import kovteba.onlineshopapi.entity.UserEntity;
import kovteba.onlineshopapi.mapper.UserMapper;
import kovteba.onlineshopapi.responce.Responce;
import kovteba.onlineshopapi.service.EmailService;
import kovteba.onlineshopapi.service.JwtUserDetailsService;
import kovteba.onlineshopapi.service.RecoveryService;
import kovteba.onlineshopapi.service.UserService;
import kovteba.onlineshopapi.util.JwtTokenUtil;
import kovteba.onlineshopapi.util.UUIDRandom;
import kovteba.onlineshopcommon.enums.RoleUser;
import kovteba.onlineshopcommon.pojo.User;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import kovteba.onlineshopcommon.model.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class JwtAuthenticationController {

	private final Logger log = LoggerFactory.getLogger(JwtAuthenticationController.class);

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final UserService userService;
	private final RecoveryService recoveryService;
	private final EmailService emailService;
	private final UserMapper userMapper;

	public JwtAuthenticationController(AuthenticationManager authenticationManager,
									   JwtTokenUtil jwtTokenUtil,
									   UserService userService,
									   RecoveryService recoveryService,
									   EmailService emailService,
									   UserMapper userMapper) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userService = userService;
		this.recoveryService = recoveryService;
		this.emailService = emailService;
		this.userMapper = userMapper;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletRequest req) throws Exception {

		log.info("createAuthenticationToken method with " + authenticationRequest.toString());

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		final UserDetails userDetails = userService.authentication(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);

		JwtResponse jwtResponse = new JwtResponse(token);
		req.getSession().setAttribute("Authorization", jwtResponse.getToken());

		return jwtResponse.getToken();

//		final String token = jwtTokenUtil.generateToken(userDetails);
//		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> addNewUser(@RequestBody UserEntity userEntity) {

		System.out.println("!!!!!!!!!!!! : " + userEntity.toString());

		log.info("addNewUser, " + this.getClass());
		userEntity.setRoleUser(RoleUser.USER);
		Responce responce = userService.addNewUser(userEntity);
		return ResponseEntity.status(responce.getStatus()).body(userMapper.userEntityToUser((UserEntity) responce.getObject()));
	}

	@PostMapping("/createSecretToken")
	public void createSecretToken(@RequestBody String email){
		UserEntity userEntity = (UserEntity)userService.getUserByEmail(email).getObject();
		if (userEntity != null){
			String secretToken = UUIDRandom.generateToken();
			recoveryService.addNewRecovery(new RecoveryEntity(email, secretToken));
			emailService.sendSimpleMessage("kovteba@gmail.com", "RECOVERY PASSWORD", secretToken);
		}
	}



	private void authenticate(String username, String password) throws Exception {

		log.info("authenticate, " + this.getClass());

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

	}
}
