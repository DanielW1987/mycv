package com.wagner.mycv.config;

import com.wagner.mycv.service.*;
import com.wagner.mycv.web.dto.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@Profile({"dev", "integration-test"})
public class DevelopmentDbInitializer implements ApplicationRunner {

  private final UserService              userService;
  private final UserProfileService       userProfileService;
  private final CertificationService     certificationService;
  private final EducationService         educationService;
  private final LanguageService          languageService;
  private final ProgrammingProjectService programmingProjectService;
  private final TechnologySkillService   technologySkillService;
  private final WorkingExperienceService workingExperienceService;

  @Autowired
  public DevelopmentDbInitializer(UserService userService, UserProfileService userProfileService,
                                  CertificationService certificationService, EducationService educationService,
                                  LanguageService languageService, ProgrammingProjectService programmingProjectService,
                                  TechnologySkillService technologySkillService, WorkingExperienceService workingExperienceService) {
    this.userService = userService;
    this.userProfileService = userProfileService;
    this.certificationService = certificationService;
    this.educationService = educationService;
    this.languageService = languageService;
    this.programmingProjectService = programmingProjectService;
    this.technologySkillService = technologySkillService;
    this.workingExperienceService = workingExperienceService;
  }

  @Override
  public void run(ApplicationArguments applicationArguments) {
    createTestUser();
    createTestCertifications();
    createTestEducations();
    createTestLanguages();
    createTestProgrammingProjects();
    createTestTechnologySkills();
    createTestUserProfiles();
    createTestWorkingExperience();
  }

  private void createTestUser() {
    UserRequestDto requestDto = UserRequestDto.builder()
            .userId(ApplicationConstants.PUBLIC_USER_ID) // static UUID value; is OK for dev and integration tests purpose
            .build();

    userService.create(requestDto);
  }

  private void createTestCertifications() {
    CertificationRequestDto mta = CertificationRequestDto.builder()
            .name("Microsoft Technology Associate: Database Fundamentals")
            .dateOfAchievement(LocalDate.of(2014, 10, 1))
            .certificate("certification file")
            .build();

    CertificationRequestDto oca = CertificationRequestDto.builder()
            .name("Oracle Certified Associate, Java SE 8 Programmer I")
            .dateOfAchievement(LocalDate.of(2017, 7, 1))
            .certificate("certification file")
            .build();

    CertificationRequestDto ocp = CertificationRequestDto.builder()
            .name("Oracle Certified Professional, Java SE 8 Programmer II")
            .dateOfAchievement(LocalDate.of(2018, 3, 1))
            .certificate("certification file")
            .build();

    certificationService.createAll(Arrays.asList(mta, oca, ocp));
  }

  private void createTestEducations() {
    EducationRequestDto highSchool = EducationRequestDto.builder()
            .facility("Berufliche Schule des Landkreises Ludwigslust")
            .begin(LocalDate.of(2014, 9, 1))
            .end(LocalDate.of(2008, 7, 31))
            .graduation("Allgemeine Hochschulreife und Berufsausbildung (kfm. Assistent für Informationsverarbeitung)")
            .build();

    EducationRequestDto bachelor = EducationRequestDto.builder()
            .facility("Hochschule für Technik und Wirtschaft Berlin")
            .begin(LocalDate.of(2008, 10, 1))
            .end(LocalDate.of(2011, 9, 30))
            .graduation("B. Sc. Wirtschaftsinformatik")
            .build();

    EducationRequestDto master = EducationRequestDto.builder()
            .facility("Hochschule für Technik und Wirtschaft Berlin")
            .begin(LocalDate.of(2011, 10, 1))
            .end(LocalDate.of(2013, 9, 30))
            .graduation("M. Sc. Wirtschaftsinformatik")
            .build();

    educationService.createAll(Arrays.asList(highSchool, bachelor, master));
  }

  private void createTestLanguages() {
    LanguageRequestDto german = LanguageRequestDto.builder()
            .name("Deutsch")
            .level((byte) 100)
            .build();

    LanguageRequestDto english = LanguageRequestDto.builder()
            .name("Englisch")
            .level((byte) 60)
            .build();

    LanguageRequestDto french = LanguageRequestDto.builder()
            .name("Französisch")
            .level((byte) 30)
            .build();

    languageService.createAll(Arrays.asList(german, english, french));
  }

  private void createTestProgrammingProjects() {
    ProgrammingProjectRequestDto myBeautifulCV = ProgrammingProjectRequestDto.builder()
            .name("My Beautiful CV")
            .technologiesUsed(Arrays.asList("Spring Boot", "Angular"))
            .description("Lorem ipsum...")
            .vcsUrl("https://www.bitbucket.com/foobar")
            .build();

    ProgrammingProjectRequestDto examedy = ProgrammingProjectRequestDto.builder()
            .name("Examedy")
            .technologiesUsed(Arrays.asList("Spring Boot", "Angular"))
            .description("Lorem ipsum...")
            .vcsUrl("https://www.github.com/foobar")
            .build();

    programmingProjectService.createAll(Arrays.asList(myBeautifulCV, examedy));
  }

  private void createTestTechnologySkills() {
    TechnologySkillRequestDto programming = TechnologySkillRequestDto.builder()
            .category("Programmierung")
            .skillNames(Arrays.asList("Java", "Spring / Spring Boot",
                    "Design Pattners", "REST und SOAP WebServices", "Git", "Maven", "Python"))
            .build();

    TechnologySkillRequestDto testing = TechnologySkillRequestDto.builder()
            .category("Tests")
            .skillNames(Arrays.asList("JUnit", "Mockito", "Rest Assured"))
            .build();

    TechnologySkillRequestDto web = TechnologySkillRequestDto.builder()
            .category("Webtechnologien")
            .skillNames(Arrays.asList("HTML", "CSS", "Bootstrap", "AngularCLI", "jQuery"))
            .build();

    TechnologySkillRequestDto databases = TechnologySkillRequestDto.builder()
            .category("Datenbanken")
            .skillNames(Arrays.asList("(My)SQL", "Datenbankmodellierung", "JDBC"))
            .build();

    TechnologySkillRequestDto modeling = TechnologySkillRequestDto.builder()
            .category("Modellierung")
            .skillNames(Arrays.asList("UML", "Domain Driven Design"))
            .build();

    TechnologySkillRequestDto agileMethods = TechnologySkillRequestDto.builder()
            .category("Agile Methoden")
            .skillNames(Arrays.asList("Scrum", "Kanban"))
            .build();

    technologySkillService.createAll(Arrays.asList(programming, testing, web, databases, modeling, agileMethods));
  }

  private void createTestUserProfiles() {
    UserProfileRequestDto userProfile = UserProfileRequestDto.builder()
            .firstName("John")
            .lastName("Doe")
            .currentJob("Java Developer")
            .email("john.doe@example")
            .mobilePhone("01520 12 34 567")
            .placeOfResidence("Berlin")
            .profileImage("Profile Image")
            .build();

    userProfileService.create(userProfile);
  }

  private void createTestWorkingExperience() {
    WorkingExperienceRequestDto freelancer = WorkingExperienceRequestDto.builder()
            .company("Selbstständig")
            .jobTitle("Webentwicklung, IT-Projekte, Beratung")
            .begin(LocalDate.of(2011, 1, 1))
            .end(LocalDate.of(2013, 9, 30))
            .placeOfWork("Berlin")
            .focalPoints(Arrays.asList(
                    "Realisierung von Internetauftritten und OnlineShops sowie Online-Marketing",
                    "verschiedene Entwicklungsprojekte u. a. mit PHP, Zend Framework 2, HTML, CSS, JavaScript",
                    "Dozententätigkeit (SQL Server 2010, Excel VBA)"))
            .build();

    WorkingExperienceRequestDto etlDeveloper = WorkingExperienceRequestDto.builder()
            .company("John Doe Company")
            .jobTitle("Java/ETL Developer")
            .begin(LocalDate.of(2013, 10, 1))
            .end(LocalDate.of(2015, 12, 31))
            .placeOfWork("Berlin")
            .focalPoints(Arrays.asList(
                    "Schnittstellenentwicklung zwischen einer Vielzahl von ERP-Systemen und dem LucaNet DWH mittels Java und SQL",
                    "Qualitätssicherung, Dokumentation und Einführung der Schnittstellen beim Kunden",
                    "Konzeption und Weiterentwicklung von DWH-Lösungen sowie deren Integration in die LucaNet-Software"))
            .build();

    WorkingExperienceRequestDto javaConsultant = WorkingExperienceRequestDto.builder()
            .company("John Doe Company")
            .jobTitle("Java Technical Consultant")
            .begin(LocalDate.of(2016, 1, 1))
            .end(LocalDate.of(2017, 2, 28))
            .placeOfWork("Berlin")
            .focalPoints(Arrays.asList(
                    "Aufbau eines agilen Entwicklungsteams am Standort Mönchengladbach",
                    "Einführung qualitätssichernder Standards (Coding Conventions, Code Reviews, Regressionstests)",
                    "Kundenindividuelle Java-Projekte"))
            .build();

    WorkingExperienceRequestDto javaDeveloper = WorkingExperienceRequestDto.builder()
            .company("John Doe Company")
            .jobTitle("Java Developer")
            .begin(LocalDate.of(2017, 3, 1))
            .placeOfWork("Berlin")
            .focalPoints(Arrays.asList(
                    "davon 1,5 Jahre Teamleiter eines 5-köpfigen Teams",
                    "Entwicklung von Anwendungen zur automatisierten Erstellung von Konzernabschlüssen (REST-Backend, Java Swing)",
                    "Software-Security (Authentifizierung, Autorisierung, Verschlüsselung, Transparenz)",
                    "Leitung von Kooperationsprojekten mit dem Masterstudiengang Wirtschaftsinformatik der HTW Berlin"))
            .build();

    workingExperienceService.createAll(Arrays.asList(freelancer, etlDeveloper, javaConsultant, javaDeveloper));
  }
}
