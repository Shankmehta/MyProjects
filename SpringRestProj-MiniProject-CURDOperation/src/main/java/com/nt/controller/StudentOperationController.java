package com.nt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import com.nt.model.Student;
import com.nt.service.IStudentService;

@Controller
@RequestMapping("/student")
public class StudentOperationController {
	@Autowired
	private IStudentService studentservice;

	
	@GetMapping("/login")
    public String showLoginPage() {
        return "login_page";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                Model model) {
        if (studentservice.validateUser(username, password)) {
            return "redirect:/student/welcome";
        } else {
            model.addAttribute("loginError", "Invalid username or password.");
            return "login_page";
        }
    }
	@GetMapping("/welcome")
	public String showHome() {
		return "show_welcome";
	}

	@GetMapping("/report")
	public String showEmployeeReport(Map<String, Object> map) {
		Iterable<Student> itst = studentservice.getAllStudent();
		map.put("studentData",itst);
		return "show_report";

	}

	@GetMapping("/studentAdd")
	public String showFromForSaveStudent(@ModelAttribute("student") Student student) {
		return "register_Employee";
	}


	//Best Among All
	@PostMapping("/studentAdd") // Best Practice
	public String saveStudent(RedirectAttributes ratt, @ModelAttribute("student") Student student) {
		String msg = studentservice.registerStudent(student);
		ratt.addFlashAttribute("resultMsg", msg);
		return "redirect:report";
	}
	

	
	@GetMapping("/edit")
	public String showEditFormPage(@RequestParam("no") int no,@ModelAttribute("student") Student student) {
		Student student1 = studentservice.getStudentByNo(no);
		BeanUtils.copyProperties(student1,student);
		return "update_employee";
	}
	
	@PostMapping("/edit")
	public String editStudent(RedirectAttributes rtt,@ModelAttribute("emp") Student student) {
		String msg=studentservice.updateStudent(student);
		rtt.addFlashAttribute("resultMsg",msg);
		return "redirect:report";
	}
	
	@GetMapping("/delete")
	public String deleteStudent(RedirectAttributes rtt,@RequestParam int no) {
		String msg = studentservice.deleteStudentById(no);
		rtt.addFlashAttribute("resultMsg",msg);
		return "redirect:report";
	}
	 @GetMapping("/filter")
	    public String getStudentReport(
	        @RequestParam(value = "filterBySno", required = false) Integer filterBySno,
	        @RequestParam(value = "filterBySname", required = false) String filterBySname,
	        Model model) {
	        
	        // Fetch filtered student data
	        List<Student> filteredStudents = studentservice.filterStudents(filterBySno, filterBySname);
	        
	        // Pass the filtered students to the view
	        model.addAttribute("studentData", filteredStudents);
	        return "show_report"; // The name of the Thymeleaf template
	    }
	 @GetMapping("/sort")
	 public String showStudentReport(
	     @RequestParam(value = "sortBy", required = false) String sortBy,
	     @RequestParam(value = "order", required = false, defaultValue = "asc") String order,
	     Model model) {
	     
	     // Fetch sorted student data
	     List<Student> students = studentservice.getSortedStudents(sortBy, order);
	     model.addAttribute("studentData", students);
	     return "show_report";
	 }
}