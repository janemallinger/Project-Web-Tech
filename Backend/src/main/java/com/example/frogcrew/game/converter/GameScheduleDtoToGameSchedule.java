package com.example.frogcrew.game.converter;

import com.example.frogcrew.game.dto.GameScheduleRequest.GameScheduleDto;
import com.example.frogcrew.game.model.GameSchedule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GameScheduleDtoToGameSchedule implements Converter<GameScheduleDto, GameSchedule> {
    @Override
    public GameSchedule convert(GameScheduleDto source){
        GameSchedule schedule = new GameSchedule();
        schedule.setSeason(source.season());
        schedule.setSport(source.sport());
        return schedule;
    }


}
