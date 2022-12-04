# docker安装
1. 卸载旧的版本 
```shell
    yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
```
2. 需要安装包
```shell
sudo yum install -y yum-utils
```
3. 设置镜像仓库
```shell
sudo yum-config-manager \
    --add-repo \
    http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```
4. 安装docker
```shell
yum makecache fast
```
5. 安装docker-ce（社区版-免费的）
```shell
sudo yum install docker-ce docker-ce-cli containerd.io
```
6. docker 常用命令
```shell
启动docker
sudo systemctl start docker

如何判断是否成功安装docker 查看版本
docker version

查看镜像（是空的）
docker images

测试 hello-world
拉取hello-world
docker pull hello-world

运行hello-world
docker run hello-world

查看运行的容器
docker ps

查看所有的容器
docker ps -a

移除容器
docker rm 533eaa8e4c79

再次查看容器列表会发现容器没有了
docker ps -a
```

#  docker 打包Django项目

## 准备 run.sh  Dockerfile  Django project  三个放到同一文件夹下

### Django-project

wfs 是django项目

### Dockerfile  

```shell
FROM centos
FROM python:3.6.8

ADD ./WFS /opt/
ADD ./requirements.txt /opt/
ADD ./run.sh /opt/

WORKDIR /opt/

# 安装支持
RUN pip install -r requirements.txt

RUN chmod 777 run.sh
EXPOSE 9999
CMD ["/bin/sh","run.sh"]
```



### run.sh

```shell
python /WFS/manage.py runserver 127.0.0.1:9999
```

 ### 执行打包命令

```shell
docker build -t file_server .
```

### 查看打包镜像

```shell
docker images
```

### docker运行

```shell
docker run --name file2 -d -p 9999:9999 file_server
```



# idea打包docker镜像 远程一键部署docker镜像

## 开启docker 远程访问
```shell
vim /usr/lib/systemd/system/docker.service
```
## 在ExecStart=/usr/bin/dockerd 配置文件后面加上
```
-H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock
```
## 然后重启Docker
```shell
systemctl daemon-reload
systemctl restart docker
```
## 在Idea中配置远程docker连接
docker EditConfiguration > engine url > tcp://10.197.24.169:2375

