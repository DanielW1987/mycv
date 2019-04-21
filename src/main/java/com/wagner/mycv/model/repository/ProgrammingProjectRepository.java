package com.wagner.mycv.model.repository;

import com.wagner.mycv.model.entity.ProgrammingProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgrammingProjectRepository extends JpaRepository<ProgrammingProject, Long> {
}
