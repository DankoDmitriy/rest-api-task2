package com.epam.esm.impl;

import com.epam.esm.GiftCertificateDao;
import com.epam.esm.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return giftCertificateDao.findById(id);
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        return giftCertificateDao.save(giftCertificate);
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
