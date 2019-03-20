package com.wagner.mycv.model.repository;

import com.wagner.mycv.model.entity.TechnologySkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologySkillRepository extends JpaRepository<TechnologySkill, Long> {
}
