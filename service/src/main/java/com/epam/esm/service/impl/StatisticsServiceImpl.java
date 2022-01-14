package com.epam.esm.service.impl;

import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.StatisticsDao;
import com.epam.esm.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsDao statisticsDao;

    @Autowired
    public StatisticsServiceImpl(StatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
    }

    @Override
    public Tag popularTag() {
        return statisticsDao.findPopularTag().get();
    }
}
