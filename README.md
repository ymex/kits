# cocc.cute

Android App  整套解决方案库，让你快速的搭建自己的项目。

##使用
```
compile 'cn.ymex:cute:0.0.7'
```

##flux for android

cute.flux是Facebook的Flux 架构的Android实现。
cute.flux模块 定义了flux 核心组件，基于flux模式快速开发。
###使用
cute.flux 其核心依赖于事件发布/订阅框架，如[otto](https://github.com/greenrobot/EventBus) ，[EventBus](https://github.com/greenrobot/EventBus)
你可以使用这些框架，也可以自己编写发布/订阅代码, cute.flux 并不强求，这些依赖实现。

1. 实现Flux.BusAdapter接口。将其实例配置到cute.flux ,Flux.instance().setBusAdapter(busAdapter);
2.


##RecyclerViewAdapter
使RecyclerView可添加onItemClick 事件,并可添加头部和尾部。


##vikewkit
