package com.deped.repository.request;

import com.deped.model.request.RequestDetails;
import com.deped.repository.BaseRepository;

import java.util.List;

public interface RequestDetailsRepository extends BaseRepository<RequestDetails> {
    Boolean requestAll(RequestDetails... entities);

    boolean updateRequestStatus(RequestDetails[] entities);

    List<RequestDetails> fetchAllById(Long requestId);
}
