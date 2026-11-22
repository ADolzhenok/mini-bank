package com.vda.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class TransactionHelper {

    private final SessionFactory sessionFactory;

    public TransactionHelper(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public void executeTransaction(Consumer<Session> action){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try{
            action.accept(session);
            tx.commit();
        }
        catch(Exception e){
            System.out.println("Exception was accured");
            tx.rollback();
            throw e;
        }
    }

    public<T> T executeTransaction(Function<Session, T> action){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try{
            T result = action.apply(session);
            tx.commit();
            return result;
        }
        catch(Exception e){
            System.out.println("Exception was accured");
            tx.rollback();
            throw e;
        }
    }

}
