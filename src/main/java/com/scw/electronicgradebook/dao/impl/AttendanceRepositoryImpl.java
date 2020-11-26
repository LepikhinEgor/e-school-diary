package com.scw.electronicgradebook.dao.impl;

import com.scw.electronicgradebook.dao.AttendanceRepository;
import com.scw.electronicgradebook.domain.entities.Attendance;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class AttendanceRepositoryImpl implements AttendanceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Attendance attendance) {
        entityManager.persist(attendance);
    }

    @Override
    public void update(Attendance attendance) {
        entityManager.merge(attendance);
    }

    @Override
    public void delete(Attendance attendance) {
        entityManager.remove(attendance);
    }

    @Override
    public Optional<Attendance> getById(Long id) {
        return Optional.of(entityManager.find(Attendance.class, id));
    }
}
