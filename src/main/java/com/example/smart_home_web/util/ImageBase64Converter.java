package com.example.smart_home_web.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class ImageBase64Converter {

    public static String getImgBase64Str(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(data);
    }

    //获取文件
    public static String saveFile(MultipartFile file) {
        //创建输入输出流
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String path;
        String fileName;
        try {
            //获取项目路径（project）
             path = System.getProperty("user.dir");
            //获取文件的输入流
            inputStream = file.getInputStream();
            //获取上传时的文件名
            fileName = file.getOriginalFilename();
//            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            //注意是路径+文件名
            File targetFile = new File(path + "/wedding" + "/src" + "/main" + "/resources" + "/static" + "/scene/" + fileName);
            //如果之前的 String path = "d:/upload/" 没有在最后加 / ，那就要在 path 后面 + "/"
            //判断文件父目录是否存在
            if (!targetFile.getParentFile().exists()) {
                //不存在就创建一个
                targetFile.getParentFile().mkdir();
            }
            //获取文件的输出流
            outputStream = new FileOutputStream(targetFile);
            //最后使用资源访问器FileCopyUtils的copy方法拷贝文件
            FileCopyUtils.copy(inputStream, outputStream);
        } catch (
                IOException e) {
            //出现异常，则告诉页面失败
            return "";
        } finally {
            //无论成功与否，都有关闭输入输出流
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return path + "/wedding" + "/src" + "/main" + "/resources" + "/static" + "/scene/" + fileName;
    }
}
