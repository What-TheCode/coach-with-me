package com.example.coachwithme.repository;

import com.example.coachwithme.model.coachSession.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic,Integer> {
}
