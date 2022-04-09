package com.kathi.github.service;

import com.kathi.github.integration.GITApiRest;
import com.kathi.github.model.Author;
import com.kathi.github.model.Repository;
import com.kathi.github.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GitService {

    @Autowired
    GITApiRest gitApiRest;

    public Author getAuthor(String userId, String accepts){

        if(StringUtils.hasLength(userId) && StringUtils.hasLength(accepts)){
            return gitApiRest.checkForUserExistence(userId, accepts);
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
