# 博学谷安卓项目

by:小污  qqq948747710

图片都存放于客户端文件夹的image下，如果看不到可能需要梯子。

##关于后端


后端是使用GO语言开发的，算是做一下简单的对接。

根据网络资料自写了session，用来保存用户状态信息。

使用方法：

数据库：在mysql下恢复erudite.sql文件。

在main.go目录下，go build即可，然后./EruditeValley即可启动后端，端口是3000，如果需要更改可以在web/WebSerive.go更改。

![houduan](https://raw.githubusercontent.com/qqq948747710/eruditevalley/master/%E5%AE%A2%E6%88%B7%E7%AB%AF/iamge/houdan.JPG)


## 关于客户端

我的android是3.6.1

index页面
![index](https://raw.githubusercontent.com/qqq948747710/eruditevalley/master/%E5%AE%A2%E6%88%B7%E7%AB%AF/iamge/index.JPG)

video页面
![video](https://raw.githubusercontent.com/qqq948747710/eruditevalley/master/%E5%AE%A2%E6%88%B7%E7%AB%AF/iamge/video.JPG)

习题页面
![xiti](https://raw.githubusercontent.com/qqq948747710/eruditevalley/master/%E5%AE%A2%E6%88%B7%E7%AB%AF/iamge/xiti.JPG)

选择界面
![option](https://raw.githubusercontent.com/qqq948747710/eruditevalley/master/%E5%AE%A2%E6%88%B7%E7%AB%AF/iamge/xitili.JPG)

我的资料界面
![my](https://raw.githubusercontent.com/qqq948747710/eruditevalley/master/%E5%AE%A2%E6%88%B7%E7%AB%AF/iamge/my.JPG)

登录界面
![login](https://raw.githubusercontent.com/qqq948747710/eruditevalley/master/%E5%AE%A2%E6%88%B7%E7%AB%AF/iamge/login.JPG)

还有一些这里没列出，可以自行体验。

## 使用方法:

把app文件放入项目中即可，不过这样可能会出现一些不可预料的错误。可以自行新建项目，引入好库和配置项。然后再把main中的java文件和资源文件等等导入。

关于库的引入：

build.gradle(app)文件

```Java
//这边需要用到java8的支持，不然饺子video会报错
android {
    compileSdkVersion 28

    compileOptions {
        targetCompatibility = "8"
        sourceCompatibility = "8"
    }
}

dependencies {

    implementation 'io.reactivex.rxjava2:rxjava:2.1.14' //rxjava
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'

    implementation 'com.squareup.retrofit2:converter-gson:2.5.0' //retrofit2网络框架
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'

    implementation 'com.github.franmontiel:PersistentCookieJar:v1.0.1' //persistentCookie实现用户状态登录

    implementation 'com.android.support:recyclerview-v7:28.0.0'

    implementation 'cn.jzvd:jiaozivideoplayer:7.3.0'//这个是饺子video播放器,功能强大，自定义能力强！

    implementation 'com.github.bumptech.glide:glide:4.8.0'//这个是一个图片加载器，用于饺子video的缩略图加载。
   }

```
## 关于AndroidManifest.xml
```Java
 android:screenOrientation="portrait"
 //这一条是给video横屏的权限，不过会报红可以忽略
 
 //权限
     <uses-permission android:name="android.permission.INTERNET" />
       <uses-permission android:name="android.permission.INTERNET"/>
        //它可以监听用户的连接状态并在用户重新连接到网络时重启之前失败的请求
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
        //用于硬盘缓存和读取
        <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

## 技术要点
RxJava+Retrofit2的网络请求框架,项目使用了传统的mvc开发模式。

Services文件夹：
   里面存放着网络请求的服务、请求接口和网络实体类用于把网络请求json字符串转成实体对象，由retrofit2对这些接口通过注释去实现，Service类都是extends Service。Service简单的使用了一个自写队列的操作，可以有效等待多队列请求后刷新视图。
   
View文件夹：
   里面都是一些Activity和一些自写的轮播图组件，Fragment碎片等等。
Model文件夹：
   里面是一些数据对象，还有单例的数据管理类。
   
## 模块关系
  View--》Controller--》Model---》Services
