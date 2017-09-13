# chumba-portal
POC microservice

Current state - use IDE to run:

- dev-database --> Database.java
- cp-authentication --> Authentication.java
- cp-registration --> Register.java
- cp-web --> serve packages folder on port 8080

Run nginx (see config below)

Browse: http://localhost:9000 

### Bind them together

#### use nginx

```shell
#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;

events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

    server {
        listen       9000;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location /auth {
        		proxy_pass http://127.0.0.1:4545;
        }

        location /register {
        		proxy_pass http://127.0.0.1:4566;
        }

        location / {
        		proxy_pass http://127.0.0.1:8080;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
       }

    }

}

```

#### or get fancy
Use https://www.membrane-soa.org/
