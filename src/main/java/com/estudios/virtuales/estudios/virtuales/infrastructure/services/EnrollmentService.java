package com.estudios.virtuales.estudios.virtuales.infrastructure.services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.EnrollmentReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.CourseBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.EnrollmentResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.UserBasicResp;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Course;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Enrollment;
import com.estudios.virtuales.estudios.virtuales.domain.entities.User;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.CourseRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.EnrollmentRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.UserRepository;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.IEnrollmentService;
import com.estudios.virtuales.estudios.virtuales.utils.enums.Role;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import com.estudios.virtuales.estudios.virtuales.utils.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnrollmentService implements IEnrollmentService {

    @Autowired
    private final EnrollmentRepository enrollmentRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CourseRepository courseRepository;

    private static final Role REQUIRED_ROLE=Role.STUDENT;
    @Transactional
    public EnrollmentResp create(EnrollmentReq request) {
        User student = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
        validateInstructorRole(student);

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new BadRequestException("Curso no encontrado"));

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(request.getEnrollmentDate()); // Puedes establecer la fecha si la tienes en el request
        enrollment.setUsers(student);
        enrollment.setCourse(course);

        return entityToResp(enrollmentRepository.save(enrollment));
    }

    @Override
    public EnrollmentResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public EnrollmentResp update(EnrollmentReq request, Long id) {
        Enrollment enrollment=this.find(id);
        User user=this.userRepository.findById(request.getUserId())
                .orElseThrow(()->new BadRequestException("no hay usuarios con ese id"));
        Course course=this.courseRepository.findById(request.getCourseId())
                .orElseThrow(()-> new BadRequestException("no hay cursos con ese id"));
        enrollment=this.requestToEntity(request);

        enrollment.setCourse(course);
        enrollment.setUsers(user);
        enrollment.setId(id);
        return this.entityToResp(this.enrollmentRepository.save(enrollment));
    }

    @Override
    public void delete(Long id) {
            this.enrollmentRepository.delete(this.find(id));
    }

    @Override
    public Page<EnrollmentResp> getAll(int page, int size, SortType sort) {
        if(page<0){
            page=0;
        }
        PageRequest pagination=null;
        switch (sort){
            case NONE:
                pagination=PageRequest.of(page,size);
                break;

            case ASC:
                pagination=PageRequest.of(page,size, Sort.by(FIELD_BY_SORT).ascending());
                break;
            case DESC:
                pagination=PageRequest.of(page,size,Sort.by(FIELD_BY_SORT).descending());
                break;
        }
        return this.enrollmentRepository.findAll(pagination).map(this::entityToResp);
    }
    private EnrollmentResp entityToResp(Enrollment entity) {
        UserBasicResp user = UserBasicResp.builder()
                .id(entity.getUsers().getId())
                .userName(entity.getUsers().getUserName())
                .email(entity.getUsers().getEmail())
                .fullName(entity.getUsers().getFullName())
                .role(entity.getUsers().getRole())
                .build();

        CourseBasicResp course = CourseBasicResp.builder()
                .id(entity.getCourse().getId())
                .courseName(entity.getCourse().getCourseName())
                .description(entity.getCourse().getDescription())
                .build();

        UserBasicResp instructor = UserBasicResp.builder()
                .id(entity.getCourse().getInstructor().getId())
                .userName(entity.getCourse().getInstructor().getUserName())
                .email(entity.getCourse().getInstructor().getEmail())
                .fullName(entity.getCourse().getInstructor().getFullName())
                .role(entity.getCourse().getInstructor().getRole())
                .build();

        course.setInstructor(instructor);

        EnrollmentResp response = new EnrollmentResp();
        BeanUtils.copyProperties(entity, response);

        response.setStudent(user);
        response.setCourse(course);

        return response;
    }


    private Enrollment requestToEntity(EnrollmentReq request){
        return Enrollment.builder()
                .enrollmentDate(request.getEnrollmentDate())
                .build();
    }
    private Enrollment find(Long id){
        return this.enrollmentRepository.findById(id).orElseThrow(()->new BadRequestException("no hay matriculas con ese id"));
    }
    private void validateInstructorRole(User student) {
        if (!REQUIRED_ROLE.equals(student.getRole())) {
            throw new IllegalArgumentException("El estudiante debe tener el rol de STUDENT");
        }
    }
}
