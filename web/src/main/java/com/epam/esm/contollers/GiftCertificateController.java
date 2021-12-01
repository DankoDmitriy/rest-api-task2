package com.epam.esm.contollers;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.impl.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/gift", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {
    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificate> getAllGiftCertificates() {
        List<GiftCertificate> giftCertificateListItems = giftCertificateService.findAll();
        return giftCertificateListItems;
    }

    @GetMapping("/{id}")
    public GiftCertificate getGiftCertificateById(@PathVariable("id") long id) {
        return giftCertificateService.findById(id).orElse(null);
    }

    @PostMapping
    public GiftCertificate addGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        return giftCertificateService.save(giftCertificate);
    }

    @DeleteMapping("/{id}")
    public String deleteGiftCertificateById(@PathVariable("id") Long id) {
        giftCertificateService.delete(id);
        return "delete";
    }

    @PatchMapping("/{id}")
    public GiftCertificate updateGiftCertificate(@RequestBody GiftCertificate giftCertificate, @PathVariable("id") long id) {
        return giftCertificateService.update(giftCertificate);
    }
}
