融聚V3 SDK使用说明

 - doc:
 包含API的 java doc 文档

 - sdk: 
 包含assets,jniLibs,libs 三部分
    ├── assets
    │   └── libsdk.zip
    ├── jniLibs
    │   ├── armeabi
    │   │   └── libluajava.so
    │   └── armeabi-v7a
    │       └── libluajava.so
    └── libs
        ├── UCommon.jar
        ├── UWS.jar
        ├── Usdk.jar
        ├── gson-2.3.jar
        └── zip4j-1.3.2.jar

  建议：为减小apk包的体积，建议 jniLibs 仅使用armeabi-v7a架构。

- demo:
 将sdk/* copy 到demo/uwsdemo/src/main/ 目录下即可，使用Android studio编译uswdemo

