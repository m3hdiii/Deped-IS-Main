package com.deped.repository.other;

import com.deped.model.Dashboard;
import com.deped.repository.utils.HibernateFacade;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DashboardRepositoryImpl implements DashboardRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Dashboard getBasicInfo() {
        String lastWeekRequestNo = "SELECT count(*) FROM request WHERE request_date BETWEEN date_sub(now(),INTERVAL 1 WEEK) AND now()";
        String lastWeekOrdersNo = "SELECT count(*) FROM order_ WHERE order_date BETWEEN date_sub(now(),INTERVAL 1 WEEK) AND now()";
        String belowThreshold = "SELECT count(*) from (SELECT name from item WHERE quantity <= threshold group by name) s";
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

            List lastWeekRequestNoResult = nativeQuery1.list();
            List lastWeekOrdersNoResult = nativeQuery2.list();
            List belowThresholdResult = nativeQuery3.list();
            List totalUserResult = nativeQuery4.list();

            if (lastWeekRequestNoResult.get(0) != null) {
                try {
                    String st1 = lastWeekRequestNoResult.get(0).toString();
                    lastWeekRequestNoLong = Long.parseLong(st1);
                } catch (NumberFormatException e) {

                }
            }

            if (lastWeekOrdersNoResult.get(0) != null) {
                try {
                    String st2 = lastWeekOrdersNoResult.get(0).toString();
                    lastWeekOrdersNoLong = Long.parseLong(st2);
                } catch (NumberFormatException e) {

                }
            }

            if (belowThresholdResult.get(0) != null) {
                try {
                    String st3 = belowThresholdResult.get(0).toString();
                    belowThresholdLong = Long.parseLong(st3);
                } catch (NumberFormatException e) {
                }
            }

            if (totalUserResult.get(0) != null) {
                try {
                    String st4 = totalUserResult.get(0).toString();
                    totalUserLong = Long.parseLong(st4);
                } catch (NumberFormatException e) {
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
