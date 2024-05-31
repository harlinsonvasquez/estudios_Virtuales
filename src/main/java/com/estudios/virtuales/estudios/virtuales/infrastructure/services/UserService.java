package com.estudios.virtuales.estudios.virtuales.infrastructure.services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.UserReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.UserBasicResp;
import com.estudios.virtuales.estudios.virtuales.domain.entities.User;
import com.estudios.virtuales.estudios.virtuales.domain.repositories.UserRepository;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.IUserService;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import com.estudios.virtuales.estudios.virtuales.utils.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService  implements IUserService {
    @Autowired
    private  final UserRepository userRepository;
    @Override
    public UserBasicResp create(UserReq request) {
        User user=this.requestToEntity(request);

        return this.entityToResp(this.userRepository.save(user));
    }

    @Override
    public UserBasicResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public UserBasicResp update(UserReq request, Long id) {
        User user=this.find(id);
        user=this.requestToEntity(request);
        user.setId(id);
        return this.entityToResp(this.userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        this.userRepository.delete(this.find(id));
    }

    @Override
    public Page<UserBasicResp> getAll(int page, int size, SortType sort) {
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
        return this.userRepository.findAll(pagination).map(this::entityToResp);
    }
    private UserBasicResp entityToResp(User entity){
        UserBasicResp response=new UserBasicResp();
        BeanUtils.copyProperties(entity,response);
        return response;
    }
    private User requestToEntity(UserReq request){
        User user=new User();
        BeanUtils.copyProperties(request,user);
        return  user;

    }
    private User find(Long id){
        return this.userRepository.findById(id).orElseThrow(()-> new BadRequestException("no hay registros de usuarios con ese id"));
    }
}
