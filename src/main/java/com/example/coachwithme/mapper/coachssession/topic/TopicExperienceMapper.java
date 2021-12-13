package com.example.coachwithme.mapper.coachssession.topic;

import com.example.coachwithme.dto.coachsession.topic.CreateTopicExperienceDto;
import com.example.coachwithme.dto.coachsession.topic.TopicExperienceDto;
import com.example.coachwithme.model.coachSession.topic.TopicExperience;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public TopicExperience toEntity(CreateTopicExperienceDto createTopicExperienceDto){
        return TopicExperience.builder()
                .experience(createTopicExperienceDto.getExperience())
                .topic(topicMapper.toEntity(createTopicExperienceDto.getTopic()))
                .build();
    }

    public List<TopicExperience> toEntities(List<TopicExperienceDto> topicExperienceDtos){
        return topicExperienceDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<TopicExperience> fromCreateDtoToEntities(List<CreateTopicExperienceDto> createTopicExperienceDtos){
        return createTopicExperienceDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<TopicExperienceDto> toDtos(List<TopicExperience> topicExperiences){
        if(topicExperiences == null){
            return new ArrayList<>();
        }
        return topicExperiences.stream().map(this::toDto).collect(Collectors.toList());
    }
}
