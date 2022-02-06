package com.entity.app.repository;

import com.entity.app.entity.Trail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrailRepository extends JpaRepository<Trail, Long> {


}