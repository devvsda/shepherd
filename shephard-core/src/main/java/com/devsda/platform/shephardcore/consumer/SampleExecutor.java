package com.devsda.platform.shephardcore.consumer;

import com.devsda.platform.shephardcore.dao.RegisterationDao;
import com.devsda.platform.shephardcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherd.model.ClientDetails;
import com.devsda.platform.shepherd.model.EndpointDetails;
import com.google.inject.Inject;

public class SampleExecutor {

    @Inject
    private RegisterationDao registerationDao;

    @Inject
    private WorkflowOperationDao workflowOperationDao;

    public void sample() {
        ClientDetails clientDetails = registerationDao.getClientDetails("bcci");

        EndpointDetails endpointDetails = registerationDao.getEndpointDetails(clientDetails.getClientId(), "selection_dev");

        System.out.println(clientDetails);
        System.out.println(endpointDetails  );
    }
}
