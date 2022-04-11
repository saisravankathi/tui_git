package com.kathi.github.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "This will hold the Request information: user, repo and sha")
public class Request {

    @ApiModelProperty(notes = "Username in the request", position = 0)
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
