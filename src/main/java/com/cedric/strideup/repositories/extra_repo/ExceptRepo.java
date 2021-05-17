package com.cedric.strideup.repositories.extra_repo;

import java.util.List;

import com.cedric.strideup.models_dao.extra_dao.Except;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExceptRepo extends JpaRepository<Except, Integer> {
    List<Except> findAll();
    List<Except> findByDataIdAndOperationHourId( int a , int b );
}
