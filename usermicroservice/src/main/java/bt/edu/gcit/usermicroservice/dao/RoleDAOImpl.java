package bt.edu.gcit.usermicroservice.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bt.edu.gcit.usermicroservice.entity.Role;
import jakarta.persistence.EntityManager;

@Repository
public class RoleDAOImpl implements RoleDAO {
    private EntityManager entityManager;

    @Autowired
    public RoleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addRole(Role role) {
        // TODO Auto-generated method
        entityManager.persist(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("SELECT u FROM Role u", Role.class).getResultList();
    }
}