package com.deped.repository.request;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.items.Item;
import com.deped.model.request.Request;
import com.deped.model.request.RequestDetails;
import com.deped.model.request.RequestDetailsStatus;
import com.deped.model.request.RequestStatus;
import com.deped.model.tracker.RequestTracker;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RequestTrackerRepositoryImpl implements RequestTrackerRepository {


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

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public RequestTracker create(RequestTracker entity) throws DatabaseRolesViolationException {
        return null;
    }

    @Override
    public Boolean update(RequestTracker entity) throws DatabaseRolesViolationException {
        return null;
    }

    @Override
    public List<RequestTracker> fetchAll() {
        return null;
    }

    @Override
    public List<RequestTracker> fetchByRange(Range range) {
        return null;
    }

    @Override
    public RequestTracker fetchById(Long aLong) {
        return null;
    }

    @Override
    public Boolean remove(RequestTracker... entities) throws DatabaseRolesViolationException {
        return null;
    }

    @Override
    public Boolean createOrUpdateAll(RequestTracker... entities) throws DatabaseRolesViolationException {
        Boolean isDone = hibernateFacade.createOrUpdateAll(RequestTracker.class, entities);
        return isDone;
    }

    @Override
    public Boolean updateRequestStatus(String username, RequestDetailsStatus requestDetailsStatus, RequestDetails[] entities) {
        List<RequestTrackerRepositoryImpl.StatusUpdateBean> statusUpdateBeanList = makeStatusUpdateBeanList(entities, username, requestDetailsStatus);
        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().getCurrentSession();
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


            for (RequestTrackerRepositoryImpl.StatusUpdateBean sub : statusUpdateBeanList) {
                NativeQuery<RequestDetails> nativeQuery = hibernateSession.createNativeQuery(sub.getQuery(), RequestDetails.class);
                for (Map.Entry<String, Object> entry : sub.getParameterMap().entrySet()) {
                    nativeQuery.setParameter(entry.getKey(), entry.getValue());
                }
                effectedRows = nativeQuery.executeUpdate();
                if (effectedRows <= 0) {
                    throw new Exception("Sql update operation encountered a problem");
                }
            }


            String cancellationReasonQuery = "UPDATE request_details SET cancellation_reason = :reason WHERE request_request_id = :requestId AND item_item_name = :itemName";
            String disapprovalReasonQuery = "UPDATE request_details SET disapproval_message = :reason WHERE request_request_id = :requestId AND item_item_name = :itemName";
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

            String updateItemQuantityQuery = "UPDATE item SET quantity = (quantity - :newQuantity) WHERE item_name = :itemName";
            if (requestDetailsStatus == RequestDetailsStatus.RELEASED) {
                for (RequestDetails od : entities) {
                    if (od.getRequestDetailsStatus() == RequestDetailsStatus.RELEASED) {
                        NativeQuery<Item> nativeQuery = hibernateSession.createNativeQuery(updateItemQuantityQuery, Item.class);
                        nativeQuery.setParameter("newQuantity", od.getRequestQuantity());
                        nativeQuery.setParameter("itemName", od.getRequestDetailsID().getItemName());
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
        }

        return true;
    }

    private void cancellationOrDisapprovalProcessor(RequestDetails rd, String reason, Session hibernateSession, String query) {
        NativeQuery<RequestDetails> updateCancellationReason = hibernateSession.createNativeQuery(query, RequestDetails.class);
        updateCancellationReason.setParameter("requestId", rd.getRequestDetailsID().getRequestId());
        updateCancellationReason.setParameter("itemName", rd.getRequestDetailsID().getItemName());
        updateCancellationReason.setParameter("reason", reason);
        updateCancellationReason.executeUpdate();
    }

    private List<RequestTrackerRepositoryImpl.StatusUpdateBean> makeStatusUpdateBeanList(RequestDetails[] entities, String username, RequestDetailsStatus requestDetailsStatus) {

        List<RequestTrackerRepositoryImpl.StatusUpdateBean> statusUpdateBeanList = new ArrayList<>();
        String stitch = "";
        switch (requestDetailsStatus) {
            case APPROVED:
            case DISAPPROVED:
                stitch = ", considered_by_username = :username";
                break;
            case RELEASED:
                stitch = ", issued_by_username = :username";
                break;
        }

        final String strFormat = "UPDATE request_details SET request_details_status = :requestDetailsState %s "
                .concat("WHERE (request_request_id, item_item_name) IN (");

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
        final String itemIdMapKeyFormat = "itemName%d";
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
                parameterMap.put("username", username);

                String query = sb.toString();
                query = query.substring(0, query.lastIndexOf(",")).concat(")");
                statusUpdateBeanList.add(new RequestTrackerRepositoryImpl.StatusUpdateBean(query, parameterMap));
                parameterMap = new HashMap<>();
                sb.setLength(0);
                sb.append(baseQuery);
                firstElementState = nextIndexStatus;
            }
        }

        return statusUpdateBeanList;
    }

    private void setMap(Map<String, Object> parameterMap, RequestDetails temp, String requestIdMapKey, String itemIdMapKey) {
        parameterMap.put(requestIdMapKey, temp.getRequestDetailsID().getRequestId());
        parameterMap.put(itemIdMapKey, temp.getRequestDetailsID().getItemName());
    }
}
