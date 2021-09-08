package ManagerProduct.controller;

import ManagerProduct.model.Product;
import ManagerProduct.service.ManagerProduct;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@WebServlet (name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    private ManagerProduct managerProduct;

    public void init() {
        managerProduct = new ManagerProduct();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("Homepage");
        if(action == null){
            action = "";
        }
        switch (action) {
            case "create":
                showCreateForm(req,resp);
                break;
            case "edit":
                showEditForm(req,resp);
                break;
            case "delete":
                deleteProduct(req, resp);
                break;
            default:
                showListProducts(req, resp);
                break;
        }
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        List<Product> listProducts = null;
        try {
            managerProduct.deleteProduct(id);

            listProducts = managerProduct.selectAllProduct();

            req.setAttribute("listProducts", listProducts);
            RequestDispatcher dispatcher = req.getRequestDispatcher("listProducts.jsp");

            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void showListProducts(HttpServletRequest req, HttpServletResponse resp) {
        List<Product> listProducts = null;
        try {
            listProducts = managerProduct.selectAllProduct();
            req.setAttribute("listProducts", listProducts);
            RequestDispatcher dispatcher = req.getRequestDispatcher("listProducts.jsp");
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Product existingProduct = null;
        try {
            existingProduct = managerProduct.selectProduct(id);
            RequestDispatcher dispatcher = req.getRequestDispatcher("edit.jsp");
            req.setAttribute("product", existingProduct);
            dispatcher.forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("create.jsp");
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("Homepage");
        if(action == null){
            action = "";
        }
        switch (action){
            case "create":
                createProduct(req,resp);
                break;
            case "edit":
                editProduct(req,resp);
                break;
        }
    }

    private void editProduct(HttpServletRequest req, HttpServletResponse resp) {

        String name1 = req.getParameter("name_product");
        int price = Integer.parseInt(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String color = req.getParameter("color");
        String description = req.getParameter("description");
        String category = req.getParameter("category");

        Product book = new Product(name1, price, quantity, color, description, category);
        RequestDispatcher dispatcher = req.getRequestDispatcher("edit.jsp");
        try {
            managerProduct.updateProduct(book);
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createProduct(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name_product");
        int price = Integer.parseInt(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String color = req.getParameter("color");
        String description = req.getParameter("description");
        String category = req.getParameter("category");

        Product newProduct = new Product(name, price, quantity,color, description, category);
        try {
            managerProduct.insertProduct(newProduct);
            resp.sendRedirect("/products?Homepage");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
