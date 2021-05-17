package com.cedric.strideup.repositories.extra_repo;

import java.util.List;

import com.cedric.strideup.models_dao.extra_dao.Topic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicsRepo extends JpaRepository<Topic, Integer> {
    List<Topic> findAll();
    List<Topic> findAllByDataId( int a );
}
