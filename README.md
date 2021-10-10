# Netty-demo
Netty-demo

目前Json序列化还有问题


## 异常报错及解决方案

```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```

异常解决方案，缺少相关依赖，导入对应依赖即可

```
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.25</version>
</dependency>
```


## 修改Typora图片格式
文件–>偏好设置–>外观–>打开主题文件夹–>找到github.css文件–>在github.css文件的最下面添加如下的css样式。
```css
p .md-image:only-child{
    width: auto;
    text-align: inherit;
}
```


## idea使用技巧
右键匿名内部类refactor，将匿名内部类转为静态内部类
