package com.epam.esm.contollers;

import com.epam.esm.hateaos.HateoasBuilder;
import com.epam.esm.service.StatisticsService;
import com.epam.esm.service.dto.TagDto;
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
    private final StatisticsService service;
    private final HateoasBuilder hateoasBuilder;

    @Autowired
    public StatisticController(StatisticsService service, HateoasBuilder hateoasBuilder) {
        this.service = service;
        this.hateoasBuilder = hateoasBuilder;
    }

    @GetMapping("/popular-tag")
    public ResponseEntity<TagDto> getTagById() {
        TagDto tagDto = service.popularTag();
        hateoasBuilder.setLinks(tagDto);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }
}
