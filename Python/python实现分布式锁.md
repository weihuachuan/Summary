# python实现分布式锁
## zookeeper 实现
```shell
"""
不使用:
from kazoo.recipe.lock import Lock
"""
import logging

from kazoo.client import KazooClient
from threading import Thread, Condition

logger = logging.getLogger()

logger.setLevel(logging.INFO)

sh = logging.StreamHandler()

formatter = logging.Formatter('%(asctime)s -%(module)s:%(filename)s-L%(lineno)d-%(levelname)s: %(message)s')

sh.setFormatter(formatter)

logger.addHandler(sh)

class CountDownLatch:

    def __init__(self, count):
        self.count = count
        self.condition = Condition()

    def awaits(self):
        try:
            self.condition.acquire()
            while self.count > 0:
                self.condition.wait()
        finally:
            self.condition.release()

    def countDown(self):
        try:
            self.condition.acquire()
            self.count -= 1
            self.condition.notifyAll()
        finally:
            self.condition.release()

    def getCount(self):
        return self.count


class zkLock(Thread):

    def __init__(self, *args, **kwargs):
        super(zkLock, self).__init__(*args, **kwargs)
        self.zk = KazooClient('10.197.26.8:2181,10.197.24.254:2181,10.197.26.10:2181', logger=logger)
        self.zk.start()
        self.cc = CountDownLatch(1)

    def run(self) -> None:
        self.trylock()
        print(self.name + "  " + "start working......")
        self.unlink()

    def trylock(self):
        """
        获取锁
        :return:
        """
        print(self.name + " create lock")
        ret_async = self.zk.create_async("/lock", ephemeral=True, sequence=True)
        self.node = ret_async.get()
        ret_async.rawlink(self.create_called)
        print(self.name + "  " + self.node)
        self.cc.awaits()

    def unlink(self):
        """
        释放锁
        :return:
        """
        print(self.name + "  " + "over work......")
        self.zk.delete(self.node)

    def create_called(self, obj=None):
        """
        创建节点的回调及上一个节点的watch回调
        :param obj:
        :return:
        """
        ret_async = self.zk.get_children_async("/")
        ret_async.rawlink(self.getchildren_called)

    def getchildren_called(self, obj=None):
        """
        判断是不是最小的序号, 如果是, 获得锁, 不是等待
        :param obj:
        :return:
        """
        self.node_list = list(obj.get())
        self.node_list.sort()
        index = self.node_list.index(self.node[1:])
        if index == 0:
            self.cc.countDown()
        else:
            ret_async = self.zk.exists_async(self.node_list[index - 1], watch=self.create_called)
            ret_async.rawlink(self.change_called)

    def change_called(self, obj=None):
        """
        判断前个节点在watch前已不存在, 这样会出现死锁
        这个可能还有很好的方法, 欢迎流程交流
        :param obj:
        :return:
        """
        if not obj.get():
            self.create_called()


# 测试
if __name__ == '__main__':
    l = []
    for i in range(50):
        t = zkLock()
        l.append(t)
    for i in l:
        i.start()
    for j in l:
        j.join()
```
## zookeeper实现2
```shell
import threading
import time
import kazoo.client

# python 版本的CountDown
class CountDownLatch:

    def __init__(self, count):
        self.count = count
        self.condition = threading.Condition()

    def awaits(self):
        try:
            self.condition.acquire()
            while self.count > 0:
                self.condition.wait()
        finally:
            self.condition.release()

    def countDown(self):
        try:
            self.condition.acquire()
            self.count -= 1
            self.condition.notifyAll()
        finally:
            self.condition.release()

    def getCount(self):
        return self.count


class MyZK:
    def __init__(self, name):
        hosts = '10.197.26.8:2181'
        self.zk = kazoo.client.KazooClient(hosts=hosts, timeout=3)
        self.name = name
        self.pathName = None
        self.cc = CountDownLatch(1)

    # 获得锁
    def treLock(self):
        self.zk.start()
        data = self.zk.create(path='/lock',
                              value=self.name.encode(),
                              ephemeral=True,
                              makepath=True,
                              sequence=True)
        # 如果创建成功
        if data != None:
            # 获取子节点
            children_list = self.zk.get_children(path='/', watch=False)
            self.pathName = data
            # 判断子节点 是否存在
            if children_list != None:
                # 对子节点排序
                children_list.sort()
                i = children_list.index(self.pathName[1:])
                # 判断是不是第一个
                if i == 0:
                    # 如果是的话就继续执行
                    print(f'{self.name} i am fist')
                    self.zk.set('/', self.name.encode())
                    print(self.zk.get('/'))
                    # 释放停止阻塞
                    self.cc.countDown()
                else:
                    self.zk.exists(path='/' + children_list[i - 1], watch=self.call_watch)

    def call_watch(self, event):
        # node 执行删除的时候再次执行 获取所有的子节点
        if event.type == 'DELETED':
            children_result = self.zk.get_children(path='/', watch=False)
            # 再次执行判断是否是第一个
            self.get_callback(children_result)

    def get_callback(self, children_result):
        if children_result != None:
            children_list = sorted(children_result)
            i = children_list.index(self.pathName[1:])
            # 判断是不是第一个
            if i == 0:
                print(f'{self.name} i am fist')
                self.zk.set('/', self.name.encode())
                print(self.zk.get('/'))
                self.cc.countDown()
            else:
                self.zk.exists(path='/' + children_list[i - 1], watch=self.call_watch)
            # 不是就跳过

    def release_key(self):
        print(f'release_key:{self.pathName}')
        self.zk.delete(self.pathName, version=-1)


def run():
    name = threading.currentThread().getName()
    watchzk = MyZK(name)

    # 获取锁
    watchzk.treLock()
    watchzk.cc.awaits()
    print(watchzk.name + " work start.......")
    # 释放锁
    watchzk.release_key()


if __name__ == '__main__':
    for i in range(10):
        t = threading.Thread(target=run)
        t.start()
    t.join()
```

## redis实现分布式锁
```shell
# -*- coding: UTF-8 -*-
"""
# rs勿忘初心
"""
import time
import uuid
import redis
from threading import Thread

# redis连接
redis_client = redis.Redis(host="10.197.26.8",
                           port=6379)


# 获取一个锁
# lock_name：锁定名称
# acquire_time: 客户端等待获取锁的时间
# time_out: 锁的超时时间
def acquire_lock(lock_name, acquire_time=10, time_out=10):
    """获取一个分布式锁"""
    identifier = str(uuid.uuid4())
    end = time.time() + acquire_time
    lock = "string:lock:" + lock_name
    while time.time() < end:
        if redis_client.setnx(lock, identifier):
            # 给锁设置超时时间, 防止进程崩溃导致其他进程无法获取锁
            redis_client.expire(lock, time_out)
            return identifier
        elif not redis_client.ttl(lock):
            redis_client.expire(lock, time_out)
    return False


# 释放一个锁
def release_lock(lock_name, identifier):
    """通用的锁释放函数"""
    lock = "string:lock:" + lock_name
    pip = redis_client.pipeline(True)
    while True:
        try:
            pip.watch(lock)
            lock_value = redis_client.get(lock)
            if not lock_value:
                return True

            if lock_value.decode() == identifier:
                pip.multi()
                pip.delete(lock)
                pip.execute()
                return True
            pip.unwatch()
            break
        except redis.excetions.WacthcError:
            pass
    return False


# 测试刚才实现的分布式锁
# 例子中使用20个线程模拟秒杀5张票，使用–运算符来实现商品减少，从结果有序性就可以看出是否为加锁状态。
count = 5


def seckill(i):
    identifier = acquire_lock('resource')
    print("线程:{}--获得了锁".format(i))
    global count
    if count < 1:
        print("线程:{}--没抢到，票抢完了".format(i))
        return
    count -= 1
    print("线程:{}--抢到一张票，还剩{}张票".format(i, count))
    release_lock('resource', identifier)


for i in range(20):
    t = Thread(target=seckill, args=(i,))
    t.start()
```
