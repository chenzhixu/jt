package com.jt.service;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class UserServiceImpl implements UserService{


    /**
     * 由jt-web向jt-sso进行数据的请求.之后获取数据.
     * @param userId
     * @return
     */
    @Override
    public User findUserById(Long userId) {
        //1.定义url地址
        String url = "http://sso.jt.com/user/findUserById/"+userId;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            //httpClient执行业务操作
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                //json格式
                String result = EntityUtils.toString(httpEntity, "UTF-8");
                return ObjectMapperUtil.toObject(result, User.class);
            }else{
                throw new RuntimeException("请求失败,请校验地址信息");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
