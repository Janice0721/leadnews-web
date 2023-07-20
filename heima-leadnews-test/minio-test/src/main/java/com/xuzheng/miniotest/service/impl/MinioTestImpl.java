package com.xuzheng.miniotest.service.impl;

import com.xuzheng.miniotest.config.MinioConfig;
import com.xuzheng.miniotest.config.MinioProperties;
import com.xuzheng.miniotest.service.MinioTestService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

@Slf4j
@EnableConfigurationProperties(MinioProperties.class)
@Import(MinioConfig.class)
public class MinioTestImpl implements MinioTestService {

    @Autowired
   private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;



    @Override
    public void uploadFile2Minio(String filePath, String upFilePath, String fileType) throws Exception {

        FileInputStream is =new FileInputStream(filePath);
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .object(upFilePath) //存储文件路径名称，实际开发中可以通过业务划分
                .contentType(fileType) //存储文件类型
                .bucket(minioProperties.getBucket()) //存储到哪个bucket
                .stream(is,is.available(),-1) //存储文件的内容，以及大小
                .build();

        minioClient.putObject(putObjectArgs);

        //创建完成后返回访问路径
        StringBuilder urlPath = new StringBuilder(minioProperties.getReadPath());
        urlPath.append(minioProperties.getBucket());
        urlPath.append("/");
        urlPath.append(upFilePath);
        System.out.println(urlPath.toString());

    }

    @Override
    public void downloadFile4Minio(String fileName) throws Exception {

        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(minioProperties.getBucket()).object(fileName).build();

        InputStream is = minioClient.getObject(getObjectArgs);
        FileOutputStream fos =new FileOutputStream("src/main/resources/file/"+fileName);

        int i=0;
        byte[] bytes =new byte[1024];
        while((i=is.read(bytes))!=-1){
            fos.write(bytes,0,i);
        }
        is.close();
        fos.close();
    }


    @Override
    public void deleteFile(String fileName)throws Exception{
        RemoveObjectArgs tagsArgs = RemoveObjectArgs.builder().object(fileName).bucket(minioProperties.getBucket()).build();
        minioClient.removeObject(tagsArgs);
    }
}
