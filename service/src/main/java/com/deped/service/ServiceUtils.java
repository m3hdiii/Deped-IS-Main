package com.deped.service;

import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.ResponseStatus;

public class ServiceUtils {
    private final static String CREATE_SUCCESSFUL_MESSAGE = "%s successfully created";
    private final static String CREATE_FAILURE_MESSAGE = "%s failed to creat";

    private final static String UPDATE_SUCCESSFUL_MESSAGE = "%s successfully updated";
    private final static String UPDATE_FAILURE_MESSAGE = "%s failed to update";

    private final static String DELETE_SUCCESSFUL_MESSAGE = "%s/s successfully removed";
    private final static String DELETE_FAILURE_MESSAGE = "%s/s failed to removed";

    public final static Response makeResponse(Boolean isSuccessful, Operation operation, Class<?> entity) {
        String successMessage = "", failureMessage = "";
        switch (operation) {
            case CREATE:
                successMessage = String.format(CREATE_SUCCESSFUL_MESSAGE, entity.getSimpleName());
                failureMessage = String.format(CREATE_FAILURE_MESSAGE, entity.getSimpleName());
                break;
            case DELETE:
                successMessage = String.format(DELETE_SUCCESSFUL_MESSAGE, entity.getSimpleName());
                failureMessage = String.format(DELETE_FAILURE_MESSAGE, entity.getSimpleName());
                break;
            case UPDATE:
                successMessage = String.format(UPDATE_SUCCESSFUL_MESSAGE, entity.getSimpleName());
                failureMessage = String.format(UPDATE_FAILURE_MESSAGE, entity.getSimpleName());
                break;
        }

        if (isSuccessful) {
            return new Response(ResponseStatus.SUCCESSFUL, successMessage);
        } else {
            return new Response(ResponseStatus.FAILED, failureMessage);
        }
    }
}
