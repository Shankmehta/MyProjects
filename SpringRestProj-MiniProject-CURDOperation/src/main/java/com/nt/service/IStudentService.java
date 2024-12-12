package com.nt.service;
import org.springframework.data.domain.Sort;
import java.util.List;

import com.nt.model.Student;

public interface IStudentService {
   public Iterable<Student> getAllStudent();
   public String registerStudent(Student emp);
   public Student getStudentByNo(int no);
   public String updateStudent(Student emp);
   public String deleteStudentById(int no);
//   public List<Student> getSortedStudents(boolean ascending);
//   List<Student> getFilteredStudents(String column, String value);
   public List<Student> filterStudents(Integer filterBySno, String filterBySname);
   public List<Student> getSortedStudents(String sortBy, String order);
   boolean validateUser(String username, String password);
}
