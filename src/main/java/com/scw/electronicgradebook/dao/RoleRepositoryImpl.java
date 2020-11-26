package com.scw.electronicgradebook.dao;

import com.scw.electronicgradebook.domain.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Role> getByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery(
                "select r from Role r where r.name = :name",
                Role.class);
        query.setParameter("name", name);

        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
