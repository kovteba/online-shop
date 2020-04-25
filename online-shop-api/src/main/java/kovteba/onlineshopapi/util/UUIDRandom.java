package kovteba.onlineshopapi.util;

import java.util.UUID;

public class UUIDRandom {

    public static String generateToken(){
        return String.valueOf(UUID.randomUUID());
    }

}
