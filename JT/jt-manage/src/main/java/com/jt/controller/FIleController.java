package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FIleController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/pic/upload")
    public ImageVO upload(MultipartFile uploadFile){
        //将所有的业务操作,放到Service层中完成!!!
        ImageVO imageVO=fileService.upload(uploadFile);
        return imageVO;

    }




















    /**
     *  MultipartFile 接口作用 主要就是优化了文件上传 API集合
     * 1. 文件上传位置???   D:\JT-SOFT\images
     * 2. 判断一下文件目录是否存在
     * 3. 利用API实现文件上传.
     */
    @RequestMapping("/file")
    public String file(MultipartFile fileImage){
        String fileDir="D:/software/JT/images";
        File file=new File(fileDir);
        if(!file.exists()){//文件不存在则创建文件
            file.mkdirs();//一次性创建多级目录
        }
       // 文件信息=文件名+后缀名
        String fileName=fileImage.getOriginalFilename(); //获取原始的文件名称
        //将文件的整体封装为对象 文件路径(文件目录)/文件名称
        File imageFile=new File(fileDir+"/"+fileName);
        //实现文件上传,将文件字节数组传输到指定的位置.
        try {
            fileImage.transferTo(imageFile);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return "文件上传成功!!!!";
    }
}
