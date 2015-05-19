package org.jshaw.manner.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jshaw.manner.common.Role;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(
        name = "t_user",
        indexes = {
                @Index(name = "username_index", columnList = "username", unique = true),
                @Index(name = "email_index", columnList = "email", unique = true),
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class User extends AbstractPersistable<Long> {

    @NotEmpty
    //@Pattern(regexp = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")
    private String username;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @Email
    private String email;

    @NotEmpty
    private String password;

    @NotNull
    private Role role;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Collection<Group> groups = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
