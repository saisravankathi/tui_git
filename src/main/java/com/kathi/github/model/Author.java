package com.kathi.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Author Class represents data which holds Author information as well as exception information.")
public class Author {

    @ApiModelProperty(notes = "Username of the github user.", example = "saisravankathi", position = 0)
    public String login;
    @ApiModelProperty(notes = "Github URL of the User", example = "https://github.com/saisravankathi", position = 1)
    public String url;
    @ApiModelProperty(notes = "Full name of the User.", example = "Sai Sravan Kathi", position = 2)
    public String name;
    @ApiModelProperty(notes = "Bio of the User", example = "", position = 3)
    public String bio;

    @ApiModelProperty(notes = "Total number of public repositories created", example = "12", position = 4)
    @JsonProperty("public_repos")
    public String publicRepos;

    @ApiModelProperty(notes = "Message or the reason for which there is an exception in the System or API", example = "NOT FOUND", position = 5)
    public String message;

    @ApiModelProperty(notes = "The rest documentation URL.", example = "", position = 6)
    @JsonProperty("documentation_url")
    public String documentationUrl;

    @ApiModelProperty(notes = "This will store the Statuscode only when there are some exceptions or wrong content posted..", example = "", position = 7)
    public String status;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(String publicRepos) {
        this.publicRepos = publicRepos;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
