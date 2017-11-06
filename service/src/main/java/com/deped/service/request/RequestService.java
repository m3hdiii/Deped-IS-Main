package com.deped.service.request;

import com.deped.model.request.Request;
import com.deped.model.request.RequestStatus;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RequestService extends BaseService<Request> {
    ResponseEntity<List<Request>> fetchAllByRequestStatus(RequestStatus requestStatus);

    ResponseEntity<List<Request>> fetchAllByUserId(Long userId);
}
