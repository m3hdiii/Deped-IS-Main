package com.deped.repository.places;

import com.deped.model.location.office.Section;

import static com.deped.repository.utils.ConstantValues.*;

import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SectionRepositoryImpl implements SectionRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Section create(Section entity) {
        return hibernateFacade.saveEntity(Section.class, entity);
    }

    @Override
    public Boolean update(Section entity) {
        return hibernateFacade.updateEntity(entity);
    }

    @Override
    public List<Section> fetchAll() {
        List<Section> sections = hibernateFacade.fetchAllEntity(FETCH_ALL_SECTION, Section.class);
        return sections;
    }

    @Override
    public List<Section> fetchByRange(Range range) {
        List<Section> sections = hibernateFacade.fetchAllEntity(FETCH_ALL_SECTION_RANGE, range, Section.class);
        return sections;
    }

    @Override
    public Section fetchById(Object id) {
        Section section


                = hibernateFacade.fetchEntityById(Section.class, id);
        return section;
    }

    @Override
    public Boolean remove(Section... entities) {
        Boolean isDeleted = hibernateFacade.removeEntities(SECTION_TABLE, SECTION_TABLE_ID, entities);
        return isDeleted;
    }
}
