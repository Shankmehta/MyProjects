package com.nt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.nt.model.Student;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Integer> {
	 List<Student> findBySno(Integer sno);

	    // Find students by Sname containing the given string
	    List<Student> findBySnameContaining(String sname);

	    // Find students by both Sno and Sname containing the given values
	    List<Student> findBySnoAndSnameContaining(Integer sno, String sname);
}
