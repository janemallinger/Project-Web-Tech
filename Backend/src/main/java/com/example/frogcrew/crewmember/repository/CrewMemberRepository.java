package com.example.frogcrew.crewmember.repository;

import com.example.frogcrew.crewmember.model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
    boolean existsByEmail(String email);
}
