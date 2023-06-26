# pywin32 自动化模块
## 使用
### 查询句柄
```python
# 根据类名及标题名查询句柄，
hwnd = win32gui.FindWindow("Tfrm_YzzPlayer","叶子猪手游模拟器")
# 查找指定句柄的子句柄，后两个参数为子类的类名与标题，如果没有或不确定，可以写None
hwnd = win32gui.FindWindow(hwnd,None,"sub_class","sub_title")
```

### 修改窗口大小
```python
# 没有直接修改窗口大小的方式，但可以曲线救国，几个参数分别表示句柄,起始点坐标,宽高度,是否重绘界面 ，如果想改变窗口大小，就必须指定起始点的坐标，没果对起始点坐标没有要求，随便写就可以；如果还想要放在原先的位置，就需要先获取之前的边框位置，再调用该方法即可
win32gui.MoveWindow(hwnd,20,20,405,756,True)
```
#### 前台后台
```python
# 指定句柄设置为前台，也就是激活
win32gui.SetForegroundWindow(hwnd)
# 设置为后台
win32gui.SetBkMode(hwnd, win32con.TRANSPARENT)
```
### 按键
```python
# 在这里两几种方式可以选择 可以使用win32gui包和win32api的包，目前未深入了解，感觉是一样的，每一个里面还有PostMessage与SendMessage两都可选，依据其他文档的说法是SendMessage是同步的，在成功执行后才会返回，而PostMessage是异步执行的，直接返回，只是把内容加在队列里
# 几个参数分别为: 操作的句柄 , 按键的类型(是按下或者是弹起), 键码（大部分的功能键在win32con包中都，对于常用的数字或字母，直接去查找ASII码即可，如A 65 等等），相对于句柄中的位置(在这里需要使用win32api.MAKELONG(x,y)将两个地址转换为一个长地址；
# 在这种情况下，可以做到后台的操作
# 需要注意的是每一个按键要有按下与弹起两个过程，比果我们要按Enter键，就需要有两句代码，第二个参数分别为 KEYDOAWN与 KEYUP ，如果是组合键，就先把组合键分别按下后再分别弹起即可
# win32gui.PostMessage(tid, win32con.WM_KEYDOWN, win32con.VK_RETURN, 0)
# win32gui.SendMessage(tid, win32con.WM_KEYDOWN, win32con.VK_RETURN, 0)
win32api.SendMessage(hwd, win32con.WM_LBUTTONDOWN, win32con.MK_LBUTTON, long_position)
win32api.PostMessage(hwd, win32con.WM_LBUTTONDOWN, win32con.MK_LBUTTON, long_position)
```
### 发送消息
在这里有两种方式，一种是找到输入框的句柄，将键类型设置为SETTEXT
另外一种方式为将需要输入的内容放到粘贴板中，直接粘贴即可
```python
# 方式一为网络上说明，自己在测试的时候一直不成功，因为我是操作安卓模拟器里面的软件，查找不到输入框的句柄的原因
win32gui.SendMessage(tid, win32con.WM_SETTEXT,None,‘hello')
# 方式二，测试通过 其实就是把内容放到剪贴板中，直接ctrl + v即可，感觉适用于找不到输入框的具体句柄，但焦点已经在输入框中的情况
# 定义两个方法，来读写剪贴板，注意要和目标系统的编码方式相同
def getText():
# 读取剪切板
w.OpenClipboard()
d = w.GetClipboardData(win32con.CF_TEXT)
w.CloseClipboard()
return d
def setText(aString):
# 写入剪切板
w.OpenClipboard()
w.EmptyClipboard()
w.SetClipboardData(win32con.CF_TEXT, aString.encode(encoding='gbk'))
w.CloseClipboard()
```
### 示例代码
下面的代码功能是：从一个文本读取每一行记录，然后到安卓模拟器中的旺信中查询联系人，发送指定内容的消息。
```python
# coding: utf-8
import win32gui, win32api, win32con
import time
import win32clipboard as w

import logging


def click_position(hwd, x_position, y_position, sleep):
  """
  鼠标左键点击指定坐标
  :param hwd: 
  :param x_position: 
  :param y_position: 
  :param sleep: 
  :return: 
  """
  # 将两个16位的值连接成一个32位的地址坐标
  long_position = win32api.MAKELONG(x_position, y_position)
  # win32api.SendMessage(hwnd, win32con.MOUSEEVENTF_LEFTDOWN, win32con.MOUSEEVENTF_LEFTUP, long_position)
  # 点击左键
  win32api.SendMessage(hwd, win32con.WM_LBUTTONDOWN, win32con.MK_LBUTTON, long_position)
  win32api.SendMessage(hwd, win32con.WM_LBUTTONUP, win32con.MK_LBUTTON, long_position)
  time.sleep(int(sleep))


def getText():
  # 读取剪切板
  w.OpenClipboard()
  d = w.GetClipboardData(win32con.CF_TEXT)
  w.CloseClipboard()
  return d


def setText(aString):
  # 写入剪切板
  w.OpenClipboard()
  w.EmptyClipboard()
  w.SetClipboardData(win32con.CF_TEXT, aString.encode(encoding='gbk'))
  w.CloseClipboard()


def input_content(hwd, content, sleep, is_enter):
  """
  从站贴板中查找输入的内容
  :param hwd: 
  :param content: 
  :param sleep: 
  :param is_enter 是否要在最后输入enter键,内容与enter之间间隔一秒
  :return: 
  """
  setText(content)
  time.sleep(0.3)
  click_keys(hwd, win32con.VK_CONTROL, 86)
  if is_enter:
    time.sleep(1)
    click_keys(hwd, win32con.VK_RETURN)
  time.sleep(sleep)


def click_keys(hwd, *args):
  """
  定义组合按键
  :param hwd: 
  :param args: 
  :return: 
  """
  for arg in args:
    win32api.SendMessage(hwd, win32con.WM_KEYDOWN, arg, 0)
  for arg in args:
    win32api.SendMessage(hwd, win32con.WM_KEYUP, arg, 0)


def wangwang_operation(hwd, salesname, content1, content2):
  """
  阿里旺旺的操作
  :param hwd: 句柄
  :param salesname: 
  :param content1: 发送一
  :param content2: 发送二
  :return: 
  """
  # 下方联系人标签
  click_position(hwd, 200, 685, 2)
  # 新增好友按钮
  click_position(hwd, 372, 44, 3)
  # 搜索好友
  input_content(hwd, salesname, 3, False)
  # 点击搜索
  click_position(hwd, 345, 117, 5)
  # 点击发送消息
  click_position(hwd, 350, 700, 3)
  # 发送消息一
  input_content(hwd, content1, 1, False)
  click_keys(hwd, win32con.VK_CONTROL, win32con.VK_RETURN)
  time.sleep(1)
  input_content(hwd, content2, 1, False)
  click_keys(hwd, win32con.VK_CONTROL, win32con.VK_RETURN)
  time.sleep(1)
  # 返回原始状态
  click_position(hwd, 20, 45, 1)
  time.sleep(1)
  click_position(hwd, 20, 45, 1)


def wangwang_operation_by_file(hwd, file, content1, content2):
  with open(file, 'r') as f:
    line = f.readline()
    while len(line) >= 1:
      try:
        line = line.replace('\r', '').replace('\n', '')
        print("正在处理   %s   ....................................." % line)
        wangwang_operation(hwd, line, content1, content2)
        line = f.readline()
      except BaseException as e:
        print("处理 %s 时出错了............." % line)
        logging.exception(e)


if __name__ == "__main__":
  # 查找句柄
  hwnd = win32gui.FindWindow("Tfrm_YzzPlayer", "叶子猪手游模拟器")
  if int(hwnd) <= 0:
    print("没有找到模拟器，退出进程................")
    exit(0)
  print("查询到模拟器句柄: %s " % hwnd)
  win32gui.MoveWindow(hwnd, 20, 20, 405, 756, True)
  time.sleep(2)
  # 屏幕坐标到客户端坐标
  # print(win32gui.ScreenToClient(hwnd, (1446, 722)))
  # 设置为前台
  # win32gui.SetForegroundWindow(hwnd)
  # 设置为后台
  win32gui.SetBkMode(hwnd, win32con.TRANSPARENT)
  time.sleep(2)
  # 下列的后三个参数分别表示: 文件路径 打招呼句子 广告语
  wangwang_operation_by_file(hwnd, "D:/2.txt", "你好", "测试广告语")
````
