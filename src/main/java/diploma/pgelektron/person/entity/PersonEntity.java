package diploma.pgelektron.person.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import diploma.pgelektron.utility.valiation.EmailValidation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.*;


import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "person")
@Transactional
public class PersonEntity implements Serializable {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Column(nullable = false, updatable = false)
    private Long id;

    @Unique
    private UUID externalId;
    @NotEmpty
    @NotBlank
    private String firstName;


    @NotBlank
    @NotEmpty
    private String lastName;


    @NotBlank
    @NotEmpty
    @Email
    private String email;


    @NotBlank
    @NotEmpty
    private String username;

    @NotBlank
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank
    @NotEmpty
    @Size(min = 6, max = 12)
    private String phoneFix;

    @NotBlank
    @NotEmpty
    @Size(min = 10, max = 12)
    private String phoneMobile;

    @NotBlank
    @NotEmpty
    @Size(min = 10, max = 255)
    private String address;


    private String profileImageUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date lastLoginDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date lastLoginDateDisplay;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date joinDate;

    private String role; //ROLE_USER, ROLE_ADMIN
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;


    public PersonEntity(String firstName,
                        String lastName,
                        String email,
                        String username,
                        String password,
                        String phoneFix,
                        String phoneMobile,
                        String address,
                        String profileImageUrl,
                        Date lastLoginDate,
                        Date lastLoginDateDisplay,
                        Date joinDate,
                        String role,
                        String[] authorities,
                        boolean isActive,
                        boolean isNotLocked) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneFix = phoneFix;
        this.phoneMobile = phoneMobile;
        this.address = address;
        this.profileImageUrl = profileImageUrl;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginDateDisplay = lastLoginDateDisplay;
        this.joinDate = joinDate;
        this.role = role;
        this.authorities = authorities;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }


}
