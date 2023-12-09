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

To allow for an easier deployment of the static Web site, we decided to use Docker compose. We added a `docker-compose.yml` file in the root directory of the repository to start the static Web site. We used the `build` instruction to build the image from the `web` directory and the `ports` instruction to expose the port 80 of the container to the port 8080 of the host.

To test the Docker compose file, we used the `docker-compose up` command to start the container and verified that the static Web site was accessible from the host. We also modified the static web site to verify that the changes were taken into account when rebuilding the image using `docker-compose build`.

Step 3: HTTP API server
-----------------------



Step 4: Reverse proxy with Traefik
----------------------------------



Step 5: Scalability and load balancing
--------------------------------------



Step 6: Load balancing with round-robin and sticky sessions
-----------------------------------------------------------


Step 7: Securing Traefik with HTTPS
-----------------------------------



Optional steps
==============

Optional step 1: Management UI
------------------------------


Optional step 2: Integration API - static Web site
--------------------------------------------------

