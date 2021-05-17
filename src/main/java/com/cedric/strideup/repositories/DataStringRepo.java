package com.cedric.strideup.repositories;

import java.util.List;

import com.cedric.strideup.models_dao.DataString;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DataStringRepo extends JpaRepository<DataString, Integer> {
    List<DataString> findAll();
    List<DataString> findAllByParkCode( String a );
    List<DataString> findAllByStates( String a );
    DataString findByParkCode( String a );
    long deleteByParkCode( String a );
}
