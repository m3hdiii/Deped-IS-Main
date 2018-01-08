package com.deped.repository.request;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.features.EquipmentAvailability;
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

import java.util.List;

@Repository
public class RequestTrackerRepositoryImpl implements RequestTrackerRepository {


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
    public Boolean updateRequestStatus(String username, RequestDetailsStatus requestDetailsStatus, RequestTracker[] entities) {

        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;

        try {
            tx = hibernateSession.beginTransaction();
            for (int i = 0; i < entities.length; i++) {
                hibernateSession.save("RequestTracker", entities[i]);

                //insert requestTracker
                String updateItemDetails = "UPDATE item_details SET equipment_availability = :availability WHERE office_serial_number = :srNo";
                NativeQuery<ItemDetails> updateItemDetailsUpdateQuery = hibernateSession.createNativeQuery(updateItemDetails, ItemDetails.class);
                updateItemDetailsUpdateQuery.setParameter("srNo", entities[i].getItemDetails().getOfficeSerialNo());
                updateItemDetailsUpdateQuery.setParameter("availability", EquipmentAvailability.HELD.toString());

                RequestDetails rd = entities[i].getRequestDetails();
                String requestDetails = "UPDATE request_details set request_details_status = :requestDetailsStatus, issued_by_username = :username WHERE request_request_id = :requestId AND item_item_name = :itemId";
                NativeQuery<RequestDetails> requestDetailsUpdateQuery = hibernateSession.createNativeQuery(requestDetails, RequestDetails.class);
                requestDetailsUpdateQuery.setParameter("requestDetailsStatus", RequestDetailsStatus.RELEASED.toString());
                requestDetailsUpdateQuery.setParameter("requestId", rd.getRequest().getRequestId());
                requestDetailsUpdateQuery.setParameter("itemId", rd.getRequestDetailsID().getItemName());//FIXME
                requestDetailsUpdateQuery.setParameter("username", username);

                updateItemDetailsUpdateQuery.executeUpdate();
                requestDetailsUpdateQuery.executeUpdate();
            }


            String request = "UPDATE request set request_status = :requestStatus WHERE request_id = :requestId";
            NativeQuery<Request> requestUpdateQuery = hibernateSession.createNativeQuery(request, Request.class);
            requestUpdateQuery.setParameter("requestStatus", RequestStatus.FINALIZED.toString());
            requestUpdateQuery.setParameter("requestId", entities[0].getRequestDetails().getRequest().getRequestId());
            requestUpdateQuery.executeUpdate();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();

            return false;
        }

        return true;
    }

    public static void main(String[] args) {

    }

}
