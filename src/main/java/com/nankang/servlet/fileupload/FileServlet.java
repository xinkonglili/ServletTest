package com.nankang.servlet.fileupload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //判断上传的文件是普通表单还是文件表单
        if (ServletFileUpload.isMultipartContent(req)){
            return;//终止方法运行，说明这是一个普通的表单，直接返回
        }
        System.out.println("文件上传------------");

        String uploadPath = this.getServletContext().getRealPath("/webapp/upload");
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


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
