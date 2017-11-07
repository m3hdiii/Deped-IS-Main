package com.deped.service.request;

import com.deped.model.Response;
import com.deped.model.request.RequestDetails;
import com.deped.model.request.RequestDetailsStatus;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RequestDetailsService extends BaseService<RequestDetails> {
    ResponseEntity<Response> requestAll(RequestDetails... entities);

    ResponseEntity<Response> updateRequestStatus(Long userId, RequestDetailsStatus requestDetailsStatus, RequestDetails[] entities);

    ResponseEntity<List<RequestDetails>> fetchAllById(Long requestId);
}
