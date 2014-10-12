//package org.jshaw.manner.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import org.hibernate.validator.constraints.NotEmpty;
//import org.jshaw.manner.common.Priority;
//import org.jshaw.manner.common.Status;
//import org.springframework.data.jpa.domain.AbstractPersistable;
//
//import javax.persistence.Entity;
//import javax.persistence.Index;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//import java.util.Date;
//import java.util.Set;
//
//@Entity
//@Table(name = "t_item")
//@Data
//@EqualsAndHashCode(callSuper = false)
//@NoArgsConstructor
//@AllArgsConstructor(staticName = "of")
//public class Item extends AbstractPersistable<Long> {
//
//    private String subject;
//
//    @NotEmpty
//    private String content;
//
//    private User owner;
//
//    private Status status;
//
//    @Min(0) @Max(100)
//    private int percentage;
//
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
//
//    private Set<String> tag;
//
//    private String group;
//
//}
