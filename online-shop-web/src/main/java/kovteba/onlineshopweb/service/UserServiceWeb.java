package kovteba.onlineshopweb.service;


import kovteba.onlineshopcommon.pojo.User;
import kovteba.onlineshopcommon.model.*;

import java.io.IOException;

public interface UserServiceWeb {

    String auth(JwtRequest authenticationRequest);

    User getUserByRole(String token, String Role);

    User getUserByPhoneNumber(String token, String phoneNumber);

    User addToBasket(String token, Long userId, Long productId, String count);

    void generateReceipt(String token, String userId);

    String banUserByEmail(String token, String email);

    String unBanUserByEmail(String token, String email);

    User getUserByEmail(String email) throws IOException;

}
