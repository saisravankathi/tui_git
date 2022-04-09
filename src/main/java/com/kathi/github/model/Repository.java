package com.kathi.github.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Class to hold the whole repository related Information.")
public class Repository {

    @ApiModelProperty(notes = "Stores all the repositories of specific User", position = 1)
    private List<String> repositories;
    @ApiModelProperty(notes = "Author related Information", position = 0)
    private Author author;
    @ApiModelProperty(notes = "Branches Information for each Repository", position = 2)
    private List<Branch> branches;

    public List<String> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<String> repositories) {
        this.repositories = repositories;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}
