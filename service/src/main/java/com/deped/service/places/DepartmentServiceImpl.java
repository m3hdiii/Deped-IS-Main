package com.deped.service.places;

import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.location.office.Department;
import com.deped.repository.places.DepartmentRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;


    @Override
    public ResponseEntity<Department> create(Department entity) {
        Department savedEntity = departmentRepository.create(entity);
        ResponseEntity<Department> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Department entity) {
        Boolean isUpdated = departmentRepository.update(entity);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Department.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Department>> fetchAll() {
        List<Department> departments = departmentRepository.fetchAll();
        ResponseEntity<List<Department>> responseEntity = new ResponseEntity<>(departments, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Department>> fetchByRange(Range range) {
        List<Department> departments = departmentRepository.fetchByRange(range);
        ResponseEntity<List<Department>> responseEntity = new ResponseEntity<>(departments, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Department> fetchById(Object id) {
        Department department = departmentRepository.fetchById(id);
        ResponseEntity<Department> responseEntity = new ResponseEntity<>(department, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Department... entities) {
        Boolean isRemoved = departmentRepository.remove(entities);
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Department.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }
}
