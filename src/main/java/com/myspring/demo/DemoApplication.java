package com.myspring.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// 1. to export war -> to external traditional standalone tomcat server
///////////////////////////////////////////////////////////////////////////////
// import org.springframework.boot.builder.SpringApplicationBuilder;
// import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication 
// 2. extends SpringBootServletInitializer
{

	// @Override 
	// protected SpringApplicationBuilder configure( 
	// 	SpringApplicationBuilder application
	// ) { 
	// 	return application.sources(MyApiApplication.class); 
	// }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
			return "My API is running";
	}
}
