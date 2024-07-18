package com.supermarkets.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.supermarkets.exception.CustomizedException;
import com.supermarkets.v1.model.Department;
import com.supermarkets.v1.model.Product;
import com.supermarkets.v1.model.Supermarket;


@Component
public class SupermarketDB {

	@Autowired
	private DBConnection db;

	public String saveSuperMarket(Supermarket supermarket) {
		try (Connection conn = db.getConnection()) {
			conn.setAutoCommit(false); // Start transaction
			long supermarketId = 0;
			long departmentId = 0;
			// Insert a supermarket
			String insertSupermarketSQL = "INSERT INTO Supermarket (name,address) VALUES (?,?)";
			try (PreparedStatement pstmt = conn.prepareStatement(insertSupermarketSQL,
					PreparedStatement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, supermarket.getName());
				pstmt.setString(2, supermarket.getAddress());
				pstmt.executeUpdate();
				ResultSet generatedKeys = pstmt.getGeneratedKeys();
				if (generatedKeys.next()) {
					supermarketId = generatedKeys.getLong(1);
				}
				// Insert departments linked to that supermarket
				String insertDepartmentSQL = "INSERT INTO Department (category, supermarket_id) VALUES (?, ?)";
				String insertProductSQL = "INSERT INTO Product (name,quantity, price, department_id) VALUES (?, ?, ?,?)";
				try (PreparedStatement pstmtDept = conn.prepareStatement(insertDepartmentSQL,
						PreparedStatement.RETURN_GENERATED_KEYS)) {
					for (Department d : supermarket.getDepartments()) {
						pstmtDept.setString(1, d.getCategory());
						pstmtDept.setLong(2, supermarketId);
						pstmtDept.executeUpdate();
						generatedKeys = pstmtDept.getGeneratedKeys();
						if (generatedKeys.next()) {
							departmentId = generatedKeys.getLong(1);
						}
						for (Product p : d.getProducts()) {
							try (PreparedStatement pstmtProd = conn.prepareStatement(insertProductSQL)) {
								pstmtProd.setString(1, p.getName());
								pstmtProd.setLong(2, p.getQuantity());
								pstmtProd.setDouble(3, p.getPrice());
								pstmtProd.setLong(4, departmentId);
								pstmtProd.executeUpdate();

							} catch (SQLException e) {
								e.printStackTrace();
								throw new CustomizedException("Unable to add products");
							}
						}
					}
					conn.commit(); // Commit transaction
					return "record inserted";
				} catch (SQLException e) {
					conn.rollback();
					e.printStackTrace();
					throw new CustomizedException("Unable to create departments");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new CustomizedException("Unable to create supermarket");
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new CustomizedException("Unable to connect to database");
		}

	}
	public Supermarket getSupermarketById(Integer supermarketId)
	{
		boolean flag=false;
		try (Connection connection =db.getConnection()) {
            String sql = "SELECT s.id, s.name AS supermarket_name, s.address AS supermarket_address, " +
                         "d.category AS department_category,p.name AS product_name, " +
                         "p.quantity AS product_quantity, p.price AS product_price " +
                         "FROM Supermarket s " +
                         "LEFT JOIN Department d ON s.id = d.supermarket_id " +
                         "LEFT JOIN Product p ON d.id = p.department_id " +
                         "WHERE s.id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, supermarketId);

            ResultSet resultSet = statement.executeQuery();
            Supermarket supermarket = new Supermarket();
            supermarket.setDepartments(new ArrayList<>());

            while (resultSet.next()) {
                // Set supermarket details
            	flag=true;
                if (supermarket.getId() == null) {
                    supermarket.setId(resultSet.getInt("id"));
                    supermarket.setName(resultSet.getString("supermarket_name"));
                    supermarket.setAddress(resultSet.getString("supermarket_address"));
                }

            
                String departmentCategory = resultSet.getString("department_category");
                if (departmentCategory != null) {
                    // Find or create department
                    Department department = supermarket.getDepartments().stream()
                            .filter(d -> d.getCategory().equals(departmentCategory))
                            .findFirst()
                            .orElseGet(() -> {
                                Department newDept = new Department();
              
                                newDept.setCategory(departmentCategory);
                                newDept.setProducts(new ArrayList<>());
                                supermarket.getDepartments().add(newDept);
                                return newDept;
                            });

                    // Add product to department
                    Product product = new Product();
                    product.setName(resultSet.getString("product_name"));
                    product.setQuantity(resultSet.getInt("product_quantity"));
                    product.setPrice(resultSet.getDouble("product_price"));
                    department.getProducts().add(product);
                }
            }

            if(flag) return supermarket;
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CustomizedException("Unable to connect to database");
        }
	
    }
	public String deleteSuperMarket(Integer id) {
		// TODO Auto-generated method stub
		try {
			Connection con = db.getConnection();
			PreparedStatement stmt = con.prepareStatement("delete from supermarket where id=" + id);
			int result = stmt.executeUpdate();
			if (result == 1) {
				return "record deleted";
			}
			stmt.close();
			db.closeConnection(con);
		
		} catch (SQLException e) {
			System.out.println(e);			            
	         e.printStackTrace();


	}
		return "Error occured";
	}
}
