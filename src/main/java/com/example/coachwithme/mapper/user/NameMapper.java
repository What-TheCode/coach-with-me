package com.example.coachwithme.mapper.user;

import com.example.coachwithme.dto.user.NameDto;
import com.example.coachwithme.model.user.Name;
import org.springframework.stereotype.Component;

@Component
public class NameMapper {

    public NameDto toDto(Name name) {
        return NameDto.builder()
                .firstName(name.getFirstName())
                .lastName(name.getLastName())
                .build();
    }

    public Name toEntity(NameDto nameDto) {
        return Name.builder()
                .firstName(nameDto.getFirstName())
                .lastName(nameDto.getLastName())
                .build();
    }

}
