package com.epam.esm.repository;

import com.epam.esm.model.impl.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
}
