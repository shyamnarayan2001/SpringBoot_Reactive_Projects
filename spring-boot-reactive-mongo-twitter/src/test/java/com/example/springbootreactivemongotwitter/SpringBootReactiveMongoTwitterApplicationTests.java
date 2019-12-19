package com.example.springbootreactivemongotwitter;

import com.example.springbootreactivemongotwitter.model.Tweet;
import com.example.springbootreactivemongotwitter.repository.TweetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootReactiveMongoTwitterApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    TweetRepository tweetRepository;

    @Test
    public void testCreateTweet() {
        Tweet tweet = new Tweet ("This is a test tweet");

        webTestClient.post ().uri ("/tweets")
                .contentType (MediaType.APPLICATION_JSON)
                .accept (MediaType.APPLICATION_JSON)
                .body (Mono.just (tweet), Tweet.class)
                .exchange ()
                .expectStatus ().isOk ()
                .expectHeader ().contentType (MediaType.APPLICATION_JSON)
                .expectBody ()
                .jsonPath ("$.id").isNotEmpty ()
                .jsonPath ("$.text").isEqualTo ("This is a test tweet");

    }

    @Test
    public void testGetAllTweets() {
        webTestClient.get ().uri ("/tweets")
                .accept (MediaType.APPLICATION_JSON)
                .exchange ()
                .expectStatus ().isOk ()
                .expectBodyList (Tweet.class);
    }

    @Test
    public void testGetSingleTweet() {
        Tweet tweet = tweetRepository.save (new Tweet ("Hello World!!")).block ();

        webTestClient.get ()
                .uri ("/tweets/{id}", Collections.singletonMap ("id", tweet.getId ()))
                .exchange ()
                .expectStatus ().isOk ()
                .expectBody ()
                .consumeWith (response -> Assertions.assertNotNull (response.getResponseBody ()));
    }

    @Test
    public void testUpdateTweet() {
        Tweet tweet = tweetRepository.save (new Tweet ("Initial tweet!!")).block ();
        Tweet newTweet = new Tweet ("Updated tweet!!");

        webTestClient.put ()
                .uri ("/tweets/{id}", Collections.singletonMap ("id", tweet.getId ()))
                .contentType (MediaType.APPLICATION_JSON)
                .accept (MediaType.APPLICATION_JSON)
                .body (Mono.just (newTweet), Tweet.class)
                .exchange ()
                .expectStatus ().isOk ()
                .expectHeader ().contentType (MediaType.APPLICATION_JSON)
                .expectBody ()
                .jsonPath ("$.text", "Updated tweet!!");
    }

    @Test
    public void testDeleteTweet() {
        Tweet tweet = tweetRepository.save (new Tweet ("To be deleted")).block ();

        webTestClient.delete ()
                .uri ("/tweets/{id}", Collections.singletonMap ("id", tweet.getId ()))
                .exchange ()
                .expectStatus ().isOk ();
    }

}
