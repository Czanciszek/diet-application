package com.springboot.dietapplication.Model.Base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocRef<E extends BaseDocInterface> implements Serializable {

    private String id;
    private String type;
    private String name;
    private String primaryImageId;

    public DocRef() {
    }

    public static <E extends BaseDocInterface> DocRef<E> fromDoc(E doc) {
        DocRef<E> ref = new DocRef<>();
        ref.setId(doc.getId());
        ref.setType(doc.getClass().getSimpleName());
        ref.setName(doc.getName());
        ref.setPrimaryImageId(doc.getPrimaryImageId());
        return ref;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrimaryImageId() {
        return primaryImageId;
    }

    public void setPrimaryImageId(String primaryImageId) {
        this.primaryImageId = primaryImageId;
    }

}
