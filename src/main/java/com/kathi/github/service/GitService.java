package com.kathi.github.service;

import com.kathi.github.integration.GITApiRest;
import com.kathi.github.model.Author;
import com.kathi.github.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GitService {

    @Autowired
    GITApiRest gitApiRest;

    public Author getAuthor(Map<String, Object> reqMap){

        if(reqMap.containsKey("user") && reqMap.containsKey("accept")){
            String userName = reqMap.get("user").toString();
            String accepts = reqMap.get("accept").toString();
            return gitApiRest.checkForUserExistence(userName, accepts);
        }

        return new Author();
    }

    public Repository getRepositories(Map<String, Object> reqMap) {

        Repository r = new Repository();
        if(reqMap.containsKey("user") && reqMap.containsKey("accept")){
            String userName = reqMap.get("user").toString();
            String accepts = reqMap.get("accept").toString();
            Author a = getAuthor(reqMap);
            r = gitApiRest.getRepositoriesForUser(userName, accepts);
            r.setAuthor(a);
        }
        return r;
    }

    public Repository getBranches(Map<String, Object> reqMap){

        Repository r = new Repository();
        if(reqMap.containsKey("user") && reqMap.containsKey("accept") && reqMap.containsKey("repo")){
            String userName = reqMap.get("user").toString();
            String accepts = reqMap.get("accept").toString();
            String repo = reqMap.get("repo").toString();
            Author a = getAuthor(reqMap);
            r = gitApiRest.getBranchesForTheRepository(userName, accepts, repo);
            r.setAuthor(a);
        }
        return r;
    }

    public List<Repository> getAllUserInfo(Map<String, Object> reqMap) {

        List<Repository> allRepositories = new ArrayList<>();

        if(reqMap.containsKey("user") && reqMap.containsKey("accept")) {
            String userName = reqMap.get("user").toString();
            String accepts = reqMap.get("accept").toString();

            // calling of Services Starts.
            Repository r = this.getRepositories(reqMap);
            List<String> repositories = r.getRepositories();
            if(repositories != null && repositories.size() > 0){
                for(String rep: repositories){
                    reqMap.put("repo", rep);
                    allRepositories.add(this.getBranches(reqMap));
                }
            }
        }
        return allRepositories;
    }
}
