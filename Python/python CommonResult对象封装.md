# ptyhon web 端CommonResult对象封装
## ResultCode.py
```python
from enum import Enum, unique


@unique
class ResultCode(Enum):
    SUCCESS = (200, '成功')
    FAILED = (500, "操作失败")
    VALIDATE_FAILED = (403, "参数检验失败")
    UNAUTHORIZED = (401, "暂未登录或token已经过期")
    FORBIDDEN = (403, "没有相关权限")

    def get_message(self):
        return self.value[1]

    def get_code(self):
        return self.value[0]


if __name__ == '__main__':
    print(ResultCode.FORBIDDEN.get_message())
```

## CommonResult.py
```python
import functools
from functools import singledispatch, update_wrapper

from index.service.CoexTaskService import CoexTaskService
from index.utils.ResultCode import ResultCode


def multidispatch(*types):
    """
    python多参数重载
    :param types:
    :return:
    """
    def register(function):
        name = function.__name__
        mm = multidispatch.registry.get(name)
        if mm is None:
            @functools.wraps(function)
            def wrapper(self, *args):
                types = tuple(arg.__class__ for arg in args)
                function = wrapper.typemap.get(types)
                if function is None:
                    raise TypeError("no match")
                return function(self, *args)

            wrapper.typemap = {}
            mm = multidispatch.registry[name] = wrapper
        if types in mm.typemap:
            raise TypeError("duplicate registration")
        mm.typemap[types] = function
        return mm

    return register


multidispatch.registry = {}


class CommonResult(object):
    """
    响应结果包装类
    """

    def __init__(self):
        self.ResultCode = ResultCode

    @multidispatch(dict)
    def success(self, data):
        return {"code": self.ResultCode.SUCCESS.get_code(), 'message': self.ResultCode.SUCCESS.get_message(),
                'data': data}

    @multidispatch(str, dict)
    def success(self, message, data):
        return {"code": self.ResultCode.SUCCESS.get_code(), 'message': message,
                'data': data}

    @multidispatch()
    def failed(self):
        return {"code": self.ResultCode.FAILED.get_code(), 'message': self.ResultCode.FAILED.get_message(),
                'data': {}}

    @multidispatch(str)
    def failed(self, message):
        return {"code": self.ResultCode.FAILED.get_code(), 'message': message,
                'data': {}}

    @multidispatch()
    def validate(self):
        return {"code": self.ResultCode.VALIDATE_FAILED.get_code(),
                'message': self.ResultCode.VALIDATE_FAILED.get_message(),
                'data': {}}

    @multidispatch(str)
    def validate(self, message):
        return {"code": self.ResultCode.VALIDATE_FAILED.get_code(), 'message': message,
                'data': {}}

    @multidispatch()
    def unauthrized(self):
        return {"code": self.ResultCode.UNAUTHORIZED.get_code(),
                'message': self.ResultCode.UNAUTHORIZED.get_message(),
                'data': {}}

    @multidispatch(str)
    def unauthrized(self, message):
        return {"code": self.ResultCode.UNAUTHORIZED.get_code(), 'message': message,
                'data': {}}

    @multidispatch()
    def forbidden(self):
        return {"code": self.ResultCode.FORBIDDEN.get_code(),
                'message': self.ResultCode.FORBIDDEN.get_message(),
                'data': {}}

    @multidispatch(str)
    def forbidden(self, message):
        return {"code": self.ResultCode.FORBIDDEN.get_code(), 'message': message,
                'data': {}}


if __name__ == '__main__':
    res = CoexTaskService.task_list()
    CommonResult = CommonResult()
    result1 = CommonResult.success(res)
    result2 = CommonResult.success("哈哈成功了", {'sdf': 123, 'df': 234})
    result3 = CommonResult.failed()
    result4 = CommonResult.failed('更新失败')
    print(result1)
    print(result2)
    print(result3)
    print(result4)

```