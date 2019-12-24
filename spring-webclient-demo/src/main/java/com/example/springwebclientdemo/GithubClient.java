package com.example.springwebclientdemo;

import com.example.springwebclientdemo.config.AppProperties;
import com.example.springwebclientdemo.payload.GithubRepo;
import com.example.springwebclientdemo.payload.RepoRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class GithubClient {
    private static final String GITHUB_V3_MIME_TYPE = "application/vnd.github.v3+json";
    private static final String GITHUB_API_BASE_URL = "https://api.github.com";
    private static final String USER_AGENT = "Spring 5 WebClient";

    private final WebClient webClient;
    private final AppProperties appProperties;

    public GithubClient(AppProperties appProperties) {
        this.appProperties = appProperties;
        this.webClient = WebClient.builder()
                .baseUrl(GITHUB_API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, GITHUB_V3_MIME_TYPE)
                .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
                .filter(ExchangeFilterFunctions
                        .basicAuthentication(appProperties.getUsername(),
                                appProperties.getToken()))
                .filter(logRequest())
                .filter(logResponse ())
                .build();
    }

    public Flux<GithubRepo> listGithubRepositories() {
        return webClient.get ()
                .uri ("/user/repos?sort={sortField}&direction={sortDirection}", "updated", "desc")
                .exchange ()
                .flatMapMany (clientResponse -> clientResponse.bodyToFlux (GithubRepo.class));
    }

    public  Mono<GithubRepo> createGithubRepository(RepoRequest repoRequest) {
        return  webClient.post ()
                .uri("/user/repos")
                .body (Mono.just (repoRequest), RepoRequest.class)
                .retrieve ()
                .bodyToMono (GithubRepo.class);
    }

    public Mono<GithubRepo> getGithubRepository(String owner, String repo) {
        return webClient.get ()
                .uri ("/repos/{owner}/{repo}", owner, repo)
                .retrieve ()
                .bodyToMono (GithubRepo.class);
    }

    public Mono<GithubRepo> editGithubRepository(String owner, String repo, RepoRequest editRepoRequest) {
        return webClient.patch ()
                .uri ("/repos/{owner}/{repo}", owner, repo)
                .body(BodyInserters.fromValue (editRepoRequest))
                .retrieve ()
                .bodyToMono (GithubRepo.class);
    }

    public  Mono<Void> deleteGithubRepository(String owner, String repo) {
        return  webClient.delete ()
                .uri ("/repos/{owner}/{repo}", owner, repo)
                .retrieve ()
                .bodyToMono (Void.class);
    }

    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor (clientRequest -> {
            if(log.isInfoEnabled ()){
                log.info ("Invoke the REST API with inputs: {} {}", clientRequest.method (), clientRequest.url ());
                log.info("Request header values:");
                clientRequest
                        .headers ()
                        .forEach ((name, values) -> values.forEach (value -> log.info("{}={}", name, value)));
            }return Mono.just(clientRequest);
        });
    }
    private static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor (clientResponse -> {
            if(log.isInfoEnabled ()) {
                log.info("Response Status {}", clientResponse.statusCode());
            }
            return Mono.just(clientResponse);
        });
    }


}
