package com.example.frogcrew.game.repository;

import com.example.frogcrew.game.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {

}
