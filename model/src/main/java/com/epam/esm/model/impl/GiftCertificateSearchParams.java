package com.epam.esm.model.impl;

import com.epam.esm.model.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class GiftCertificateSearchParams
        extends RepresentationModel<GiftCertificateSearchParams>
        implements AbstractEntity {
    private List<String> tags;
    private String giftCertificateName;
    private String giftCertificateDescription;
//    private Set<String> sorts;
//    private Set<String> sortTypes = new HashSet<>(
//            Arrays.asList(
//                    "sortByGiftCertificateName-",
//                    "sortByGiftCertificateName+",
//                    "sortByCreateDate+",
//                    "sortByCreateDate-"));

    public GiftCertificateSearchParams(List<String> tags,
                                       String giftCertificateName,
                                       String giftCertificateDescription
//            , Set<String> sort
    ) {
        this.tags = tags;
        this.giftCertificateName = giftCertificateName;
        this.giftCertificateDescription = giftCertificateDescription;
//        this.sorts = sort;
    }
}
