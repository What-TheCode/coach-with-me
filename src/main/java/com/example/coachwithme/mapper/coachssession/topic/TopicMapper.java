package com.example.coachwithme.mapper.coachssession.topic;

import com.example.coachwithme.dto.coachsession.topic.CreateTopicDto;
import com.example.coachwithme.dto.coachsession.topic.TopicDto;
import com.example.coachwithme.model.coachSession.topic.Topic;
import com.example.coachwithme.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopicMapper {

    private final TopicRepository topicRepository;

    public TopicDto toDto(Topic topic) {
        return TopicDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .build();
    }

    public Topic toEntity(TopicDto topicDto) {
        return Topic.builder()
                .id(topicDto.getId())
                .name(topicDto.getName())
                .build();
    }

    public Topic toEntity(CreateTopicDto createTopicDto) {
        Topic topic = topicRepository.findByNameIgnoreCase(createTopicDto.getName().trim());
        if (topic != null) {
            return topic;
        }

        return Topic.builder()
                .name(createTopicDto.getName())
                .build();
    }
}
