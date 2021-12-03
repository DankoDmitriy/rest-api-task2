package com.epam.esm.impl;

import com.epam.esm.GiftCertificateDao;
import com.epam.esm.GiftCertificateRowMapper;
import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;
    private GiftCertificateRowMapper giftCertificateRowMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return giftCertificateDao.findById(id);
    }

    //    TODO - add transactions
    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        giftCertificate = giftCertificateDao.save(giftCertificate);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        List<Tag> tagItems = giftCertificate.getTagItems();
        for (Tag tagItem : tagItems) {
            tagItem = tagDao.save(tagItem);
            giftCertificateDao.attachTag(giftCertificate.getId(), tagItem.getId());
        }
        return giftCertificate;
    }

    @Override
    public void delete(Long id) {
        giftCertificateDao.delete(id);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return giftCertificateDao.update(giftCertificate);
    }
}
