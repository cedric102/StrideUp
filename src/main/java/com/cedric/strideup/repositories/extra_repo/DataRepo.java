package com.cedric.strideup.repositories.extra_repo;

import java.util.List;

import com.cedric.strideup.models_dao.extra_dao.DEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepo extends JpaRepository<DEntity, Integer> {
    List<DEntity> findAll();
    DEntity findAllByParkCode( String s );
}
