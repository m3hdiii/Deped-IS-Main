package com.deped.repository.other;

import com.deped.model.Dashboard;
import com.deped.repository.utils.HibernateFacade;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardRepositoryImpl implements DashboardRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Dashboard getBasicInfo() {
        String lastWeekRequestNo = "SELECT count(*) FROM request WHERE request_date BETWEEN date_sub(now(),INTERVAL 1 WEEK) AND now()";
        String lastWeekOrdersNo = "SELECT count(*) FROM order_ WHERE order_date BETWEEN date_sub(now(),INTERVAL 1 WEEK) AND now()";
        String belowThreshold = "SELECT count(*) from (SELECT item_name from item WHERE quantity <= threshold group by item_name) s";
        String totalUserNo = "SELECT count(*) FROM user";

        Long lastWeekRequestNoLong = 0L;
        Long lastWeekOrdersNoLong = 0L;
        Long belowThresholdLong = 0L;
        Long totalUserLong = 0L;
        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;
        Dashboard dashboard = null;

        try {
            tx = hibernateSession.beginTransaction();
            NativeQuery nativeQuery1 = hibernateSession.createNativeQuery(lastWeekRequestNo);
            NativeQuery nativeQuery2 = hibernateSession.createNativeQuery(lastWeekOrdersNo);
            NativeQuery nativeQuery3 = hibernateSession.createNativeQuery(belowThreshold);
            NativeQuery nativeQuery4 = hibernateSession.createNativeQuery(totalUserNo);

            Object lastWeekRequestNoResult = nativeQuery1.getSingleResult();
            Object lastWeekOrdersNoResult = nativeQuery2.getSingleResult();
            Object belowThresholdResult = nativeQuery3.getSingleResult();
            Object totalUserResult = nativeQuery4.getSingleResult();

            if (lastWeekRequestNoResult != null) {
                try {
                    String st1 = lastWeekRequestNoResult.toString();
                    System.out.println(st1);
                    lastWeekRequestNoLong = Long.parseLong(st1);
                } catch (NumberFormatException e) {
                    e.printStackTrace();

                }
            }

            if (lastWeekOrdersNoResult != null) {
                try {
                    String st2 = lastWeekOrdersNoResult.toString();
                    System.out.println(st2);
                    lastWeekOrdersNoLong = Long.parseLong(st2);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            if (belowThresholdResult != null) {
                try {
                    String st3 = belowThresholdResult.toString();
                    System.out.println(st3);
                    belowThresholdLong = Long.parseLong(st3);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            if (totalUserResult != null) {
                try {
                    String st4 = totalUserResult.toString();
                    System.out.println(st4);
                    totalUserLong = Long.parseLong(st4);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            tx.commit();
            dashboard = new Dashboard(lastWeekRequestNoLong, lastWeekOrdersNoLong, belowThresholdLong, totalUserLong);
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return null;
        } finally {
            if (hibernateSession != null)
                hibernateSession.close();
        }

        return dashboard;
    }
}
