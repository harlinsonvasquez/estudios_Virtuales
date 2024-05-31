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
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import com.estudios.virtuales.estudios.virtuales.utils.exceptions.BadRequestException;
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
    @Override
    public EnrollmentResp create(EnrollmentReq request) {
        User user=this.userRepository.findById(request.getUserId())
                .orElseThrow(()->new BadRequestException("no hay usuarios con estudiantes con ese id"));
        Course course=this.courseRepository.findById(request.getCourseId())
                .orElseThrow(()->new BadRequestException("no hay cursos con ese id"));

        Enrollment enrollment=this.requestToEntity(request);
        enrollment.setUsers(user);
        enrollment.setCourses(course);
        return this.entityToResp(this.enrollmentRepository.save(enrollment));
    }

    @Override
    public EnrollmentResp get(Long aLong) {
        return null;
    }

    @Override
    public EnrollmentResp update(EnrollmentReq request, Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

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
    private EnrollmentResp entityToResp(Enrollment entity){
        UserBasicResp user=new UserBasicResp();
        BeanUtils.copyProperties(entity.getUsers(),user);

        CourseBasicResp course=new CourseBasicResp();
        BeanUtils.copyProperties(entity.getCourses(),course);

        UserBasicResp instructor =  UserBasicResp.builder()
                        .id(entity.getCourses().getInstructor().getId())
                        .userName(entity.getCourses().getInstructor().getUserName())
                        .email(entity.getCourses().getInstructor().getEmail())
                        .fullName(entity.getCourses().getInstructor().getFullName())
                        .role(entity.getCourses().getInstructor().getRole())
                        .build();

        course.setInstructor(instructor);

        EnrollmentResp response=new EnrollmentResp();
        BeanUtils.copyProperties(entity,response);

        response.setUser(user);
        response.setCourse(course);

        return response;
    }

    private Enrollment requestToEntity(EnrollmentReq request){
        return Enrollment.builder()
                .enrollmentDate(request.getEnrollmentDate())
                .build();
    }
}
//quedo funcionando la la insercion de matriculas