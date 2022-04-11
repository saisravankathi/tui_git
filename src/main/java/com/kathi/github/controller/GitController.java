package com.kathi.github.controller;

import com.kathi.github.model.Author;
import com.kathi.github.model.Repository;
import com.kathi.github.model.Request;
import com.kathi.github.service.GitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tui")
@Api(produces = "application/json", tags = "Git Controller", description = "This Controller has a set of POST methods where in the data is posted and branch related information with commit IDs will be available.")
public class GitController {

    @Autowired
    GitService gitService;

    @ApiOperation(value = "Returns Author Information, in case of 404 or 415, the status and message holds the Information.",
    response = Author.class, tags = "Get Author")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 415, message = "Un supported media format!!!") })

    @GetMapping(value = "/author/{userId}", produces = "application/json")
    public Author getAuthor(@PathVariable String userId,  @RequestHeader("accept") String accepts){
        return gitService.getAuthor(userId, accepts);
    }

    @ApiOperation(value = "Returns a list of Repositories for a given User.",
    response = Repository.class, tags = "Get Repositories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 415, message = "Un supported media format!!!") })
    @GetMapping(value = "/repositories/{userId}", produces = "application/json")
    public Repository getRepositories(@PathVariable String userId, @RequestHeader("accept") String accepts){
        return gitService.getRepositories(userId, accepts);
    }

    @GetMapping(value = "/branches/{userId}/{repo}", produces = "application/json")
    @ApiOperation(value = "Returns a list of Branches for a given user and repository",
    response = Repository.class, tags = "Get Branches")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 415, message = "Un supported media format!!!") })
    public Repository getBranches(@PathVariable String userId, @PathVariable String repo,  @RequestHeader("accept") String accepts){
        return gitService.getBranches(userId, repo, accepts);
    }

    @PostMapping(value = "/all-user-info", produces = "application/json", consumes = {"application/json"})
    @ApiOperation(value = "Returns all branches with last commit IDSs when user information is provided",
    response = List.class, tags = "Get Branches and Last Commit Information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 415, message = "Un supported media format!!!") })
    public List<Repository> getAllRepositoriesAndBranches(@RequestBody Request reqData, @RequestHeader("accept") String accepts){
        return gitService.getAllUserInfo(reqData, accepts);
    }
}
