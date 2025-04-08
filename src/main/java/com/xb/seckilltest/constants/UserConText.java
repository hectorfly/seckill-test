package com.xb.seckilltest.constants;

import com.xb.seckilltest.pojo.User;

public class UserConText {

    public static final ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void set(User user){
        threadLocal.set(user);
    }
    public static User get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }


}
