package com.deped.repository;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.repository.utils.Range;

import java.util.List;

public interface BaseRepository<T, ID> {

    T create(T entity) throws DatabaseRolesViolationException;

    Boolean update(T entity) throws DatabaseRolesViolationException;

    List<T> fetchAll();

    List<T> fetchByRange(Range range);

    T fetchById(ID id);

    Boolean remove(T... entities) throws DatabaseRolesViolationException;

    Boolean createOrUpdateAll(T... entities) throws DatabaseRolesViolationException;
}
