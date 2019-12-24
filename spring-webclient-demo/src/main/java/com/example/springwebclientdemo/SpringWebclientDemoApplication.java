package com.example.springwebclientdemo;

import com.example.springwebclientdemo.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SpringWebclientDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebclientDemoApplication.class, args);
	}

}
