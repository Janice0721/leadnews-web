package com.xuzheng.miniotest.config;

import com.xuzheng.miniotest.service.impl.MinioTestImpl;
import io.minio.MinioClient;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties({MinioProperties.class})
@ConditionalOnClass(MinioTestImpl.class)
public class MinioConfig {

    @Autowired
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient(){

         MinioClient minioClient = MinioClient.
                builder().
                credentials(minioProperties.getUsername(), minioProperties.getPassword()).
                endpoint(minioProperties.getEndpoint()).
                build();

         return minioClient;
    }
}
