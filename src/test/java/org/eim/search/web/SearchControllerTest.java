package org.eim.search.web;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.dom4j.Document;
import org.dom4j.io.DOMReader;
import org.eim.search.SpringSearchApplication;
import org.eim.search.controller.SearchController;
import org.eim.search.entity.FilePartEntity;
import org.eim.search.repository.FileJpaRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {SpringSearchApplication.class})
@WebIntegrationTest("server.port=8070")
public class SearchControllerTest {

  private static final String[] PATHS = {"filePath0","filePath1"};

  private static final String[] PARTS = {"0","1"};

  private static final String[] ENTRIES = {"test0 value","test1 value"};

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @Autowired
  private FileJpaRepository fileJpaRepository;

  @Before
  public void setUp() throws Exception {
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
    fileJpaRepository.saveAndFlush(getFilePartEntity(0));
    fileJpaRepository.saveAndFlush(getFilePartEntity(1));
  }

  private FilePartEntity getFilePartEntity(int i) {
    FilePartEntity filePartEntity = new FilePartEntity();
    filePartEntity.setFilePath(PATHS[i]);
    filePartEntity.setPart(PARTS[i]);
    filePartEntity.setEntry(ENTRIES[i]);
    return filePartEntity;
  }

  @After
  public void cleanup() {
    fileJpaRepository.deleteAll();
  }

  @Profile("test")
  @Test
  public void testGetViewCategoryById() throws Exception {

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders
            .get(SearchController.PATH+"?q=value")
            .accept(MediaType.APPLICATION_XHTML_XML);

    MvcResult result =
        mvc
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

    String htmlDoc = result.getResponse().getContentAsString();
    assertNotNull(htmlDoc);
    Document document = readDocument(htmlDoc);
    List<Node> nodes = getNodes(document,"");
    System.out.println(htmlDoc);
    assertNotNull(nodes);
    assertEquals(2,nodes.size());
  }

  private org.dom4j.Document readDocument(String value) throws SAXException, IOException {

    DOMParser parser = new DOMParser();
    parser.parse(value);
    org.w3c.dom.Document doc = parser.getDocument();

    DOMReader reader = new DOMReader();
    return reader.read(doc);
  }

  private List<Node> getNodes(org.dom4j.Document document, String xpath) {
    return document.selectNodes(xpath);
  }

}
