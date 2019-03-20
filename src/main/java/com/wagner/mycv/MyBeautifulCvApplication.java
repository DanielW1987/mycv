package com.wagner.mycv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyBeautifulCvApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyBeautifulCvApplication.class, args);
  }

}

// ToDo UserId ist bisher nur provisorisch mit den Daten verknüpft. Aktuell ist es noch nicht wirklich möglich, CVs je Benutzer zu verwalten
// Hierbei muss man auch hinsichtlich Sicherheits prüfen, dass nicht die Daten anderer geändert oder gelesen werden können.
// Also einfach nur die UserId zum Server mitsenden ist wahrscheinlich alleine zu wenig.

// ToDo: Wenn im Request ein Datum nicht zu LocalDate geparst werden kann, werden dem Client noch Fehlermeldungen mit Stacktrace gesendet.
// Hier muss noch eine grundsätzliche Lösung gefunden werden, wie der Client auch bei nicht explizit behandelten Fehlern keinen Stacktrace
// zu Gesicht bekommt.

// ToDo: @NotNull und @Nullable Annotations hinzufügen und Parameter und Rückgabewerte annotieren
