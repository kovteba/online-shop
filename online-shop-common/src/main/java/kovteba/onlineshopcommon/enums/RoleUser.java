package kovteba.onlineshopcommon.enums;

import java.util.Arrays;
import java.util.List;

public enum RoleUser {

    USER("USER"),
    ADMIN("ADMIN");

    private String roleValue;

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
        RoleUser role = null;
        return role = getListRole()
                .stream()
                .filter(s -> s.getRoleValue().equals(roleString))
                .findAny()
                .get();
    }

}
