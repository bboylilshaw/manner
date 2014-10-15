package org.jshaw.manner.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Entity
@Table(name = "t_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Group extends AbstractPersistable<Long> {
    private String name;
    private Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToMany(
            targetEntity = User.class,
            fetch = FetchType.LAZY
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "t_group_user",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
//    @OneToMany(mappedBy = "group")
    private Collection<User> users = new HashSet<>();

//    @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
//    private Collection<Item> items = new ArrayList<>();

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
