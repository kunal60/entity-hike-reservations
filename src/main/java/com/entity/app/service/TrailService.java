package com.entity.app.service;

import com.entity.app.entity.Trail;

import java.util.List;

public interface TrailService {
    void saveTrails(List<Trail> trails);

    List<Trail> getTrails();

    Trail findTrailById(Long trailId);
}