package com.jt.service;

import com.jt.vo.ImageVO;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

public interface FileService {

    public ImageVO upload(MultipartFile uploadFile);
}
