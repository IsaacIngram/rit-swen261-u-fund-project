FROM node:20 AS build
WORKDIR /app
COPY package* .
RUN npm install
COPY . .
RUN npm run build
FROM nginxinc/nginx-unprivileged
RUN sed -i 's/index  index.html index.htm;/try_files $uri $uri\/ \/index.html;/g' /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/ufund-ui/browser/* /usr/share/nginx/html
