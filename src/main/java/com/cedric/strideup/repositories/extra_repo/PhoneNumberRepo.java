package com.cedric.strideup.repositories.extra_repo;

import java.util.List;

import com.cedric.strideup.models_dao.extra_dao.PhoneNumber;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepo extends JpaRepository<PhoneNumber, Integer> {
    List<PhoneNumber> findAll();
    List<PhoneNumber> findAllByDataId( int a );
}
