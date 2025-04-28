package com.example.frogcrew.game.service;

import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    public List<Game>  findAllGames(){
        return gameRepository.findAll();
    }
}
