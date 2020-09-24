package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ImageVO implements Serializable {
    //{"error":0,"url":"图片的保存路径","width":图片的宽度,"height":图片的高度}
    //说明:
    //error: 代表文件上传的错误. 0 文件上传正确 1.文件上传失败.
    //url地址: 访问图片的网络地址… 用户通过url地址获取图片信息
    //访问图片的物理地址… 真实存储的地址 D:/a/a.jpg
    private Integer error;
    private String  url;     //图片虚拟访问路径
    private Integer width;  //宽度
    private Integer height; //高度


    //封装静态的API success fail

    public static ImageVO success(String url,Integer width,Integer height){
        ImageVO imageVO=new ImageVO(0,url,width,height);
        return imageVO;
    }
    public static ImageVO fail(){
        ImageVO imageVO=new ImageVO(1,null,null,null);
        return imageVO;
    }




}
