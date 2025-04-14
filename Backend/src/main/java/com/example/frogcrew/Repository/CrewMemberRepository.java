package com.example.frogcrew.Repository;

import com.example.frogcrew.Model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CrewMemberRepository extends JpaRepository<CrewMember, UUID> {
}
