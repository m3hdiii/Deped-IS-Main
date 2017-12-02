package com.deped.repository.request;

import com.deped.model.request.Request;
import com.deped.model.request.RequestStatus;
import com.deped.repository.BaseRepository;

import java.util.List;

public interface RequestRepository extends BaseRepository<Request, Long> {
    List<Request> fetchAllByRequestStatus(RequestStatus requestStatus);

    List<Request> fetchAllByUsername(String username);
}
