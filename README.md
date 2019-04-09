# shepherd
Heterogeneous Workflow executor service


# Steps to setup shepherd-core.
1. Build below dependent projects in sequence :
    1. shepherd-model : https://github.com/devvsda/shepherd/tree/master/shepherd-model
      1. Command to install : mvn clean install
    2. http-utils : https://github.com/devvsda/http-utils
      1. Command to install : mvn clean install
2. Build shepherd-core. Command to install : mvn clean install
3. Validate server with HealthCheck API : http://localhost:8080/shephard-core/healthCheck/<ANY_STRING>
