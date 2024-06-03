package com.estudios.virtuales.estudios.virtuales.api.controller;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.CourseReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.CourseBasicResp;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.ICourseService;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/course")
@AllArgsConstructor
public class CourseController {
    @Autowired
    private final ICourseService courseService;


    @GetMapping
    public ResponseEntity<Page<CourseBasicResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType))
            sortType = SortType.NONE;

        return ResponseEntity.ok(this.courseService.getAll(page - 1, size, sortType));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<CourseBasicResp>get(@PathVariable Long id){
        return ResponseEntity.ok(this.courseService.get(id));
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
