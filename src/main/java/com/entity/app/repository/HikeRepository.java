package com.entity.app.repository;

import com.entity.app.entity.Hike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HikeRepository extends JpaRepository<Hike, Long> {

}