package com.example.mongdb.mongdb.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mongdb.mongdb.model.entity.SysDataRights;
import com.example.mongdb.mongdb.model.entity.User;
import com.example.mongdb.mongdb.service.IUserService;
import com.example.mongdb.mongdb.service.IsysDataRightService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 测试Controller
 * @create by Kellach 2019年9月20日
 */
@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class TestRestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IsysDataRightService service;

    /**
     * 测试接口是否通
     * @return
     */
    @GetMapping(value = "/getUser")
    public String getTestUser(){
        List<User> all = userService.findAll();
        String rest = JSONObject.toJSON(all).toString();
        return rest;
    }

    /**
     * 测试接口
     * @return
     */
    @PostMapping(value = "/insertUser")
    public String insertTest(){
        List<User> userList = new ArrayList<User>();
        for(int i = 0;i<10;i++){
            User user = new User();
            ObjectId objectId = new ObjectId();
            user.setId(objectId.toString());
            user.setAge(i+20);
            user.setUserName("test"+i);
            user.setNickName("昵称："+i);
            userList.add(user);
        }
        userService.saveListUser(userList);
        return "success!";
    }

    /**
     * 获取数据权限数据
     */
    @GetMapping(value = "/getAdata")
    public void getTestAuthiorData(){
        System.out.println("test 权限数据");
        SysDataRights byId = service.getById("123");
        System.out.println();
    }


    @PostMapping(value = "/upfile")
    public void upfile(HttpServletRequest request) throws Exception{
        BufferedReader reader = request.getReader();
        byte[] bytes = IOUtils.toByteArray(reader, "ISO-8859-1");

        System.out.println("test!");

//        String value = IOUtils.toString(request.getInputStream(),"utf-8");
//        String decode2 = unescape(value);
//        String decode = URLDecoder.decode(file,"UTF-8");
        //       ËIK,ÎÉNIËÊÎI,ÎJIQ)Y@*Hgç@ÅÓr²/¯Ú)
//        String bytes = this.uncompress(decode.getBytes(),"UTF-8");
//
//
//        System.out.println(bytes);
//        InputStream inputStream = IOUtils.toInputStream(file, "utf-8");
//        FileUtils.copyInputStreamToFile(inputStream,new File("C:\\Users\\nitam\\Desktop\\test\\aaaa\\file.ssjson"));
        System.out.println();
    }

    /**
     * 字符串的解压
     *
     * @param b
     *            对字符串解压
     * @return    返回解压缩后的字符串
     * @throws IOException
     */

    public String uncompress(byte[] bytes, String charset) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        GZIPInputStream gzipInputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            //使用org.apache.commons.io.IOUtils 简化流的操作
            IOUtils.copy(gzipInputStream, byteArrayOutputStream);
            return byteArrayOutputStream.toString(charset);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放流资源
            IOUtils.closeQuietly(gzipInputStream);
            IOUtils.closeQuietly(byteArrayInputStream);
            IOUtils.closeQuietly(byteArrayOutputStream);
        }
        return null;
    }

    public static void main(String[] args) throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        String str="lfaslkdfjklasjdfklasdjfkljasdklfjklasjflk";
        gzip.write(str.getBytes());
        gzip.close();
        System.out.println(out.toString("ISO-8859-1"));;
    }

    /*
     * 对应javascript的unescape()函数, 可对javascript的escape()进行解码
     */
    public static String unescape(String src) {
     StringBuffer tmp = new StringBuffer();
     tmp.ensureCapacity(src.length());
     int lastPos = 0, pos = 0;
     char ch;
     while (lastPos < src.length()) {
     pos = src.indexOf("%", lastPos);
     if (pos == lastPos) {
     if (src.charAt(pos + 1) == 'u') {
     ch = (char) Integer.parseInt(src
     .substring(pos + 2, pos + 6), 16);
     tmp.append(ch);
     lastPos = pos + 6;
     } else {
     ch = (char) Integer.parseInt(src
     .substring(pos + 1, pos + 3), 16);
     tmp.append(ch);
     lastPos = pos + 3;
     }
     } else {
     if (pos == -1) {
     tmp.append(src.substring(lastPos));
     lastPos = src.length();
     } else {
     tmp.append(src.substring(lastPos, pos));
     lastPos = pos;
     }
     }
     }
     return tmp.toString();
    }

}
