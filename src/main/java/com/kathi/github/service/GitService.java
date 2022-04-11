package com.kathi.github.service;

import com.kathi.github.integration.GITApiRest;
import com.kathi.github.model.Author;
import com.kathi.github.model.Repository;
import com.kathi.github.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class GitService {

    private static String NOT_FOUND_MESSAGE = "Not Found";

    @Autowired
    GITApiRest gitApiRest;

    public Author getAuthor(String userId, String accepts){

        if(StringUtils.hasLength(userId) && StringUtils.hasLength(accepts)){
            Author a =  gitApiRest.checkForUserExistence(userId, accepts);
            if(a != null && a.getStatus() != null && a.getStatus().equalsIgnoreCase(String.valueOf(HttpStatus.NOT_FOUND.value()))){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
            }
            return a;
        }

        return new Author();
    }

    public Repository getRepositories(String userId, String accepts) {

        Repository r = new Repository();
        if(StringUtils.hasLength(userId) && StringUtils.hasLength(accepts)){
            Author a = getAuthor(userId, accepts);
            r = gitApiRest.getRepositoriesForUser(userId, accepts);
            r.setAuthor(a);
        }
        return r;
    }

    public Repository getBranches(String userId, String repo,  String accepts){

        Repository r = new Repository();
        if(StringUtils.hasLength(userId) && StringUtils.hasLength(accepts) && StringUtils.hasLength(repo)){
            Author a = getAuthor(userId, accepts);
            r = gitApiRest.getBranchesForTheRepository(userId, accepts, repo);
            r.setAuthor(a);
        }
        if(r.getRepositories() == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
        }
        return r;
    }

    public List<Repository> getAllUserInfo(Request reqMap, String accepts) {

        List<Repository> allRepositories = new ArrayList<>();

        if(StringUtils.hasLength(reqMap.getUser()) && StringUtils.hasLength(accepts)) {
            String userName = reqMap.getUser();

            // calling of Services Starts.
            Repository r = this.getRepositories(userName, accepts);
            List<String> repositories = r.getRepositories();
            if(repositories != null && repositories.size() > 0){
                for(String rep: repositories){
                    allRepositories.add(this.getBranches(userName, rep, accepts));
                }
            }
        }
        return allRepositories;
    }
}
