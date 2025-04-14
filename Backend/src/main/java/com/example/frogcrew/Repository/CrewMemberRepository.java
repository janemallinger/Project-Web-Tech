package com.example.frogcrew.Repository;

import com.example.frogcrew.Model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface CrewMemberRepository extends JpaRepository<CrewMember, UUID> {
    //will implement custom Spring Data methods here.. for now most of the ones i can think of are inherited from JPA
}
