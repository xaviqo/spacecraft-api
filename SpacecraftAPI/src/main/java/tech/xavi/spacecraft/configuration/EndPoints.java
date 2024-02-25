package tech.xavi.spacecraft.configuration;

public class EndPoints {

    public static final String EP_VERSION = "/v1";
    public static final String EP_API_ROOT = EP_VERSION + "/api";

    public static final String EP_SPACECRAFT = EP_API_ROOT + "/spacecraft";
    public static final String EP_SC_BY_ID = EP_SPACECRAFT + "/{id}";
    public static final String EP_SC_NAME_CONTAINS = EP_SPACECRAFT + "/search";

}
