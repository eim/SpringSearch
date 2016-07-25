package org.eim.search.service;

import org.eim.search.SpringSearchApplication;
import org.eim.search.entity.FilePartEntity;
import org.eim.search.repository.FileJpaRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringSearchApplication.class)
@WebAppConfiguration
@IntegrationTest
@TestPropertySource(locations = "/application-test.properties")
public class TermSearchTest {

  private static final String[] PATHS = {"filePath0","filePath1"};

  private static final String[] PARTS = {"0","1"};

  private static final String[] ENTRIES = {"test0 value","test1 value"};

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @Autowired
  private FileJpaRepository fileJpaRepository;

  @Autowired
  private TermSearch termSearch;

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
  public void cleanUp() {
    fileJpaRepository.deleteAll();
  }

  @Test
  public void testTotalTermCount() throws IOException {
    long totalTermCount = termSearch.getTotalTermCount();
    assertEquals(4,totalTermCount);
  }

  @Test
  public void testTermCount() throws IOException {
    long totalTermCount = termSearch.getTermCount("value");
    assertEquals(2,totalTermCount);
  }

  @Test
  public void testEntiyList() throws IOException {
    List<FilePartEntity> listOfFileEntity = termSearch.getEntityList("value");
    assertNotNull(listOfFileEntity);
    assertEquals(2,listOfFileEntity.size());
  }

}
