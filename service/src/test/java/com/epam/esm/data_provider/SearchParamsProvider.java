package com.epam.esm.data_provider;

import com.epam.esm.model.impl.GiftCertificateSearchParams;

public class SearchParamsProvider {

    public GiftCertificateSearchParams getEmptyParameters() {
        return new GiftCertificateSearchParams();
    }

    public GiftCertificateSearchParams getParametersByTagName() {
        GiftCertificateSearchParams searchParams = new GiftCertificateSearchParams();
        searchParams.setTagName("Tag");
        return searchParams;
    }
}
