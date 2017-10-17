package com.deped.controller;

import com.deped.model.Response;
import org.springframework.ui.Model;

import java.util.List;

public interface MainRestController<T, ID> extends ConstantController {

    T create(T entity);

    Response update(T entity);

    List<T> fetchAll();

    List<T> fetchByRange(int from, int to);

    T fetchById(ID id);

    Response remove(T... entities);

    String showCreatePage(T entity, Model model);

    String showListPage(Model model);

    String showUpdatePage();
}
