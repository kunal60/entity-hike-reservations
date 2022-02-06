package com.entity.app.entity;

import com.entity.app.model.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Constants.BOOKING_TABLE_NAME)
public class Booking {

    /**
     * Holds value of property id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long customerId;
    /**
     * Holds value of property first name.
     */
    @Column(name = "first_Name", nullable = false)
    private String firstName;
    /**
     * Holds value of property last name.
     */
    @Column(name = "last_Name", nullable = false)
    private String lastName;
    /**
     * Holds value of property email.
     */
    @Column(name = "email", nullable = false)
    private String email;
    /**
     * Holds value of property email.
     */
    @Column(name = "age", nullable = false)
    private Integer age;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bookingDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date eventDate;

    /**
     * Holds value of property active
     */
    @Column(name = "active", nullable = false)
    @Type(type = "yes_no")
    private Boolean active = Boolean.TRUE;

    @ManyToOne
    private Trail trail;
}