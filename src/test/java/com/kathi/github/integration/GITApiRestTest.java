package com.kathi.github.integration;

import com.kathi.github.model.Author;
import com.kathi.github.model.Repository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GITApiRestTest {


    final String NOT_FOUND_STATUS = "404";
    final String UNSUPPORTED_MEDIA_TYPE = "415";

    @Test
    public void testUserExists(){
        String user = "saisravankathi";
        String accept = "application/json";
        GITApiRest gitApi = new GITApiRest();
        Author author = gitApi.checkForUserExistence(user, accept);
        assertThat(author.login).isEqualTo(user);
    }

    @Test
    public void testUserDoesntExist() {
        String user = "saisravankathifsfsdfsfs";
        String accept = "application/json";
        GITApiRest gitApi = new GITApiRest();
        Author author = gitApi.checkForUserExistence(user, accept);
        assertThat(author.login).isNotEqualToIgnoringCase(user);
        assertThat(author.status).isEqualTo(NOT_FOUND_STATUS);
    }

    @Test
    public void testAcceptAsXML() {
        String user = "saisravankathi";
        String accept = "application/xml";
        GITApiRest gitApi = new GITApiRest();
        Author author = gitApi.checkForUserExistence(user, accept);
        assertThat(author.login).isNotEqualToIgnoringCase(user);
        assertThat(author.status).isEqualTo(UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void testFrameUserFromExceptionMessage(){
        Author a = new Author();
        String exMessageStr = "404 Not Found: \"{\"message\":\"Not Found\",\"documentation_url\":\"https://docs.github.com/rest/reference/users#get-a-user\"}\"";
        GITApiRest gitApi = new GITApiRest();
        Author author = gitApi.frameUserResponseFromException(a, exMessageStr);
        assertThat(author.message).isEqualTo("Not Found");
    }

    @Test
    public void testFrameUserFromExceptionMessageWithException(){
        Author a = new Author();
        String exMessageStr = "404 Not Found: \"{\"message\":\"Not Found\",\"documentation_url\":\"https://docs.github.com/rest/reference/users#get-a-user\"}";
        GITApiRest gitApi = new GITApiRest();
        Author author = gitApi.frameUserResponseFromException(a, exMessageStr);
        assertThat(author.message).isNotEqualToIgnoringCase("Not Found");
    }

    @Test
    public void testRepositoryExists(){
        String user = "saisravankathi";
        String accept = "application/json";
        GITApiRest gitApi = new GITApiRest();
        Repository repository = gitApi.getRepositoriesForUser(user, accept);
        assertThat(repository.getRepositories()).hasSizeGreaterThan(0);
        assertThat(repository.getAuthor()).isNotNull();
    }

    @Test
    public void testRepositoryDoesntExists(){
        String user = "saisravankathidasdadsa";
        String accept = "application/json";
        GITApiRest gitApi = new GITApiRest();
        Repository repository = gitApi.getRepositoriesForUser(user, accept);
        assertThat(repository.getRepositories()).isNull();
        assertThat(repository.getAuthor().getMessage()).isNotNull();
        assertThat(repository.getAuthor().getStatus()).isNotNull();
    }

    @Test
    public void testRepositoryXMLHeader(){
        String user = "saisravankathi";
        String accept = "application/xml";
        GITApiRest gitApi = new GITApiRest();
        Repository repository = gitApi.getRepositoriesForUser(user, accept);
        assertThat(repository.getRepositories()).isNull();
        assertThat(repository.getAuthor().getMessage()).isNotNull();
        assertThat(repository.getAuthor().getStatus()).isNotNull();
        assertThat(repository.getAuthor().getStatus()).isEqualTo(UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void testBranchExists(){
        String user = "saisravankathi";
        String accept = "application/json";
        String rep = "altitude-django";
        GITApiRest gitApi = new GITApiRest();
        Repository repository = gitApi.getBranchesForTheRepository(user, accept, rep);
        assertThat(repository.getRepositories()).hasSizeGreaterThan(0);
        assertThat(repository.getBranches()).hasSizeGreaterThan(0);
        assertThat(repository.getAuthor()).isNotNull();
    }

    @Test
    public void testRepositoryDoesntExistsForBranches(){
        String user = "saisravankathi";
        String accept = "application/json";
        String rep = "altitude-django1";
        GITApiRest gitApi = new GITApiRest();
        Repository repository = gitApi.getBranchesForTheRepository(user, accept, rep);
        assertThat(repository.getRepositories()).isNull();
        assertThat(repository.getAuthor().getMessage()).isNotNull();
        assertThat(repository.getAuthor().getStatus()).isNotNull();
    }

    @Test
    public void testBranchesForRepositoryXMLHeader(){
        String user = "saisravankathi";
        String accept = "application/xml";
        String rep = "altitude-django";
        GITApiRest gitApi = new GITApiRest();
        Repository repository = gitApi.getBranchesForTheRepository(user, accept, rep);
        assertThat(repository.getRepositories()).isNull();
        assertThat(repository.getAuthor().getMessage()).isNotNull();
        assertThat(repository.getAuthor().getStatus()).isNotNull();
        assertThat(repository.getAuthor().getStatus()).isEqualTo(UNSUPPORTED_MEDIA_TYPE);
    }
}
