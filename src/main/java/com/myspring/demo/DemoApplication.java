
package com.myspring.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.myspring.demo.files.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

	// protected SpringApplicationBuilder configure(
    //         SpringApplicationBuilder application) {
    //     return application.sources(DemoApplication.class);
    // }
}
