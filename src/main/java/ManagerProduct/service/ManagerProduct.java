package ManagerProduct.service;

import ManagerProduct.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerProduct implements IManagerProduct{
    private static final String INSERT_PRODUCT_SQL = "insert into product"+"(name_product,price,quantity,color,description,category)"+"value (?,?,?,?,?,?);";
    private static final String SELECT_PRODUCT_BY_ID = "select * from product where id_product = ?;" ;
    private static final String SELECT_ALL_PRODUCTS = "select * from product;";
    private static final String DELETE_PRODUCTS_SQL = "delete from product where id_product = ?;";
    private static final String UPDATE_PRODUCTS_SQL = "update product set name_product = ?,price= ?, quantity =?,color =?,description =?,category =? where id_product = ?;";
    private String jdbcURL =  "jdbc:mysql://localhost:3306/managerproduct";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    public ManagerProduct(){
    }
    protected Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
            System.out.println("Ket noi thanh cong");
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void insertProduct(Product product) throws SQLException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {

            preparedStatement.setString(1, product.getNameProduct());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescribe());
            preparedStatement.setString(6, product.getCategory());

            preparedStatement.executeUpdate();
        }
}

    @Override
    public Product selectProduct(int id) throws SQLException {
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                String name = rs.getString("name_product");
                int price = rs.getInt("price");
                int quantity  = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                String category = rs.getString("category");
                product = new Product(id, name, price,quantity,color, description,category);
            }
        }
        return product;
    }

    @Override
    public List<Product> selectAllProduct() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS)){

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name_product");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                String category = rs.getString("category");
                products.add(new Product(id, name, price, quantity,color, description, category));
            }
        }
        return products;
    }

    @Override
    public boolean deleteProduct(int id) throws SQLException {
        boolean rowDelete;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCTS_SQL)) {
            preparedStatement.setInt(1,id);
            rowDelete = preparedStatement.executeUpdate() > 0;
        }
        return rowDelete;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdate;
        try(Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(UPDATE_PRODUCTS_SQL)) {
            prs.setString(1, product.getNameProduct());
            prs.setInt(2, product.getPrice());
            prs.setInt(3, product.getQuantity());
            prs.setString(4, product.getColor());
            prs.setString(5, product.getDescribe());
            prs.setString(4, product.getCategory());

            rowUpdate = prs.executeUpdate() > 0;
        }
        return rowUpdate;
    }
}
