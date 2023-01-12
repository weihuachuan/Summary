# SpringBoot中自定义注解
注解是一种能被添加到java代码中的元数据（python中的函数装饰器），类、方法、参数、变量和包都可以用注解来修饰。用来定义一个类、属性或者一些方法，以便程序能被捕译处理。相当于一个说明文件，告诉应用程序某个被注解的类或者属性是什么，要怎么处理。对被修饰的代码本身没有直接影响。

使用范围：
 1. 为编译器提供信息：注解能被编译器检测到错误或抑制警告。
 2. 编译时和部署时的处理： 软件工具能处理注解信息从而生成代码，XML文件等等。
 3. 运行时的处理：有些注解在运行时能被检测到。

自定义注解的步骤：
  1. 定义注解 
  2. 配置注解
  3. 解析注解


自定义注解举例

第一步：自定义的注解如下

```java
package my_annotation;

import java.lang.annotation.*;

@Target(value={ElementType.METHOD}) //只能被使用在方法上面
@Retention(RetentionPolicy.RUNTIME)//设置注解的生命力在运行期，那么这个注解可以在运行期的加载阶段被加载到Class对象中。那么在程序运行阶段，我们可以通过反射得到这个注解，并通过判断是否有这个注解或这个注解中属性的值，从而执行不同的程序代码段
@Documented
public @interface MyAnnotation {
    public String name();

    int age();

    String sex() default "男";

    String[] hobby();
}
```

第二步：创建一个类，新建方法使用该注解
```java
package my_annotation;

public class TestAnnotation {
    @MyAnnotation(name = "Shine",age = 16, hobby ={"跑步","打游戏"})//注解里面的字段
    public String get(){
        return "Hello Annotation";
    }
}
```

第三步：利用反射获取注解。创建一个类，代码如下：
```java
package my_annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) {
        try {
            //获取Class对象
            Class mylass = Class.forName("my_annotation.TestAnnotation");
            //获得该对象身上配置的所有的注解
            Annotation[] annotations = mylass.getAnnotations();
            System.out.println(annotations.toString());
            //获取里面的一个方法
            Method method = mylass.getMethod("get");
            //判断该元素上是否配置有某个指定的注解
            if(method.isAnnotationPresent(MyAnnotation.class)){
                System.out.println("UserController类的get方法上配置了MyAnnotation注解！");
                //获取该元素上指定类型的注解
                MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
                System.out.println("name: " + myAnnotation.name() + ", age: " + myAnnotation.age()
                        + ",sex:"+myAnnotation.sex()+", hobby: " + myAnnotation.hobby()[0]);
            }else{
                System.out.println("UserController类的get方法上没有配置MyAnnotation注解！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
```
