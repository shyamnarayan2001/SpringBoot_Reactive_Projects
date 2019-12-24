package com.example.springwebclientdemo.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.github")
@Data
@NoArgsConstructor
public class AppProperties {
    private String username;
    private String token;
}
