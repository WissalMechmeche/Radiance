/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author MSI
 */
public interface IService<T> {
    public void create(T t) throws SQLException ;
    public void update(T t) throws SQLException ;
    public void delete(T t) throws SQLException ;
    public List <T> selectAll() throws SQLException ;
    
}
