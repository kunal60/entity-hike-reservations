package com.entity.app.service.impl;

import com.entity.app.entity.Trail;
import com.entity.app.exception.TrailNotAvailableException;
import com.entity.app.repository.TrailRepository;
import com.entity.app.service.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TrailServiceImpl implements TrailService {

    @Autowired
    TrailRepository trailRepository;

    @Override
    @Transactional
    public void saveTrails(List<Trail> trails) {
        trailRepository.saveAll(trails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trail> getTrails() {
        List<Trail> trails = trailRepository.findAll();
        if (trails.isEmpty()) {
            throw new TrailNotAvailableException("No Trail was found");
        }
        return trails;
    }

    @Override
    @Transactional(readOnly = true)
    public Trail findTrailById(Long uuid) {
        Optional<Trail> booking = trailRepository.findById(uuid);
        if (booking.isEmpty()) {
            throw new TrailNotAvailableException(String.format("Trail was not found for uuid=%s", uuid));
        }
        return booking.get();
    }

}
