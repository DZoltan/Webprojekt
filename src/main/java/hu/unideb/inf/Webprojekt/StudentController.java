package hu.unideb.inf.Webprojekt;

import hu.unideb.inf.Webprojekt.model.Student;
import hu.unideb.inf.Webprojekt.model.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;


    @RequestMapping(value = "/add", method = POST)
    public RedirectView addStudent(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam int born) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setBorn(born);
        studentRepository.save(student);
        return new RedirectView("/");
    }

    @RequestMapping(value= "/")
    public String showAllStudent(Model model){
        String num = String.valueOf(studentRepository.count());
        model.addAttribute("students",num);
        return "index";
    }

    @RequestMapping(value= "/list")
    public String listStudents(Model model){
        Iterable<Student> studentList = studentRepository.findAll();
        model.addAttribute("studentsList", studentList);
        return "list";
    }

    @RequestMapping(value="/delete/{id}", method = GET)
    @Transactional
    public RedirectView deleteStudent( @PathVariable("id") Integer id){
        studentRepository.deleteStudentById(id);

        return new RedirectView("/list");
    }

    @RequestMapping(value="/update/{id}", method = GET)
    public String updateStudent(Model model, @PathVariable("id") Integer id){
        Student student = studentRepository.findStudentById(id);
        model.addAttribute("student", student);
        return "update";
    }

    @RequestMapping(value="/update/{id}", method = POST)
    @Transactional
    public RedirectView updateStudent(@PathVariable("id") Integer id,
                                   @RequestParam String firstName,
                                   @RequestParam String lastName,
                                   @RequestParam int born){
        Student student = studentRepository.findStudentById(id);
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setBorn(born);
        studentRepository.save(student);

        return new  RedirectView("/list");

    }



    @GetMapping("/new")
    public String newStudent(){
        return "new";
    }
}
