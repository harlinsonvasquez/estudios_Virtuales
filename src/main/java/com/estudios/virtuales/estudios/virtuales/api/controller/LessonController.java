package com.estudios.virtuales.estudios.virtuales.api.controller;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.LessonReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.request.UserReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.LessonBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.UserBasicResp;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.ILessonService;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<LessonBasicResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestHeader(required = false) SortType sortType
    ){

        if (Objects.isNull(sortType)) sortType = SortType.NONE;

        return ResponseEntity.ok(this.lessonService.getAll(page -1, size, sortType));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<LessonBasicResp>get(@PathVariable Long id){
        return ResponseEntity.ok(this.lessonService.get(id));
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
