# python实现FTP文件上传和下载

## 前言

python拥有丰富且强大的类库，借助ftplib模块，可以很方便的实现FTP文件的上传和下载。

## 代码实例

```python
# -*- coding: utf-8 -*-
from ftplib import FTP
import datetime, os, re


class FtpUtil(object):
    """
    FTP客户端工具类
    常用方法
    ftp.set_debuglevel(2)  # 打开调试级别2，显示详细信息
    ftp.connect("IPaddress", "port")  # 连接的ftp sever和端口
    ftp.login("user", "password")  # 连接的用户名，密码
    ftp.cwd(pathname)  # 设置FTP当前操作的路径
    ftp.dir()  # 显示目录下所有目录信息
    ftp.nlst()  # 获取目录下的文件
    ftp.mkd(pathname)  # 新建远程目录
    ftp.pwd()  # 返回当前所在位置
    ftp.rmd(dirname)  # 删除远程目录
    ftp.delete(filename)  # 删除远程文件
    ftp.rename(oldname, newname)  # 将fromname修改名称为toname
    ftp.storbinaly("STOR filename.txt", file_handel, bufsize)  # 上传目标文件
    ftp.retrbinary("RETR filename.txt", file_handel, bufsize)  # 下载FTP文件
    ftp.set_debuglevel(0)  # 关闭调试模式
    ftp.quit()  # 退出ftp
    """
    def __init__(self, host, username, password):
        self.port = 21
        self.bufsize = 1024
        self.conn = self.ftp_connect(host, username, password)

    def ftp_connect(self, host, username, password):
        """
        建立连接
        :param host: 地址 
        :param username: 用户名 
        :param password: 密码
        :return: 
        """
        ftp = FTP()
        # ftp.set_debuglevel(2) # 调试级别2 打印详细信息
        ftp.encoding = 'utf-8'  # 防止中文目录报错 GB2312
        ftp.connect(host, self.port)  # 填自己服务的端口号 一般是21
        ftp.login(username, password)  # 如果匿名登录可以使用空字符串
        # ftp.set_pasv(False)  # 主动模式
        # print(ftp.getwelcome()) # 打印欢迎信息
        return ftp

    def upload_file(self, localpath, remotepath):
        """
        上传文件
        :param localpath: 本地文件全路径
        :param remotepath: 远程文件全路径
        :return: 
        """
        try:
            # 从本地上传文件到ftp
            fp = open(localpath, 'rb')
            self.conn.storbinary('STOR ' + remotepath, fp, self.bufsize)
            self.conn.set_debuglevel(0)
            fp.close()
            return True
        except Exception as e:
            print(e)
            return False

    def upload_dir(self, local_dir, remote_dir):
        """
        上传整个目录
        :param local_dir: 本地目录
        :param remote_dir: 远程目录
        :return:
        """
        if not os.path.isdir(local_dir):
            print(local_dir, '不是目录')
            return False

        ftp_path = remote_dir.rstrip('/')
        try:
            # 如果上传路径是文件夹，则创建目录
            self.conn.mkd(ftp_path)
        except:
            print(ftp_path, "已存在")

        # 进入本地目录
        local_files = os.listdir(local_dir)
        for file in local_files:
            local_file = os.path.join(local_dir, file)
            # 如果file本地路径是目录则递归上传文件
            if os.path.isdir(local_file):
                self.upload_dir(local_file, ftp_path + "/" + file)
            # 如果file本地路径是文件则直接上传文件
            else:
                self.upload_file(local_file, ftp_path + "/" + file)

    def download_file(self, remotepath, localpath):
        """
        下载ftp文件
        :param remotepath: 远程文件全路径
        :param localpath: 本地文件全路径
        :return: 
        """
        try:
            # 从ftp下载文件
            fp = open(localpath, 'wb')
            self.conn.retrbinary('RETR ' + remotepath, fp.write, self.bufsize)
            self.conn.set_debuglevel(0)
            fp.close()
            return True
        except Exception as e:
            print(e)
            return False

    def download_dir(self, remote_dir, local_dir):
        """
        下载整个目录，将 remote_dir 下载到 local_dir。
        :param local_dir: 本地目录地址
        :param remote_dir: 远程目录地址
        :return: 成功标识
        """
        try:
            # 如果本地目录不存在，则创建
            if not os.path.exists(local_dir):
                os.makedirs(local_dir)

            remote_dir = remote_dir.rstrip('/')
            remote_names = self.conn.nlst(remote_dir)
            for file in remote_names:
                # 忽略隐藏文件
                if file not in [".", ".."]:
                    remote = remote_dir + "/" + file
                    print("正在下载", remote)
                    if self.isDir(remote):  # 子文件夹递归
                        self.download_dir(remote, os.path.join(local_dir, file))
                    else:
                        self.download_file(remote, os.path.join(local_dir, file))
            return True
        except:
            return False

    def isDir(self, path):
        """
        判断是否为目录 并不是很优雅
        :param path: 路径
        :return:
        """
        try:
            self.conn.cwd(path)
            return True
        except:
            return False

    def close_conn(self):
        """
        关闭连接
        :return: 
        """
        self.conn.quit()


if __name__ == "__main__":
    ftp = FtpUtil("192.168.0.110", "user", "123456")
    # ftp.upload_file("D:\\LogProtocal.txt", "/test/a.txt")
    ftp.upload_dir("D:\\test", "/test/")
    # ftp.download_dir("/test/demo", "D:\\test")
    # downloadfile("/wait.jpg", "D:\\a.jpg")
    ftp.close_conn()

```

