// package com.nizar.SahTech.security;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {
//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**") // Allow CORS for all paths
//                 .allowedOrigins("http://localhost:4200") // Whitelist your Angular app's origin
//                 .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
//                 .allowCredentials(true); // Allow cookies, authorization headers, etc.
//     }
// }
