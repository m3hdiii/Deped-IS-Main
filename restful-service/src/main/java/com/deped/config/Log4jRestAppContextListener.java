package com.deped.config;

import com.deped.log.Log4jBootstrap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Log4jRestAppContextListener implements ServletContextListener {

    private static final String LOG4J_PATH_ENV = "rootPath";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        System.setProperty(LOG4J_PATH_ENV, context.getRealPath("/"));
        String contextName = context.getServletContextName();
        Log4jBootstrap.loader(contextName);
        System.out.println();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}