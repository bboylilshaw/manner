package org.jshaw.manner.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jshaw.manner.common.Role;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "t_user",
        indexes = @Index(name = "username_index", columnList = "username", unique = true),
        uniqueConstraints = @UniqueConstraint(name = "email_uni", columnNames = "email"))
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class User extends AbstractPersistable<Long> {

    @NotEmpty
    //@Pattern(regexp = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")
    private String username;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Email
    private String email;
    @NotEmpty
    private String password;
    @NotNull
    private Role role;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @ManyToMany(
            mappedBy = "users",
            targetEntity = Group.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private Collection<Group> groups = new HashSet<>();

}
