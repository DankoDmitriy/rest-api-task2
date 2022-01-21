package com.epam.esm.repository;

import com.epam.esm.model.impl.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {
    Optional<GiftCertificate> findFirstByTags_Id(Long tagId);
}
