package com.deped.service.places;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.location.office.Section;
import com.deped.repository.places.SectionRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Override
    public ResponseEntity<Section> create(Section entity) {
        Section savedEntity = null;
        try {
            savedEntity = sectionRepository.create(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        if (savedEntity == null) {
            ResponseEntity<Section> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return responseEntity;
        }

        ResponseEntity<Section> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Section entity) {
        Boolean isUpdated = null;
        try {
            isUpdated = sectionRepository.update(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Section.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Section>> fetchAll() {
        List<Section> sections = sectionRepository.fetchAll();
        ResponseEntity<List<Section>> responseEntity = new ResponseEntity<>(sections, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Section>> fetchAllByDepartment(String departmentId) {
        List<Section> sections = sectionRepository.fetchAllByDepartment(departmentId);
        ResponseEntity<List<Section>> responseEntity = new ResponseEntity<>(sections, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Section>> fetchByRange(Range range) {
        List<Section> sections = sectionRepository.fetchByRange(range);
        ResponseEntity<List<Section>> responseEntity = new ResponseEntity<>(sections, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Section> fetchById(String id) {
        Section section = sectionRepository.fetchById(id);
        ResponseEntity<Section> responseEntity = new ResponseEntity<>(section, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Section... entities) {
        Boolean isRemoved = null;
        try {
            isRemoved = sectionRepository.remove(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Section.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Section... entities) {
        return null;
    }
}
