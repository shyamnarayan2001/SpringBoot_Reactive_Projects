package com.example.springbootreactivemongotwitter.repository;

import com.example.springbootreactivemongotwitter.model.Tweet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TweetRepository extends ReactiveMongoRepository<Tweet, String> {
}
