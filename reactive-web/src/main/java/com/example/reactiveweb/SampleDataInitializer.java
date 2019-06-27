package com.example.reactiveweb;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import java.util.UUID;

@Log4j2 //a Lombok annotation that results in the creation of a log field that is a Log4J logger being added to the class
@Component
//this bean initializes sample data that is only useful for a demo. We don’t want this sample data being initialized every time. Spring’s Profile annotation tags an object for initialization only when the profile that matches the profile specified in the annotation is specifically activated.
//-Dspring.profiles.active=demo to activate the Spring Profile from command line
@org.springframework.context.annotation.Profile("demo")
public class SampleDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final ProfileRepository repository;

    public SampleDataInitializer(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent e){
        repository
                .deleteAll() //Deletes first everything from the database
                .thenMany( //chaining processing
                        Flux.just("A","B","C","D")
                        .map(name -> new Profile(UUID.randomUUID().toString(), name + "@email.com")) //Create a new Profile object
                        .flatMap(repository::save) //Save to DB
                )
                .thenMany(repository.findAll()) //Fetch all the records from the database
                .subscribe(profile -> log.info("Saving :"+profile.toString())); //Lazy, so need to subscribe to trigger the execution

    }
}
