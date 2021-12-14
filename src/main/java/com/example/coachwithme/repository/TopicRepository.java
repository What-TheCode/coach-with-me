package com.example.coachwithme.repository;

import com.example.coachwithme.model.coachSession.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    Topic findByName(String name);

    Topic findByNameIgnoreCase(String name);
}
