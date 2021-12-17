package com.epam.esm.contollers;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.GiftCertificateSearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/giftCertificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificate>> getAllGiftCertificates(GiftCertificateSearchParams searchParams) {
        List<GiftCertificate> giftCertificateListItems = giftCertificateService.findAll(searchParams);
        return new ResponseEntity<>(giftCertificateListItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> getGiftCertificateById(@PathVariable("id") long id) {
        return new ResponseEntity<>(giftCertificateService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GiftCertificate> addGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        return new ResponseEntity<>(giftCertificateService.save(giftCertificate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftCertificateById(@PathVariable("id") Long id) {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificate> updateGiftCertificate(@RequestBody GiftCertificate giftCertificate,
                                                                 @PathVariable("id") long id) {
        return new ResponseEntity<>(giftCertificateService.update(id, giftCertificate), HttpStatus.OK);
    }
}
