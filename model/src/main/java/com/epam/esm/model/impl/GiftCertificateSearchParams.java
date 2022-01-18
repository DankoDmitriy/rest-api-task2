package com.epam.esm.model.impl;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GiftCertificateSearchParams {
    private List<String> tags;
    private String giftCertificateName;
    private String giftCertificateDescription;

    public GiftCertificateSearchParams(List<String> tags,
                                       String giftCertificateName,
                                       String giftCertificateDescription) {
        this.tags = tags;
        this.giftCertificateName = giftCertificateName;
        this.giftCertificateDescription = giftCertificateDescription;
    }
}
