package com.xb.seckilltest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xb.seckilltest.pojo.User;
import com.xb.seckilltest.vo.RespBean;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserCreateUtil {

    public static void createUser(int count) throws Exception {
        List<User> users = new ArrayList<>();

        //创建用户
        for (int i = 0; i < count ; i++) {
            User user = new User();
            user.setId(18011111111L + i);
            user.setHead("0");
            user.setNickname("test" + i);
            user.setPassword("05b7466ecace73652d8b7ad2d5d797d9");
            user.setRegisterDate(new Date());
            user.setSalt("2sc9s0xz2ned");
            user.setLoginCount(0);
            user.setLastLoginDate(new Date());

            users.add(user);
        }

//        //将用户保存至数据库
//        Connection con = getCon();
//        String sql = "insert into t_user(id,nickname,password,salt,head,register_date,last_login_date,login_count) values(?,?,?,?,?,?,?,?)";
//        PreparedStatement preparedStatement = con.prepareStatement(sql);
//        for (User user : users) {
//            preparedStatement.setLong(1,user.getId());
//            preparedStatement.setString(2,user.getNickname());
//            preparedStatement.setString(3,user.getPassword());
//            preparedStatement.setString(4,user.getSalt());
//            preparedStatement.setString(5,user.getHead());
//            preparedStatement.setTimestamp(6,new Timestamp(user.getRegisterDate().getTime()));
//            preparedStatement.setTimestamp(7,new Timestamp(user.getLastLoginDate().getTime()));
//            preparedStatement.setInt(8,user.getLoginCount());
//            preparedStatement.addBatch();
//        }
//        preparedStatement.executeBatch();
//        preparedStatement.clearParameters();
//        con.close();
//        System.out.println("insert ok！");

        //通过发送http请求获取设置的uuid（userTicket）
        String urlString = "http://localhost:8080/login/doLogin";
        File file = new File("D:\\desktop\\seckillConfig.txt");
        if(file.exists()){
            file.delete();
        }
        //todo 不明白，可以去了解
        RandomAccessFile raf = new RandomAccessFile(file,"rw");
        raf.seek(0);
        for (User user : users) {
            URL url = new URL(urlString);
            HttpURLConnection uco = (HttpURLConnection)url.openConnection();
            uco.setRequestMethod("POST");
            uco.setDoOutput(true);
            OutputStream outputStream = uco.getOutputStream();

            String params = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFromPass("123456");
            outputStream.write(params.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            InputStream inputStream = uco.getInputStream();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] bytes = new byte[1024];
            int len =0;
            while((len = inputStream.read(bytes)) >=0){
                bos.write(bytes,0,len);
            }
            inputStream.close();
            bos.close();

            String response = new String(bos.toByteArray());
            ObjectMapper mapper = new ObjectMapper();
            RespBean respBean = mapper.readValue(response, RespBean.class);
            String userTicket = (String)respBean.getObj();
            System.out.println("get userId:"+user.getId() + "  ----  " + "get userTicket:" +userTicket);

            String row = user.getId() + "," + userTicket;
            raf.seek(raf.length());//?????????????????todo
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());

            System.out.println("write to file:" + row);

        }
        raf.close();

        System.out.println("game over!!!!!!!!!!!!!");

    }

    public static Connection getCon() throws Exception {
        String url = "jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String user= "root";
        String password = "7JSRNRD6";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url,user,password);
    }

    public static void main(String[] args) throws Exception {
        createUser(5000);
    }

}
