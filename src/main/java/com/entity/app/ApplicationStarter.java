package com.entity.app;

import com.entity.app.model.Trails;
import com.entity.app.service.TrailService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Kunal Malhotra
 */
@SpringBootApplication
public class ApplicationStarter {

    @Autowired
    private Jackson2ObjectMapperBuilder builder;

    @Autowired
    TrailService trailServiceImpl;

    /**
     * This method is called on Application Startup
     */
    @PostConstruct
    public void init() {
        Trails trails = readJsonFile();
        loadTrailsInDB(trails);
    }

    /**
     * Reads Json File on Application StartUp
     *
     * @return
     */
    private Trails readJsonFile() {
        ObjectMapper mapper = builder.build();
        TypeReference<Trails> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/dataset.json");
        Trails trails = new Trails();
        try {
            trails = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trails;
    }

    /**
     * Stores Trail's Data in DB
     *
     * @param trails
     */
    private void loadTrailsInDB(Trails trails) {
        trailServiceImpl.saveTrails(trails.getTrails());
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

}
