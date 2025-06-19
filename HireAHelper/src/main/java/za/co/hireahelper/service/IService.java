package za.co.hireahelper.service;

import java.util.List;

public interface IService<T, ID> {
    T create(T entity);

    T read(ID id);

    T update(T entity);

    boolean delete(ID id);

    List<T> getAll();


}
