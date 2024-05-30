package com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services;

import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import org.springframework.data.domain.Page;

public interface CrudService <RQ,RS,ID>{
    public RS create(RQ request);
    public RS ger(ID id);
    public RS update(RQ request, ID id);
    public void delete(ID id);
    public Page<RS>getAll(int page, int size, SortType sort);
}
