# python 日志
## LogX.py 
```python
"""
@note: 
Damn it! 
Need to re-write it again!?
"""
import logging
import os
import time


class RfLog(object):
    """
    @param:
    1. name => log.write_name
    2. username => log.username 
    3. path => log.write_path
    4. fmt => log.user-info-format
    5. datefmt => timestamp
    6. logging level => default is Logging.INFO
    
    @method:
    log_status => msg = "logging message"
                  method_level = "message level"
    """
    name = "rf {}.log".format(time.strftime("%Y-%m-%d")[2:])
    username = "NPI-RF"
    parpath = os.path.expanduser('~')
    path = os.path.join(parpath, ".rf")
    keep_time = 3600 * 24 * 2

    def __init__(self,
                 name=None,
                 username=None,
                 path=None,
                 fmt="%(asctime)-15s %(name)s %(levelname)s: %(message)s",
                 datafmt="%Y-%m-%d %H:%M:%S",
                 level=logging.INFO,
                 ):

        self.name = self.__get_log_name(name)
        self.username = self.__get_log_username(username)
        self.path = self.__get_log_path(path)
        self.level = self.__get_log_level(level)

        fmt = self.__get_log_fmt(fmt)
        datefmt = self.__get_log_datafmt(datafmt)

        self.fmtter = self.__get_log_formatter(fmt, datefmt)
        self.handler = self.__get_log_handler()
        self.logger = self.__get_logger()

    def __get_log_name(self, name):

        if (name is None):
            name = RfLog.name
        else:
            name

        return name

    def __get_log_username(self, name):

        if (name is None):
            name = RfLog.username

        return name

    def __get_log_path(self, path=None):

        if (path is None):
            path = RfLog.path

        # set path if(!exist)
        if (not os.path.exists(path)):
            os.mkdir(path)
        # or clear futile logs
        #else:
        #    os.chdir(path)
        #    logs = os.listdir(path)
        #    for log_ in logs:
        #        ctime = os.path.getctime(log_)
        #        if (time.time() - ctime >= self.keep_time):
        #            os.remove(log_)

        return path

    def __get_log_level(self, level):

        assert level in (logging.INFO, logging.WARN, logging.DEBUG, logging.ERROR, logging.CRITICAL), \
            "Level should be within [logging.INFO, logging.WARN, logging.DEBUG, logging.ERROR, logging.CRITICAL]"

        return level

    def __get_log_fmt(self, fmt):

        if (fmt is None):
            fmt = "%(asctime)-15s %(name)s : %(message)s"
        return fmt

    def __get_log_datafmt(self, datefmt):

        if (datefmt is None):
            datefmt = "%Y-%m-%d %H:%M:%S"
        return datefmt

    def __get_log_formatter(self, fmt=None, datefmt=None):

        fmt = self.__get_log_fmt(fmt)
        datefmt = self.__get_log_datafmt(datefmt)
        fmtter = logging.Formatter(fmt, datefmt)

        return fmtter

    def __get_log_handler(self):

        log_path = self.path + os.sep + self.name
        fh = logging.FileHandler(log_path, mode='a+', encoding='utf-8')
        fh.setLevel(self.level)
        fh.setFormatter(self.fmtter)

        return fh

    def __get_logger(self):

        name = self.username
        logger = logging.Logger(name)
        logger.addHandler(self.handler)
        logger.setLevel(self.level)

        return logger

    def log_status(self, msg, method_level=2):
        """
        @note: for user to log
        """
        method_dict = {1: "debug",
                       2: "info",
                       3: "warn",
                       4: "error",
                       5: "critical"}

        getattr(self.logger, method_dict[method_level])(msg)


if (__name__ == "__main__"):
    # test
    logger = RfLog()

    logger.log_status("Very good!")
    logger.log_status("Very good!", 1)
    logger.log_status("Very good!", 3)
    logger.log_status("Very good!", 4)
    logger.log_status("Very good!", 5)

```
## Log.py
```python
import os
import sys
import time

from LogX import RfLog


class Log(RfLog):
    _instance = None

    """
    @Param: path 日志保存路径
    """
    def __init__(self, path):
        log_filename = "rf {}.log".format(time.strftime("%Y-%m-%d")[2:])
        super().__init__(log_filename, None, path)
        self._error_list = list()       # 记录日志里的错误日志

    """
    @Param: path 日志保存路径
    """
    @staticmethod
    def init_instance(path: str):
        log_obj = Log(path)
        Log._instance = log_obj
        return Log._instance

    @staticmethod
    def get_instance():
        if Log._instance is None:
            log_obj = Log(os.path.dirname(sys.argv[0]))
            Log._instance = log_obj
        return Log._instance

    """
    获取全部错误日志
    """
    def get_error_log(self):
        return self._error_list

    def info(self, message: str):
        self.log_status(message, 2)

    def error(self, message: str):
        self._error_list.append(message)
        self.log_status(message, 4)

    def warn(self, message: str):
        self.log_status(message, 3)

        
# 使用方法
if __name__ == '__main__':
    # Log.get_instance().info("123")
    # Log.get_instance().error("123")
    # Log.get_instance().warn("123")

    # 使用方法2
    log = Log("./")
    log.info("123")
    log.error("123")
    log.warn("123")

```