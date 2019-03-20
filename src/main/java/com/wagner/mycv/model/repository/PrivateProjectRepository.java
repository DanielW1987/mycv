package com.wagner.mycv.model.repository;

import com.wagner.mycv.model.entity.PrivateProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateProjectRepository extends JpaRepository<PrivateProject, Long> {
}
