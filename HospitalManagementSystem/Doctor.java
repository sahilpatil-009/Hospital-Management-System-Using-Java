package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor {
	
	private Connection connection;
	
	public Doctor(Connection connection) {
		this.connection = connection;
	}
	
	
	public void viewDoctors() {
		
		String query = "select * from doctor";
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Doctors : ");
			System.out.println("+------------+-------------------------------------+--------------+------------------+");
			System.out.println("| Doctor ID  |              Doctor Name            |      Specialization             |");
			System.out.println("+------------+-------------------------------------+--------------+------------------+");
			
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String Special = resultSet.getString("specialization");
				System.out.printf("|%-12s|%-37s|%-33s|\n", id, name, Special);
				System.out.println("+------------+-------------------------------------+--------------+------------------+");
 			}
			
		}catch(Exception e) {
			System.out.print(e);
		}
	}
	
	public boolean getDoctorById(int id){
		
		String query = "select * from doctor where id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			System.out.print(e);
		}
		return false;
	}
}
