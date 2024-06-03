package com.estudios.virtuales.estudios.virtuales.api.controller;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.LessonReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.request.TaskReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.LessonBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.TaskBasicResp;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.ITaskService;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/task")
@AllArgsConstructor
public class TaskController {
    @Autowired
    private final ITaskService taskService;


    @GetMapping
    public ResponseEntity<Page<TaskBasicResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestHeader(required = false) SortType sortType
    ){

        if (Objects.isNull(sortType)) sortType = SortType.NONE;

        return ResponseEntity.ok(this.taskService.getAll(page -1, size, sortType));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskBasicResp>get(@PathVariable Long id){
        return ResponseEntity.ok(this.taskService.get(id));
    }
    @PostMapping
    public ResponseEntity<TaskBasicResp>insert(@Validated @RequestBody TaskReq request){
        return ResponseEntity.ok(this.taskService.create(request));
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<TaskBasicResp>update(@Validated @RequestBody TaskReq request,
                                                 @PathVariable Long id){
        return ResponseEntity.ok(this.taskService.update(request, id));
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
