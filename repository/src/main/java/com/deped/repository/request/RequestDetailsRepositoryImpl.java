package com.deped.repository.request;

import com.deped.model.items.Item;
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
    public boolean updateRequestStatus(Long userId, RequestDetailsStatus requestDetailsStatus, RequestDetails[] entities) {
        List<StatusUpdateBean> statusUpdateBeanList = makeStatusUpdateBeanList(entities, userId, requestDetailsStatus);
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


            String cancellationReasonQuery = "UPDATE request_details SET cancellation_reason = :reason WHERE request_request_id = :requestId AND item_item_id = :itemId";
            String disapprovalReasonQuery = "UPDATE request_details SET disapproval_message = :reason WHERE request_request_id = :requestId AND item_item_id = :itemId";
            for (RequestDetails rd : entities) {
                String cancellationReason = rd.getCancellationReason();
                String disapprovalMessage = rd.getDisapprovalMessage();
                if (cancellationReason != null && !cancellationReason.isEmpty()) {
                    cancellationOrDisapprovalProcessor(rd, cancellationReason, hibernateSession, cancellationReasonQuery);
                }

                if (disapprovalMessage != null && !disapprovalMessage.isEmpty()) {
                    cancellationOrDisapprovalProcessor(rd, disapprovalMessage, hibernateSession, disapprovalReasonQuery);
                }
            }

            String updateItemQuantityQuery = "UPDATE item SET quantity = (quantity - :newQuantity) WHERE item_id = :itemId";
            if (requestDetailsStatus == RequestDetailsStatus.RELEASED) {
                for (RequestDetails od : entities) {
                    if (od.getRequestDetailsStatus() == RequestDetailsStatus.RELEASED) {
                        NativeQuery<Item> nativeQuery = hibernateSession.createNativeQuery(updateItemQuantityQuery, Item.class);
                        nativeQuery.setParameter("newQuantity", od.getRequestQuantity());
                        nativeQuery.setParameter("itemId", od.getRequestDetailsID().getItemId());
                        nativeQuery.executeUpdate();
                    }
                }
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

    private void cancellationOrDisapprovalProcessor(RequestDetails rd, String reason, Session hibernateSession, String query) {
        NativeQuery<RequestDetails> updateCancellationReason = hibernateSession.createNativeQuery(query, RequestDetails.class);
        updateCancellationReason.setParameter("requestId", rd.getRequestDetailsID().getRequestId());
        updateCancellationReason.setParameter("itemId", rd.getRequestDetailsID().getItemId());
        updateCancellationReason.setParameter("reason", reason);
        updateCancellationReason.executeUpdate();
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

        new RequestDetailsRepositoryImpl().updateRequestStatus(0L, null, requestDetailsList);
    }

    private List<StatusUpdateBean> makeStatusUpdateBeanList(RequestDetails[] entities, Long userId, RequestDetailsStatus requestDetailsStatus) {

        List<StatusUpdateBean> statusUpdateBeanList = new ArrayList<>();
        String stitch = "";
        switch (requestDetailsStatus) {
            case APPROVED:
            case DISAPPROVED:
                stitch = ", considered_by_user_id = :userId";
                break;
            case RELEASED:
                stitch = ", issued_by_user_id = :userId";
                break;
        }

        final String strFormat = "UPDATE request_details SET request_details_status = :requestDetailsState %s "
                .concat("WHERE (request_request_id, item_item_id) IN (");

        final String baseQuery = String.format(strFormat, stitch);
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
                parameterMap.put("userId", userId);

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
