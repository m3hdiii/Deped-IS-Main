package com.deped.service.places;


import com.deped.model.location.office.Section;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SectionService extends BaseService<Section> {

    ResponseEntity<List<Section>> fetchAllByDepartment(Long departmentId);
}
