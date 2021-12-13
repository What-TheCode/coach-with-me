package com.example.coachwithme.mapper.coachssession.topic;

import com.example.coachwithme.dto.coachsession.topic.CreateTopicDto;
import com.example.coachwithme.dto.coachsession.topic.TopicDto;
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
