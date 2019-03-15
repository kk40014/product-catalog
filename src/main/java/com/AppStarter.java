package com;

import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
  
@SpringBootApplication(scanBasePackages =
{ "com" })
public class AppStarter extends SpringBootServletInitializer {  
	
	  @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(AppStarter.class);
	    }
	  
    public static void main(String[] args) {  
    	System.setProperty("server.servlet.context-path", "/product-catalog");
        SpringApplication.run(AppStarter.class, args);  
    }  
} 