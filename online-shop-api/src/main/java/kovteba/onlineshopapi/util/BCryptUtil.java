package kovteba.onlineshopapi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptUtil {


    public static String encrypt(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static boolean decrypt(String passwordLogin, String paswordDB) {
        return new BCryptPasswordEncoder().matches(passwordLogin, paswordDB);
    }

}
