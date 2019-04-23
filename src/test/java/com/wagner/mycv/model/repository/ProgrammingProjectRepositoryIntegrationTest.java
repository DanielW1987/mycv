package com.wagner.mycv.model.repository;

import com.wagner.mycv.model.entity.ProgrammingProject;
import com.wagner.mycv.testutil.ProgrammingProjectTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class ProgrammingProjectRepositoryIntegrationTest {

  private final ProgrammingProject programmingProject = ProgrammingProjectTestUtil.createTestEntity();

  @Autowired private DataSource dataSource;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private EntityManager entityManager;
  @Autowired private ProgrammingProjectRepository programmingProjectRepository;

  @BeforeEach
  void setup() {
    entityManager.persist(programmingProject);
  }

  @Test
  void injectedComponentsAreNotNull(){
    assertThat(dataSource).isNotNull();
    assertThat(jdbcTemplate).isNotNull();
    assertThat(entityManager).isNotNull();
    assertThat(programmingProjectRepository).isNotNull();
  }

  @Test
  void test_find_programming_project() {
    Optional<ProgrammingProject> result = programmingProjectRepository.findById(1L);
    assertThat(result.isPresent()).isTrue();

    //noinspection OptionalGetWithoutIsPresent
    ProgrammingProject programmingProjectResponse = result.get();

    assertThat(programmingProjectResponse).isNotNull();
    assertThat(programmingProjectResponse.getId()).isEqualTo(1L);
    assertThat(programmingProjectResponse.getName()).isEqualTo(programmingProject.getName());
    assertThat(programmingProjectResponse.getDescription()).isEqualTo(programmingProject.getDescription());
    assertThat(programmingProjectResponse.getTechnologiesUsed()).isEqualTo(programmingProject.getTechnologiesUsed());
    assertThat(programmingProjectResponse.getVcsUrl()).isEqualTo(programmingProject.getVcsUrl());
    assertThat(programmingProjectResponse.getUserId()).isEqualTo(programmingProject.getUserId());
  }
}