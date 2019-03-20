package com.wagner.mycv.model.repository;

import com.wagner.mycv.model.entity.WorkingExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingExperienceRepository extends JpaRepository<WorkingExperience, Long> {
}
