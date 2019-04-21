package com.wagner.mycv.model.repository;

import com.wagner.mycv.model.entity.UserProfile;
import com.wagner.mycv.testutil.UserProfileTestUtil;
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
class UserProfileRepositoryTest {

  private final UserProfile userProfile = UserProfileTestUtil.createTestEntity();

  @Autowired private DataSource dataSource;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private EntityManager entityManager;
  @Autowired private UserProfileRepository userProfileRepository;

  @BeforeEach
  void setup() {
    entityManager.persist(userProfile);
  }

  @Test
  void injectedComponentsAreNotNull(){
    assertThat(dataSource).isNotNull();
    assertThat(jdbcTemplate).isNotNull();
    assertThat(entityManager).isNotNull();
    assertThat(userProfileRepository).isNotNull();
  }

  @Test
  void test_find_user_profile() {
    Optional<UserProfile> result = userProfileRepository.findById(1L);
    assertThat(result.isPresent()).isTrue();

    //noinspection OptionalGetWithoutIsPresent
    UserProfile userProfileResponse = result.get();

    assertThat(userProfileResponse).isNotNull();
    assertThat(userProfileResponse.getId()).isEqualTo(1L);
    assertThat(userProfileResponse.getFirstName()).isEqualTo(userProfile.getFirstName());
    assertThat(userProfileResponse.getLastName()).isEqualTo(userProfile.getLastName());
    assertThat(userProfileResponse.getEmail()).isEqualTo(userProfile.getEmail());
    assertThat(userProfileResponse.getCurrentJob()).isEqualTo(userProfile.getCurrentJob());
    assertThat(userProfileResponse.getMobilePhone()).isEqualTo(userProfile.getMobilePhone());
    assertThat(userProfileResponse.getPlaceOfResidence()).isEqualTo(userProfile.getPlaceOfResidence());
    assertThat(userProfileResponse.getProfileImage()).isEqualTo(userProfile.getProfileImage());
    assertThat(userProfileResponse.getUserId()).isEqualTo(userProfile.getUserId());
  }
}