package tn.esprit.ktebi.services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void createOne(T t) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deletOne(T t) throws SQLException;
    List<T> selectAll() throws SQLException;

}
