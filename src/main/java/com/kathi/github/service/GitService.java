package com.kathi.github.service;

import com.kathi.github.integration.GITApiRest;
import com.kathi.github.model.Author;
import com.kathi.github.model.Repository;
import com.kathi.github.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GitService {

    @Autowired
    GITApiRest gitApiRest;

    public Mono<Author> getAuthor(String userId, String accepts){

        if(StringUtils.hasLength(userId) && StringUtils.hasLength(accepts)){
            return Mono.just(gitApiRest.checkForUserExistence(userId, accepts)).log();
        }

        return Mono.just(new Author()).log();
    }

    public Mono<Repository> getRepositories(String userId, String accepts) {

        if(StringUtils.hasLength(userId) && StringUtils.hasLength(accepts)){
            Mono<Author> a = getAuthor(userId, accepts);
            final Repository r = gitApiRest.getRepositoriesForUser(userId, accepts);
            a.subscribe(author -> {
                r.setAuthor(author);
            }).dispose();
            return Mono.just(r).log();
        }
        return Mono.just(new Repository()).log();
    }

    public Mono<Repository> getBranches(String userId, String repo,  String accepts){

        if(StringUtils.hasLength(userId) && StringUtils.hasLength(accepts) && StringUtils.hasLength(repo)){
            Mono<Author> a = getAuthor(userId, accepts);
            final Repository r = gitApiRest.getBranchesForTheRepository(userId, accepts, repo);
            a.subscribe(author -> {
                r.setAuthor(author);
            }).dispose();
            return Mono.just(r).log();
        }
        return Mono.just(new Repository()).log();
    }

    public Mono<List<Repository>> getAllUserInfo(Request reqMap, String accepts) {

        Flux<Repository> allRepositories = Flux.just();
        List<Mono<Repository>> monoList = new ArrayList<>();

        if(StringUtils.hasLength(reqMap.getUser()) && StringUtils.hasLength(accepts)) {
            String userName = reqMap.getUser();

            // calling of Services Starts.
            Mono<Repository> r = this.getRepositories(userName, accepts).log();
            r.subscribe(
                    repository -> {
                        Mono<List<String>> reps = Mono.just(repository.getRepositories()).log();
                        Flux<String> repsFlux = reps.flatMapIterable(list -> list).log();
                        repsFlux.subscribe(repo -> {
                            Mono<Repository> tempRep = this.getBranches(userName, repo, accepts).log();
                            monoList.add(tempRep);
                        }).dispose();
                    }
            ).dispose();
        }
        return Flux.concat(monoList).collectList();
    }
}
