package com.kathi.github.controller;

import com.kathi.github.utils.Utils;
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
import java.util.Map;

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

    @PostMapping(value = "/author", produces = "application/json", consumes = {"application/json", "application/xml"})
    public Author getAuthor(@RequestBody Request reqData, @RequestHeader Map<String, String> headers){
        String accepts = Utils.getAcceptsFromHeader(headers);
        return gitService.getAuthor(reqData, accepts);
    }

    @ApiOperation(value = "Returns a list of Repositories for a given User.",
    response = Repository.class, tags = "Get Repositories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 415, message = "Un supported media format!!!") })
    @PostMapping(value = "/repositories", produces = "application/json", consumes = {"application/json", "application/xml"})
    public Repository getRepositories(@RequestBody Request reqData, @RequestHeader Map<String, String> headers){
        String accepts = Utils.getAcceptsFromHeader(headers);
        return gitService.getRepositories(reqData, accepts);
    }

    @PostMapping(value = "/branches", produces = "application/json", consumes = {"application/json", "application/xml"})
    @ApiOperation(value = "Returns a list of Branches for a given user and repository",
    response = Repository.class, tags = "Get Branches")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 415, message = "Un supported media format!!!") })
    public Repository getBranches(@RequestBody Request reqData, @RequestHeader Map<String, String> headers){
        String accepts = Utils.getAcceptsFromHeader(headers);
        return gitService.getBranches(reqData, accepts);
    }

    @PostMapping(value = "/all-user-info", produces = "application/json", consumes = {"application/json", "application/xml"})
    @ApiOperation(value = "Returns all branches with last commit IDSs when user information is provided",
    response = List.class, tags = "Get Branches and Last Commit Information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 415, message = "Un supported media format!!!") })
    public List<Repository> getAllRepositoriesAndBranches(@RequestBody Request reqData, @RequestHeader Map<String, String> headers){
        String accepts = Utils.getAcceptsFromHeader(headers);
        return gitService.getAllUserInfo(reqData, accepts);
    }
}
