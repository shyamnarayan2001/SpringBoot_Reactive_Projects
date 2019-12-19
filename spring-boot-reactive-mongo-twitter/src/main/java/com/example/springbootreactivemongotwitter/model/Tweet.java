package com.example.springbootreactivemongotwitter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "tweets")
public class Tweet {

    @Id
    private String id;

    @NotBlank
    @Size(max = 140)
    private String text;

    @NotNull
    private Date createdDate = new Date ();

    public Tweet(String text) {
        this.id = id;
        this.text = text;
    }

}
