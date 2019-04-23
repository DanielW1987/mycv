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
//  Hierbei muss man auch hinsichtlich Sicherheits prüfen, dass nicht die Daten anderer geändert oder gelesen werden können.
//  Also einfach nur die UserId zum Server mitsenden ist wahrscheinlich alleine zu wenig.

// ToDo: Wenn im Request ein Datum nicht zu LocalDate geparst werden kann, werden dem Client noch Fehlermeldungen mit Stacktrace gesendet.
//  Hier muss noch eine grundsätzliche Lösung gefunden werden, wie der Client auch bei nicht explizit behandelten Fehlern keinen Stacktrace
//  zu Gesicht bekommt.
//  Mögliche Lösung: Überall nur primitive Datentypen oder Strings verwenden und eigene Validator und Converter schreiben.

// ToDo: @NotNull und @Nullable Annotations hinzufügen und Parameter und Rückgabewerte annotieren

// ToDo DanielW: Es ist doof, dass Aufzählungen wie "skillNames" oder "technologyUsed" aktuell als kommaseparierte Liste gespeichert werden.
//  Das fliegt sofort auseinander, wenn in den einzelnen Strings das Trennzeichen (wie bspw. Komma) enthalten ist.

// ToDo DanielW: Aktuell wird die ID der Datenbank noch nach draußen gegeben. Das ist auch unschön. Hier sollte es eine
//  alphanumerische public id geben.

// ToDo DanielW: Aktuell werden die PublicUserIds noch statisch in den create- und createAll-Methoden der Services gesetzt. Das muss
//  natürlich noch anders gelöst werden. Fraglich ist, ob diese Info in das jeweilige RequestDto gehört, da sich der Wert eigentlich mehr
//  aus dem Kontext der Anfrage heraus ergibt. Die Anfrage eines Clients erfolgt bspw. aus einer Session und diese gehört genau einem User
//  oder der User tätigt seine Anfrage über einen anderen Authentifizierungs-/Autorisierungsweg.

// ToDo DanielW: die Zeiten createdAt und lastModifiedAt sind aktuell LocalDates? Das müsste mal geprüft werden. Es sollte auf jeden Fall
//  Serverzeit gespeichert werden und nicht die Zeit des Clients