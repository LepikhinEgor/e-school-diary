package com.scw.electronicgradebook.dao.impl;

import com.scw.electronicgradebook.dao.GradeRepository;
import com.scw.electronicgradebook.domain.entities.Grade;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class GradeRepositoryImpl implements GradeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Grade grade) {
        entityManager.persist(grade);
    }

    @Override
    public void update(Grade grade) {
        entityManager.merge(grade);
    }

    @Override
    public void delete(Grade grade) {
        entityManager.remove(grade);
    }

    @Override
    public Optional<Grade> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Grade.class, id));
    }

    @Override
    public List<Grade> getInTimeInterval(Date dateFrom, Date dateTo) {
        TypedQuery<Grade> query = entityManager.createQuery("" +
                "select g from Grade g join  g.exam e " +
                        "where e.endTime > :dateFrom and e.endTime < :dateTo"
                , Grade.class);
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateFrom);

        return query.getResultList();
    }
}
