package com.estudios.virtuales.estudios.virtuales.api.controller;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.CourseReq;

import com.estudios.virtuales.estudios.virtuales.api.dto.response.CourseBasic;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.CourseBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.CourseLessonResp;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.ICourseService;
import com.estudios.virtuales.estudios.virtuales.util.CourseMapper;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/course")
@AllArgsConstructor
public class CourseController {
    @Autowired
    private final ICourseService courseService;


    @GetMapping
    public Page<CourseBasic> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NONE") SortType sort) {

        Page<CourseBasicResp> courseBasicRespPage = courseService.getAll(page, size, sort);
        List<CourseBasic> courseBasicList = courseBasicRespPage.getContent().stream()
                .map(CourseMapper::toCourseBasic)
                .collect(Collectors.toList());

        return new PageImpl<>(courseBasicList, courseBasicRespPage.getPageable(), courseBasicRespPage.getTotalElements());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<CourseBasicResp>get(@PathVariable Long id){
        return ResponseEntity.ok(this.courseService.get(id));
    }
    @GetMapping(path = "/{id}/lesson")
    public ResponseEntity<CourseLessonResp> getCourseWithLessons(@PathVariable Long id) {
        return ResponseEntity.ok(this.courseService.getWithLessons(id));
    }

    @PostMapping
    public ResponseEntity<CourseBasicResp>insert(@Validated @RequestBody CourseReq request){
        return ResponseEntity.ok(this.courseService.create(request));
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<CourseBasicResp> update(@Validated @RequestBody CourseReq request,
                                                  @PathVariable Long id){
        return ResponseEntity.ok(this.courseService.update(request,id));
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        this.courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
