# python socket 监听
## SocketUtil server
```python
import socket
import json, struct
import threading
from concurrent.futures import ThreadPoolExecutor
from apscheduler.schedulers.blocking import BlockingScheduler
from utils.ThreadUtil import ThreadUtil


class Server(object):
    def __init__(self, ip, port):
        self.s = socket.socket()
        self.s.bind((ip, port))
        self.s.listen()
        self.clients = {}
        self.pool = ThreadPoolExecutor(100)
        self.threading = {}
        self.debug = True

    def run(self):
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

            if data['command_type'] == 'start':
                if data['task_mode'] == 0:
                    # 创建调度器：BlockingScheduler
                    scheduler = BlockingScheduler(timezone='Asia/Shanghai')
                    t = threading.Thread(target=ThreadUtil.start_thread, args=(data, scheduler, {}))
                    t.setName(data['task_name'])
                    self.threading[t.getName()] = t
                    self.threading[data['task_name'] + '_scheduler'] = scheduler
                    t.start()
                elif data['task_mode'] == 1:
                    t = threading.Thread(target=ThreadUtil.start_thread, args=(data, {}, self.threading))
                    t.setName(data['task_name'])
                    self.threading[t.getName()] = t
                    t.start()
            elif data['command_type'] == 'stop':
                if data['task_mode'] == 0:
                    if data['task_name'] in self.threading.keys():
                        print('停止' + data['task_name'])
                        ThreadUtil.stop_thread(self.threading[data['task_name']])
                        self.threading[data['task_name'] + '_scheduler'].shutdown()
                        self.threading.pop(data['task_name'])
                        self.threading.pop(data['task_name'] + '_scheduler')
                    else:
                        print(data['task_name'] + "运行完成")
                elif data['task_mode'] == 1:
                    if data['task_name'] in self.threading.keys():
                        print('停止' + data['task_name'])
                        ThreadUtil.stop_thread(self.threading[data['task_name']])
                        self.threading.pop(data['task_name'])
                    else:
                        print(data['task_name'] + "运行完成")
```

## SocketUtil client
```python
import json
import socket
import struct


class SocketUtil(object):

    def __init__(self):
        # 建立socket链接
        self.conn = socket.socket()
        self.conn.connect(("server端的ip", 8848))
        print("连接服务器成功!")

    def send_msg(self, data):
        data = json.dumps(data).encode('utf-8')
        self.conn.send(struct.pack("q", len(data)))
        self.conn.send(data)

    def close(self):
        self.conn.close()        
```
