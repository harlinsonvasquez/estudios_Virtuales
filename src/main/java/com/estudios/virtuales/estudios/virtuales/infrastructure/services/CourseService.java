package com.estudios.virtuales.estudios.virtuales.infrastructure.services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.CourseReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.*;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Course;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Enrollment;
import com.estudios.virtuales.estudios.virtuales.domain.entities.User;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.CourseRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.EnrollmentRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.UserRepository;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.ICourseService;
import com.estudios.virtuales.estudios.virtuales.util.CourseMapper;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CourseService implements ICourseService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    private static final Role REQUIRED_ROLE=Role.TEACHER;
    @Transactional
    @Override
    public CourseBasicResp create(CourseReq request) {
        User instructor=this.userRepository.findById(request.getInstructorId())
                .orElseThrow(()-> new BadRequestException("el id del instructor no se encuentra"));
        validateInstructorRole(instructor);
        Course course=this.requestToEntity(request);
        course.setInstructor(instructor);
        // Si request.getEnrollmentIds() no es nulo y no está vacío, asociar enrollments existentes
        if (request.getEnrollmentIds() != null && !request.getEnrollmentIds().isEmpty()) {
            Set<Enrollment> enrollments = request.getEnrollmentIds().stream()
                    .map(enrollmentId -> {
                        Enrollment enrollment = this.enrollmentRepository.findById(enrollmentId)
                                .orElseThrow(() -> new BadRequestException("La matrícula con el id proporcionado no existe"));
                        return enrollment;
                    })
                    .collect(Collectors.toSet());
            course.setEnrollments(enrollments);
        }
        return this.entityToResp(this.courseRepository.save(course));
    }

    @Override
    public CourseBasicResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    @Transactional
    public CourseBasicResp update(CourseReq request, Long id) {
        Course course = this.find(id);

        User instructor = this.userRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new BadRequestException("El instructor con el id proporcionado no existe"));

        // Actualizar la entidad Course
        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());
        course.setInstructor(instructor);

        // Actualizar la relación con enrollments
        // Eliminar enrollments que ya no están en la lista request.getEnrollmentIds()
        if (request.getEnrollmentIds() != null) {
            course.getEnrollments().removeIf(enrollment -> !request.getEnrollmentIds().contains(enrollment.getId()));

            // Agregar nuevos enrollments que no están en la colección actual
            request.getEnrollmentIds().forEach(enrollmentId -> {
                boolean exists = course.getEnrollments().stream().anyMatch(enrollment -> enrollment.getId().equals(enrollmentId));
                if (!exists) {
                    Enrollment enrollment = new Enrollment();
                    enrollment.setId(enrollmentId);
                    enrollment.setCourse(course);
                    course.getEnrollments().add(enrollment);
                }
            });
        }

        return this.entityToResp(this.courseRepository.save(course));
    }

    @Override
    public void delete(Long id) {
        this.courseRepository.delete(this.find(id));
    }

    @Override
    public Page<CourseBasicResp> getAll(int page, int size, SortType sort) {
        if(page<0)
            page=0;
        PageRequest pagination=null;
        switch (sort){
            case NONE -> pagination=PageRequest.of(page,size);
            case ASC -> pagination=PageRequest.of(page,size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination=PageRequest.of(page,size,Sort.by(FIELD_BY_SORT).descending());
        }
        return this.courseRepository.findAll(pagination).map(this::entityToResp);
    }
    private CourseBasicResp entityToResp(Course entity){

        UserBasicResp instructor=null;

        if(entity.getInstructor()!=null){
            if(REQUIRED_ROLE.equals(entity.getInstructor().getRole())){
                instructor=new UserBasicResp();
                BeanUtils.copyProperties(entity.getInstructor(),instructor);
            }else {
                throw new IllegalArgumentException("El instructor debe tener el rol de TEACHER");
            }
        }

        return CourseBasicResp.builder()
                .id(entity.getId())
                .courseName(entity.getCourseName())
                .description(entity.getDescription())
                .instructor(instructor)
                .build();
    }
    private Course requestToEntity(CourseReq request){
        return Course.builder()
                .courseName(request.getCourseName())
                .description(request.getDescription())
                .build();
    }
    public Course find(Long id){
        return this.courseRepository.findById(id)
                .orElseThrow(()->new BadRequestException("no hay registro de cursos con ese id"));
    }
    private void validateInstructorRole(User instructor) {
        if (!REQUIRED_ROLE.equals(instructor.getRole())) {
            throw new IllegalArgumentException("El instructor debe tener el rol de TEACHER");
        }
    }

    @Override
    public CourseLessonResp getWithLessons(Long id) {
        Course course = this.find(id);
        return CourseMapper.toCourseLessonResp(course);
    }
    @Override
    public List<CourseBasic> getCoursesByUserId(Long userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUsersId(userId);
        return enrollments.stream()
                .map(enrollment -> {
                    Course course = enrollment.getCourse();
                    return new CourseBasic(course.getId(), course.getCourseName(), course.getDescription());
                })
                .collect(Collectors.toList());
    }
    // Método para obtener todos los usuarios inscritos en un curso
    @Override
    public CourseUserResp getUsersInCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Curso no encontrado"));

        List<UserBasic> users = enrollmentRepository.findByCourseId(courseId).stream()
                .map(enrollment -> {
                    UserBasic userBasic = new UserBasic();
                    BeanUtils.copyProperties(enrollment.getUsers(), userBasic);
                    return userBasic;
                })
                .collect(Collectors.toList());

        return CourseUserResp.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .users(users)
                .build();
    }

}
