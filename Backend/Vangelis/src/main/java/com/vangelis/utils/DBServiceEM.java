package com.vangelis.utils;

import com.vangelis.utils.IDBService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
@Service
public class DBServiceEM implements IDBService {
    @PersistenceContext
    private EntityManager em = Persistence
            .createEntityManagerFactory("PersistenceUnit")
            .createEntityManager();

    public <T> void Save(T objectT) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(objectT);
        tx.commit();
    }

        /*
        public void delete(long id) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.remove(get(id));
            tx.commit();
        }

        public Student get(long id) {
            return em.find(Student.class, id);
        }

        public void update(Student student) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.merge(student);
            tx.commit();
        }

        public List<Student> getAll() {
            TypedQuery<Student> namedQuery =
                    em.createNamedQuery("Student.getAll", Student.class);
            return namedQuery.getResultList();
        }*/

}

