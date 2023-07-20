package com.xuzheng.miniotest.service;

import io.minio.GetObjectArgs;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public interface MinioTestService {
    void uploadFile2Minio(String filePath,String upFilePath,String fileType) throws Exception;
    void downloadFile4Minio(String fileName) throws Exception;
    void deleteFile(String fileName) throws Exception;
}
