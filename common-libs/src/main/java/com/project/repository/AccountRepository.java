package com.project.repository;

import com.project.model.Account;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {
    @Inject
    EntityManager em;

    public List<Account> getAllWithPagination(int offset, int limit) {
        return em.createNamedQuery("findAllAccount", Account.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Account findByUsername(String username) {
        return find("#findByUsername", Parameters.with("username", username)).firstResult();
    }

    public Account findByEmail(String email) {
        return find("#findByEmail", Parameters.with("email", email)).firstResult();
    }

    public Account findByPhoneNumber(String phoneNumber) {
        return find("#findByPhoneNumber", Parameters.with("phoneNumber", phoneNumber)).firstResult();
    }

    public Account findByIdentityNumber(String identityNumber) {
        return find("#findByIdentityNumber", Parameters.with("identityNumber", identityNumber)).firstResult();
    }
}
