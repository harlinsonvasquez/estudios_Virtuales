package com.estudios.virtuales.estudios.virtuales.api.controller;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.UserReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.UserBasicResp;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.IUserService;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor

public class UserController {
    @Autowired
    private final IUserService userService;

    @GetMapping
    public ResponseEntity<Page<UserBasicResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestHeader(required = false) SortType sortType
    ){

        if (Objects.isNull(sortType)) sortType = SortType.NONE;

        return ResponseEntity.ok(this.userService.getAll(page -1, size, sortType));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserBasicResp>get(@PathVariable Long id){
        return ResponseEntity.ok(this.userService.get(id));
    }
    @PostMapping
    public ResponseEntity<UserBasicResp>insert(@Validated @RequestBody UserReq request){
        return ResponseEntity.ok(this.userService.create(request));
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<UserBasicResp>update(@Validated @RequestBody UserReq request,
                                               @PathVariable Long id){
        return ResponseEntity.ok(this.userService.update(request, id));
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
