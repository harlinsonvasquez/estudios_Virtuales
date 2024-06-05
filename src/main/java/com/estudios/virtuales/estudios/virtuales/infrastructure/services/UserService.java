package com.estudios.virtuales.estudios.virtuales.infrastructure.services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.UserReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.*;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Course;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Enrollment;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Submission;
import com.estudios.virtuales.estudios.virtuales.domain.entities.User;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.CourseRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.EnrollmentRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.SubmissionRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.UserRepository;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.IUserService;
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
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService  implements IUserService {
    @Autowired
    private  final UserRepository userRepository;
    @Autowired
    private  final SubmissionRepository submissionRepository;
    @Autowired
    private final CourseRepository courseRepository;
    @Autowired
    private final EnrollmentRepository enrollmentRepository;
    @Override
    public UserBasicResp create(UserReq request) {
        User user=this.requestToEntity(request);

        return this.entityToResp(this.userRepository.save(user));
    }

    @Override
    public UserBasicResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public UserBasicResp update(UserReq request, Long id) {
        User user=this.find(id);
        user=this.requestToEntity(request);
        user.setId(id);
        return this.entityToResp(this.userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        this.userRepository.delete(this.find(id));
    }


    @Override
    @Transactional
    public Page<UserBasicResp> getAll(int page, int size, SortType sort) {
        PageRequest pageRequest = getPageRequest(page, size, sort);
        return userRepository.findAll(pageRequest).map(this::entityToResp);
    }
    private PageRequest getPageRequest(int page, int size, SortType sort) {
        if (page < 0) page = 0;
        Sort.Direction direction = sort.equals(SortType.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page, size, Sort.by(direction, "id"));
    }

    private UserBasicResp entityToResp(User entity){
        UserBasicResp response=new UserBasicResp();
        BeanUtils.copyProperties(entity,response);
        return response;
    }
    private User requestToEntity(UserReq request){
        User user=new User();
        BeanUtils.copyProperties(request,user);
        return  user;

    }
    private User find(Long id){
        return this.userRepository.findById(id).orElseThrow(()-> new BadRequestException("no hay registros de usuarios con ese id"));
    }
    @Override
    @Transactional
    public UserSubmissionResp getWithSubmissions(Long id) {
        User user = find(id);
        List<SubmissionBasic> submissions = submissionRepository.findByUser_Id(id)
                .stream()
                .map(this::submissionEntityToBasicResp)
                .collect(Collectors.toList());

        return UserSubmissionResp.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .submissions(submissions)
                .build();
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
    @Override
    @Transactional
    public UserCourseResp getWithCourses(Long id) {
        User user = find(id);
        List<CourseBasic> courses = getCoursesByUserId(id);
        return UserCourseResp.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .courses(courses)
                .build();
    }

    private SubmissionBasic submissionEntityToBasicResp(Submission submission) {
        return SubmissionBasic.builder()
                .id(submission.getId())
                .submissionDate(submission.getSubmissionDate())
                .content(submission.getContent())
                .grade(submission.getGrade())
                .build();
    }
}
