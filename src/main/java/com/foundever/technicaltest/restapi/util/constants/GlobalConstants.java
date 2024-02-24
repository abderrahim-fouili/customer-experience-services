package com.foundever.technicaltest.restapi.util.constants;

public final class GlobalConstants {

    private GlobalConstants() throws InstantiationException {
        throw new InstantiationException("Instances of this type are forbidden");
    }

    /**
     * PATH API
     **/
    public static final String BASE_PATH = "/v1/CustomerExperience/Services/";


    /**
     * OpenApi
     **/
    public static final String INFO_API_TITLE = "Customer Experience Services RESTful APIs";
    public static final String INFO_API_DESCRIPTION = "RESTful API for managing Messages.";
    public static final String MESSAGE_APIS_TAG = "Messages APIs";


    /**
     * API ERROR CODES
     **/
    private static final String BASE_URI = "/problem";
    public static final String URI_DEFAULT = BASE_URI + "/error-with-detail";
    public static final String URI_MISSING_REQUEST_PARAMETER = BASE_URI + "/missing-request-params";
    public static final String URI_VALIDATION_CONSTRAINT_VIOLATION = BASE_URI + "/validation-constraint-violation";
    public static final String URI_METHOD_ARGUMENT_NOT_VALID = BASE_URI + "/method-argument-not-valid";

}
