package hu.unideb.inf.Webprojekt.model;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {

    Student findStudentById(Integer id);
    void deleteStudentById(Integer id);
}
