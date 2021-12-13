package com.example.coachwithme.repository;

import com.example.coachwithme.model.coachSession.CoachSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachSessionRepository extends JpaRepository<CoachSession,Integer> {

    List<CoachSession> findCoachSessionsByCoachId(int id);

    List<CoachSession> findCoachSessionsByCoacheeId(int id);

}
