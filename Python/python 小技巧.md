# python小技巧

## 消除if else 语句让业务代码更简洁

```python
import functools


def value_dispatch(func):
    registry = {}

    @functools.wraps(func)
    def wrapper(arg0, *args, **kwargs):
        try:
            delegate = registry[arg0]
        except KeyError:
            pass
        else:
            return delegate(arg0, *args, **kwargs)

        return func(arg0, *args, **kwargs)

    def register(value):
        def wrap(func):
            if value in registry:
                raise ValueError(
                    '@value_dispatch: there is already a handler '
                )
            registry[value] = func
            return func
        return wrap

    def register_for_all(values):
        def wrap(func):
            for value in values:
                if value in registry:
                    raise ValueError(
                        '@value_dispatch: there is already a handler '
                    )
                registry[value] = func
            return func
        return wrap

    wrapper.register = register
    wrapper.register_for_all = register_for_all
    return wrapper


@value_dispatch
def get_discount(level):
    return '等级错误'


@get_discount.register(1)
def parse_level_1(level):
    "大量计算代码"
    discount = 0.1
    return discount


@get_discount.register(2)
def parse_level_2(level):
    "大量计算代码"
    discount = 0.2
    return discount


@get_discount.register(3)
def parse_level_3(level):
    "大量计算代码"
    discount = 0.3
    return discount


@get_discount.register(4)
def parse_level_4(level):
    "大量计算代码"
    discount = 0.4
    return discount


@get_discount.register(5)
def parse_level_5(level):
    "大量计算代码"
    discount = 0.5
    return discount


@get_discount.register(6)
def parse_level_1(level):
    "大量计算代码"
    discount = 3 + 2 - 5 * 0.1
    return discount


@get_discount.register(7)
def parse_level_1(level):
    "大量计算代码"
    discount = 0.7
    return discount


if __name__ == '__main__':
    discount = get_discount(7)
    print('等级3的用户，获得的折扣是：%s' % discount)
    # test_oracle1 = TestOracle('mmbs_user', '123456', '10.175.94.58', '7035', 'MMBS')
    # test_oracle1.select_single(DEPART_INDEX_SQL)

```

