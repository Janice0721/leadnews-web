package com.xuzheng.miniotest;

import io.minio.*;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class MinioTestApplicationTests {
    private MinioClient minioClient = MinioClient.
                                        builder().
                                        credentials("minio", "minio123").
                                        endpoint("http://192.168.200.130:9000").
                                        build();

    @Test
    void contextLoads() {
    }

    @Test
    void testFileUpload() throws Exception {

        FileInputStream is =new FileInputStream("C:\\Users\\Janice\\Desktop\\1.txt");
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .object("1.txt") //存储文件路径名称，实际开发中可以通过业务划分
                .contentType("text/html") //存储文件类型
                .bucket("testbucket") //存储到哪个bucket
                .stream(is,is.available(),-1) //存储文件的内容，以及大小
                .build();

         minioClient.putObject(putObjectArgs);

         //创建完成后返回访问路径
        StringBuilder urlPath = new StringBuilder("http://192.168.200.130:9000");
        urlPath.append("/testbucket");
        urlPath.append("/1.txt");
        System.out.println(urlPath.toString());

    }

    @Test
    void testDownLoad() throws Exception {

        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("testbucket").object("1.txt").build();

        InputStream is = minioClient.getObject(getObjectArgs);
        FileOutputStream fos =new FileOutputStream("src/test/java/1.txt");

        int i=0;
        byte[] bytes =new byte[1024];
        while((i=is.read(bytes))!=-1){
            fos.write(bytes,0,i);
        }
        is.close();
        fos.close();
    }

    @Test
    void deleteFile()throws Exception{
        RemoveObjectArgs tagsArgs = RemoveObjectArgs.builder().object("1.txt").bucket("testbucket").build();
        minioClient.removeObject(tagsArgs);
    }

}
