package com.codingrecipe.member.repository.likesRepository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class LikesRepositoryCustomImpl implements LikesRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long getLikesCountForHospital(String hospitalId) {
        String jpql = "SELECT COUNT(l) FROM Likes l WHERE l.hospital.id = :hospitalId";
        return entityManager.createQuery(jpql, Long.class)
                .setParameter("hospitalId", hospitalId)
                .getSingleResult();
    }

}
