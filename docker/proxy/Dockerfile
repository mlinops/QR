FROM nginx:latest

WORKDIR /app

RUN mkdir ssl

COPY ./common/conf.d /etc/nginx/conf.d
COPY ./ssl/chain.pem /app/chain.pem
COPY ./ssl/private.key /app/private.key