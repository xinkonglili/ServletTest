/*
package com.nankang.servlet.fileupload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
*/
/*import javax.servlet.http.HttpServlet;*//*

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class FileServlet extends HttpServlet {*/
/*
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //判断上传的文件是普通表单还是文件表单
        if (ServletFileUpload.isMultipartContent(req)){
            return;//终止方法运行，说明这是一个普通的表单，直接返回
        }
        System.out.println("文件上传------------");

       // String uploadPath = this.getServletContext().getRealPath("/webapp/upload");
        String uploadPath = "D:\\nankang company\\ProjectTest\\ServletTest\\src\\main\\webapp\\upload";
        System.out.println(uploadPath);
        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()){
            uploadFile.mkdir();
        }

        //最重要的2句  缓存，临时文件
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //2、获取servletFileupload
        ServletFileUpload upload = new ServletFileUpload(factory);

 //监听文件上传的速度
        upload.setProgressListener(new ProgressListener() {
            @Override
            public void update(long pByteRead, long pContentLength, int pItems) {
                System.out.println("总大小："+ pContentLength+"已上传：" + pByteRead);
            }
        });

        //处理乱码问题
        upload.setHeaderEncoding("UTF-8");
        upload.setFileSizeMax(1024 * 1024 * 10);
        upload.setSizeMax(1024 * 1024 * 10);


        //3、处理上传文件
        //把前端请求解析，封装成一个FileItem对象
        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            //fileItems.for:快捷写法
            for (FileItem fileItem : fileItems) {
                String msg = null;
                if (fileItem.isFormField()) {
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString("UTF-8");
                    System.out.println(name + ":" + value);
                } else {
                    //处理文件
                    //拿到文件名字
                    String uploadFileName = fileItem.getName();
                    if (uploadFileName.trim().equals("") || uploadFileName == null) {
                        continue;
                    }

                    //获取文件上传的名字
                    String fileName = uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
                    String fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
                    String uuidPath = UUID.randomUUID().toString();
                    //存放地址
                    String realPath = uploadPath + "/" + uuidPath;
                    File realPathFile = new File(realPath);
                    if (!realPathFile.exists()) {
                        realPathFile.mkdir();
                    }

                    //文件传输
                    //获取文件上传的流
                    InputStream inputStream = fileItem.getInputStream();
                    FileOutputStream fos = new FileOutputStream(realPathFile + "/" + fileName);
                    //创建一个缓冲区
                    byte[] buffer = new byte[1024 * 1024];
                    //判断是否读取完毕
                    int len = 0;
                    //如果大于0说明还存在数据
                    while ((len = inputStream.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    //关闭流
                    fos.close();
                    inputStream.close();

                    msg = "文件上传成功";
                }
                //servlet请求转发消息
                req.setAttribute("msg", msg);
                req.getRequestDispatcher("info.jsp").forward(req, resp);

            }
        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        }


    }*//*


  */
/*  @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }*//*



}
*/


package com.nankang.servlet.fileupload;

import com.nankang.util.UploadUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@MultipartConfig
@WebServlet(name = "UploadPhotoServlet", value = "/UploadPhotoServlet")
public class UploadPhotoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("utf-8");//设置编码格式
        //获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        String path =  "D:\\nankang company\\ProjectTest\\ServletTest\\src\\main\\webapp\\upload";;//本地计算机中的	String path = req.getSession().getServletContext().getRealPath("/img");//这个是tomact的wabapps中的工程的
        System.out.print(path);
        //如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
        //设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
        /**
         * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上，
         * 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
         * 然后再将其真正写到 对应目录的硬盘上
         */
        factory.setRepository(new File(path));
        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
        factory.setSizeThreshold(1024*1024);
        //初始化ServletFileUpload，可以说是一个封装好的的容器
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            //可以上传多个文件
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list)
            {
                //获取表单的属性名字
                String name = item.getFieldName();
                //如果获取的 表单信息是普通的 文本 信息
                if(item.isFormField())
                {
                    //获取用户具体输入的字符串，因为表单提交过来的是 字符串类型的
                    String value = item.getString();
                    request.setAttribute(name, value);
                }
                //对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
                else
                {
                    //获取路径名
                    String value = item.getName();//真实文件名
                    value= UploadUtils.generateRandonFileName(value);//uuid名
                    //索引到最后一个反斜杠
                    int start = value.lastIndexOf("\\");
                    //截取 上传文件的 字符串名字，加1是 去掉反斜杠，
                    String filename = value.substring(start+1);
                    //request.setAttribute(name, filename);
                    //真正写到磁盘上
                    InputStream is =item.getInputStream();
                    FileOutputStream fos= new FileOutputStream(filename);
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length =is.read(b))) {
                        fos.write(b,0, length);
                    }
                    fos.flush();
                    fos.close();
                    item.write( new File(path,filename));//第三方提供的
                    request.setAttribute("filename", filename);
                }
            }
            request.getRequestDispatcher("upload.jsp").forward(request, response);
        } catch (FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }
}
