package com.jt.thread;

import com.jt.pojo.User;

public class UserThreadLocal {

    //1.定义变量
    private static ThreadLocal<User> thread = new ThreadLocal<>();

    public static void set(User user){  //赋值
        thread.set(user);
    }

    public static User get(){           //取值

        return thread.get();
    }

    public static void remove(){        //移除
        thread.remove();
    }
}
