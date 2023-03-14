# ss+v2ray+nginx
## 首先申请域名 网址 https://www.freenom.com/
1. 搜索域名的时候要带上后缀不然免费的域名不可用 例子:totosa.cf
2. 加入购物车 点击完成 
3. Verify My Email Address 这一步就需要虚拟一个美国人的身份方便注册 [虚拟身份网址](https://www.fakenamegenerator.com/)
4. 通过虚拟身份注册好 freenom的用户 再用DNS域名解析关联你的服务器ip

### 服务器配置
#### 申请证书
通过acme.sh申请证书自动跟新
```shell
curl  https://get.acme.sh | sh

acme.sh  --issue -d www.totosay.cf  --standalone \
--key-file /opt/module/nginx/conf/ssl/www.totosay.cf.key \
--fullchain-file /opt/module/nginx/conf/ssl/www.totosay.cf.crt
```
这里/opt/module/nginx/conf/ssl 目录提前创建好 并且本地80端口开放

#### 利用dockers配置 ss+v2ray
```shell
docker pull acrisliu/shadowsocks-libev

docker run -d \
-e "ARGS=--plugin v2ray-plugin --plugin-opts server;tls;host=www.totosay.top;path=/v2ray;cert=/root/.acme.sh/www.totosay.top/fullchain.cer;key=/root/.acme.sh/www.totosay.top/www.totosay.top.key -u" \
-e PASSWORD=whc159357. \
-v /root/.acme.sh:/root/.acme.sh \
--user root \
--name=ss-libev \
-p 8388:8388/tcp \
-p 8388:8388/udp \
--restart=always \
acrisliu/shadowsocks-libev

docker start ss-libev

docker logs ss-libev
```
看日志启动没报错启动成功

#### 个人网盘安装

​	[kiftd-master.zip](https://www.totosay.cf/externalLinksController/downloadFileByKey/kiftd-master.zip?dkey=5af247f8-e8f2-4d0c-9c12-c3bcf2745e0d)地址

1. 将kiftd-master.zip上传服务器目录 /opt/module/software/ 下
2. cd /opt/module/software/
3. zip -r kiftd-master.zip  /opt/module/
4. cd /opt/module/kiftd-master
5. nohup java -jar kiftd-1.0.35-RELEASE.jar  -start & echo $! 项目启动

#### web-ssh安装

Quick start

```shell
$ docker pull genshen/ssh-web-console:latest
# docker build --build-arg GOMODULE=on -t genshen/ssh-web-console . # or build docker image on your own machine
$ docker run -v ${PWD}/conf:/home/web/conf -p 2222:2222 --rm genshen/ssh-web-console
```

config.yaml 

```shell
site:
  appname: ssh-web-console
  listen_addr: :2222
  runmode: prod
  deploy_host: console.hpc.gensh.me

prod:
  # http path for static files and views
  static_prefix: /
  api_prefix: ""

dev: # config used in debug mode.
  # http prefix for static files
  static_prefix: /static/
  api_prefix: /
  # redirect static files requests to this address, redirect "static_prefix" to "static_redirect"
  # for example, static_prefix is "/static", static_redirect is "localhost:8080/dist",
  # this will redirect all requests having prefix "/static" to "localhost:8080/dist"
  static_redirect: "localhost:8080"
  static_dir: ./dist/ # if static_redirect is empty, http server will read static file from this dir.
  views_prefix: / #
  views_dir: views/ # views(html) directory.

ssh:
  #  io_mode: 1  # the mode reading data from ssh server: channel mode (0) OR session mode (1)
  buffer_checker_cycle_time: 60 # check buffer every { buffer_checker_cycle_time } ms. if buffer is not empty , then send buffered data back to client(browser/webSocket)
jwt:
  jwt_secret: secret.console.hpc.gensh.me
  token_lifetime: 7200
  issuer: issuer.ssh.gensh.me
  query_token_key: _t
```



#### nginix配置文档

```shell
worker_processes  1;
events {
    worker_connections  1024;
}
http {
    client_max_body_size 3072m;#配置nginx上传文件最大限制
    include       mime.types;
    default_type  application/octet-stream;
    server_tokens off;
    sendfile        on;
    tcp_nopush on;
    tcp_nodelay on;
    client_header_timeout 10;
    client_body_timeout 10;
    reset_timedout_connection on;
    send_timeout 10;
    keepalive_timeout  65;
    access_log off;
    limit_conn_zone $binary_remote_addr zone=addr:5m;
    limit_conn addr 100;
    charset UTF-8;
    gzip on;
    gzip_disable "msie6";
    gzip_proxied any;
    gzip_min_length 1000;
    gzip_comp_level 4;
    gzip_types text/plain text/css application/json application/x-javascript text/xml application/xml application/xml+rss text/javascript;
    open_file_cache max=100000 inactive=20s;
    open_file_cache_valid 30s;
    open_file_cache_min_uses 2;
    open_file_cache_errors on;
    server {
        listen  8080;

        location / {
                root   html;
                index  index.html index.htm;
        }

        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }
    server {
        listen  443 ssl;
        ssl on;
        ssl_certificate       /root/.acme.sh/www.totosay.cf/fullchain.cer;
        ssl_certificate_key   /root/.acme.sh/www.totosay.cf/www.totosay.cf.key;
        ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
        ssl_ciphers TLS13-AES-256-GCM-SHA384:TLS13-CHACHA20-POLY1305-SHA256:TLS13-AES-128-GCM-SHA256:TLS13-AES-128-CCM-8-SHA256:TLS13-AES-128-CCM-SHA256:EECDH+CHACHA20:EECDH+AES128:RSA+AES128:EECDH+AES256:RSA+AES256:EECDH+3DES:RSA+3DES:!MD5;
        ssl_prefer_server_ciphers on;
        ssl_session_timeout 10m;
        ssl_session_cache builtin:1000 shared:SSL:10m;
        ssl_buffer_size 1400;
        add_header Strict-Transport-Security max-age=15768000;
        ssl_stapling on;
        ssl_stapling_verify on;
        server_name           www.totosay.cf;
        location /v2ray {
                proxy_redirect off;
                proxy_pass http://127.0.0.1:8388;
                proxy_http_version 1.1;
                proxy_set_header Upgrade   $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_set_header Host      $http_host;
                }
	location / {
		proxy_redirect off;
                proxy_pass http://173.82.226.172:8082/;
		proxy_http_version 1.1;
                proxy_set_header Upgrade   $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_set_header Host      $http_host;
                }
        }
   server {
        listen  443 ssl;
        ssl on;
        ssl_certificate       /root/.acme.sh/a.totosay.cf/fullchain.cer;
        ssl_certificate_key   /root/.acme.sh/a.totosay.cf/a.totosay.cf.key;
        ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
        ssl_ciphers TLS13-AES-256-GCM-SHA384:TLS13-CHACHA20-POLY1305-SHA256:TLS13-AES-128-GCM-SHA256:TLS13-AES-128-CCM-8-SHA256:TLS13-AES-128-CCM-SHA256:EECDH+CHACHA20:EECDH+AES128:RSA+AES128:EECDH+AES256:RSA+AES256:EECDH+3DES:RSA+3DES:!MD5;
        ssl_prefer_server_ciphers on;
        ssl_session_timeout 10m;
        ssl_session_cache builtin:1000 shared:SSL:10m;
        ssl_buffer_size 1400;
        add_header Strict-Transport-Security max-age=15768000;
        ssl_stapling on;
        ssl_stapling_verify on;
        server_name           a.totosay.cf;
        location / {
                proxy_redirect off;
                proxy_pass http://173.82.226.172:2222/;
                proxy_http_version 1.1;
                proxy_set_header Upgrade   $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_set_header Host      $http_host;
                }
        }	
	server {
        listen  443 ssl;
        ssl on;
        ssl_certificate       /root/.acme.sh/b.totosay.cf/fullchain.cer;
        ssl_certificate_key   /root/.acme.sh/b.totosay.cf/b.totosay.cf.key;
        ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
        ssl_ciphers TLS13-AES-256-GCM-SHA384:TLS13-CHACHA20-POLY1305-SHA256:TLS13-AES-128-GCM-SHA256:TLS13-AES-128-CCM-8-SHA256:TLS13-AES-128-CCM-SHA256:EECDH+CHACHA20:EECDH+AES128:RSA+AES128:EECDH+AES256:RSA+AES256:EECDH+3DES:RSA+3DES:!MD5;
        ssl_prefer_server_ciphers on;
        ssl_session_timeout 10m;
        ssl_session_cache builtin:1000 shared:SSL:10m;
        ssl_buffer_size 1400;
        add_header Strict-Transport-Security max-age=15768000;
        ssl_stapling on;
        ssl_stapling_verify on;
        server_name           b.totosay.cf;
        location / {
                proxy_redirect off;
                proxy_pass http://173.82.226.172:6080/;
                proxy_http_version 1.1;
                proxy_set_header Upgrade   $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_set_header Host      $http_host;
                }
        }
}

```
配置完重启nginx  /opt/module/nginx/sbin/nginx -s reload  ---proxy_pass http://173.82.226.172:8082/; 这个是我自己的网盘服务

通过windows客户端 ISO客户端 Android客户端登录 [网址](https://funnyjs.com/shadowsocks-v2ray-ws-proxy/) :文章末尾有详细介绍

##### Nginx解决跨域问题

```shell
 server {
        listen       83; #监听83端口，可以改成其他端口
        server_name  localhost; # 当前服务的域名(nginx所在服务器域名)
 
        #charset koi8-r;
 
        #access_log  logs/host.access.log  main;
 
        location / {
            proxy_pass http://localhost:8080;#代理项目部署的地址(这里项目部署在了当前服务器tomcat上,端口8080)
            proxy_redirect default;
        }
 
		location /api { #添加访问目录为/api的代理配置,使以“/api”开头的地址都转到“http://192.168.1.111:8080”上
			rewrite  ^/api/(.*)$ /$1 break;
			proxy_pass   http://192.168.1.111:8080;
        }
}
```



