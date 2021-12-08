package com.example.coachwithme.mapper;

import com.example.coachwithme.dto.TopicExperienceDto;
import com.example.coachwithme.model.coachSession.topic.TopicExperience;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TopicExperienceMapper {

    private final TopicMapper topicMapper;

    public TopicExperienceMapper(TopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }

    public TopicExperienceDto toDto(TopicExperience topicExperience){
        return TopicExperienceDto.builder()
                .id(topicExperience.getId())
                .experience(topicExperience.getExperience())
                .topic(topicMapper.toDto(topicExperience.getTopic()))
                .build();
    }

    public TopicExperience toEntity(TopicExperienceDto topicExperienceDto){
        return TopicExperience.builder()
                .id(topicExperienceDto.getId())
                .experience(topicExperienceDto.getExperience())
                .topic(topicMapper.toEntity(topicExperienceDto.getTopic()))
                .build();
    }

    public List<TopicExperience> toEntities(List<TopicExperienceDto> topicExperienceDtos){
        return topicExperienceDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<TopicExperienceDto> toDtos(List<TopicExperience> topicExperiences){
        return topicExperiences.stream().map(this::toDto).collect(Collectors.toList());
    }
}
