package com.estudios.virtuales.estudios.virtuales.api.controller;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.EnrollmentReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.EnrollmentResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.UserBasicResp;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Enrollment;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.IEnrollmentService;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/enrollment")
@AllArgsConstructor
public class EnrollmentController {
    @Autowired
    private  final IEnrollmentService enrollmentService;
    @GetMapping
    public ResponseEntity<Page<EnrollmentResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestHeader(required = false) SortType sortType
    ){

        if (Objects.isNull(sortType)) sortType = SortType.NONE;

        return ResponseEntity.ok(this.enrollmentService.getAll(page -1, size, sortType));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<EnrollmentResp>get(@PathVariable Long id){
        return ResponseEntity.ok(this.enrollmentService.get(id));
    }
    @PostMapping
    public ResponseEntity<EnrollmentResp> insert(@Validated @RequestBody EnrollmentReq request){
        return ResponseEntity.ok(this.enrollmentService.create(request));
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<EnrollmentResp>update(
            @Validated @RequestBody EnrollmentReq request,
            @PathVariable Long id ){
        return ResponseEntity.ok(this.enrollmentService.update(request,id));
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        this.enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
