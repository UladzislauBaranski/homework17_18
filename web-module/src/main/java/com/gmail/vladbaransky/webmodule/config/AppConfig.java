package com.gmail.vladbaransky.webmodule.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.gmail.vladbaransky.service", "com.gmail.vladbaransky.repository"})
public class AppConfig {
}
