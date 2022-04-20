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
-e "ARGS=--plugin v2ray-plugin --plugin-opts server;tls;host=yourdomain.com;path=/v2ray -u" \
-e PASSWORD=YourPassword \
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
#### nginix配置文档
```shell
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
    }
```
配置完重启nginx  /opt/module/nginx/sbin/nginx -s reload  ---proxy_pass http://173.82.226.172:8082/; 这个是我自己的网盘服务

通过windows客户端 ISO客户端 Android客户端登录 [网址](https://funnyjs.com/shadowsocks-v2ray-ws-proxy/) :文章末尾有详细介绍



