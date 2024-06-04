package com.estudios.virtuales.estudios.virtuales.util;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.*;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Course;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Lesson;
import com.estudios.virtuales.estudios.virtuales.utils.enums.Role;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CourseMapper {
    public static CourseBasic toCourseBasic(CourseBasicResp courseBasicResp) {
        if (courseBasicResp == null) {
            return null;
        }
        CourseBasic courseBasic = new CourseBasic();
        courseBasic.setId(courseBasicResp.getId());
        courseBasic.setCourseName(courseBasicResp.getCourseName());
        courseBasic.setDescription(courseBasicResp.getDescription());

        return courseBasic;
    }
    public static CourseBasicResp toCourseBasicResp(Course entity) {
        UserBasicResp instructor = null;
        if (entity.getInstructor() != null) {
            if (Role.TEACHER.equals(entity.getInstructor().getRole())) {
                instructor = new UserBasicResp();
                BeanUtils.copyProperties(entity.getInstructor(), instructor);
            } else {
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

    public static CourseLessonResp toCourseLessonResp(Course entity) {
        CourseBasicResp basicResp = toCourseBasicResp(entity);
        List<LessonBasic> lessons = entity.getLessons().stream()
                .map(CourseMapper::toLessonBasicResp)
                .collect(Collectors.toList());

        return CourseLessonResp.builder()
                .id(basicResp.getId())
                .courseName(basicResp.getCourseName())
                .description(basicResp.getDescription())
                .instructor(basicResp.getInstructor())
                .lessons(lessons)
                .build();
    }

    private static LessonBasic toLessonBasicResp(Lesson lesson) {
        return LessonBasic.builder()
                .id(lesson.getId())
                .lessonTitle(lesson.getLessonTitle())
                .content(lesson.getContent())
                .build();
    }
}
