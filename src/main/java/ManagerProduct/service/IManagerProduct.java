package ManagerProduct.service;

import ManagerProduct.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IManagerProduct {
    public void insertProduct(Product product) throws SQLException;
    public Product selectProduct(int id) throws SQLException;
    public List<Product> selectAllProduct() throws SQLException;
    public boolean deleteProduct(int id) throws SQLException;
    public boolean updateProduct(Product product) throws SQLException;
}
