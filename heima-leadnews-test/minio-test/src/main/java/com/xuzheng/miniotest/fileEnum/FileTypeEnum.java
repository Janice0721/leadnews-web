package com.xuzheng.miniotest.fileEnum;

public enum FileTypeEnum {
    HTML("text/html"),
    TXT("text/html"),
    JPG("image/jpg"),
    PNG("image/jpg");
    private String type;
    FileTypeEnum(String type){
        this.type=type;
    }



}
