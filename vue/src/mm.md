![](https://csdnimg.cn/release/blogv2/dist/pc/img/original.png)

版权声明：本文为博主原创文章，遵循 [CC 4.0 BY-SA](http://creativecommons.org/licenses/by-sa/4.0/) 版权协议，转载请附上原文出处链接和本声明。

今天遇到一个坑，这里给大家介绍一下。

想学习**分布式、微服务、JVM、多线程、架构、java、python**的童鞋，千万**不要**扫码，否则后果自负~

![](https://img-blog.csdnimg.cn/20190410135519572.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpbnpoaXFpYW5nMDMxNg==,size_16,color_FFFFFF,t_70)

现在很多文件上传类型都是multipart/form-data类型的，HTTP请求如下所示：

![](https://img-blog.csdn.net/20170809213023576?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGluemhpcWlhbmcwMzE2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

可是问题就在于如果用传统的Struts2或者[servlet](https://so.csdn.net/so/search?q=servlet&spm=1001.2101.3001.7020)等都可以很容易的实现文件接收的功能，例如下面的代码就可以实现：

```
boolean isMultipart = ServletFileUpload.isMultipartContent(request);//判断是否是表单文件类型  DiskFileItemFactory factory = new DiskFileItemFactory();  ServletFileUpload sfu = new ServletFileUpload(factory);  List items = sfu.parseRequest(request);//从request得到所有上传域的列表  for(Iterator iter = items.iterator();iter.hasNext();){      FileItem fileitem =(FileItem) iter.next();      if(!fileitem.isFormField()&&fileitem!=null){//判读不是普通表单域即是file          System.out.println("name:"+fileitem.getName());      }  } 
```

可是今天我把这一段代码放在SpringBoot上面的时候就怎么也接收不到文件信息了，一直以为是前端什么数据传输错了。后来才知道原来SpringBoot有它自己的接收请求的代码。下面就给大家详细介绍一下它是如何实现这个功能的。

首选做一个简单的案例，也就是单个文件上传的案例。（这个案例是基于SpringBoot上面的，所以大家首先得搭建好SpringBoot这个[框架](https://so.csdn.net/so/search?q=%E6%A1%86%E6%9E%B6&spm=1001.2101.3001.7020)）

前台HTML代码：

```
<html>  <body>  <form action="/upload" method="POST" enctype="multipart/form-data">  <input type="file" name="file"/>  <input type="submit" value="Upload"/>   </form>  </body>  </html>  
```

![](https://img-blog.csdn.net/20170809213046330?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGluemhpcWlhbmcwMzE2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

后台接收代码：

```
@RequestMapping("/upload")    @ResponseBody    public String handleFileUpload(@RequestParam("file") MultipartFile file) {    if (!file.isEmpty()) {    try {    BufferedOutputStream out = new BufferedOutputStream(    new FileOutputStream(new File(                                    file.getOriginalFilename())));                    System.out.println(file.getName());                  out.write(file.getBytes());                    out.flush();                    out.close();                } catch (FileNotFoundException e) {                    e.printStackTrace();    return "上传失败," + e.getMessage();                } catch (IOException e) {                    e.printStackTrace();    return "上传失败," + e.getMessage();                }    return "上传成功";            } else {    return "上传失败，因为文件是空的.";            }        }    
```

这样就可以实现对multipart/form-data类型文件的接收了。那如果是多个文件外加多个字段呢，下面接着看下一个多个文件上传的案例。

前台HTML界面：

```
<!DOCTYPE html>    <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"    xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">    <head>    <title>Hello World!</title>    </head>    <body>    <form method="POST" enctype="multipart/form-data" action="/batch/upload">     <p>文件1：<input type="text" name="id" /></p>    <p>文件2：<input type="text" name="name" /></p>    <p>文件3：<input type="file" name="file" /></p>    <p><input type="submit" value="上传" /></p>    </form>    </body>    </html>     
```

![](https://img-blog.csdn.net/20170809213113866?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGluemhpcWlhbmcwMzE2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

后台接收代码：

```
@RequestMapping(value = "/batch/upload", method = RequestMethod.POST)    @ResponseBody    public String handleFileUpload(HttpServletRequest request) {            MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);            List<MultipartFile> files = ((MultipartHttpServletRequest) request)                      .getFiles("file");             String name=params.getParameter("name");            System.out.println("name:"+name);            String id=params.getParameter("id");            System.out.println("id:"+id);  MultipartFile file = null;    BufferedOutputStream stream = null;    for (int i = 0; i < files.size(); ++i) {                  file = files.get(i);    if (!file.isEmpty()) {    try {    byte[] bytes = file.getBytes();                          stream = new BufferedOutputStream(new FileOutputStream(    new File(file.getOriginalFilename())));                          stream.write(bytes);                          stream.close();                      } catch (Exception e) {                          stream = null;    return "You failed to upload " + i + " => "                                  + e.getMessage();                    }                  } else {    return "You failed to upload " + i                              + " because the file was empty.";                  }            }    return "upload successful";        }    
```

这样就可以实现对多个文件的接收了功能了。SpringBoot还可以对接收文件的格式还有个数等等进行限制，我这里就不多说了，大家有兴趣的可以自己去了解了解。

千万要记住SpringBoot对multipart/form-data类型的文件接收和其它是不一样的，大家以后遇到的时候要千万小心，不要像我一样一往无前的踩进去还傻傻的以为是前端的错误。

**如果大家对文章有什么问题或者疑意之类的，可以加我订阅号在上面留言，订阅号上面我会定期更新最新博客。如果嫌麻烦可以直接加我wechat：lzqcode**

![](https://note.youdao.com/yws/api/group/67516516/noteresource/C5C7035B3E5147589191CE5096EC74CE/version/284?method=get-resource&shareToken=6CEF0002846344EAA8E81FC9E51F5C29&entryId=182345157)