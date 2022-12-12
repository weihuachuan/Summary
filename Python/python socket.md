# python socket 服务端 客户端
## SocketServer.py
```python
import socket
import json, struct
import threading

from concurrent.futures import ThreadPoolExecutor
from apscheduler.schedulers.blocking import BlockingScheduler

from index.socket.ThreadUtil import ThreadUtil


class Server(object):
    def __init__(self, ip, port):
        self.s = socket.socket()
        self.s.bind((ip, port))
        self.s.listen()
        self.clients = {}
        self.pool = ThreadPoolExecutor(100)
        self.threading = {}  # 用于记录每个任务线程
        self.debug = True

    def run(self):
        print("開始監聽前台命令數據！")
        while True:
            '''以下代码用于检测是否有客户端连接'''
            c, add = self.s.accept()
            print("%s" % add[0], "连接到服务器!")
            self.clients[add[0]] = c  # 把ip地址作为key，conn作为value存入clients字典中 ,clients = {'192.188.3.4':conn链接}
            if self.debug:
                print('clients=%s' % self.clients)

            # '''以下代码用于监听已经建立连接的客户端发来的消息'''
            self.pool.submit(self.monitor, c)

    def send_msg(self, soc, msg):
        l = len(msg.encode("utf-8"))
        soc.send(struct.pack("q",l))

        # 发数据
        soc.send(msg.encode("utf-8"))

    '''用于监听已经建立连接的客户端发来的消息'''
    def monitor(self, c):
        while True:
            l = c.recv(8)
            ls = struct.unpack("q", l)[0]
            data = {}
            try:
                data = json.loads(c.recv(ls).decode("utf-8"))  # 接收到来自客户端的消息。 eg:    data = {'to_addr':'msg':''}
                print(data)
            except Exception as e:
                print('不是标准命令=====>' + str(e))
            # 启动 停止任务逻辑
            if data['command_type'] == 'start':
                # 创建调度器：BlockingScheduler
                scheduler = BlockingScheduler(timezone='Asia/Shanghai')
                t = threading.Thread(target=ThreadUtil.start_thread, args=(data, scheduler, self.threading))
                t.setName(data['task_name'])
                self.threading[t.getName()] = t
                self.threading[data['task_name'] + '_scheduler'] = scheduler
                t.start()
                print("自动主线程执行完成！")
            elif data['command_type'] == 'stop':
                if data['task_name'] in self.threading.keys():
                    print('停止' + data['task_name'])
                    ThreadUtil.stop_thread(self.threading[data['task_name']])
                    self.threading[data['task_name'] + '_scheduler'].shutdown(wait=False)
                    self.threading.pop(data['task_name'])
                    self.threading.pop(data['task_name'] + '_scheduler')
                else:
                    print(data['task_name'] + "运行完成")


if __name__ == '__main__':
    server = Server("10.197.24.57", 9000)
    server.run()
```
## ThreadUtil.py
```python

```

## SocketClient.py
```python
import json
import socket
import struct


class SocketUtil(object):

    def __init__(self):
        # 建立socket链接
        self.conn = socket.socket()
        self.conn.connect(("10.197.24.57", 9000))
        print("连接服务器成功!")

    def send_msg(self, data):
        data = json.dumps(data).encode('utf-8')
        self.conn.send(struct.pack("q", len(data)))
        self.conn.send(data)

    def close(self):
        self.conn.close()


if __name__ == '__main__':
    # 建立客户端连接
    my_socket = SocketUtil()
    # 定义字典封装要发送的信息
    # data = {"command_type": "start", "task_name": "test"}
    data = {"command_type": "stop", "task_name": "test"}
    my_socket.send_msg(data)
    my_socket.close()

```