package com.deped.service.request;

import com.deped.model.Response;
import com.deped.model.request.RequestDetails;
import com.deped.model.request.RequestDetailsStatus;
import com.deped.model.tracker.RequestTracker;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

public interface RequestTrackerService extends BaseService<RequestTracker, Long> {
    ResponseEntity<Response> updateRequestStatus(String username, RequestDetailsStatus status, RequestDetails[] entities);
}
