package com.nt.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.model.Student;
import com.nt.repo.IStudentRepository;

@Service("studentService")
public class StudentMgmtServiceImpl implements IStudentService {

	@Autowired
	private IStudentRepository studentrepo;
	
	@Override
	public Iterable<Student> getAllStudent() {
		
		return studentrepo.findAll();
	}

	@Override
	public String registerStudent(Student student) {
		
		return "Employee is saved with id value :"+studentrepo.save(student);
	}

	@Override
	public Student getStudentByNo(int no) {
		Student student=studentrepo.findById(no).orElseThrow(()-> new IllegalArgumentException());
		return student;
	}

	@Override
	public String updateStudent(Student student) {
		
		return "Employee is Updated with having id value :"+studentrepo.save(student);
	}

	@Override
	public String deleteStudentById(int no) {
	     studentrepo.deleteById(no);
		return no+ " Employee id Employee is Deleted";
	}
	
	private static final Map<String, String> USERS = Map.of(
            "SHANK", "SHANK",
            "ADMIN","ADMIN"
            
    );

    @Override
    public boolean validateUser(String username, String password) {
        return USERS.containsKey(username) && USERS.get(username).equals(password);
    }
	
	
	@Override
	 public List<Student> filterStudents(Integer filterBySno, String filterBySname) {
	        // If both filters are null, return all students
	        if (filterBySno == null && (filterBySname == null || filterBySname.isEmpty())) {
	            return studentrepo.findAll();
	        }

	        // Filtering based on both fields (if provided)
	        if (filterBySno != null && (filterBySname != null && !filterBySname.isEmpty())) {
	            return studentrepo.findBySnoAndSnameContaining(filterBySno, filterBySname);
	        }

	        // Filtering based on Sno
	        if (filterBySno != null) {
	            return studentrepo.findBySno(filterBySno);
	        }

	        // Filtering based on Sname
	        if (filterBySname != null && !filterBySname.isEmpty()) {
	            return studentrepo.findBySnameContaining(filterBySname);
	        }

	        return studentrepo.findAll(); // Return all students if no filters are applied
	    }
	
	 @Override
	    public List<Student> getSortedStudents(String sortBy, String order) {
	        List<Student> students = (List<Student>) studentrepo.findAll();

	        if (sortBy != null) {
	            Comparator<Student> comparator = null;

	            switch (sortBy) {
	                case "sno":
	                    comparator = Comparator.comparing(Student::getSno);
	                    break;
	                case "sname":
	                    comparator = Comparator.comparing(student -> student.getSname().toLowerCase());
	                    break;

	                case "saddress":
	                    comparator = Comparator.comparing(student -> student.getSaddress().toLowerCase());
	                    break;
	                case "fees":
	                    comparator = Comparator.comparing(Student::getFees);
	                    break;
	                default:
	                    throw new IllegalArgumentException("Invalid sortBy parameter");
	            }

	            if ("desc".equalsIgnoreCase(order)) {
	                comparator = comparator.reversed();
	            }

	            students = students.stream().sorted(comparator).collect(Collectors.toList());
	        }

	        return students;
	    }


}
