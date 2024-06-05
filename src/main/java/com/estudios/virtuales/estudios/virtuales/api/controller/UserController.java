package com.estudios.virtuales.estudios.virtuales.api.controller;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.UserReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.UserBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.UserCourseResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.UserSubmissionResp;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.IUserService;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
@Tag(name = "Usuarios")
public class UserController {
    @Autowired
    private final IUserService userService;
    @Operation(
            summary ="lista todos los usuarios con la info basica",
            description = "debes enviar la pagina y el tamaño para recibir todos los usuarios"
    )
    @GetMapping
    public Page<UserBasicResp> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NONE") SortType sort) {

        Page<UserBasicResp> userBasicRespPage = userService.getAll(page, size, sort);
        return new PageImpl<>(userBasicRespPage.getContent(), userBasicRespPage.getPageable(), userBasicRespPage.getTotalElements());
    }
    @ApiResponse(
            responseCode = "400",
            description = "cuando el id no es valido",
            content = {
                    @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            }
    )
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserBasicResp>get(@PathVariable Long id){
        return ResponseEntity.ok(this.userService.get(id));
    }
    @GetMapping(path = "/{id}/submissions")
    public ResponseEntity<UserSubmissionResp> getUserWithSubmissions(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getWithSubmissions(id));
    }
    @GetMapping(path = "/{id}/courses")
    public ResponseEntity<UserCourseResp> getUserWithCourses(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getWithCourses(id));
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
