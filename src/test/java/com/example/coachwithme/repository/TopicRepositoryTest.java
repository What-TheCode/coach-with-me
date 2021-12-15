package com.example.coachwithme.repository;

import com.example.coachwithme.model.coachSession.topic.Topic;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class TopicRepositoryTest {

    @Autowired
    TopicRepository topicRepository;
    Topic java;
    Topic cPlusPlus;
    Topic cSharp;
    Topic python;

    @BeforeEach
    void populateTopicRepository() {
        this.java = this.topicRepository.save(Topic.builder().name("Java").build());
        this.cPlusPlus = this.topicRepository.save(Topic.builder().name("C++").build());
        this.cSharp = this.topicRepository.save(Topic.builder().name("C#").build());
        this.python = this.topicRepository.save(Topic.builder().name("Python").build());
    }

    @Nested
    @DisplayName("When retrieving topic")
    class RetrievingTopic {

        @Test
        @Disabled
        @DisplayName("With Topic id, then topic is fetched from repo")
        void whenTopicIsFetchedFromrepositoryById_thenCorrectTopicIsFetched() {
            Topic topic = topicRepository.findById(2).get();

            assertThat(topic).isEqualTo(cPlusPlus);
        }

        @Test
        @Disabled
        @DisplayName("With wrong Topic id, then no topic is fetched from repo")
        void whenTopicIsFetchedFromrepositoryById_thenNoTopicIsFetched() {
            Topic topic = topicRepository.findById(2000).get();

            assertThat(topic).isEqualTo(null);
        }

        @Test
        @DisplayName("With Topic name, then topic is fetched from repo")
        void whenTopicIsFetchedFromrepositoryByName_thenCorrectTopicIsFetched() {
            Topic topic = topicRepository.findByName("Java");

            assertThat(topic).isEqualTo(java);
        }

        @Test
        @DisplayName("With wrong Topic name, then no topic is fetched from repo")
        void whenTopicIsFetchedFromrepositoryByName_thenNoTopicIsFetched() {
            Topic topic = topicRepository.findByName("C#+");

            assertThat(topic).isEqualTo(null);
        }

        @Test
        @DisplayName("With Topic name ignoring case, then topic is fetched from repo")
        void whenTopicIsFetchedFromrepositoryByNameIgnoringCase_thenCorrectTopicIsFetched() {
            Topic topic = topicRepository.findByNameIgnoreCase("pYThOn");

            assertThat(topic).isEqualTo(python);
        }

    }


}