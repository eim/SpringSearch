package org.eim.search.web;

import org.eim.search.SpringSearchApplication;
import org.eim.search.controller.SearchController;
import org.eim.search.entity.FilePartEntity;
import org.eim.search.repository.FileJpaRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = {SpringSearchApplication.class})
//@ActiveProfiles("test")
//@WebAppConfiguration
//@ConfigurationProperties
//@PropertySource("application-test.properties")
public class SearchControllerTest {

//  private static final String[] PATHS = {"filePath0","filePath1"};
//
//  private static final String[] PARTS = {"0","1"};
//
//  private static final String[] ENTRIES = {"test0 value","test1 value"};
//
//  @Autowired
//  private WebApplicationContext context;
//
//  private MockMvc mvc;
//
//  @Autowired
//  private FileJpaRepository fileJpaRepository;
//
//  @Before
//  public void setUp() throws Exception {
//    mvc = MockMvcBuilders.webAppContextSetup(context).build();
//    fileJpaRepository.saveAndFlush(getFilePartEntity(0));
//    fileJpaRepository.saveAndFlush(getFilePartEntity(1));
//  }
//
//  private FilePartEntity getFilePartEntity(int i) {
//    FilePartEntity filePartEntity = new FilePartEntity();
//    filePartEntity.setFilePath(PATHS[i]);
//    filePartEntity.setPart(PARTS[i]);
//    filePartEntity.setEntry(ENTRIES[i]);
//    return filePartEntity;
//  }
//
//  @After
//  public void cleanup() {
//    fileJpaRepository.deleteAll();
//  }
//
//  @Profile("test")
//  @Test
//  public void testGetViewCategoryById() throws Exception {
//
//    MockHttpServletRequestBuilder requestBuilder =
//        MockMvcRequestBuilders
//            .get(SearchController.PATH+"?q=test")
//            .accept(MediaType.APPLICATION_XHTML_XML);
//
//    MvcResult result =
//        mvc
//            .perform(requestBuilder)
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andReturn();
//
//    String json = result.getResponse().getContentAsString();
//    System.out.println(json);
//  }

}
