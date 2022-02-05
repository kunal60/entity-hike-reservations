package com.entity.app.model.data;

import com.entity.app.entity.Hike;
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
public class Hikes {
    private List<Hike> hikes = new ArrayList<>();
}