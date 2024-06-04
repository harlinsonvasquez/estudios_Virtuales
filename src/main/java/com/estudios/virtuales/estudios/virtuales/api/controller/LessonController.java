package com.estudios.virtuales.estudios.virtuales.api.controller;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.LessonReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.request.UserReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.LessonBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.LessonResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.UserBasicResp;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.ILessonService;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/lesson")
@AllArgsConstructor
public class LessonController {
    @Autowired
    private final ILessonService lessonService;

    @GetMapping
    public Page<LessonBasicResp> getAllLessons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NONE") SortType sort) {

        Page<LessonBasicResp> lessonBasicRespPage = lessonService.getAll(page, size, sort);
        return new PageImpl<>(lessonBasicRespPage.getContent(), lessonBasicRespPage.getPageable(), lessonBasicRespPage.getTotalElements());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<LessonBasicResp>get(@PathVariable Long id){
        return ResponseEntity.ok(this.lessonService.get(id));
    }
    @GetMapping(path = "/{id}/tasks")
    public ResponseEntity<LessonResp> getLessonWithTasks(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getWithTasks(id));
    }
    @PostMapping
    public ResponseEntity<LessonBasicResp>insert(@Validated @RequestBody LessonReq request){
        return ResponseEntity.ok(this.lessonService.create(request));
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<LessonBasicResp>update(@Validated @RequestBody LessonReq request,
                                               @PathVariable Long id){
        return ResponseEntity.ok(this.lessonService.update(request, id));
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        this.lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
