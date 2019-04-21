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
    String userId = createTestUser();
    createTestCertifications(userId);
    createTestEducations(userId);
    createTestLanguages(userId);
    createTestPrivateProjects(userId);
    createTestTechnologySkills(userId);
    createTestUserProfiles(userId);
    createTestWorkingExperience(userId);
  }

  private String createTestUser() {
    UserRequestDto requestDto = new UserRequestDto();
    String publicUserId       = "ebbddc3e-8414-4555-97ca-c247cc785cef"; // static UUID value; is OK for dev and integration tests purpose

    requestDto.setUserId(publicUserId);
    userService.create(requestDto);

    return publicUserId;
  }

  private void createTestCertifications(String userId) {
    CertificationRequestDto mta = CertificationRequestDto.builder()
            .name("Microsoft Technology Associate: Database Fundamentals")
            .dateOfAchievement(LocalDate.of(2014, 10, 1))
            .certificate("certification file")
            .userId(userId)
            .build();

    CertificationRequestDto oca = CertificationRequestDto.builder()
            .name("Oracle Certified Associate, Java SE 8 Programmer I")
            .dateOfAchievement(LocalDate.of(2017, 7, 1))
            .certificate("certification file")
            .userId(userId)
            .build();

    CertificationRequestDto ocp = CertificationRequestDto.builder()
            .name("Oracle Certified Professional, Java SE 8 Programmer II")
            .dateOfAchievement(LocalDate.of(2018, 3, 1))
            .certificate("certification file")
            .userId(userId)
            .build();

    certificationService.createAll(Arrays.asList(mta, oca, ocp));
  }

  private void createTestEducations(String userId) {
    EducationRequestDto highSchool = new EducationRequestDto();
    highSchool.setFacility("Berufliche Schule des Landkreises Ludwigslust");
    highSchool.setBegin(LocalDate.of(2014, 9, 1));
    highSchool.setEnd(LocalDate.of(2008, 7, 31));
    highSchool.setGraduation("Allgemeine Hochschulreife und Berufsausbildung (kfm. Assistent für Informationsverarbeitung)");
    highSchool.setUserId(userId);

    EducationRequestDto bachelor = new EducationRequestDto();
    bachelor.setFacility("Hochschule für Technik und Wirtschaft Berlin");
    bachelor.setBegin(LocalDate.of(2008, 10, 1));
    bachelor.setEnd(LocalDate.of(2011, 9, 30));
    bachelor.setGraduation("B. Sc. Wirtschaftsinformatik");
    bachelor.setUserId(userId);

    EducationRequestDto master = new EducationRequestDto();
    master.setFacility("Hochschule für Technik und Wirtschaft Berlin");
    master.setBegin(LocalDate.of(2011, 10, 1));
    master.setEnd(LocalDate.of(2013, 9, 30));
    master.setGraduation("M. Sc. Wirtschaftsinformatik");
    master.setUserId(userId);

    educationService.createAll(Arrays.asList(highSchool, bachelor, master));
  }

  private void createTestLanguages(String userId) {
    LanguageRequestDto german = new LanguageRequestDto();
    german.setName("Deutsch");
    german.setLevel((byte) 100);
    german.setUserId(userId);

    LanguageRequestDto english = new LanguageRequestDto();
    english.setName("Englisch");
    english.setLevel((byte) 60);
    english.setUserId(userId);

    LanguageRequestDto french = new LanguageRequestDto();
    french.setName("Französisch");
    french.setLevel((byte) 30);
    french.setUserId(userId);

    languageService.createAll(Arrays.asList(german, english, french));
  }

  private void createTestPrivateProjects(String userId) {
    ProgrammingProjectRequestDto myBeautifulCV = new ProgrammingProjectRequestDto();
    myBeautifulCV.setName("My Beautiful CurriculumVitae");
    myBeautifulCV.setDescription("Lorem ipsum...");
    myBeautifulCV.setTechnologiesUsed(Arrays.asList("Spring Boot", "Angular"));
    myBeautifulCV.setVcsUrl("https://www.bitbucket.com/foobar");
    myBeautifulCV.setUserId(userId);

    ProgrammingProjectRequestDto examedy = new ProgrammingProjectRequestDto();
    examedy.setName("Examedy");
    examedy.setDescription("Lorem ipsum...");
    examedy.setTechnologiesUsed(Arrays.asList("Spring Boot", "Angular"));
    examedy.setVcsUrl("https://www.github.com/foobar");
    examedy.setUserId(userId);

    programmingProjectService.createAll(Arrays.asList(myBeautifulCV, examedy));
  }

  private void createTestTechnologySkills(String userId) {
    TechnologySkillRequestDto programming = new TechnologySkillRequestDto();
    programming.setCategory("Programmierung");
    programming.setSkillNames(Arrays.asList("Java", "Spring / Spring Boot (Security, JPA, MVC, Actuator)",
            "Design Pattners", "REST und SOAP WebServices", "Git", "Maven", "Python"));
    programming.setUserId(userId);

    TechnologySkillRequestDto testing = new TechnologySkillRequestDto();
    testing.setCategory("Tests");
    testing.setSkillNames(Arrays.asList("JUnit", "Mockito", "Rest Assured"));
    testing.setUserId(userId);

    TechnologySkillRequestDto web = new TechnologySkillRequestDto();
    web.setCategory("Webtechnologien");
    web.setSkillNames(Arrays.asList("HTML", "CSS", "Bootstrap", "AngularCLI", "jQuery"));
    web.setUserId(userId);

    TechnologySkillRequestDto databases = new TechnologySkillRequestDto();
    databases.setCategory("Datenbanken");
    databases.setSkillNames(Arrays.asList("(My)SQL", "Datenbankmodellierung", "JDBC"));
    databases.setUserId(userId);

    TechnologySkillRequestDto modeling = new TechnologySkillRequestDto();
    modeling.setCategory("Modellierung");
    modeling.setSkillNames(Arrays.asList("UML", "Domain Driven Design"));
    modeling.setUserId(userId);

    TechnologySkillRequestDto agileMethods = new TechnologySkillRequestDto();
    agileMethods.setCategory("Agile Methoden");
    agileMethods.setSkillNames(Arrays.asList("Scrum", "Kanban"));
    agileMethods.setUserId(userId);

    technologySkillService.createAll(Arrays.asList(programming, testing, web, databases, modeling, agileMethods));
  }

  private void createTestUserProfiles(String userId) {
    UserProfileRequestDto userProfile = new UserProfileRequestDto();
    userProfile.setFirstName("Daniel");
    userProfile.setLastName("Wagner");
    userProfile.setCurrentJob("Java Developer");
    userProfile.setEmail("wagner.daniel87@gmail.com");
    userProfile.setMobilePhone("01520 35 36 248");
    userProfile.setPlaceOfResidence("12439 Berlin");
    userProfile.setProfileImage("Profile Image");
    userProfile.setUserId(userId);

    userProfileService.create(userProfile);
  }

  private void createTestWorkingExperience(String userId) {
    WorkingExperienceRequestDto freelancer = new WorkingExperienceRequestDto();
    freelancer.setCompany("Selbstständig");
    freelancer.setJobTitle("Webentwicklung, IT-Projekte, Beratung");
    freelancer.setBegin(LocalDate.of(2011, 1, 1));
    freelancer.setEnd(LocalDate.of(2013, 9, 30));
    freelancer.setPlaceOfWork("Berlin");
    freelancer.setFocalPoints(Arrays.asList(
            "Realisierung von Internetauftritten und OnlineShops sowie Online-Marketing",
            "verschiedene Entwicklungsprojekte u. a. mit PHP, Zend Framework 2, HTML, CSS, JavaScript",
            "Dozententätigkeit (SQL Server 2010, Excel VBA)"));
    freelancer.setUserId(userId);

    WorkingExperienceRequestDto etlDeveloper = new WorkingExperienceRequestDto();
    etlDeveloper.setCompany("LucaNet AG");
    etlDeveloper.setJobTitle("Java/ETL Developer");
    etlDeveloper.setBegin(LocalDate.of(2013, 10, 1));
    etlDeveloper.setEnd(LocalDate.of(2015, 12, 31));
    etlDeveloper.setPlaceOfWork("Berlin");
    etlDeveloper.setFocalPoints(Arrays.asList(
            "Schnittstellenentwicklung zwischen einer Vielzahl von ERP-Systemen und dem LucaNet DWH mittels Java und SQL",
            "Qualitätssicherung, Dokumentation und Einführung der Schnittstellen beim Kunden",
            "Konzeption und Weiterentwicklung von DWH-Lösungen sowie deren Integration in die LucaNet-Software"));
    etlDeveloper.setUserId(userId);

    WorkingExperienceRequestDto javaConsultant = new WorkingExperienceRequestDto();
    javaConsultant.setCompany("LucaNet AG");
    javaConsultant.setJobTitle("Java Technical Consultant");
    javaConsultant.setBegin(LocalDate.of(2016, 1, 1));
    javaConsultant.setEnd(LocalDate.of(2017, 2, 28));
    javaConsultant.setPlaceOfWork("Berlin");
    javaConsultant.setFocalPoints(Arrays.asList(
            "Aufbau eines agilen Entwicklungsteams am Standort Mönchengladbach",
            "Einführung qualitätssichernder Standards (Coding Conventions, Code Reviews, Regressionstests)",
            "Kundenindividuelle Java-Projekte"));
    javaConsultant.setUserId(userId);

    WorkingExperienceRequestDto javaDeveloper = new WorkingExperienceRequestDto();
    javaDeveloper.setCompany("LucaNet AG");
    javaDeveloper.setJobTitle("Java Developer");
    javaDeveloper.setBegin(LocalDate.of(2017, 3, 1));
    javaDeveloper.setPlaceOfWork("Berlin");
    javaDeveloper.setFocalPoints(Arrays.asList(
            "davon 1,5 Jahre Teamleiter eines 5-köpfigen Teams",
            "Entwicklung von Anwendungen zur automatisierten Erstellung von Konzernabschlüssen (REST-Backend, Java Swing)",
            "Software-Security (Authentifizierung, Autorisierung, Verschlüsselung, Transparenz)",
            "Leitung von Kooperationsprojekten mit dem Masterstudiengang Wirtschaftsinformatik der HTW Berlin"));
    javaDeveloper.setUserId(userId);

    workingExperienceService.createAll(Arrays.asList(freelancer, etlDeveloper, javaConsultant, javaDeveloper));
  }
}
