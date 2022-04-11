package com.kathi.github.controller;

import com.kathi.github.AbstractTest;
import com.kathi.github.model.Author;
import com.kathi.github.model.Repository;
import com.kathi.github.model.Request;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GitControllerTest extends AbstractTest {

    @Override
    @Before
    public void setUp(){
        super.setUp();
    }

    @Test
    public void getAuthor() throws Exception {
        String url = "/tui/author/saisravankathi";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
        .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status);
        String content = result.getResponse().getContentAsString();
        Author author = super.mapFromJson(content, Author.class);
        assertEquals(author.getLogin(),"saisravankathi");
    }

    @Test
    public void getAuthorWithNotAcceptableFormat() throws Exception {
        String url = "/tui/author/fdfdfsfsdfsfdsfsf";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_XML)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(406, status);
        String content = result.getResponse().getContentAsString();
        Map<String, String> responseMap = super.mapFromJson(content, Map.class);
        assertTrue(responseMap.containsKey("status"));
        assertTrue(responseMap.containsKey("message"));
        assertEquals(responseMap.get("status"),"406");
    }

    @Test
    public void getAuthorWithUserNotFound() throws Exception {
        String url = "/tui/author/fdfdfsfsdfsfdsfsf";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(404, status);
        String content = result.getResponse().getContentAsString();
        Map<String, String> responseMap = super.mapFromJson(content, Map.class);
        assertTrue(responseMap.containsKey("status"));
        assertTrue(responseMap.containsKey("message"));
        assertEquals(responseMap.get("status"),"404");
    }

    @Test
    public void getRepositories() throws Exception {
        String url = "/tui/repositories/saisravankathi";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status);
        String content = result.getResponse().getContentAsString();
        Repository repository = super.mapFromJson(content, Repository.class);
        assertEquals(repository.getAuthor().getLogin(),"saisravankathi");
        assertTrue(repository.getRepositories() != null);
    }

    @Test
    public void getRepositoriesWithNotAcceptableFormat() throws Exception {
        String url = "/tui/repositories/saisravankathi";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_XML)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(406, status);
        String content = result.getResponse().getContentAsString();
        Map<String, String> responseMap = super.mapFromJson(content, Map.class);
        assertTrue(responseMap.containsKey("status"));
        assertTrue(responseMap.containsKey("message"));
        assertEquals(responseMap.get("status"),"406");
    }

    @Test
    public void getRepositoriesWithNoActualUser() throws Exception {
        String url = "/tui/repositories/saisravankathifjskfdjsljfsjdl";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(404, status);
        String content = result.getResponse().getContentAsString();
        Map<String, String> responseMap = super.mapFromJson(content, Map.class);
        assertTrue(responseMap.containsKey("status"));
        assertTrue(responseMap.containsKey("message"));
        assertEquals(responseMap.get("status"),"404");
    }

    @Test
    public void getBranches() throws Exception {
        String url = "/tui/branches/saisravankathi/tui_git";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status);
        String content = result.getResponse().getContentAsString();
        Repository repository = super.mapFromJson(content, Repository.class);
        assertEquals(repository.getAuthor().getLogin(),"saisravankathi");
        assertTrue(repository.getRepositories() != null);
    }

    @Test
    public void getBranchesWithNotAcceptableFormat() throws Exception {
        String url = "/tui/branches/saisravankathi/tui_git";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_XML)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(406, status);
        String content = result.getResponse().getContentAsString();
        Map<String, String> responseMap = super.mapFromJson(content, Map.class);
        assertTrue(responseMap.containsKey("status"));
        assertTrue(responseMap.containsKey("message"));
        assertEquals(responseMap.get("status"),"406");
    }

    @Test
    public void getBranchesWithNotProperUser() throws Exception {
        String url = "/tui/branches/saisravankathidfdfdf/tui_git";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(404, status);
        String content = result.getResponse().getContentAsString();
        Map<String, String> responseMap = super.mapFromJson(content, Map.class);
        assertTrue(responseMap.containsKey("status"));
        assertTrue(responseMap.containsKey("message"));
        assertEquals(responseMap.get("status"),"404");
    }

    @Test
    public void getBranchesWithNotProperRepository() throws Exception {
        String url = "/tui/branches/saisravankathi/tui_gitdfdfd";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(404, status);
        String content = result.getResponse().getContentAsString();
        Map<String, String> responseMap = super.mapFromJson(content, Map.class);
        assertTrue(responseMap.containsKey("status"));
        assertTrue(responseMap.containsKey("message"));
        assertEquals(responseMap.get("status"),"404");
    }

    @Test
    public void getBranchesWithNotProperUserAndRepository() throws Exception {
        String url = "/tui/branches/saisravankathidfdfdf/tui_gitdfdfd";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(404, status);
        String content = result.getResponse().getContentAsString();
        Map<String, String> responseMap = super.mapFromJson(content, Map.class);
        assertTrue(responseMap.containsKey("status"));
        assertTrue(responseMap.containsKey("message"));
        assertEquals(responseMap.get("status"),"404");
    }

    @Test
    public void getRepositoriesAndBranches() throws Exception {
        String url = "/tui/all-user-info";
        Request request = new Request();
        request.setUser("saisravankathi");
        String inputRequestAsString = super.mapToJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(inputRequestAsString)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status);
        String content = result.getResponse().getContentAsString();
        List<Repository> repository = super.mapFromJsonForList(content, Repository.class);
        assertTrue(repository != null);
    }

    @Test
    public void getRepositoriesAndBranchesNotAcceptableFormat() throws Exception {
        String url = "/tui/all-user-info";
        Request request = new Request();
        request.setUser("saisravankathi");
        String inputRequestAsString = super.mapToJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_XML).content(inputRequestAsString)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(406, status);
        String content = result.getResponse().getContentAsString();
        Map<String, String> responseMap = super.mapFromJson(content, Map.class);
        assertTrue(responseMap.containsKey("status"));
        assertTrue(responseMap.containsKey("message"));
        assertEquals(responseMap.get("status"),"406");
    }

    @Test
    public void getRepositoriesAndBranchesForNoUser() throws Exception {
        String url = "/tui/all-user-info";
        Request request = new Request();
        request.setUser("saisravankathidfdfdfdf");
        String inputRequestAsString = super.mapToJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(inputRequestAsString)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(404, status);
        String content = result.getResponse().getContentAsString();
        Map<String, String> responseMap = super.mapFromJson(content, Map.class);
        assertTrue(responseMap.containsKey("status"));
        assertTrue(responseMap.containsKey("message"));
        assertEquals(responseMap.get("status"),"404");
    }
}
