package com.epam.esm.repository.imp;

import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final int QUERY_PARAM_FIRST_INDEX = 1;
    private static final long ZERO_ROWS_IN_TABLE = 0;
    private static final String SQL_FIND_ALL_TAGS = "FROM Tag";
    private static final String SQL_TAG_IS_USED = "SELECT COUNT(*) FROM certificate_tag where tag_id=?";

    private final EntityManager entityManager;

    @Autowired
    public TagDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Tag> findAll() {
        return entityManager.createQuery(SQL_FIND_ALL_TAGS).getResultList();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Tag save(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public boolean isTagUsedInGiftCertificate(Long id) {
        Query query = entityManager.createNativeQuery(SQL_TAG_IS_USED);
        query.setParameter(QUERY_PARAM_FIRST_INDEX, id);
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.longValue() > ZERO_ROWS_IN_TABLE;
    }
}
