package com.jt.service;

import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service//将此类将给spring容器管理
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements  FileService {
    //通过properties配置文件为属性赋值
    @Value("${image.dirPath}")  //由spring框架提供的spring EL表达式
    private String dirPath;
    @Value("${image.urlPath}")
    private String urlPath;

    //为了防止Set集合每次都要创建,则通过static代码块的形式负责封装数据
    private static Set<String>  imageSet=new HashSet<>();

    static {
        imageSet.add(".jpg");
        imageSet.add(".png");
        imageSet.add(".gif");
        //....

    }



    /**
     * 文件上传具体步骤:
     *      1.如何校验用户上传的是图片?    jpg|png
     *      2.如何访问用户上传恶意程序     木马.exe.jpg  宽度*高度
     *      3.应该采用分目录存储的方式     保存数据
     *      4.上传的文件名称应该尽量避免重名  自定义文件名称...  UUID.后缀...
     * @param uploadFile
     * @return
     */
    @Override
    public ImageVO upload(MultipartFile uploadFile) {
        //1.校验图片类型是否正确 jpg|png|gifxxxx  方式1.正则表达式判断  方式2.准备集合之后进行校验Set<去重>
        //1.1获取上传的图片类型
        String fileName=uploadFile.getOriginalFilename();//文件的全名 eg：abc.jpg
        fileName=fileName.toLowerCase();//将所有的字符转化为小写
        int index=fileName.lastIndexOf("."); //检索点最后一次出现的位置
        String fileType=fileName.substring(index);   //含头不含尾
        //1.2判断是否为图片类型
        if(!imageSet.contains(fileType)){
            //用户上传的不是图片
            return ImageVO.fail();
        }

        //2.上传的数据是否为恶意程序. 高度和宽度是否为null. 利用图片的API
        try {
            BufferedImage bufferedImage=ImageIO.read(uploadFile.getInputStream());
            int width=bufferedImage.getWidth();
            int height=bufferedImage.getHeight();
            if(width==0||height==0){
                return ImageVO.fail(); //用户上传的不是图片
            }
            //3.采用分目录存储的方式
            //String dirPath="D:/software/JT/images";
            //3.1 分目录存储方式2  采用时间存储方式 yyyy/MM/dd
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("/yyyy/MM/dd/");
            String dateDir=simpleDateFormat.format(new Date());
            //3.2 准备文件存储的目录=目录路径+日期目录
            String imageDir=dirPath+dateDir;   //eg:D:/software/JT/images/2020/09/02/
            File imageFileDir=new File(imageDir);
            if(!imageFileDir.exists()){
                imageFileDir.mkdirs();
            }
            //如上文件目录结构准备完成，下面可以实现文件上传操作了
            //4.实现文件上传
            //4.1 动态拼接文件名称  uuid.后缀
            String uuid=UUID.randomUUID().toString().replace("-","");
            String realFileName=uuid+fileType;
            //4.2   准备文件上传的全路径 磁盘路径地址+文件名称
            File imageFile=new File(imageDir+realFileName);
            //4.3实现文件上传
            uploadFile.transferTo(imageFile);

            //5.动态生成URL地址
            //请求协议: http://  https:// 带证书的网址 安全性更高 公钥私钥进行加密解密.
            //向服务器运行商购买域名  com  cn  org   hosts文件
            //图片存储的虚拟地址的路径 动态变化的路径
            //http://image.jt.com/2020/09/02/uuid.jpg
            String url = urlPath+dateDir+realFileName;
            return ImageVO.success(url,width,height); //返回成功


        } catch (IOException e) {
            e.printStackTrace();
            return ImageVO.fail();
        }


    }
}
