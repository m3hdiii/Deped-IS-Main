package com.deped.repository.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.MalformedParametersException;
import java.util.List;
import java.util.Map;

@Component
public class HibernateFacade {
    private static volatile SessionFactory sessionInstance;

    private HibernateFacade() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionInstance == null) {
            synchronized (HibernateFacade.class) {
                if (sessionInstance == null) {
                    try {
                        StandardServiceRegistry standardRegistry =
                                new StandardServiceRegistryBuilder().configure("hibernate.configs.xml").build();
                        Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
                        sessionInstance = metaData.getSessionFactoryBuilder().build();

                    } catch (Throwable th) {
                        System.err.println("Enitial SessionFactory creation failed" + th);
                        throw new ExceptionInInitializerError(th);
                    }
                }
            }
        }
        return sessionInstance;
    }


    public Boolean updateEntity(Object entity) {
        Session hibernateSession;
        try {
            hibernateSession = getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Transaction tx = null;

        try {
            tx = hibernateSession.beginTransaction();
            hibernateSession.update(entity);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return false;
        } finally {
            if (hibernateSession != null)
                hibernateSession.close();
        }
        return true;
    }

    public <T> T saveEntity(Class<T> entityClass, T object) {
        Session hibernateSession;
        try {
            hibernateSession = getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;

        try {
            tx = hibernateSession.beginTransaction();
            hibernateSession.save(entityClass.getSimpleName(), object);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return null;
        } finally {
            if (hibernateSession != null)
                hibernateSession.close();
        }

        return object;
    }

    public <T> List<T> fetchAllByParameterMap(String nameQuery, Class<T> entityClass, Map<String, Object> parameterMap) {
        Session hibernateSession;
        try {
            hibernateSession = getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;

        List<T> rows;

        try {
            tx = hibernateSession.beginTransaction();
            Query<T> namedQuery = hibernateSession.createNamedQuery(nameQuery, entityClass);

            for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                namedQuery.setParameter(entry.getKey(), entry.getValue());
            }

            rows = namedQuery.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return null;
        } finally {
//            if (hibernateSession != null)
//                hibernateSession.close();
        }
        return rows;
    }

    public <T> List<T> fetchAllEntity(String nameQuery, Class<T> entityClass) {
        return fetchAllEntity(nameQuery, null, entityClass);
    }

    public <T> List<T> fetchAllEntity(String nameQuery, Range range, Class<T> entityClass) {
        Session hibernateSession;
        try {
            hibernateSession = getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;

        List<T> rows;

        try {
            tx = hibernateSession.beginTransaction();
            Query<T> namedQuery = hibernateSession.createNamedQuery(nameQuery, entityClass);

            setRange(range, namedQuery);

            rows = namedQuery.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return null;
        } finally {
//            if (hibernateSession != null)
//                hibernateSession.close();
        }
        return rows;
    }

    public <T> Boolean removeEntities(String tableName, String tableIdName, T... entities) {
        Session hibernateSession;
        try {
            hibernateSession = getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Transaction tx = null;


        try {
            tx = hibernateSession.beginTransaction();
            String nativeQueryStr = createDeleteQuery(tableName, tableIdName, entities);
            NativeQuery query = hibernateSession.createNativeQuery(nativeQueryStr);
            for (int i = 0; i < entities.length; i++) {
                query.setParameter(i + 1, getIdValue(entities[i]));
            }

            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return false;
        } finally {
            if (hibernateSession != null)
                hibernateSession.close();
        }
        return true;
    }

    public <T> T fetchEntityById(Class<T> entityClass, Object entityId) {
        Session hibernateSession;
        try {
            hibernateSession = getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;

        T entity;
        try {
            tx = hibernateSession.beginTransaction();
            entity = hibernateSession.find(entityClass, entityId);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return null;
        } finally {
//            if (hibernateSession != null)
//                hibernateSession.close();
        }
        return entity;
    }

    private <T> String createDeleteQuery(String tableName, String tableIdName, T... entities) {
        StringBuilder sb = new StringBuilder(String.format("DELETE FROM %s WHERE %s IN ( ", tableName, tableIdName));
        for (int i = 0; i < entities.length; i++) {
            sb.append("?" + " , ");
        }
        String queryStr = sb.toString();
        queryStr = queryStr.substring(0, queryStr.length() - 2);
        queryStr += ")";
        return queryStr;
    }

    private <T> Long getIdValue(T entity) {
        for (Field f : entity.getClass().getDeclaredFields()) {

            if (f.isAnnotationPresent(Id.class)) {
                try {
                    return f.getLong(entity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1L;
    }

    private <T> void setRange(Range range, Query<T> namedQuery) {
        if (range != null) {
            int from = range.getFrom();
            int to = range.getTo();
            if (from >= 0 && to >= 0 && to > from) {
                namedQuery.setParameter("from", range.getFrom());
                namedQuery.setParameter("to", range.getTo());
            } else {
                try {
                    throw new MalformedParametersException();
                } catch (MalformedParametersException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }
}
