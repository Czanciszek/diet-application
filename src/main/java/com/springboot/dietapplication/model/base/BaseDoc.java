package com.springboot.dietapplication.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseDoc implements BaseDocInterface {

    private String id;
    private Header header;
    private String name;
    private String primaryImageId;

    public BaseDoc() {
    }

    public BaseDoc(String id, Header header, String name, String primaryImageId) {
        this.id = id;
        this.header = header;
        this.name = name;
        this.primaryImageId = primaryImageId;
    }

    @Override
    public Header getHeader() {
        return header;
    }

    @Override
    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPrimaryImageId() {
        return primaryImageId;
    }

    @Override
    public void setPrimaryImageId(String primaryImageId) {
        this.primaryImageId = primaryImageId;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public final void setType(String type) {

    }
}
