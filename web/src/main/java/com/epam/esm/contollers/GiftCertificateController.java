package com.epam.esm.contollers;

import com.epam.esm.hateaos.HateoasBuilder;
import com.epam.esm.model.impl.GiftCertificateSearchParams;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.CustomPageDto;
import com.epam.esm.service.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequestMapping(value = "/api/giftCertificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final HateoasBuilder hateoasBuilder;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, HateoasBuilder hateoasBuilder) {
        this.giftCertificateService = giftCertificateService;
        this.hateoasBuilder = hateoasBuilder;
    }

    @GetMapping
    public ResponseEntity<CustomPageDto> getAllGiftCertificates(GiftCertificateSearchParams searchParams, Pageable pageable) {
        CustomPageDto<GiftCertificateDto> customPage = giftCertificateService.findAll(searchParams, pageable);
//        List<GiftCertificate> certificates = customPage.getItems();
//        hateoasBuilder.setLinksGiftCertificates(certificates);
        return new ResponseEntity<>(customPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> getGiftCertificateById(@PathVariable("id") long id) {
        GiftCertificateDto certificate = giftCertificateService.findById(id);
//        hateoasBuilder.setLinks(certificate);
        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GiftCertificateDto> addGiftCertificate(@RequestBody GiftCertificateDto giftCertificate) {
        GiftCertificateDto certificate = giftCertificateService.save(giftCertificate);
//        hateoasBuilder.setLinks(certificate);
        return new ResponseEntity<>(certificate, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftCertificateById(@PathVariable("id") Long id) {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> updateGiftCertificate(@RequestBody GiftCertificateDto giftCertificate,
                                                                    @PathVariable("id") long id) {
        GiftCertificateDto certificate = giftCertificateService.update(id, giftCertificate);
//        hateoasBuilder.setLinks(certificate);
        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }
}
