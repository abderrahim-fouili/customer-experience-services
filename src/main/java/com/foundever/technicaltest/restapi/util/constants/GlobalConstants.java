package com.foundever.technicaltest.restapi.util.constants;

public final class GlobalConstants {

    private GlobalConstants() throws InstantiationException {
        throw new InstantiationException("Instances of this type are forbidden");
    }
    /**
     * PATH API
     **/
    public static final String BASE_PATH = "/v1/customer-experience/services/";
    /**
     * OpenApi
     **/
    public static final String INFO_API_TITLE = "Customer Experience Services RESTful APIs";
    public static final String INFO_API_DESCRIPTION_MESSAGE = "RESTful API for managing Messages.";
    public static final String INFO_API_DESCRIPTION_CLIENT_CASES = "RESTful API for managing Clients Case.";

    public static final String MESSAGE_APIS_TAG = "Messages APIs";
    public static final String CLIENTCASE_APIS_TAG = "Clients Case APIs";


    /**
     * API ERROR CODES
     **/
    private static final String BASE_URI = "/problem";

}
