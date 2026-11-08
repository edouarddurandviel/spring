package com.myspring.demo.config;

import org.springframework.boot.web.server.servlet.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;


// should use springframework current version implemented Strategies 

@Configuration
@EnableJdbcHttpSession // saved in db
public class SessionConfig {

    // overrides default application.properties
    @Bean
	public CookieSerializer cookieSerializer() {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		serializer.setCookieName("OVERIDE_MY_JSESSIONID"); 
		serializer.setCookiePath("/"); 
		serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); 
		// serializer.setSameSite("Lax");
		return serializer;
	}

	// should be instanciated directly instead in serializer
	@Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier
			.ofLax()
			.whenHasName("OVERIDE_MY_JSESSIONID");
    }
}
