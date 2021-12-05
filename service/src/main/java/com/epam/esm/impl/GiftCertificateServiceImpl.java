package com.epam.esm.impl;

import com.epam.esm.GiftCertificateDao;
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
        attachTagToGiftCertificate(giftCertificate.getId(), giftCertificate.getTagItems());
        return giftCertificate;
    }

    @Override
    public void delete(Long id) {
        giftCertificateDao.delete(id);
    }

    //    TODO add Transactions and TEST this method
    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        GiftCertificate certificateFromDB = giftCertificateDao.findById(giftCertificate.getId()).get();

        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificate = giftCertificateDao.update(giftCertificate);
        attachTagToGiftCertificate(giftCertificate.getId(), giftCertificate.getTagItems());

        List<Tag> tagItemsFromDb = certificateFromDB.getTagItems();
        for (Tag tag : tagItemsFromDb) {
            if (!giftCertificate.getTagItems().contains(tag)) {
                giftCertificateDao.detachTag(giftCertificate.getId(), tag.getId());
            }
        }
        return giftCertificate;
    }

    private void attachTagToGiftCertificate(Long giftCertificateId, List<Tag> tagItems) {
        for (Tag tag : tagItems) {
            tag = tagDao.save(tag);
            giftCertificateDao.attachTag(giftCertificateId, tag.getId());
        }
    }
}
