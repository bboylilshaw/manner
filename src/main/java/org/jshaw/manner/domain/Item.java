package org.jshaw.manner.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.jshaw.manner.common.Status;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "t_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Item extends AbstractPersistable<Long> {

    @NotEmpty
    private String content;

    @ManyToOne
    private User owner;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private Group group;

    private Status status;

    @Min(0) @Max(100)
    private int percentage;

//    private Date dueDate;
//
//    private Date deferDate;
//
//    private Priority priority;
//
//    private String category;
//
//    private String remarks;
//
//    @Min(0) @Max(5)
//    private int level;

}
