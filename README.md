DAI Lab - HTTP infrastructure
=============================

Objectives
----------

Step 0: GitHub repository
-------------------------

To setup the Github repository, we first fork the original repository to our Github account. Then, we clone the forked repository to our local machine using `git clone` to have a local copy of the repository ready to be modified.

Step 1: Static Web site
-----------------------

To have a nice landing page, we decided to use a bootstrap template from [Start Bootstrap](https://startbootstrap.com/). We chose the [Coming Soon](https://startbootstrap.com/theme/coming-soon) template and we modified it to our needs before adding it to the repository under `web/static`.

We then added a Dockerfile in the `web` directory built on top of the `nginx` image to serve the static files and an `nginx.conf` file to configure the server to listen on port 80 and to serve the static files from the `/usr/share/nginx/html` directory.

The only thing we had to do add in the Dockerfile is the `COPY` instruction to copy the static files from the `static` directory to the `/usr/share/nginx/html` directory and the `nginx.conf` file to the `/etc/nginx/nginx.conf` file to configure the server.

To test the image, we used the `docker build` command to build the image and the `docker run` command to run the container. We used the `-p` option to expose the port 80 of the container to the port 8080 of the host and verified that the static Web site was accessible from the host.

Step 2: Docker compose
----------------------

To allow for an easier deployment of the static Web site, we used Docker compose. We added a `docker-compose.yml` file in the root directory of the repository to start the static Web site. We used the `build` instruction to build the image from the `web` directory and the `ports` instruction to map the port 80 of the container to the port 8080 of the host.

To test the Docker compose file, we used the `docker-compose up` command to start the container and verified that the static Web site was accessible from the host. We also modified the static web site to verify that the changes were taken into account when rebuilding the image using `docker-compose build`.

Step 3: HTTP API server
-----------------------

We decided to implement a simple HTTP API server to manage a TODO list, where a user would be able to:

- List all the TODOs
- Delete all the TODOs
- Add a TODO
- Delete a TODO
- Update a TODO
- Get a specific TODO

The API uses Javalin to handle the HTTP requests and the TODOs are stored in-memory in an `ArrayList`. The API is implemented in the `api/src` directory and the Dockerfile to run it is in the `api` directory. The Dockerfile is built on top of the `openjdk:21` image and simply copies the built `TodoApi-1.0.jar` from the `target` directory to the `/app` directory of the image and runs it using the `java -jar` command. To bundle all the API dependencies in the `.jar` file and prevent any missing dependency exceptions, we used the `maven-shade-plugin` plugin in the `pom.xml` file which will be called when running the `mvn package` command.

The API is exposed by default on port 80, and to allow for an easy mapping depending on the user configuration, we added the service to the Docker compose file and used the `build` instruction to build the image from the `api` directory and the `ports` instruction to map the port 80 of the container to the port 8081 of the host.

To test the API, we first ran it using the `docker-compose up` command and then used the Bruno collection stored in `api/tests` to issue requests to the API and validated that the different endpoints were working as expected.

Step 4: Reverse proxy with Traefik
----------------------------------

To implement the reverse proxy, we first added a new `reverse-proxy` service to the Docker compose file. We used the `image` instruction to use the `traefik:v2.10` image and the `ports` instruction to expose the ports 80 and 8080 of the container. We also used the `volumes` instruction to mount the Docker socket to the container to allow Traefik to listen to Docker events and the `command` instruction to specify the provider, as described in the [Quick-Start documentation](https://doc.traefik.io/traefik/getting-started/quick-start/).

We then added the `web` and `api` services to the `reverse-proxy` service by using the `labels` instruction to specify the Traefik configuration for each service, where `traefik.http.routers.<service>.rule=Host('localhost')` exposes the service on the `localhost` address and `traefik.http.routers.<service>.rule=PathPrefix('/<path>')` specifies the path prefix for the service. As specified in the [documentation](https://doc.traefik.io/traefik/routing/providers/docker/), Traefik will automatically uses the first exposed port of a container as the default port for the service. To use this feature, we used the `expose` instruction in the Docker compose file to expose the port 80 of the `web` service and the port 80 of the `api` service, which prevents us from having to specify the port in the Traefik configuration using `traefik.http.services.xxx.loadbalancer.server.port`.

To test the reverse proxy, we first ran it using the `docker-compose up` command and then verified that the static Web site and the API were accessible from the host browser using the `localhost` address and the `localhost/api` address respectively. We then updated the Bruno collection stored in `api/tests` to use the `localhost/api` address and verified that the requests were correctly forwarded to the API.

Step 5: Scalability and load balancing
--------------------------------------

To scale our services such as multiple instances are started at once, we used the `deploy.replicas` instruction in the Docker compose file to specify the number of instances to start for each service. Once running using `docker-compose up`, we then used the `docker-compose up --scale <service>=<number>` command to scale the numbers of instances of a given service.

To verify that the load balancing was working as expected, we verified in the Traefik dashboard that the number of instances was correctly displayed under the `Servers` part of each service and we also verified that upon issuing several requests to the services, the requests were correctly distributed among the different instances.

Step 6: Load balancing with round-robin and sticky sessions
-----------------------------------------------------------

To use sticky sessions on the API service, we added the `traefik.http.services.api.loadbalancer.sticky.cookie=true` instruction to the Traefik configuration of the `api` service. Using a Python script, we then verified that upon issuing several requests to the API, the requests were correctly forwarded to the same instance when sent in the same session and to different instances when sent in different sessions.

To validate that the load balancing with round-robin was working as expected, we simply issued several requests to the static Web site from our browser and verified that the requests were correctly distributed among the different instances of the `web` service.

Step 7: Securing Traefik with HTTPS
-----------------------------------



Optional steps
==============

Optional step 1: Management UI
------------------------------


Optional step 2: Integration API - static Web site
--------------------------------------------------

