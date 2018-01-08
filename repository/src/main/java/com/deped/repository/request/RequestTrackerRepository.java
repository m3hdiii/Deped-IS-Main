package com.deped.repository.request;

import com.deped.model.request.RequestDetails;
import com.deped.model.request.RequestDetailsStatus;
import com.deped.model.tracker.RequestTracker;
import com.deped.repository.BaseRepository;

public interface RequestTrackerRepository extends BaseRepository<RequestTracker, Long> {
    Boolean updateRequestStatus(String username, RequestDetailsStatus status, RequestDetails[] entities);
}
