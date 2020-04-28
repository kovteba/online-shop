package kovteba.onlineshopapi.controller;

import kovteba.onlineshopapi.entity.BanEntity;
import kovteba.onlineshopapi.entity.RecoveryEntity;
import kovteba.onlineshopapi.entity.UserEntity;
import kovteba.onlineshopapi.mapper.UserMapper;
import kovteba.onlineshopapi.responce.Responce;
import kovteba.onlineshopapi.service.BanService;
import kovteba.onlineshopapi.service.EmailService;
import kovteba.onlineshopapi.service.RecoveryService;
import kovteba.onlineshopapi.service.UserService;
import kovteba.onlineshopapi.util.JwtTokenUtil;
import kovteba.onlineshopapi.util.UUIDRandom;
import kovteba.onlineshopcommon.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
public class AuthenticationController {

    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final RecoveryService recoveryService;
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final BanService banService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenUtil jwtTokenUtil,
                                    UserService userService,
                                    RecoveryService recoveryService,
                                    EmailService emailService,
                                    UserMapper userMapper,
                                    BanService banService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.recoveryService = recoveryService;
        this.emailService = emailService;
        this.userMapper = userMapper;
        this.banService = banService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<String> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
                                            HttpServletRequest req) throws Exception {
        log.info("createAuthenticationToken method with " + authenticationRequest.toString());
        BanEntity banEntity = (BanEntity) banService.findBanRecordByEmail(authenticationRequest.getEmail()).getObject();

        if (banEntity == null){
            authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
            final UserDetails userDetails = userService.authentication(authenticationRequest.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(jwtTokenUtil.generateToken(userDetails));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("YOU BLOCKED");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        log.info("addNewUser, " + this.getClass());
        Responce responce = userService.addNewUser(userMapper.userToUserEntity(user));
        return ResponseEntity.status(responce.getStatus())
                .body(userMapper.userEntityToUser((UserEntity) responce.getObject()));
    }

    @PostMapping("/createSecretToken")
    public ResponseEntity<String> createSecretToken(@RequestBody String email) {
        UserEntity userEntity = (UserEntity) userService.getUserByEmail(email).getObject();
        if (userEntity != null) {
            String secretToken = UUIDRandom.generateToken();
            recoveryService.addNewRecovery(new RecoveryEntity(email, secretToken));
            emailService.sendSimpleMessage(email, "RECOVERY PASSWORD", secretToken);
            return ResponseEntity.status(HttpStatus.CREATED).body("SECRET TOKEN SENDED ON YOUR EMAIL");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USER WITH EMAIL DONT FOUND");
        }
    }

    @PostMapping("/resetPass")
    public ResponseEntity<String> resetPass(@RequestBody RecoveryPass recoveryPass) {
        RecoveryEntity recoveryEntity = recoveryService.getRecoveryByEmail(recoveryPass.getEmail());
        if (recoveryEntity != null && recoveryEntity.getSecreToken().equals(recoveryPass.getSecretToken())) {
            if (userService.resetPass(recoveryPass.getEmail(), recoveryPass.getNewPass())) {
                recoveryService.deleteSecretTokenByEmail(recoveryPass.getEmail());
                return ResponseEntity.status(HttpStatus.OK).body("PASS CHANGED");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("PASS NOT CHANGED");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("USER WITH EMAIL DONT FOUND OR SECRETTOKEN NOT CORRECT");
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
