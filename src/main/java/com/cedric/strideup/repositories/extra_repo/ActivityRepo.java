package com.cedric.strideup.repositories.extra_repo;

import java.util.List;

import com.cedric.strideup.models_dao.extra_dao.Activity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepo extends JpaRepository<Activity, Integer> {
    List<Activity> findAll();
    List<Activity> findAllByDataId( int a );
}
