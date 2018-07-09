package com.devsda.platform.shephardcore.resources;

import com.devsda.platform.shephardcore.constants.ResourceName;
import com.devsda.platform.shephardcore.model.ShepherdResponse;

public class ResourceHelper {

    public ShepherdResponse createShepherdResponse(ResourceName resourceName, Object message, String errorMessage) {

        ShepherdResponse shepherdResponse = new ShepherdResponse();
        shepherdResponse.setAPIName(resourceName);
        shepherdResponse.setMessage(message);
        shepherdResponse.setErrorMessage(errorMessage);

        return shepherdResponse;

    }
}
