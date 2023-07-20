package com.xuzheng.miniotest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String username;
    private String password;
    private String bucket;
    private String endpoint;
    private String readPath;
}
