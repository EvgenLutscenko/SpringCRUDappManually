package com.ua.javarush.dao;

import com.ua.javarush.domain.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskDAO {
    private final SessionFactory sessionFactory;

    public TaskDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Task> getAll(int offset, int limit){
        Query<Task> query = getSession().createQuery("from Task", Task.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int getAllCount(){
        Query<Long> query = getSession().createQuery("select count(*) from Task ", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<Task> getById(int id){
        Query<Task> query = getSession().createQuery("select t from Task t where t.id=:ID", Task.class);
        query.setParameter("ID", id);
        return Optional.of(query.uniqueResult());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(Task task){
        getSession().persist(task);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Task task){
        getSession().remove(task);
    }

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}