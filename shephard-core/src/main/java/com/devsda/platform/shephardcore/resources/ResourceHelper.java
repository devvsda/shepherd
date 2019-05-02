package com.devsda.platform.shephardcore.resources;


import com.devsda.platform.shepherd.constants.ResourceName;
import com.devsda.platform.shepherd.model.ShepherdResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class ResourceHelper {

    private static final Logger log = LoggerFactory.getLogger(ResourceHelper.class);

    public ShepherdResponse createShepherdResponse(ResourceName resourceName, Map<String, Object> resourceResponse,
                                                   String message, String errorMessage) {

        ShepherdResponse shepherdResponse = new ShepherdResponse();

        shepherdResponse.setResourceName(resourceName);
        shepherdResponse.setMessage(message);
        shepherdResponse.setErrorMessage(errorMessage);
        shepherdResponse.setResponseData(resourceResponse);

        try {
            shepherdResponse.setServerName(InetAddress.getLocalHost().getHostName());
        } catch(UnknownHostException e) {
            log.error("Not able to fetch DNS name of Shepherd server.");
        }

        return shepherdResponse;

    }
}
