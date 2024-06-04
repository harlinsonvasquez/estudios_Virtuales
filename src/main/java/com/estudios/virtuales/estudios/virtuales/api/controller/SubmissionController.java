package com.estudios.virtuales.estudios.virtuales.api.controller;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.LessonReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.request.SubmissionReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.LessonBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.SubmissionResp;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.ISubmissionService;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/submission")
@AllArgsConstructor
public class SubmissionController {
    @Autowired
    private  final ISubmissionService submissionService;
    @GetMapping
    public ResponseEntity<Page<SubmissionResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestHeader(required = false) SortType sortType
    ){

        if (Objects.isNull(sortType)) sortType = SortType.NONE;

        return ResponseEntity.ok(this.submissionService.getAll(page -1, size, sortType));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<SubmissionResp>get(@PathVariable Long id){
        return ResponseEntity.ok(this.submissionService.get(id));
    }
    @PostMapping
    public ResponseEntity<SubmissionResp>insert(@Validated @RequestBody SubmissionReq request){
        return ResponseEntity.ok(this.submissionService.create(request));
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<SubmissionResp>update(@Validated @RequestBody SubmissionReq request,
                                                 @PathVariable Long id){
        return ResponseEntity.ok(this.submissionService.update(request, id));
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        this.submissionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
