package com.example.frogcrew.repository;

import com.example.frogcrew.model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
    //will implement custom Spring Data methods here.. for now most of the ones i can think of are inherited from JPA
}
