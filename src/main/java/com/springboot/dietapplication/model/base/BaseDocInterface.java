package com.springboot.dietapplication.model.base;

public interface BaseDocInterface {

    String getId();
    void setId(String id);
    Header getHeader();
    void setHeader(Header header);
    String getName();
    void setName(String name);
    String getPrimaryImageId();
    void setPrimaryImageId(String primaryImageId);
    String getType();
    void setType(String type);
}
