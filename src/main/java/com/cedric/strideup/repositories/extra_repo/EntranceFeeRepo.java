package com.cedric.strideup.repositories.extra_repo;

import java.util.List;

import com.cedric.strideup.models_dao.extra_dao.EntranceFee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntranceFeeRepo extends JpaRepository<EntranceFee, Integer> {
    List<EntranceFee> findAll();
    List<EntranceFee> findAllByDataId( int a );
}