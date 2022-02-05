package com.entity.app.service;

import com.entity.app.entity.Hike;
import com.entity.app.repository.HikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HikeService {

    @Autowired
    HikeRepository hikeRepository;

    public void createHike(List<Hike> hikes) {
        hikeRepository.saveAll(hikes);
    }

    public List<Hike> getHikes() {
        return hikeRepository.findAll();
    }

    public Optional<Hike> getHike(Long hikeId) {
        return hikeRepository.findById(hikeId);
    }
}
