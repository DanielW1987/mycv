package com.wagner.mycv.model.repository;

import com.wagner.mycv.model.entity.Certification;
import com.wagner.mycv.testutil.StubFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
// @TestPropertySource(locations = "classpath:application-integrationtest.properties")
class CertificationRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CertificationRepository certificationRepository;

  @BeforeEach
  void setUp() {

  }

  @Test
  void test_find_all() {
    // given
    Certification certification = StubFactory.testCertificationEntity("OCA Certification", LocalDate.of(2019, 1, 1));
    entityManager.persist(certification);
    entityManager.flush();

    // when
    List<Certification> certificationList = certificationRepository.findAll();

    // then
    assertNotNull(certificationList);
    assertEquals(1, certificationList.size());

    Certification actualCertification = certificationList.get(0);
    assertTrue(actualCertification.getId() > 0);
//    assertNotNull(actualCertification.getCreatedBy());
//    assertNotNull(actualCertification.getCreatedDate());
//    assertNotNull(actualCertification.getLastModifiedBy());
//    assertNotNull(actualCertification.getLastModifiedDate());
  }
}