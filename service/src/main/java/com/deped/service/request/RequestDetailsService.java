package com.deped.service.request;

import com.deped.model.Response;
import com.deped.model.request.RequestDetails;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

public interface RequestDetailsService extends BaseService<RequestDetails> {
    ResponseEntity<Response> requestAll(RequestDetails... entities);

    ResponseEntity<Response> updateRequestStatus(RequestDetails[] entities);
}
