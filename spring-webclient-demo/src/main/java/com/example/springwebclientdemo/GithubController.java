package com.example.springwebclientdemo;

import com.example.springwebclientdemo.config.AppProperties;
import com.example.springwebclientdemo.payload.GithubRepo;
import com.example.springwebclientdemo.payload.RepoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class GithubController {

    private final GithubClient githubClient;
    private final AppProperties appProperties;

    @GetMapping("/repos")
    public Flux<GithubRepo> listGithubRepositories() {
        return githubClient.listGithubRepositories ();
    }

    @PostMapping("/repos")
    public Mono<GithubRepo> createGithubRepositories(@RequestBody RepoRequest repoRequest) {
        return githubClient.createGithubRepository (repoRequest);
    }

    @GetMapping("/repos/{repo}")
    public Mono<GithubRepo> getGithubRepository(@PathVariable String repo) {
        return githubClient.getGithubRepository (appProperties.getUsername (), repo);
    }

    @PatchMapping("/repos/{repo}")
    public Mono<GithubRepo> editGithubRepository(@PathVariable String repo, @Valid @RequestBody RepoRequest repoRequest) {
        return githubClient.editGithubRepository ( appProperties.getUsername(), repo, repoRequest);
    }

    @DeleteMapping("/repos/{repo}")
    public Mono<Void> deleteGithubRepository(@PathVariable String repo) {
        return githubClient.deleteGithubRepository(appProperties.getUsername(), repo);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebclientResponseException(WebClientResponseException e) {
        log.error ("Error from Webclient - Status {}, Body {}", e.getRawStatusCode (), e.getResponseBodyAsString (), e);
        return ResponseEntity.status (e.getRawStatusCode ()).body (e.getResponseBodyAsString ());
    }
}
