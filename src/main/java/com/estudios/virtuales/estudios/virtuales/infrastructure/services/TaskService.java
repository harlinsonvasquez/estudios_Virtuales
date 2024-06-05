package com.estudios.virtuales.estudios.virtuales.infrastructure.services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.TaskReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.*;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Lesson;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Submission;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Task;
import com.estudios.virtuales.estudios.virtuales.domain.entities.User;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
   @Transactional
   public Page<TaskBasicResp> getAll(int page, int size, SortType sort) {
       PageRequest pageRequest = getPageRequest(page, size, sort);
       return taskRepository.findAll(pageRequest).map(this::entityToResp);
   }
    private PageRequest getPageRequest(int page, int size, SortType sort) {
        if (page < 0) page = 0;
        Sort.Direction direction = sort.equals(SortType.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page, size, Sort.by(direction, "id"));
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

    @Override
    @Transactional
    public TaskResp getWithSubmissions(Long id) {
        Task task = find(id);
        List<SubmissionBasicResp> submissions = submissionRepository.findByTasksId(id)
                .stream()
                .map(this::submissionEntityToBasicResp)
                .collect(Collectors.toList());

        return TaskResp.builder()
                .id(task.getId())
                .taskTitle(task.getTaskTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .submissions(submissions)
                .build();
    }
    private SubmissionBasicResp submissionEntityToBasicResp(Submission submission) {
        User student = submission.getUser();
        UserBasic userResp = UserBasic.builder()
                .id(student.getId())
                .fullName(student.getUserName())
                .email(student.getEmail())
                .build();

        return SubmissionBasicResp.builder()
                .id(submission.getId())
                .content(submission.getContent())
                .submissionDate(submission.getSubmissionDate())
                .grade(submission.getGrade())
                .student(userResp)
                .build();
    }
}
