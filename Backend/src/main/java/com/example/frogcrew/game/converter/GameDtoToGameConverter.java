package com.example.frogcrew.game.converter;

import com.example.frogcrew.game.dto.GameDTO;
import com.example.frogcrew.game.model.Game;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GameDtoToGameConverter implements Converter<GameDTO, Game> {
    @Override
    public Game convert(GameDTO source) {
        Game game = new Game();
        game.setGameDate(source.gameDate());
        game.setVenue(source.venue());
        game.setOpponent(source.opponent());
        return game;
    }
}
