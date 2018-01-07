package com.deped.repository.request;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.account.User;
import com.deped.model.items.Item;
import com.deped.model.items.ItemType;
import com.deped.model.request.Request;
import com.deped.model.request.RequestDetailsStatus;
import com.deped.model.request.RequestStatus;
import com.deped.model.search.RequestSearch;
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
public class RequestRepositoryImpl implements RequestRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Request create(Request entity) throws DatabaseRolesViolationException {
        if (entity.getRequestStatus() == null)
            entity.setRequestStatus(RequestStatus.SAVED);

        Request request = hibernateFacade.saveEntity(Request.class, entity);
        return request;
    }

    @Override
    public Boolean update(Request entity) throws DatabaseRolesViolationException {
        return hibernateFacade.updateEntity(entity);
    }


    @Override
    public List<Request> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_REQUESTS, Request.class);
    }

    @Override
    public List<Request> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_REQUESTS, range, Request.class);
    }

    @Override
    public Request fetchById(Long id) {
        return hibernateFacade.fetchEntityById(Request.class, id);
    }

    @Override
    public Boolean remove(Request... entities) throws DatabaseRolesViolationException {
        return hibernateFacade.removeEntities(REQUEST_TABLE, REQUEST_TABLE_ID, entities);
    }

    @Override
    public Boolean createOrUpdateAll(Request... entities) throws DatabaseRolesViolationException {
        return null;
    }

    @Override
    public List<Request> fetchAllByRequestStatus(RequestStatus requestStatus) {
        String nativeQuery = "SELECT * FROM request WHERE request_status = :requestStatus AND item_type <> 'EQUIPMENT'";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("requestStatus", requestStatus.toString());
        List<Request> requests = hibernateFacade.fetchAllEntityBySqlQuery(nativeQuery, null, Request.class, parameterMap);
        return requests;
    }

    @Override
    public List<Request> fetchAllByUsername(String username) {
        String nativeQuery = "SELECT * FROM request WHERE username = :username ORDER BY request_status, request_date DESC";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("username", username);
        List<Request> requests = hibernateFacade.fetchAllEntityBySqlQuery(nativeQuery, null, Request.class, parameterMap);
        return requests;
    }

    @Override
    public List<Request> requestSearch(RequestSearch requestSearch) {

        if (requestSearch == null)
            return null;

        boolean isUserMessageEmpty = isEmpty(requestSearch.getUserMessage());
        boolean isRequestDateFromEmpty = isEmpty(requestSearch.getRequestDateFrom());
        boolean isRequestDateToEmpty = isEmpty(requestSearch.getRequestDateTo());
        boolean isRequestedUserListEmpty = isEmpty(requestSearch.getRequestedUsers());
        boolean isAdminNoticeEmpty = isEmpty(requestSearch.getAdminNotice());
        boolean isItemTypeListEmpty = isEmpty(requestSearch.getItemTypes());
        boolean isItemListEmpty = isEmpty(requestSearch.getItems());
        boolean isRequestQuantityFromEmpty = isEmpty(requestSearch.getRequestQuantityFrom());
        boolean isRequestQuantityToEmpty = isEmpty(requestSearch.getRequestQuantityTo());
        boolean isApprovedQuantityFromEmpty = isEmpty(requestSearch.getApprovedQuantityFrom());
        boolean isApprovedQuantityToEmpty = isEmpty(requestSearch.getApprovedQuantityTo());
        boolean isApprovalDisapprovalDateFromEmpty = isEmpty(requestSearch.getApprovalDisapprovalDateFrom());
        boolean isApprovalDisapprovalDateToEmpty = isEmpty(requestSearch.getApprovalDisapprovalDateTo());
        boolean isDisapprovalMessageEmpty = isEmpty(requestSearch.getDisapprovalMessage());
        boolean isReleaseDateFromEmpty = isEmpty(requestSearch.getReleaseDateFrom());
        boolean isReleaseDateToEmpty = isEmpty(requestSearch.getReleaseDateTo());
        boolean isRequestDetailsStatusListEmpty = isEmpty(requestSearch.getRequestDetailsStatuses());
        boolean isRequestStatusListEmpty = isEmpty(requestSearch.getRequestStatuses());
        boolean isCancellationReasonEmpty = isEmpty(requestSearch.getCancellationReason());
        boolean isSupplyOfficeRemarkEmpty = isEmpty(requestSearch.getSupplyOfficeRemark());
        boolean isConsideredByUserListEmpty = isEmpty(requestSearch.getConsideredByUsers());
        boolean isIssuedByUserListEmpty = isEmpty(requestSearch.getIssuedByUsers());

        boolean[] emptyList = new boolean[]{isUserMessageEmpty, isRequestDateFromEmpty, isRequestDateToEmpty,
                isRequestedUserListEmpty, isAdminNoticeEmpty, isItemTypeListEmpty, isItemListEmpty, isRequestQuantityFromEmpty,
                isRequestQuantityToEmpty, isApprovedQuantityFromEmpty, isApprovedQuantityToEmpty, isApprovalDisapprovalDateFromEmpty,
                isApprovalDisapprovalDateToEmpty, isDisapprovalMessageEmpty, isReleaseDateFromEmpty, isReleaseDateToEmpty, isRequestDetailsStatusListEmpty,
                isRequestStatusListEmpty, isCancellationReasonEmpty, isSupplyOfficeRemarkEmpty, isConsideredByUserListEmpty, isIssuedByUserListEmpty
        };

        StringBuilder sb = new StringBuilder("SELECT * FROM request");

        String where = null;
        for (boolean b : emptyList) {
            if (!b) {
                where = " JOIN request_details ON request.request_id = request_details.request_request_id WHERE\n";
                break;
            }
        }


        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;
        List<Request> list = null;

        if (where == null) {

            try {
                tx = hibernateSession.beginTransaction();
                NativeQuery<Request> query = hibernateSession.createNativeQuery(sb.toString(), Request.class);
                list = query.list();
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
                return list;
            }


            return list;

        } else {
            ListParameter requestedUserParameter = isRequestedUserListEmpty ? null : createListParameter(requestSearch.getRequestedUsers(), "requestedUser", User.class);
            ListParameter itemTypeParameter = isItemTypeListEmpty ? null : createListParameter(requestSearch.getItemTypes(), "itemType", ItemType.class);
            ListParameter itemParameter = isItemListEmpty ? null : createListParameter(requestSearch.getItems(), "item", Item.class);
            ListParameter requestStatusParameter = isRequestStatusListEmpty ? null : createListParameter(requestSearch.getRequestStatuses(), "requestStatus", RequestDetailsStatus.class);
            ListParameter requestDetailsStatusParameter = isRequestDetailsStatusListEmpty ? null : createListParameter(requestSearch.getRequestDetailsStatuses(), "requestDetailsStatus", RequestDetailsStatus.class);
            ListParameter consideredByUserParameter = isConsideredByUserListEmpty ? null : createListParameter(requestSearch.getConsideredByUsers(), "consideredByUsers", User.class);
            ListParameter issuedByUserParameter = isIssuedByUserListEmpty ? null : createListParameter(requestSearch.getIssuedByUsers(), "issuedByUsers", User.class);


            sb
                    .append(isUserMessageEmpty ? "" : "(request.user_message LIKE :userMessage) AND\n")
                    .append(isRequestDateFromEmpty ? "" : "(request.request_date >= :requestDateFrom) AND\n")
                    .append(isRequestDateToEmpty ? "" : "(request.request_date < :requestDateTo) AND\n")
                    .append(isRequestedUserListEmpty ? "" : String.format("(request.username IN ( %s )) AND\n", requestedUserParameter.getWherePartSection()))
                    .append(isAdminNoticeEmpty ? "" : "(request.admin_notice LIKE :adminNotice) AND\n")
                    .append(isItemTypeListEmpty ? "" : String.format("(request.item_type IN ( %s )) AND\n", itemTypeParameter.getWherePartSection()))
                    .append(isItemListEmpty ? "" : String.format("(request_details.item_item_name IN ( %s )) AND\n", itemParameter.getWherePartSection()))
                    .append(isRequestQuantityFromEmpty ? "" : "(request_details.request_quantity >= :requestQuantityFrom) AND\n")
                    .append(isRequestQuantityToEmpty ? "" : "(request_details.request_quantity < :requestQuantityTo) AND\n")
                    .append(isApprovedQuantityFromEmpty ? "" : "(request_details.approved_quantity >= :approvedQuantityFrom) AND\n")
                    .append(isApprovedQuantityToEmpty ? "" : "(request_details.approved_quantity < :approvedQuantityTo) AND\n")
                    .append(isApprovalDisapprovalDateFromEmpty ? "" : "(request_details.approval_disapproval_date >= :approvalDisapprovalDateFrom) AND\n")
                    .append(isApprovalDisapprovalDateToEmpty ? "" : "(request_details.approval_disapproval_date < :approvalDisapprovalDateTo) AND\n")
                    .append(isDisapprovalMessageEmpty ? "" : "(request_details.disapproval_message LIKE :disapprovalMessage) AND\n")
                    .append(isReleaseDateFromEmpty ? "" : "(request_details.release_date >= :releaseDateFrom) AND\n")
                    .append(isReleaseDateToEmpty ? "" : "(request_details.release_date < :releaseDateTo) AND\n")
                    .append(isRequestDetailsStatusListEmpty ? "" : String.format("(request_details.request_details_status IN ( %s )) AND\n", requestDetailsStatusParameter.getWherePartSection()))
                    .append(isRequestStatusListEmpty ? "" : String.format("(request.request_status IN ( %s )) AND\n", requestStatusParameter.getWherePartSection()))
                    .append(isCancellationReasonEmpty ? "" : "(request_details.cancellation_reason LIKE :cancellationReason) AND\n")
                    .append(isSupplyOfficeRemarkEmpty ? "" : "(request_details.supply_office_remark LIKE :supplyOfficeRemark) AND\n")
                    .append(isConsideredByUserListEmpty ? "" : String.format("(request_details.considered_by_username IN ( %s )) AND\n", consideredByUserParameter.getWherePartSection()))
                    .append(isIssuedByUserListEmpty ? "" : String.format("(request_details.issued_by_username IN ( %s ))", issuedByUserParameter.getWherePartSection()));


            try {
                tx = hibernateSession.beginTransaction();
                NativeQuery<Request> query = hibernateSession.createNativeQuery(sb.toString(), Request.class);

                ListParameter[] listParameters = new ListParameter[]{requestedUserParameter, itemTypeParameter, itemParameter, requestStatusParameter, requestDetailsStatusParameter, consideredByUserParameter, issuedByUserParameter};

                Map<String, Object> parameterMap = new HashMap<>();
                for (int i = 0; i < listParameters.length; i++) {
                    ListParameter lpTemp = listParameters[i];
                    if (lpTemp != null) {
                        parameterMap.putAll(lpTemp.getParameterMap());
                    }
                }

                if (!isUserMessageEmpty) {
                    parameterMap.put("userMessage", "%" + requestSearch.getUserMessage() + "%");
                }

                if (!isRequestDateFromEmpty) {
                    parameterMap.put("requestDateFrom", requestSearch.getRequestDateFrom());
                }

                if (!isRequestDateToEmpty) {
                    parameterMap.put("requestDateTo", requestSearch.getRequestDateFrom());
                }

                if (!isAdminNoticeEmpty) {
                    parameterMap.put("adminNotice", "%" + requestSearch.getAdminNotice() + "%");
                }

                if (!isRequestQuantityFromEmpty) {
                    parameterMap.put("requestQuantityFrom", requestSearch.getRequestQuantityFrom());
                }

                if (!isRequestQuantityToEmpty) {
                    parameterMap.put("requestQuantityTo", requestSearch.getRequestQuantityTo());
                }

                if (!isApprovedQuantityFromEmpty) {
                    parameterMap.put("approvedQuantityFrom", requestSearch.getApprovedQuantityFrom());
                }

                if (!isApprovedQuantityToEmpty) {
                    parameterMap.put("approvedQuantityTo", requestSearch.getApprovedQuantityTo());
                }

                if (!isApprovalDisapprovalDateFromEmpty) {
                    parameterMap.put("approvalDisapprovalDateFrom", requestSearch.getApprovalDisapprovalDateFrom());
                }

                if (!isApprovalDisapprovalDateToEmpty) {
                    parameterMap.put("approvalDisapprovalDateTo", requestSearch.getApprovalDisapprovalDateTo());
                }

                if (!isDisapprovalMessageEmpty) {
                    parameterMap.put("disapprovalMessage", "%" + requestSearch.getDisapprovalMessage() + "%");
                }

                if (!isReleaseDateFromEmpty) {
                    parameterMap.put("releaseDateFrom", requestSearch.getReleaseDateFrom());
                }

                if (!isReleaseDateToEmpty) {
                    parameterMap.put("releaseDateTo", requestSearch.getReleaseDateTo());
                }

                if (!isCancellationReasonEmpty) {
                    parameterMap.put("cancellationReason", "%" + requestSearch.getCancellationReason() + "%");
                }

                if (!isSupplyOfficeRemarkEmpty) {
                    parameterMap.put("supplyOfficeRemark", "%" + requestSearch.getSupplyOfficeRemark() + "%");
                }

                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }

                list = query.list();
                tx.commit();

            } catch (Exception e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
                return null;
            }


            return list;
        }

    }

    @Override
    public List<Request> fetchAllConsideredEquipment() {
        String nativeQuery = "SELECT * FROM request WHERE item_type = 'EQUIPMENT' AND request_status = 'CONSIDERED'";
        List<Request> requests = hibernateFacade.fetchAllEntityBySqlQuery(nativeQuery, null, Request.class, null);
        return requests;
    }

    private ListParameter createListParameter(List objects, String baseParamKey, Class<?> clazz) {
        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Map<String, Object> parameterMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < objects.size(); i++) {
            String key = baseParamKey + i;
            if (clazz == User.class)
                parameterMap.put(key, ((User) objects.get(i)).getUsername());

            if (clazz == Item.class)
                parameterMap.put(key, ((Item) objects.get(i)).getName());

            if (clazz == ItemType.class || clazz == RequestDetailsStatus.class || clazz == RequestStatus.class || clazz == RequestStatus.class)
                parameterMap.put(key, (objects.get(i)).toString());

            sb.append(":" + key + " , ");
        }

        String tmp = sb.toString();
        String wherePartSection = tmp.substring(0, (tmp.lastIndexOf(",") - 1));

        ListParameter listParameter = new ListParameter(wherePartSection, parameterMap);
        return listParameter;
    }

    private boolean isEmpty(Object object) {
        if (object == null)
            return true;

        if (object instanceof String) {
            String s = (String) object;
            if (s.isEmpty()) {
                return true;
            }
            return false;
        }

        if (object instanceof List) {
            List s = (List) object;
            if (s.isEmpty()) {
                return true;
            }
            return false;
        }

        return true;
    }


    public static void main(String[] args) {
        User u1 = new User();
        u1.setUsername("admin");
        User u2 = new User();
        u2.setUsername("mehdi");

        List<User> users = new ArrayList<User>() {{
            add(u1);
            add(u2);
        }};

        List<ItemType> itemTypes = new ArrayList<ItemType>() {{
            add(ItemType.EQUIPMENT);
            add(ItemType.SEMI_EXPENDABLE);
        }};

        Item i1 = new Item();
        i1.setName("");
        Item i2 = new Item();
        i2.setName("");
        List<Item> items = new ArrayList<Item>() {{
            add(i1);
            add(i2);
        }};

        List<RequestStatus> rss = new ArrayList<RequestStatus>() {{
            add(RequestStatus.CONSIDERED);
            add(RequestStatus.FINALIZED);
        }};

        List<RequestDetailsStatus> rdss = new ArrayList<RequestDetailsStatus>() {{
            add(RequestDetailsStatus.RELEASED);
            add(RequestDetailsStatus.DISAPPROVED);
        }};

        RequestSearch rs = new RequestSearch("Salam", new Date(), new Date(), users, "sss", itemTypes, items, 10, 100, 8, 26, new Date(), new Date(), "sss", new Date()
                , new Date(), rss, rdss, "cancellation", "office remarks", users, users, null
        );
        new RequestRepositoryImpl().requestSearch(rs);
    }

    private class ListParameter {
        String wherePartSection;
        Map<String, Object> parameterMap;

        public ListParameter(String wherePartSection, Map<String, Object> parameterMap) {
            this.wherePartSection = wherePartSection;
            this.parameterMap = parameterMap;
        }

        public String getWherePartSection() {
            return wherePartSection;
        }

        public void setWherePartSection(String wherePartSection) {
            this.wherePartSection = wherePartSection;
        }

        public Map<String, Object> getParameterMap() {
            return parameterMap;
        }

        public void setParameterMap(Map<String, Object> parameterMap) {
            this.parameterMap = parameterMap;
        }
    }
}
