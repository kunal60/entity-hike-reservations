package com.entity.app.model;

import com.entity.app.entity.Trail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kunal Malhotra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trails {
    private List<Trail> trails = new ArrayList<>();
}