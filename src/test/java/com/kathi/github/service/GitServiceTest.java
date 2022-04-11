package com.kathi.github.service;

import com.kathi.github.integration.GITApiRest;
import com.kathi.github.model.Author;
import com.kathi.github.model.Branch;
import com.kathi.github.model.Repository;
import com.kathi.github.model.Request;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class GitServiceTest {

    @InjectMocks
    GitService gitService;

    @Mock
    GITApiRest gitApiRest;


    @Test
    public void getAuthor(){
        Author a = new Author();
        a.setName("Mock");
        a.setLogin("ihtak");
        a.setStatus(null);
        Mockito.when(gitApiRest.checkForUserExistence("ihtak", "application/json")).thenReturn(a);
        Author newA = gitService.getAuthor("ihtak", "application/json");
        Assertions.assertEquals(newA.getName(), "Mock");
        Assertions.assertEquals(newA.getLogin(), "ihtak");
        Assertions.assertNull(newA.getStatus());
    }

    @Test
    public void getAuthorWithNullUserId(){
        Author a = gitService.getAuthor(null, "application/json");
        Assertions.assertNull(a.getStatus());
        Assertions.assertNull(a.getLogin());
        Assertions.assertNull(a.getName());
    }

    @Test
    public void getAuthorWithNullAcceptValue(){
        Author a = gitService.getAuthor("ihtak", null);
        Assertions.assertNull(a.getStatus());
        Assertions.assertNull(a.getLogin());
        Assertions.assertNull(a.getName());
    }

    @Test (expected = ResponseStatusException.class)
    public void getAuthorWithNotFoundStatus(){
        Author a = new Author();
        a.setName("Mock");
        a.setLogin("ihtak");
        a.setStatus("404");
        Mockito.when(gitApiRest.checkForUserExistence("ihtak", "application/json"))
                .thenThrow(ResponseStatusException.class);
        gitApiRest.checkForUserExistence("ihtak", "application/json");

    }


    @Test
    public void getRepositoriesWithNullUser(){
        Repository r = gitService.getRepositories(null, "application/json");
        Assertions.assertNull(r.getRepositories());
        Assertions.assertNull(r.getBranches());
        Assertions.assertNull(r.getAuthor());
    }

    @Test
    public void getRepositoriesWithNullAccept(){
        Repository r = gitService.getRepositories("ihtak", null);
        Assertions.assertNull(r.getRepositories());
        Assertions.assertNull(r.getBranches());
        Assertions.assertNull(r.getAuthor());
    }

    @Test
    public void getRepositoriesForUserAndAccept(){
        Author a = new Author();
        a.setName("Mock");
        a.setLogin("ihtak");
        a.setStatus(null);
        Mockito.when(gitApiRest.checkForUserExistence("ihtak", "application/json")).thenReturn(a);

        Repository rep = new Repository();
        rep.setAuthor(a);
        Mockito.when(gitApiRest.getRepositoriesForUser("ihtak", "application/json")).thenReturn(rep);
        gitService.getRepositories("ihtak", "application/json");
        Assertions.assertNull(rep.getRepositories());
        Assertions.assertNotNull(rep.getAuthor());
        Assertions.assertEquals(rep.getAuthor().getLogin(), a.getLogin());
        Assertions.assertEquals(rep.getAuthor().getName(), a.getName());
        Assertions.assertEquals(rep.getAuthor().getStatus(), a.getStatus());
    }

    @Test(expected = ResponseStatusException.class)
    public void getBranchesForRepAndUser(){
        Mockito.when(gitApiRest.checkForUserExistence("ihtak", "application/json")).thenReturn(new Author());
        Mockito.when(gitApiRest.getBranchesForTheRepository("ihtak", "application/json", "rep"))
        .thenThrow(ResponseStatusException.class);
        gitService.getBranches("ihtak","rep", "application/json");
    }

    @Test
    public void getAllUserInfoForNullUser(){
        Request reqMap = new Request();
        List<Repository> reps = gitService.getAllUserInfo(reqMap, "application/json");
        Assertions.assertTrue(reps.size() == 0);
    }

    @Test
    public void getAllUserInfoForNullAccept(){
        Request reqMap = new Request();
        reqMap.setUser("ihtak");
        List<Repository> reps = gitService.getAllUserInfo(reqMap, null);
        Assertions.assertTrue(reps.size() == 0);
    }

    @Test
    public void getAllUserInfoForUserAndAccept(){
        Request reqMap = new Request();
        reqMap.setUser("ihtak");
        Repository r = new Repository();
        Author a = new Author();
        a.setName("Kathi");
        a.setLogin("ihtak");
        a.setStatus(null);
        r.setAuthor(a);

        Branch b = new Branch();
        b.setSha("123");
        b.setName("Kathi");
        b.setUrl("/kathi");

        List<String> reps = new ArrayList<>();
        reps.add("rep");
        r.setRepositories(reps);

        List<Branch> branches = new ArrayList<>();
        branches.add(b);
        r.setBranches(branches);

        List<Repository> repositories = new ArrayList<>();
        repositories.add(r);

        Mockito.when(gitApiRest.checkForUserExistence("ihtak", "application/json")).thenReturn(a);

        Mockito.when(gitApiRest.getRepositoriesForUser("ihtak", "application/json")).thenReturn(r);

        Mockito.when(gitApiRest.getBranchesForTheRepository("ihtak", "application/json", "rep")).thenReturn(r);

        List<Repository> res = gitService.getAllUserInfo(reqMap, "application/json");
        Assertions.assertTrue(res.size() == 1);
        Assertions.assertEquals(res.get(0).getAuthor().getLogin(), a.getLogin());
        Assertions.assertEquals(res.get(0).getBranches().get(0).getSha(), b.getSha());
        Assertions.assertEquals(res.get(0).getRepositories().get(0), reps.get(0));
    }
}
