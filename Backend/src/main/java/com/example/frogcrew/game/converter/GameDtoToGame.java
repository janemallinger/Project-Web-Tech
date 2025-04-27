package com.example.frogcrew.game.converter;

import com.example.frogcrew.game.dto.GameRequest.GameRequestDto;
import com.example.frogcrew.game.model.Game;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GameDtoToGame implements Converter<GameRequestDto, Game> {
    @Override
    public Game convert(GameRequestDto source) {
        Game game = new Game();
        game.setGameDate(source.gameDate());
        game.setVenue(source.venue());
        game.setOpponent(source.opponent());
        game.setFinalized(source.isFinalized());
        return game;
    }
}
