package org.jshaw.manner.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.jshaw.manner.common.Priority;
import org.jshaw.manner.common.Status;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

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

    private Date createdDate;

    @ManyToOne
    private Group group;

    private Status status;

    @Min(0) @Max(100)
    private int percentage;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dueDate;

    private Priority priority;

    private String remarks;

}
