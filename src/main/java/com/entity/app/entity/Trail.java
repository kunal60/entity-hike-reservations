package com.entity.app.entity;

import com.entity.app.model.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kunal Malhotra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Constants.HIKE_TABLE_NAME)
public class Trail {

    @Id
    @Column(name = "trail_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long trailId;

    private String name;

    private String startAt;

    private String endAt;

    private Integer minimumAge;

    private Integer maximumAge;

    private Double unitPrice;


}