package com.rpl.ujianproject.dao;

import com.rpl.ujianproject.config.HibernateUtil;
import com.rpl.ujianproject.entity.Barang;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BarangDao {

    public void save(Barang barang) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(barang);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void update(Barang barang) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(barang);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(Barang barang) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Barang managedBarang = session.get(Barang.class, barang.getId());
            if (managedBarang != null) {
                session.remove(managedBarang);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Barang findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Barang.class, id);
        }
    }

    public List<Barang> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Barang> query = session.createQuery("FROM Barang ORDER BY id", Barang.class);
            return query.list();
        }
    }

    public List<Barang> search(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Barang> query = session.createQuery(
                "FROM Barang WHERE kodeBarang LIKE :keyword OR namaBarang LIKE :keyword ORDER BY id", 
                Barang.class
            );
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        }
    }
}
