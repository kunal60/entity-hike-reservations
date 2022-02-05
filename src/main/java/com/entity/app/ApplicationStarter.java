package com.entity.app;

import com.entity.app.model.Hikes;
import com.entity.app.service.HikeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class ApplicationStarter {

    @Autowired
    private Jackson2ObjectMapperBuilder builder;

    @Autowired
    HikeService hikeService;

    /**
     * This method is called on Application Startup
     */
    @PostConstruct
    public void init() {
        Hikes hikes = readJsonFile();
        loadHikesInDB(hikes);
    }

    /**
     * Reads Json File on Application StratUp
     *
     * @return
     */
    private Hikes readJsonFile() {
        ObjectMapper mapper = builder.build();
        TypeReference<Hikes> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/dataset.json");
        Hikes hikes = new Hikes();
        try {
            hikes = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return hikes;
    }

    /**
     * Stores hike Data in DB
     *
     * @param hikes
     */
    private void loadHikesInDB(Hikes hikes) {
        hikeService.createHike(hikes.getHikes());
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

}
