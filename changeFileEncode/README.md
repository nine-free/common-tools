
##### 文件的批量转码

```
1、执行命令 mvn package  target/release 下得到可执行jar lib目录是依赖的jar
2、执行命令 java -jar ChangeFileEncode.jar D:\release\src\reportadmin gbk D:\release\dest\reportadmin utf-8
参数说明 第一个是源文件路径 第二个是源文件编码 第三个是目标文件路径 第四个是目标编码
处理完成会输出处理文件个数&耗时 

```
注意
```
ChangeFileEncode 只处理.java文件
ChangeFileEncodeExt 处理jsp 简单匹配了一下页面编码
```
