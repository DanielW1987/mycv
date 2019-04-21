package com.wagner.mycv.model.repository;

import com.wagner.mycv.model.entity.Certification;
import com.wagner.mycv.testutil.CertificationTestUtil;
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
class CertificationRepositoryIntegrationTest {

  private final Certification certification = CertificationTestUtil.createTestEntity();

  @Autowired private DataSource              dataSource;
  @Autowired private JdbcTemplate            jdbcTemplate;
  @Autowired private EntityManager           entityManager;
  @Autowired private CertificationRepository certificationRepository;

  @BeforeEach
  void setup() {
    entityManager.persist(certification);
  }

  @Test
  void injectedComponentsAreNotNull(){
    assertThat(dataSource).isNotNull();
    assertThat(jdbcTemplate).isNotNull();
    assertThat(entityManager).isNotNull();
    assertThat(certificationRepository).isNotNull();
  }

  @Test
  void test_find_certification() {
    Optional<Certification> result = certificationRepository.findById(1L);
    assertThat(result.isPresent()).isTrue();

    //noinspection OptionalGetWithoutIsPresent
    Certification certificationResponse = result.get();

    assertThat(certificationResponse).isNotNull();
    assertThat(certificationResponse.getId()).isEqualTo(1L);
    assertThat(certificationResponse.getName()).isEqualTo(certification.getName());
    assertThat(certificationResponse.getDateOfAchievement()).isEqualTo(certification.getDateOfAchievement());
    assertThat(certificationResponse.getCertificate()).isEqualTo(certification.getCertificate());
  }

}