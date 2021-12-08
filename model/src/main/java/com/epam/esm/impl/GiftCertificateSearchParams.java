package com.epam.esm.impl;

import com.epam.esm.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
