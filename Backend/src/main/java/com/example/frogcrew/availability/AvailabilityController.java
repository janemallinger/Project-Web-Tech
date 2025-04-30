package com.example.frogcrew.availability;

import com.example.frogcrew.availability.converter.AvailabilityDtoToAvailabilityConverter;
import com.example.frogcrew.availability.converter.AvailabilityToAvailabilityDtoConverter;
import com.example.frogcrew.availability.dto.AvailabilityDto;
import com.example.frogcrew.system.Result;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/${api.version}/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;
    private final AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter;
    private final AvailabilityDtoToAvailabilityConverter availabilityDtoToAvailabilityConverter;

    public AvailabilityController(AvailabilityService availabilityService, AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter, AvailabilityDtoToAvailabilityConverter availabilityDtoToAvailabilityConverter) {
        this.availabilityService = availabilityService;
        this.availabilityToAvailabilityDtoConverter = availabilityToAvailabilityDtoConverter;
        this.availabilityDtoToAvailabilityConverter = availabilityDtoToAvailabilityConverter;
    }

    // Use case 7: crew member submits availability
    @PostMapping
    public Result addAvailability(@Valid @RequestBody AvailabilityDto dto){
        Availability availabilityEntity = availabilityDtoToAvailabilityConverter.convert(dto);
        Availability saved = availabilityService.createAvailability(availabilityEntity);
        AvailabilityDto returnedDto = availabilityToAvailabilityDtoConverter.convert(saved);

        return new Result(true, HttpStatus.CREATED.value(), "Add Success", returnedDto);
    }

}
