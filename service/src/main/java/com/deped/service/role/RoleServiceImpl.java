package com.deped.service.role;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.security.Role;
import com.deped.repository.role.RoleRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity<Role> create(Role entity) {
        Role savedEntity = null;
        try {
            savedEntity = roleRepository.create(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        ResponseEntity<Role> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Role entity) {
        Boolean isUpdated = null;
        try {
            isUpdated = roleRepository.update(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Role.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Role>> fetchAll() {
        List<Role> roles = roleRepository.fetchAll();
        ResponseEntity<List<Role>> responseEntity = new ResponseEntity<>(roles, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Role>> fetchByRange(Range range) {
        List<Role> roles = roleRepository.fetchByRange(range);
        ResponseEntity<List<Role>> responseEntity = new ResponseEntity<>(roles, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Role> fetchById(String id) {
        Role role= roleRepository.fetchById(id);
        ResponseEntity<Role> responseEntity = new ResponseEntity<>(role, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Role... entities) {
        Boolean isRemoved = null;
        try {
            isRemoved = roleRepository.remove(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Role.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Role... entities) {
        return null;
    }
}
