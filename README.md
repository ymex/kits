[ ![Download](https://api.bintray.com/packages/ymex/maven/kits/images/download.svg) ](https://bintray.com/ymex/maven/kits/_latestVersion)

# Android kits

android 便捷库，日常开发常用工具。

```
compile 'cn.ymex:kits:1.3.1'
```

目录
```

```

## 工具类

### AppContent
继承自Application 增加app 进入前后台的监听回调方法。

```
protected void applicationDidEnterBackground() //后台回调
protected void applicationDidEnterForeground() //前台回调
public int getCheckDelay()//进行前后台延时
```


### kits:Toaster
Toast

### FragmentManagerWrap
简单的标签切换管理，防止重叠。
