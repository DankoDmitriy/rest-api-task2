package com.epam.esm.service.impl;

import com.epam.esm.repository.StatisticRepository;
import com.epam.esm.service.EntityToDtoConverterService;
import com.epam.esm.service.StatisticsService;
import com.epam.esm.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticRepository statisticRepository;
    private final EntityToDtoConverterService entityToDtoConverterService;

    @Autowired
    public StatisticsServiceImpl(StatisticRepository statisticRepository,
                                 EntityToDtoConverterService entityToDtoConverterService) {
        this.statisticRepository = statisticRepository;
        this.entityToDtoConverterService = entityToDtoConverterService;
    }

    @Override
    public TagDto popularTag() {
        return entityToDtoConverterService.convert(statisticRepository.findPopularTag().get());
    }
}
