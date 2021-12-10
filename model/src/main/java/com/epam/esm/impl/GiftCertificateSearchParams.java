package com.epam.esm.impl;

import com.epam.esm.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class GiftCertificateSearchParams implements AbstractEntity {
    private String tagName;
    private String giftCertificateName;
    private String giftCertificateDescription;
    private Set<String> sort;
    private Set<String> sortTypes = new HashSet<>(
            Arrays.asList(
                    "giftCertificateName-",
                    "giftCertificateName+",
                    "sortByCreateDate+",
                    "sortByCreateDate-"));

    public GiftCertificateSearchParams(String tagName,
                                       String giftCertificateName,
                                       String giftCertificateDescription,
                                       Set<String> sort) {
        this.tagName = tagName;
        this.giftCertificateName = giftCertificateName;
        this.giftCertificateDescription = giftCertificateDescription;
        this.sort = sort;
    }
}
