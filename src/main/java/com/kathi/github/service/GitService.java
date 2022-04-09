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

    public Author getAuthor(Request reqMap, String accepts){

        if(StringUtils.hasLength(reqMap.getUser()) && StringUtils.hasLength(accepts)){
            String userName = reqMap.getUser();
            return gitApiRest.checkForUserExistence(userName, accepts);
        }

        return new Author();
    }

    public Repository getRepositories(Request reqMap, String accepts) {

        Repository r = new Repository();
        if(StringUtils.hasLength(reqMap.getUser()) && StringUtils.hasLength(accepts)){
            String userName = reqMap.getUser();
            Author a = getAuthor(reqMap, accepts);
            r = gitApiRest.getRepositoriesForUser(userName, accepts);
            r.setAuthor(a);
        }
        return r;
    }

    public Repository getBranches(Request reqMap, String accepts){

        Repository r = new Repository();
        if(StringUtils.hasLength(reqMap.getUser()) && StringUtils.hasLength(accepts) && StringUtils.hasLength(reqMap.getRepo())){
            String userName = reqMap.getUser();
            String repo = reqMap.getRepo();
            Author a = getAuthor(reqMap, accepts);
            r = gitApiRest.getBranchesForTheRepository(userName, accepts, repo);
            r.setAuthor(a);
        }
        return r;
    }

    public List<Repository> getAllUserInfo(Request reqMap, String accepts) {

        List<Repository> allRepositories = new ArrayList<>();

        if(StringUtils.hasLength(reqMap.getUser()) && StringUtils.hasLength(accepts)) {
            String userName = reqMap.getUser();

            // calling of Services Starts.
            Repository r = this.getRepositories(reqMap, accepts);
            List<String> repositories = r.getRepositories();
            if(repositories != null && repositories.size() > 0){
                for(String rep: repositories){
                    reqMap.setRepo(rep);
                    allRepositories.add(this.getBranches(reqMap, accepts));
                }
            }
        }
        return allRepositories;
    }
}
