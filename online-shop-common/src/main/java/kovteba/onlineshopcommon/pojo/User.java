package kovteba.onlineshopcommon.pojo;

import kovteba.onlineshopcommon.enums.RoleUser;

import java.util.Map;

import lombok.*;
import org.apache.pulsar.shade.com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.pulsar.shade.com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private RoleUser roleUser;

    private Map<Product, String> basket;



}
