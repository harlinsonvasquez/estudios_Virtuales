package com.estudios.virtuales.estudios.virtuales.infrastructure.services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.SubmissionReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.SubmissionResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.TaskBasic;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.TaskBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.UserBasicResp;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Submission;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Task;
import com.estudios.virtuales.estudios.virtuales.domain.entities.User;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.SubmissionRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.TaskRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.UserRepository;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.ISubmissionService;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import com.estudios.virtuales.estudios.virtuales.utils.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubmissionService implements ISubmissionService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private final SubmissionRepository submissionRepository;
    @Override
    public SubmissionResp create(SubmissionReq request) {
        User student=userRepository.findById(request.getUserId())
                .orElseThrow(()-> new BadRequestException("estudiante no encontrado"));
        Task task=taskRepository.findById(request.getTaskId())
                .orElseThrow(()-> new BadRequestException("tarea no encontrada con ese id"));

        Submission submission=new Submission();
        submission.setContent(request.getContent());
        submission.setSubmissionDate(request.getSubmissionDate());
        submission.setGrade(request.getGrade());
        submission.setUser(student);
        submission.setTasks(task);

        return entityToResp(submissionRepository.save(submission));
    }

    @Override
    public SubmissionResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public SubmissionResp update(SubmissionReq request, Long id) {
        Submission submission=this.find(id);

        User student=userRepository.findById(request.getUserId())
                .orElseThrow(()-> new BadRequestException("estudiante no encontrado"));
        Task task=taskRepository.findById(request.getTaskId())
                .orElseThrow(()-> new BadRequestException("tarea no encontrada con ese id"));
        submission=this.requestToEntity(request);
        submission.setContent(request.getContent());
        submission.setSubmissionDate(request.getSubmissionDate());
        submission.setGrade(request.getGrade());
        submission.setUser(student);
        submission.setTasks(task);
        submission.setId(id);
        return this.entityToResp(this.submissionRepository.save(submission));
    }

    @Override
    public void delete(Long id) {
        this.submissionRepository.delete(this.find(id));
    }

    @Override
    public Page<SubmissionResp> getAll(int page, int size, SortType sort) {
        if(page<0){
            page=0;
        }
        PageRequest pagination=null;
        switch (sort){
            case NONE:
                pagination=PageRequest.of(page,size);
                break;

            case ASC:
                pagination=PageRequest.of(page,size, Sort.by(FIELD_BY_SORT).ascending());
                break;
            case DESC:
                pagination=PageRequest.of(page,size,Sort.by(FIELD_BY_SORT).descending());
                break;
        }
        return this.submissionRepository.findAll(pagination).map(this::entityToResp);
    }
    private SubmissionResp entityToResp(Submission entity){
        UserBasicResp student=UserBasicResp.builder()
                .id(entity.getUser().getId())
                .userName(entity.getUser().getUserName())
                .email(entity.getUser().getEmail())
                .fullName(entity.getUser().getFullName())
                .role(entity.getUser().getRole())
                .build();

        TaskBasic task= TaskBasic.builder()
                .id(entity.getTasks().getId())
                .taskTitle(entity.getTasks().getTaskTitle())
                .description(entity.getTasks().getDescription())
                .dueDate(entity.getTasks().getDueDate())
                .build();

        SubmissionResp response=new SubmissionResp();
        BeanUtils.copyProperties(entity,response);
        response.setUserId(student);
        response.setTaskId(task);
        return response;
    }
    private Submission requestToEntity(SubmissionReq request){
        return Submission.builder()
                .content(request.getContent())
                .submissionDate(request.getSubmissionDate())
                .grade(request.getGrade())
                .build();
    }


    private Submission find(Long id){
         return this.submissionRepository.findById(id)
                 .orElseThrow(()->new BadRequestException("no hay entregas con ese id"));
    }
}
