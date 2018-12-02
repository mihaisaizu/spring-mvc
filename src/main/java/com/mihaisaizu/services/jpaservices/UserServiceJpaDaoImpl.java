package com.mihaisaizu.services.jpaservices;

import com.mihaisaizu.domain.User;
import com.mihaisaizu.services.UserService;
import com.mihaisaizu.services.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Profile("jpadao")
public class UserServiceJpaDaoImpl extends AbstractJpaDaoService implements UserService {

    private EncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public List<User> listAll() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        return em.find(User.class, id);
    }

    @Override
    public User saveOrUpdate(User domainObject) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (domainObject.getPassword() != null){
            domainObject.setEncryptedPassword(encryptionService.encryptString(domainObject.getPassword()));
        }
        User saveUser = em.merge(domainObject);
        em.getTransaction().commit();
        //em.close();
        return saveUser;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(User.class, id));
        em.getTransaction().commit();
    }
}
