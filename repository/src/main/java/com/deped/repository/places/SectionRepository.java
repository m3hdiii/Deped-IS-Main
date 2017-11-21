package com.deped.repository.places;


import com.deped.model.location.office.Section;
import com.deped.repository.BaseRepository;

import java.util.List;

public interface SectionRepository extends BaseRepository<Section> {

    List<Section> fetchAllByDepartment(Long departmentId);

}
