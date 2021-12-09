package com.example.coachwithme.mapper;

import com.example.coachwithme.dto.CreateTopicDto;
import com.example.coachwithme.dto.TopicDto;
import com.example.coachwithme.model.coachSession.topic.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {

    public TopicDto toDto( Topic topic){
        return TopicDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .build();
    }

    public Topic toEntity(TopicDto topicDto){
        return Topic.builder()
                .id(topicDto.getId())
                .name(topicDto.getName())
                .build();
    }

    public Topic toEntity(CreateTopicDto createTopicDto){
        return Topic.builder()
                .name(createTopicDto.getName())
                .build();
    }

}
