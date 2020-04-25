package kovteba.onlineshopapi.controller;

import kovteba.onlineshopapi.entity.Recovery;
import kovteba.onlineshopapi.entity.User;
import kovteba.onlineshopapi.enums.RoleUser;
import kovteba.onlineshopapi.model.JwtRequest;
import kovteba.onlineshopapi.model.JwtResponse;
import kovteba.onlineshopapi.responce.Responce;
import kovteba.onlineshopapi.service.EmailService;
import kovteba.onlineshopapi.service.JwtUserDetailsService;
import kovteba.onlineshopapi.service.RecoveryService;
import kovteba.onlineshopapi.service.UserService;
import kovteba.onlineshopapi.util.JwtTokenUtil;
import kovteba.onlineshopapi.util.ResponceType;
import kovteba.onlineshopapi.util.UUIDRandom;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
//@CrossOrigin
@AllArgsConstructor
public class JwtAuthenticationController {

	private final Logger log = LoggerFactory.getLogger(JwtAuthenticationController.class);

	@Autowired
	private final AuthenticationManager authenticationManager;

	@Autowired
	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	private final JwtUserDetailsService userDetailsService;

	private final UserService userService;

	private final RecoveryService recoveryService;

	private final EmailService emailService;


	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletRequest req) throws Exception {

		log.info("createAuthenticationToken method with " + authenticationRequest.toString());

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		final UserDetails userDetails = userService.authentication(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);

		JwtResponse jwtResponse = new JwtResponse(token);
		req.getSession().setAttribute("Authorization", jwtResponse.getToken());

		System.out.println("jwtResponse : " + jwtResponse.getToken());
		return jwtResponse.getToken();

//		final String token = jwtTokenUtil.generateToken(userDetails);
//		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ResponceType> addNewUser(@RequestBody User user) {

		System.out.println("!!!!!!!!!!!! : " + user.toString());

		log.info("addNewUser, " + this.getClass());
		user.setRoleUser(RoleUser.USER);
		Responce responce = userService.addNewUser(user);
		return ResponseEntity.status(responce.getStatus()).body(responce.getObject());
	}

	@PostMapping("/createSecretToken")
	public void createSecretToken(@RequestBody String email){
		User user = (User)userService.getUserByEmail(email).getObject();
		if (user != null){
			String secretToken = UUIDRandom.generateToken();
			recoveryService.addNewRecovery(new Recovery(email, secretToken));
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
