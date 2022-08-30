package com.itzstonlex.jnq.orm;

import com.itzstonlex.jnq.content.field.FieldOperator;
import com.itzstonlex.jnq.orm.data.repository.annotation.EntityParam;
import com.itzstonlex.jnq.orm.data.repository.annotation.FindEntity;
import com.itzstonlex.jnq.orm.data.repository.annotation.FindEntityList;
import com.itzstonlex.jnq.orm.data.repository.annotation.SaveEntity;

import java.util.List;

public interface UserRepository {

    @SaveEntity
    void save(User user);

    @FindEntity
    User findByID(@EntityParam(name = "id") int id);

    @FindEntity
    User findByName(@EntityParam(name = "name") String name);

    @FindEntityList(limit = 100)
    List<User> findListByName(@EntityParam(operator = FieldOperator.LIKE, name = "name") String name);
}