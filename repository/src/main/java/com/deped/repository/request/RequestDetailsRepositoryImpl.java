package com.deped.repository.request;

import com.deped.model.request.*;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class RequestDetailsRepositoryImpl implements RequestDetailsRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    private class StatusUpdateBean {
        private String query;
        private Map<String, Object> parameterMap;

        public StatusUpdateBean(String query, Map<String, Object> parameterMap) {
            this.query = query;
            this.parameterMap = parameterMap;
        }

        public String getQuery() {
            return query;
        }

        public Map<String, Object> getParameterMap() {
            return parameterMap;
        }
    }

    @Override
    public RequestDetails create(RequestDetails entity) {
        return hibernateFacade.saveEntity(RequestDetails.class, entity);
    }

    @Override
    public Boolean update(RequestDetails entity) {
        return hibernateFacade.updateEntity(entity);
    }

    @Override
    public List<RequestDetails> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_REQUESTS_DETAILS, RequestDetails.class);
    }

    @Override
    public List<RequestDetails> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_REQUESTS_DETAILS_RANGES, range, RequestDetails.class);
    }

    @Override
    public RequestDetails fetchById(Object id) {
        return hibernateFacade.fetchEntityById(RequestDetails.class, id);
    }

    @Override
    public Boolean remove(RequestDetails... entities) {
        //TODO write different query for this
        return hibernateFacade.removeEntities(REQUEST_DETAILS_TABLE, "composite?", entities);
    }

    @Override
    public Boolean createOrUpdateAll(RequestDetails... entities) {
        Boolean isDone = hibernateFacade.createOrUpdateAll(RequestDetails.class, entities);
        return isDone;
    }

    @Override
    public Boolean requestAll(RequestDetails... entities) {
        if (entities.length == 0) {
            return false;
        }

        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Transaction tx = null;


        try {
            tx = hibernateSession.beginTransaction();
            NativeQuery nativeQuery = hibernateSession.createNativeQuery("UPDATE request SET request_status = 'PENDING' WHERE request_id = :requestId");
            nativeQuery.setParameter("requestId", entities[0].getRequestDetailsID().getRequestId());
            nativeQuery.executeUpdate();

            for (RequestDetails elem : entities) {
                hibernateSession.saveOrUpdate("RequestDetails", elem);
            }

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


    @Override
    public boolean updateRequestStatus(RequestDetails[] entities) {
        List<StatusUpdateBean> statusUpdateBeanList = makeStatusUpdateBeanList(entities);
        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Transaction tx = null;
        int effectedRows = -1;
        try {
            tx = hibernateSession.beginTransaction();

            RequestDetailsStatus requestDetailsStatus = entities[0].getRequestDetailsStatus();
            RequestStatus nextRequestStatus = null;
            switch (requestDetailsStatus) {
                case APPROVED:
                case DISAPPROVED:
                    nextRequestStatus = RequestStatus.CONSIDERED;
                    break;
                case RELEASED:
                    nextRequestStatus = RequestStatus.FINALIZED;
                    break;
            }

            String requestStatusQuery = "UPDATE request SET request_status = :requestStatus WHERE request_id = :requestId";
            NativeQuery<Request> requestNativeQuery = hibernateSession.createNativeQuery(requestStatusQuery, Request.class);
            requestNativeQuery.setParameter("requestStatus", nextRequestStatus.toString());
            requestNativeQuery.setParameter("requestId", entities[0].getRequestDetailsID().getRequestId());
            int requestRowEffected = requestNativeQuery.executeUpdate();
            if (requestRowEffected <= 0) {
                throw new Exception("Sql update operation encountered a problem");
            }


            for (StatusUpdateBean sub : statusUpdateBeanList) {
                NativeQuery<RequestDetails> nativeQuery = hibernateSession.createNativeQuery(sub.getQuery(), RequestDetails.class);
                for (Map.Entry<String, Object> entry : sub.getParameterMap().entrySet()) {
                    nativeQuery.setParameter(entry.getKey(), entry.getValue());
                }
                effectedRows = nativeQuery.executeUpdate();
                if (effectedRows <= 0) {
                    throw new Exception("Sql update operation encountered a problem");
                }
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        } finally {
            if (hibernateSession != null)
                hibernateSession.close();
        }

        return false;
    }

    @Override
    public List<RequestDetails> fetchAllById(Long requestId) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("requestId", requestId);
        String sqlQuery = "SELECT * FROM request_details WHERE request_request_id = :requestId AND request_details_status != 'DISAPPROVED'";
        List<RequestDetails> list = hibernateFacade.fetchAllEntityBySqlQuery(sqlQuery, null, RequestDetails.class, parameterMap);
        return list;
    }

    private void setMap(Map<String, Object> parameterMap, RequestDetails temp, String requestIdMapKey, String itemIdMapKey) {
        parameterMap.put(requestIdMapKey, temp.getRequestDetailsID().getRequestId());
        parameterMap.put(itemIdMapKey, temp.getRequestDetailsID().getItemId());
    }

    public static void main(String[] args) {
        RequestDetails[] requestDetailsList = new RequestDetails[10];
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                RequestDetails od = new RequestDetails();
                Long id = Long.parseLong(i + "");
                od.setRequestDetailsID(new RequestDetailsID(id, id));
                od.setRequestDetailsStatus(RequestDetailsStatus.APPROVED);
                requestDetailsList[i] = od;
            } else {
                RequestDetails od = new RequestDetails();
                Long id = Long.parseLong(i + "");
                od.setRequestDetailsID(new RequestDetailsID(id, id));
                od.setRequestDetailsStatus(RequestDetailsStatus.DISAPPROVED);
                requestDetailsList[i] = od;
            }
        }

        new RequestDetailsRepositoryImpl().updateRequestStatus(requestDetailsList);
    }

    private List<StatusUpdateBean> makeStatusUpdateBeanList(RequestDetails[] entities) {

        List<StatusUpdateBean> statusUpdateBeanList = new ArrayList<>();

        final String baseQuery = "UPDATE request_details SET request_details_status = :requestDetailsState "
                .concat("WHERE (request_request_id, item_item_id) IN (");

        List<RequestDetails> requestDetailsList = Arrays.asList(entities);
        Collections.sort(requestDetailsList, Comparator.comparing(RequestDetails::getRequestDetailsStatus));

        RequestDetailsStatus firstElementState = null;
        if (!requestDetailsList.isEmpty()) {
            firstElementState = requestDetailsList.get(0).getRequestDetailsStatus();
        }

        StringBuilder sb = new StringBuilder(baseQuery);
        Map<String, Object> parameterMap = new HashMap<>();

        final String requestIdMapKeyFormat = "requestId%d";
        final String itemIdMapKeyFormat = "itemId%d";
        final String colonStr = ":";

        for (int i = 0; i < requestDetailsList.size(); i++) {
            RequestDetails temp = requestDetailsList.get(i);

            String requestIdMapKey = String.format(requestIdMapKeyFormat, i);
            String itemIdMapKey = String.format(itemIdMapKeyFormat, i);
            setMap(parameterMap, temp, requestIdMapKey, itemIdMapKey);
            String tempAppend = String.format("( %s , %s), ", (colonStr + requestIdMapKey), (colonStr + itemIdMapKey));
            sb.append(tempAppend);

            boolean isLastElement = (i + 1 == requestDetailsList.size());
            RequestDetailsStatus nextIndexStatus;

            if (isLastElement) {
                nextIndexStatus = requestDetailsList.get(i).getRequestDetailsStatus();
            } else {
                nextIndexStatus = requestDetailsList.get(i + 1).getRequestDetailsStatus();
            }

            if (isLastElement || nextIndexStatus != firstElementState) {
                parameterMap.put("requestDetailsState", firstElementState.toString());
                String query = sb.toString();
                query = query.substring(0, query.lastIndexOf(",")).concat(")");
                statusUpdateBeanList.add(new StatusUpdateBean(query, parameterMap));
                parameterMap = new HashMap<>();
                sb.setLength(0);
                sb.append(baseQuery);
                firstElementState = nextIndexStatus;
            }
        }

        return statusUpdateBeanList;
    }
}
