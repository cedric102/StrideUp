package com.cedric.strideup.repositories.extra_repo;

import java.util.List;

import com.cedric.strideup.models_dao.extra_dao.EmailAddress;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAddressRepo extends JpaRepository<EmailAddress, Integer> {
    List<EmailAddress> findAll();
    List<EmailAddress> findAllByDataId( int a );
}