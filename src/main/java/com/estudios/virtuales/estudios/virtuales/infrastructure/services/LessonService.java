package com.estudios.virtuales.estudios.virtuales.infrastructure.services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.LessonReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.CourseBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.LessonBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.UserBasicResp;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Course;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Lesson;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.CourseRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.LessonRepository;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.TaskRepository;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.ILessonService;
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
public class LessonService implements ILessonService {
    @Autowired
    private final CourseRepository courseRepository;
    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private final LessonRepository lessonRepository;
    @Override
    public LessonBasicResp create(LessonReq request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new BadRequestException("Curso no encontrado"));

        Lesson lesson=new Lesson();
        lesson.setLessonTitle(request.getLessonTitle());
        lesson.setContent(request.getContent());
        lesson.setCourse(course);
        return entityToResp(lessonRepository.save(lesson));
    }

    @Override
    public LessonBasicResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public LessonBasicResp update(LessonReq request, Long id) {
        Lesson lesson=this.find(id);

        Course course=this.courseRepository.findById(request.getCourseId())
                .orElseThrow(()-> new BadRequestException("NO HAY CURSOS CON ESE ID"));
        lesson=this.requestToEntity(request);

        lesson.setLessonTitle(request.getLessonTitle());
        lesson.setContent(request.getContent());
        lesson.setId(id);
        lesson.setCourse(course);

        return this.entityToResp(this.lessonRepository.save(lesson));
    }

    @Override
    public void delete(Long id) {
        this.lessonRepository.delete(this.find(id));
    }

    @Override
    public Page<LessonBasicResp> getAll(int page, int size, SortType sort) {
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
        return this.lessonRepository.findAll(pagination).map(this::entityToResp);
    }
    private LessonBasicResp entityToResp(Lesson entity){
        CourseBasicResp course = CourseBasicResp.builder()
                .id(entity.getCourse().getId())
                .courseName(entity.getCourse().getCourseName())
                .description(entity.getCourse().getDescription())
                .build();

        LessonBasicResp response=new LessonBasicResp();
        BeanUtils.copyProperties(entity,response);
        response.setCourseId(course);
        return response;
    }
    private Lesson requestToEntity(LessonReq request){
        return Lesson.builder()
                .lessonTitle(request.getLessonTitle())
                .content(request.getContent())
                .build();
    }
    private Lesson find(Long id){
        return this.lessonRepository.findById(id)
                .orElseThrow(()->new BadRequestException("no hay materias con ese id"));
    }

}
