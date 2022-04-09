package com.kathi.github.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "This will hold the Request information: user, repo and sha")
public class Request {

    @ApiModelProperty(notes = "Username in the request", position = 0)
    private String user;

    @ApiModelProperty(notes = "Repository details in the request", position = 1)
    private String repo;

    @ApiModelProperty(notes = "Sha id details of the branch in the request", position = 2)
    private String sha;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}
