package com.deped.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

public interface MainController<T, ID> extends ConstantController {

    ModelAndView renderCreatePage(T entity);

    ModelAndView createAction(T entity, BindingResult bindingResult);

    ModelAndView renderInfo(ID id);

    ModelAndView renderUpdatePage(ID id);

    ModelAndView updateAction(ID id, T entity, BindingResult bindingResult);

    ModelAndView renderListPage();

    ModelAndView renderListPage(int from, int to);

    ModelAndView removeAction(T... entity);

}
