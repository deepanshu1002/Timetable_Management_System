package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Subject;
import com.app.entities.Department;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
	@Query("SELECT new Subject(s.subjectId,s.subjectName,s.dept,s.teacherId) FROM Subject s where s.subjectId=?1")
	Subject getSubjectandDepartmentandTeacher(Long subId);
	
	List<Subject> findByDeptDeptId(Long id);
}
