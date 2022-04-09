package com.kathi.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Class to store branch related information, branch Name and last commid SHA ID")
public class Branch {

    @ApiModelProperty(notes = "Last commit sha ID of a specific branch.", position = 0)
    private String sha;
    @ApiModelProperty(notes = "Name of the branch", position = 1)
    private String name;
    @ApiModelProperty(notes = "To store the URL of the Last commit ID.", position = 2)
    private String url;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
