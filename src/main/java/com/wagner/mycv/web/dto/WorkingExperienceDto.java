package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class WorkingExperienceDto extends AbstractEntityDto {

  private String       company;
  private LocalDate    begin;
  private LocalDate    end;
  private String       jobTitle;
  private String       placeOfWork;
  private List<String> focalPoints;

  public static class UiSequenceComparator implements Comparator<WorkingExperienceDto>, Serializable {

    public static final long serialVersionUID = 1L;

    @Override
    public int compare(WorkingExperienceDto w1, WorkingExperienceDto w2) {
      if (w1.getEnd() == null && w2.getEnd() == null) {
        return w1.getBegin().compareTo(w2.getBegin());
      } else if (w1.getEnd() == null) {
        return 1;
      } else if (w2.getEnd() == null) {
        return -1;
      } else {
        return w1.getEnd().compareTo(w2.getEnd());
      }
    }
  }

}
