package com.deped.service.request;

import com.deped.model.request.Request;
import com.deped.model.request.RequestStatus;
import com.deped.model.search.RequestSearch;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RequestService extends BaseService<Request, Long> {
    ResponseEntity<List<Request>> fetchAllByRequestStatus(RequestStatus requestStatus);

    ResponseEntity<List<Request>> fetchAllConsideredEquipment();

    ResponseEntity<List<Request>> fetchAllByUsername(String username);

    ResponseEntity<List<Request>> requestSearch(RequestSearch requestSearch);
}
