package com.example.springwebclientdemo.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class RepoRequest {
    @NotBlank
    private String name;
    private String description;
    @JsonProperty("private")
    private Boolean isPrivate;

    public RepoRequest(@NotBlank String name, String description) {
        this.name = name;
        this.description = description;
    }
}
