/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190524    lsf     初始版本
 */
package com.algorithm.io.resume;

/**
 * SiteFileFetch.java 负责整个文件的抓取，控制内部线程 (FileSplitterFetch 类 )。
 * FileSplitterFetch.java 负责部分文件的抓取。
 * FileAccess.java 负责文件的存储。
 * SiteInfoBean.java 要抓取的文件的信息，如文件保存的目录，名字，抓取文件的 URL 等。
 * Utility.java 工具类，放一些简单的方法。
 * TestMethod.java 测试类。
 *
 * 基本原理是在http重传请求上携带下载进度
 *
 *
 * 实际上HTTP1.1协议（RFC2616）中定义了断点续传相关的HTTP头 Range和Content-Range字段，一个最简单的断点续传实现大概如下：
 * 1.客户端下载一个1024K的文件，已经下载了其中512K
 * 2. 网络中断，客户端请求续传，因此需要在HTTP头中申明本次需要续传的片段：Range:bytes=512000-这个头通知服务端从文件的512K位置开始传输文件
 * 3. 服务端收到断点续传请求，从文件的512K位置开始传输，并且在HTTP头中增加：Content-Range:bytes 512000-/1024000并且此时服务端返回的HTTP状态码应该是206，而不是200。
 *
 * 但是在实际场景中，会出现一种情况，即在终端发起续传请求时，URL对应的文件内容在服务端已经发生变化，此时续传的数据肯定是错误的。如何解决这个问题了？
 * 显然此时我们需要有一个标识文件唯一性的方法。在RFC2616中也有相应的定义，比如实现Last-Modified来标识文件的最后修改时间，这样即可判断出续传文件时是否已经发生过改动。
 * 同时RFC2616中还定义有一个ETag的头，可以使用ETag头来放置文件的唯一标识，比如文件的MD5值。终端在发起续传请求时应该在HTTP头中申明If-Match 或者If-Modified-Since 字段，帮助服务端判别文件变化。
 * 另外RFC2616中同时定义有一个If-Range头，终端如果在续传是使用If-Range。If-Range中的内容可以为最初收到的ETag头或者是Last-Modfied中的最后修改时候。
 * 服务端在收到续传请求时，通过If-Range中的内容进行校验，校验一致时返回206的续传回应，不一致时服务端则返回200回应，回应的内容为新的文件的全部数据。
 *
 */
public class TestMethod {
    public TestMethod()
    { ///xx/weblogic60b2_win.exe
        try{
            SiteInfoBean bean = new SiteInfoBean("http://localhost/xx/weblogic60b2_win.exe",
                    "L:\\temp","weblogic60b2_win.exe",5);
//SiteInfoBean bean = new SiteInfoBean("http://localhost:8080/down.zip","L:\\temp",
//            "weblogic60b2_win.exe",5);
            SiteFileFetch fileFetch = new SiteFileFetch(bean);
            fileFetch.start();
        }
        catch(Exception e){e.printStackTrace ();}
    }
    public static void main(String[] args)
    {
        new TestMethod();
    }
}