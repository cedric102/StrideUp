package com.cedric.strideup.repositories.extra_repo;

import java.util.List;

import com.cedric.strideup.models_dao.extra_dao.OperatingHours;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatingHoursRepo extends JpaRepository<OperatingHours, Integer> {
    List<OperatingHours> findAll();
    List<OperatingHours> findAllByDataId( int a );
}
