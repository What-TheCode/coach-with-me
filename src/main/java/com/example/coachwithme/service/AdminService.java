package com.example.coachwithme.service;

import com.example.coachwithme.dto.coachsession.topic.CreateTopicDto;
import com.example.coachwithme.dto.coachsession.topic.TopicDto;
import com.example.coachwithme.exceptions.customExceptions.TopicAlreadyExistsException;
import com.example.coachwithme.mapper.coachssession.topic.TopicMapper;
import com.example.coachwithme.model.coachSession.topic.Topic;
import com.example.coachwithme.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final SecurityService securityService;

    public TopicDto addTopic(CreateTopicDto createTopicDto) {

        securityService.assertIfUserIsAdmin();
        assertIfTopicNameAlreadyExists(createTopicDto.getName());

        log.info("New Topic created with name: {}", createTopicDto.getName());
        return topicMapper.toDto(topicRepository.save(topicMapper.toEntity(createTopicDto)));
    }

    private void assertIfTopicNameAlreadyExists(String topicName) {
        Topic topic = topicRepository.findByName(topicName);
        if (topic != null) {
            throw new TopicAlreadyExistsException("Topic already exists: " + topicName);
        }
    }


}
