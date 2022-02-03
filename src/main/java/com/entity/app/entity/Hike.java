package com.entity.app.entity;

import com.entity.app.model.data.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Kunal Malhotra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Constants.HIKE_TABLE_NAME)
public class Hike {

    private String name;

    private String startAt;

    private String endAt;

    private Integer minimumAge;

    private Integer maximumAge;

    private Double unitPrice;


}