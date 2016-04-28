# cocc.cute

Android App 整套解决方案库，让你快速的搭建自己的项目。

##cute模块的依赖

##Wiget

###Toast
Toaster 对系统Toast封装

###文本计时器
TickTextView


###WebView
WebKitView 组件
waddjavascriptInterface来注入原生接口到JS中，但是在安卓4.2以下的系统中，这种方案却我们的应用带来了很大的安全风险。


##功能组件

###日志记录
L

1.android studio 设置颜色：
File->Settings 或Ctrl + Alt +S 找到 Editor -> Colors &Fonts -> Android Logcat
去掉Use Inberited attributes的勾选框，  再将 Foreground 前的复选框选上，勾选Foreground选择框，点击Foreground后面颜色选择框。就可以选颜色了。 选好颜色点击Apply–>OK就能保存到android studio 中。

样例配色:
  |标签|色值|
  | ---- |  --- |
  |Verbose |\#F9BDBB|
  |Info    |\#B39DDB|
  |Debug   |\#738FFE|
  |warning |\#EF6C00|
  |Error   |\#E00032|
  |Assert  |\#FF1696|


##变更记录：

2016/04/24
修改日志功能，增加cute 使用样例

2016/04/25
添加部分组件
