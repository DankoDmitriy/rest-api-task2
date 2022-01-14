package com.epam.esm.data_provider;

import com.epam.esm.model.impl.GiftCertificateSearchParams;

import java.util.Arrays;

public class SearchParamsProvider {

    public GiftCertificateSearchParams getEmptyParameters() {
        return new GiftCertificateSearchParams();
    }

    public GiftCertificateSearchParams getParametersByTagName() {
        GiftCertificateSearchParams searchParams = new GiftCertificateSearchParams();
        searchParams.setTags(Arrays.asList("Tag"));
        return searchParams;
    }
}
