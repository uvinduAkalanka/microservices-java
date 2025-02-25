package org.microservices34.employeeservice.config;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ModelMapperConfig {
//    @Bean
//    ModelMapper modelMapper() {
//        return new ModelMapper()
//                .setMatchingStrategy(MatchingStrategies.LOOSE)
//                .setFieldMatchingEnabled(true)
//                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);;
//    }
//}


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }
}