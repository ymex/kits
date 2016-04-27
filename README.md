# cocc.cute
App 应用 易用的整套解决方案库，让你快速的搭建自己的项目。

##cute模块的依赖

dependencies {
    compile 'com.google.code.gson:gson:2.3'
}

##功能
1.日志记录
  使打印日志更加方便快捷 可自己定义日志的打印方案

  ###android studio 设置颜色：
  File->Settings 或Ctrl + Alt +S 找到 Editor -> Colors &Fonts -> Android Logcat
  去掉Use Inberited attributes的勾选框，  再将 Foreground 前的复选框选上，勾选Foreground选择框，点击Foreground后面颜色选择框。就可以选颜色了。 选好颜色点击Apply–>OK就能保存到android studio 中。

  样例配色:
  |标签|色值|
  | :---- |  :---: |
  |Verbose |\#F9BDBB|
  |Info    |\#B39DDB|
  |Debug   |\#738FFE|
  |warning |\#EF6C00|
  |Error   |\#E00032|
  |Assert  |\#FF1696|


2.为WebView中的Java与JavaScript提供【安全可靠】的多样互通方案
  waddjavascriptInterface来注入原生接口到JS中，但是在安卓4.2以下的系统中，这种方案却我们的应用带来了很大的安全风险。
  这里使用[safe-java-js-webview-bridge](https://github.com/pedant/safe-java-js-webview-bridge)
  来解决安全问题。

##组件

1.TickTimer 计时器

2.Toaster 对系统Toast封装



##变更记录：

2016/04/24
修改日志功能，增加cute 使用样例

2016/04/25
添加部分组件
