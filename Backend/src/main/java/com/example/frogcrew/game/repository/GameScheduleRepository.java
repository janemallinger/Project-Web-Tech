package com.example.frogcrew.game.repository;

import com.example.frogcrew.game.model.GameSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameScheduleRepository extends JpaRepository<GameSchedule, Integer> {
}
