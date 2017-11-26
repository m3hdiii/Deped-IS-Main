package com.deped.exception;

public class ResponseErrorMessages {
    private String createDuplicateMessage = "%s with the same % exist. Please choose another";
    private String updateConstraintViolationMessage = "%s with the same % exist. Please choose another";
    private String deleteConstraintViolationMessage = "%s with the same % exist. Please choose another";


    private static String startingMessage;
    private static String constraintStr;

    private ResponseErrorMessages() {
    }

    public static ResponseErrorMessages getInstance(Class clazz, String[] constraints) {
        String entityName = clazz.getSimpleName();

        switch (entityName.charAt(0)) {
            case 'a':
            case 'A':
            case 'e':
            case 'E':
            case 'i':
            case 'I':
            case 'o':
            case 'O':
            case 'u':
            case 'U':
                startingMessage = "An";

            default:
                startingMessage = "A";
        }

        StringBuilder sb = new StringBuilder();

        if (constraints.length == 1) {
            sb.append(constraints[0]);
            constraintStr = sb.toString();
        } else {
            for (int i = 0; i < constraints.length; i++) {
                sb.append(constraints[i] + " /");
            }
            String temp = sb.toString();
            constraintStr = temp.substring(0, temp.lastIndexOf("/"));
        }
        return null;
    }

    public String getDuplicateMessage() {
        String message = String.format(createDuplicateMessage, startingMessage, constraintStr);
        return message;
    }

    public String getUpdateConstraintViolationMessage() {
        String message = String.format(updateConstraintViolationMessage, startingMessage, constraintStr);
        return message;
    }


    public String getDeleteConstraintViolationMessage() {
        String message = String.format(deleteConstraintViolationMessage, startingMessage, constraintStr);
        return message;
    }
}
