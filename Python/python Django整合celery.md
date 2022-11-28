# python Django 整合celery
## 安装依赖
1. celery==5.1.2
2. Django==1.11.6
3. eventlet==0.33.2
4. redis==4.0.2
## 目录结构
1. 在project下创建一个celery_task包 > search_file包 > tasks.py
2. celery_task包 > main.py
3. celery_task包 > config.py
4. mian.py 包含app的设置
```python
import os

# 读取Django的配置，
from celery import Celery

os.environ["DJANGO_SETTINGS_MODULE"] = "project.settings"
# "redis://127.0.0.1:6379/1"
# 创建celery对象，并指定配置
app = Celery("test", backend="redis://10.175.94.58:6380/10")
# app = Celery("test")
# celery项目配置： worker代理人，指定任务存储到哪里区。
app.config_from_object('celery_tasks.config')
# 加载可用的任务
app.autodiscover_tasks([
    'celery_tasks.test',
    'celery_tasks.search_file'
])
```
5. config.py 指定任务存储到哪里区。
```python
# 代理人：指定rabbitmq作为消息队列
broker_url = "redis://10.175.94.58:6380/9"
```
6. task.py
```python
@app.task(name="search_files")
def search_files(dir_path, dir_id, id_count, file_name, username):
    print("调用开始")
    sleep(20)
    print("调用结束")
```

## 启动项目
```python
python manange.py runserver 127.0.0.1:8000
```

## 启动worker (在项目目录下)
```python
celery -A celery_tasks.main worker -l info -P eventlet
```

