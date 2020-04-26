package kovteba.onlineshopweb.service;


import kovteba.onlineshopcommon.pojo.User;
import kovteba.onlineshopcommon.model.*;

public interface UserServiceWeb {

    String auth(JwtRequest authenticationRequest);

    User getUserByEmail(String email);

}
