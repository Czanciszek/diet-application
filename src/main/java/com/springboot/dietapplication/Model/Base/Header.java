package com.springboot.dietapplication.Model.Base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.dietapplication.Model.User.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Header {

    private DocRef<User> createdBy;
    private DocRef<User> updatedBy;
    private Long createdEpochMillis;
    private Long updatedEpochMillis;

    public DocRef<User> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(DocRef<User> createdBy) {
        this.createdBy = createdBy;
    }

    public DocRef<User> getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(DocRef<User> updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getCreatedEpochMillis() {
        return createdEpochMillis;
    }

    public void setCreatedEpochMillis(Long createdEpochMillis) {
        this.createdEpochMillis = createdEpochMillis;
    }

    public Long getUpdatedEpochMillis() {
        return updatedEpochMillis;
    }

    public void setUpdatedEpochMillis(Long updatedEpochMillis) {
        this.updatedEpochMillis = updatedEpochMillis;
    }
}
