package com.estudios.virtuales.estudios.virtuales.infrastructure.services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.TaskReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.LessonBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.TaskBasicResp;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Lesson;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Task;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.LessonRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.SubmissionRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.TaskRepository;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.ITaskService;
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
public class TaskService implements ITaskService {
    @Autowired
    private final LessonRepository lessonRepository;
    @Autowired
    private final SubmissionRepository submissionRepository;
    @Autowired
    private final TaskRepository taskRepository;

    @Override
    public TaskBasicResp create(TaskReq request) {
        Lesson lesson=lessonRepository.findById(request.getLessonId())
                .orElseThrow(()->new BadRequestException("id de lesson no encontrado"));

        Task task=new Task();
        task.setTaskTitle(request.getTaskTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setLessons(lesson);
        return entityToResp(taskRepository.save(task));
    }

    @Override
    public TaskBasicResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public TaskBasicResp update(TaskReq request, Long id) {
        Task task=this.find(id);
        Lesson lesson=this.lessonRepository.findById(request.getLessonId())
                .orElseThrow(()-> new BadRequestException("no hay lesson con ese id"));
        task=this.requestToEntity(request);
        task.setTaskTitle(request.getTaskTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setId(id);
        task.setLessons(lesson);
        return this.entityToResp(this.taskRepository.save(task));
    }

    @Override
    public void delete(Long id) {
            this.taskRepository.delete(this.find(id));
    }

    @Override
    public Page<TaskBasicResp> getAll(int page, int size, SortType sort) {
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
        return this.taskRepository.findAll(pagination).map(this::entityToResp);
    }
    private TaskBasicResp entityToResp(Task entity){
        LessonBasicResp lesson=LessonBasicResp.builder()
                .id(entity.getLessons().getId())
                .lessonTitle(entity.getLessons().getLessonTitle())
                .content(entity.getLessons().getContent())
                .build();


        TaskBasicResp response= new TaskBasicResp();
        BeanUtils.copyProperties(entity,response);
        response.setLessonId(lesson);
        return response;
    }
    private Task requestToEntity(TaskReq request){
        return Task.builder()
                .taskTitle(request.getTaskTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .build();
    }
    private Task find(Long id){
        return this.taskRepository.findById(id).orElseThrow(()->new BadRequestException("no hay tareas con el id suministrado"));
    }
}
