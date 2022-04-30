##  Python  几种常见设计模式

###  适配器模式 

> 解决新旧功能接口不兼容问题

```python
class Computer:
    def __init__(self, name):
        self.name = name

    def __str__(self):
        return 'the {} computer'.format(self.name)

    def execute(self):
        return 'executes a program'


class Synthesizer:
    def __init__(self, name):
        self.name = name

    def __str__(self):
        return 'the {} synthesizer'.format(self.name)

    def play(self):
        return 'is playing an electronic song'

class Human:
    def __init__(self, name):
        self.name = name

    def __str__(self):
        return '{} the human'.format(self.name)

    def speak(self):
        return 'says hello'


class Adapter:
    def __init__(self, obj, adapted_methods):
        self.obj = obj
        self.__dict__.update(adapted_methods)

    def __str__(self):
        return str(self.obj)




if __name__ == '__main__':
    objects = [Computer('Asus')]
    synth = Synthesizer('moog')
    objects.append(Adapter(synth, dict(execute=synth.play)))
    human = Human('Bob')
    objects.append(Adapter(human, dict(execute=human.speak)))

    for i in objects:
        print('{} {}'.format(str(i), i.execute()))
        print('type is {}'.format(type(i)))
```

### 建造者模式 

> 相同的流程、不同的表示、修建者。也就是同一个对象（建筑）在同一修建者组织下，
> 以相同的实例化流程（施工流程）来达到不同的表示效果（毛坯、写字楼）
> 这样的好处使得构建与它的表示分离，使得同样的构建过程可以创建不同的表示。

```python
class Builder():
    """建造流程：原料—施工"""
    def __init__(self):
        self.materiel = None
        self.design = None

    def run(self):
        print('修建完工！设计建筑: %s | 购买原料: %s' % (self.design, self.materiel))


class A(Builder):
    """方案A，修建毛坯房"""
    def get_materiel(self):
        self.materiel = "砖瓦"

    def get_design(self):
        self.design = "毛坯房"


class B(Builder):
    """方案B，修建写字楼"""
    def get_materiel(self):
        self.materiel = "玻璃"

    def get_design(self):
        self.design = "写字楼"


class Director:
    """调度：买原料-组织施工"""
    def __init__(self):
        self.programme = None

    def build(self):
        self.programme.get_materiel()
        print("购买原料:{}".format(self.programme.materiel))
        self.programme.get_design()
        print("设计方案:{}".format(self.programme.design))
        self.programme.run()


if __name__ == '__main__':
    # A B 可以理解为不同的方法
    # 修建毛坯房
    test = Director()
    test.programme = A()
    test.build()

    # 修建写字楼
    test = Director()
    test.programme = B()
    test.build()
```

### 策略模式
> 如果在一个系统里面有许多类，它们之间的区别仅在于它们的行为，那么使用策略模式可以动态> > 地让一个对象在许多行为中选择一种行为。
> 一个系统需要动态地在几种算法中选择一种。 
> 如果一个对象有很多的行为，如果不用恰当的模式，这些行为就只好使用多重的条件选择语句来> > 实现。

```python
class People:

    def __init__(self, func=None):
        if func:
            self.speak = types.MethodType(func, self)

    def speak(self):
        print("说中文")


def speak_english(self):
    print('说英语')


def speak_german(self):
    print('说德语')


if __name__ == '__main__':
    test1 = People()
    test2 = People(speak_english)
    test3 = People(speak_german)
    [func.speak() for func in [test1, test2, test3]]
```

### 抽象工厂模式

> 抽象工厂有两个方法生产车架和制造轮胎，但是他没有指定是生产那种牌子的，
> 上图有两种牌子：飞鸽自行车、永久自行车，也就可以增加一个凤凰自行车等等。
> 工厂模式里面就指定了飞鸽厂和永久厂，并且这两个厂只能生产各自对应的轮胎。

```python
class A:

    def __init__(self):
        self.word = "运行A"

    def run(self):
        print(self.word)


class B:

    def __init__(self):
        self.word = "运行B"

    def run(self):
        print(self.word)


class Interface:
    """
    抽象工厂模式接口类
    :param classname:
    :return:
    """
    def __init__(self, classname=None):
        self.test = classname

    def run(self):
        self.test().run()


if __name__ == '__main__':
    test1 = Interface()
    test1.test = A
    test1.run()
    test1.test = B
    test1.run()
```

### 观察者模式

> 观察者核心：销售人员，被观察者number数据

```python
class Observer:
    """观察者核心：销售人员，被观察者number数据"""
    def __init__(self):
        self._number = None
        self._department = []

    @property
    def number(self):
        return self._number

    @number.setter
    def number(self, value):
        self._number = value
        print('当前客户数：{}'.format(self._number))
        for obj in self._department:
            obj.change(value)
        print('------------------')

    def notice(self, department):
        """相关部门"""
        self._department.append(department)


class Hr:
    """人事部门"""
    def change(self, value):
        if value < 10:
            print("人事变动：裁员")

        elif value > 20:
            print("人事变动：扩员")

        else:
            print("人事不受影响")


class Factory:
    """工厂类"""
    def change(self, value):
        if value < 15:
            print("生产计划变动：减产")
        elif value > 25:
            print("生产计划变动：增产")
        else:
            print("生产计划保持不变")


if __name__ == '__main__':
    observer = Observer()
    hr = Hr()
    factory = Factory()
    observer.notice(hr)
    observer.notice(factory)
    observer.number = 10
    observer.number = 15
    observer.number = 20
    observer.number = 25
```

### 原型模式

> 要实现多个人的自我介绍，一般方法是每个人都创建一个对象，
> 但是使用原型模式之后，只需要实例化一个对象（标准人）,
> 后面的人都已这个标准人为基础来实现个性化。

```python
# -*- coding:utf-8 -*-
import copy
class Information:
    """个人信息"""

    def __init__(self):
        self.name = None
        self.ager = None
        self.height = None

    def run(self):
        """
        自我介绍方法
        :return:
        """
        print("我叫{}： 年龄：{} 身高：{}".format(self.name, self.ager, self.height))


class Prototype:
    def __init__(self, obj):
        self.copy_object = obj()

    def clone(self, **attr):
        obj = copy.deepcopy(self.copy_object)
        obj.__dict__.update(attr)
        return obj


if __name__ == '__main__':
    test = Prototype(Information)
    a = test.clone(name='张山', ager="30", height='170cm')
    a.run()
    b = test.clone(name='李飞', ager="20", height='190cm')
    b.run()
```

### 代理模式

> 通过代理访问某个对象

```python
# -*- coding:utf-8 -*-


class Jurisdiction:
    """权限类"""

    def level1(self):
        print('权限等级1')

    def level2(self):
        print('权限等级2')

    def level3(self):
        print('权限等级3')

    def level4(self):
        print('权限等级4')


class Proxy:

    def __init__(self, name):
        self.user = name
        self._jurisdiction = Jurisdiction()

    def leve(self):

        if self.user == 'a':
            return self._jurisdiction.level1()
        elif self.user == 'b':
            return self._jurisdiction.level2()
        elif self.user == 'c':
            return self._jurisdiction.level3()
        elif self.user == 'd':
            return self._jurisdiction.level4()
        else:
            print('无此权限')

if __name__ == '__main__':
    test = Proxy('a')
    test.leve()
    test.user = 'b'
    test.leve()
    test.user = 'c'
    test.leve()
    test.user = 'd'
    test.leve()
    test.user = 'e'
    test.leve()
```

## Python 获取某个类所有子类列表

### 代码实现

```python
class BodyItem:
    @staticmethod
    def isMacth(x):
        if x == "aa":
            return True
        return False

    @staticmethod
    def execute():
        print("BodyItem 执行语句")


class BodyItem1(BodyItem):
    @staticmethod
    def isMacth(x):
        if x == "aa":
            return True
        return False

    @staticmethod
    def execute():
        print("BodyItem1 执行语句")


class BodyItem2(BodyItem):
    @staticmethod
    def isMacth(x):
        if x == "bb":
            return True
        return False

    @staticmethod
    def execute():
        print("BodyItem2 执行语句")


class BodyItem3(BodyItem):
    @staticmethod
    def isMacth(x):
        if x == "cc":
            return True
        return False

    @staticmethod
    def execute():
        print("BodyItem3 执行语句")


def get_subclasses(cls):
    """
    获取python类的所有子类
    :param cls: 父类
    :return: list
    """
    return [subcls for subcls in cls.__subclasses__()]


if __name__ == '__main__':
    subList = get_subclasses(BodyItem)
    for subObject in subList:
        if subObject.isMacth("cc"):
            subObject.execute()
```

