package com.epam.esm.contollers;

import com.epam.esm.model.impl.Tag;
import com.epam.esm.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticController {
    StatisticsService service;

    @Autowired
    public StatisticController(StatisticsService service) {
        this.service = service;
    }

    @GetMapping("/popular-tag")
    public ResponseEntity<Tag> getTagById() {
        return new ResponseEntity<>(service.popularTag(), HttpStatus.OK);
    }
}
