package kovteba.onlineshopcommon.enums;

import java.util.Arrays;
import java.util.List;

public enum RoleUser {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String roleValue;

    RoleUser(String roleValue) {
        this.roleValue = roleValue;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public static List<RoleUser> getListRole(){
        return Arrays.asList(RoleUser.values());
    }

    public static RoleUser findRole(String roleString){
        return getListRole()
                .stream()
                .filter(s -> s.getRoleValue().equals(roleString))
                .findAny()
                .get();
    }

}
