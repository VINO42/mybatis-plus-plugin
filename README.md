## mybatis plus idea plugin



[![Build Status](https://travis-ci.org/kana112233/mybatis-plus-plugin.svg?branch=dev)](https://travis-ci.org/kana112233/mybatis-plus-plugin)


[see wiki](https://github.com/kana112233/mybatis-plus-plugin/wiki)

#### 如何安装gradle
  * 下载最新的gradle 当前为6.6
  * 配置环境变量
    ```
     GRADLE_HOME=D:\DevTools\gradle-6.6

     GRADLE_USER_HOME=D:\MVNRepo
    ```
    这里的GRADLE_USER_HOME 直接用的原来的maven仓库
  * 配置idea
    File->Settings->BuildTools->gradle
    Use Gradle from : specified location 选择你本地装的gradle位置

    在idea插件商店找到Plugin DevKit 并安装重启
    Project Structure->SDKS 点击加号 选择和你当前idea版本相适配的plugin devkit插件
