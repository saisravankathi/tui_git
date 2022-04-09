package com.kathi.github.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kathi.github.model.Author;
import com.kathi.github.model.Branch;
import com.kathi.github.model.Repository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Component
public class GITApiRest {

    private static String GITHUB_API_BASE_URL = "https://api.github.com";

    public Author checkForUserExistence(String userName, String acceptsHeader){
        RestTemplate restTemplate = new RestTemplate();
        final String userNameUrl = GITHUB_API_BASE_URL + "/users/" + userName;
        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Accept", acceptsHeader);

            HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(
                                                        userNameUrl,
                                                        HttpMethod.GET,
                                                        requestEntity,
                                                        String.class
                                                );
            // System.out.println(response.getBody());

            ObjectMapper om = new ObjectMapper();
            Author author = om.readValue(response.getBody(), Author.class);
            return author;
        }catch(IOException ex){
            ex.printStackTrace();
        }catch(HttpClientErrorException cex){
            Author a = new Author();
            return frameUserResponseFromException(a, cex.getMessage());
        }

        return new Author();
    }


    public Repository getRepositoriesForUser(String userName, String acceptsHeader){
        RestTemplate restTemplate = new RestTemplate();
        final String userNameUrl = GITHUB_API_BASE_URL + "/users/" + userName + "/repos?type=owner";
        Repository r = new Repository();
        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Accept", acceptsHeader);

            HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(
                    userNameUrl,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );
            // System.out.println(response.getBody());

            ObjectMapper om = new ObjectMapper();
            List<Map<String, Object>> repositoriesList = om.readValue(
                                        response.getBody(),
                                        om.getTypeFactory().constructCollectionType(List.class, Map.class)
                                        );
            List<String> repositories = new ArrayList<>();
            for(Map<String, Object> repositoryMap: repositoriesList){
                if(repositoryMap.containsKey("name")){
                    repositories.add(repositoryMap.get("name").toString());
                }
            }
            r.setRepositories(repositories);
            Author a = new Author();
            a.setLogin(userName);
            r.setAuthor(a);
        }catch(IOException ex){
            ex.printStackTrace();
        }catch(HttpClientErrorException cex){
            Author a = new Author();
            a = frameUserResponseFromException(a, cex.getMessage());
            r.setAuthor(a);
        }

        return r;
    }

    public Repository getBranchesForTheRepository(String userName, String acceptsHeader, String repository){
        RestTemplate restTemplate = new RestTemplate();
        final String userNameUrl = GITHUB_API_BASE_URL + "/repos/" + userName + "/" + repository + "/branches";
        Repository r = new Repository();
        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Accept", acceptsHeader);

            HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(
                    userNameUrl,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );
            // System.out.println(response.getBody());

            ObjectMapper om = new ObjectMapper();
            List<Map<String, Object>> repositoriesList = om.readValue(
                    response.getBody(),
                    om.getTypeFactory().constructCollectionType(List.class, Map.class)
            );
            List<Branch> branches = new ArrayList<>();
            for(Map<String, Object> repositoryMap: repositoriesList){
                Branch b = new Branch();
                if(repositoryMap.containsKey("name")){
                    b.setName(repositoryMap.get("name").toString());
                }
                if(repositoryMap.containsKey("commit")){
                    Map<String, String> commitMap = (Map<String, String>) repositoryMap.get("commit");
                    b.setSha(commitMap.get("sha"));
                    b.setUrl(commitMap.get("url"));
                }
                branches.add(b);
            }
            r.setRepositories(Arrays.asList(repository));
            r.setBranches(branches);
            Author a = new Author();
            a.setLogin(userName);
            r.setAuthor(a);
        }catch(IOException ex){
            ex.printStackTrace();
        }catch(HttpClientErrorException cex){
            Author a = new Author();
            a = frameUserResponseFromException(a, cex.getMessage());
            r.setAuthor(a);
        }

        return r;
    }

    public Author frameUserResponseFromException(Author author, String message){
        String[] messageSplit = message.split(": \"");
        try {
            String status = messageSplit[0].split(" ")[0].strip();
            author.setStatus(status);
            String messageStr = messageSplit[1].strip();
            messageStr = messageStr.substring(0,messageStr.length()-1);
            // System.out.println(messageStr);
            ObjectMapper om = new ObjectMapper();
            Map<String, String> reasonMap = om.readValue(messageStr, Map.class);
            if(reasonMap.containsKey("message")){
                author.setMessage(reasonMap.get("message"));
            }
            if(reasonMap.containsKey("documentation_url")){
                author.setDocumentationUrl(reasonMap.get("documentation_url"));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return author;
    }
}


