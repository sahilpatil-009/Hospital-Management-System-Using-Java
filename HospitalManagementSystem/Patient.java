package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.mysql.cj.xdevapi.Result;


public class Patient {
	
		private Connection connection;
		private Scanner scanner;
		
		public Patient(Connection connection, Scanner scanner) {
			this.connection = connection;
			this.scanner = scanner;
		}
		
		public void addPatient() {
			System.out.print("Enter Patient Name:");
			String name = scanner.next();
			
			System.out.print("Enter Patient Age:");
			int age = scanner.nextInt();

			System.out.print("Enter Patient Gender:");
			String gender = scanner.next();
			
			try {
				
				String query = "insert into patients(name, age, gender) values(?, ?, ?)";
				
				PreparedStatement preparedStatement =  connection.prepareStatement(query);
				preparedStatement.setString(1, name);
				preparedStatement.setInt(2, age);
				preparedStatement.setString(3, gender);
				
				int affectedRows = preparedStatement.executeUpdate();
				if(affectedRows>0) {
					System.out.println("Patient Added Succesfully !");
				}else {
					System.out.println("Fail to Add Patient");
				}
				
			}catch(Exception e) {
				
			}

		}
		
		public void viewPatients() {
			
			String query = "select * from patients";
			
			try {
				
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();
				System.out.println("Patients : ");
				System.out.println("+------------+-------------------------------------+--------------+------------------+");
				System.out.println("| Parient ID |              Patient Name           |      Age     |       Gender     |");
				System.out.println("+------------+-------------------------------------+--------------+------------------+");
				
				while(resultSet.next()) {
					int id = resultSet.getInt("id");
					String name = resultSet.getString("name");
					int age = resultSet.getInt("age");
					String gender = resultSet.getString("gender");
					System.out.printf("|%-13s|%-38s|%-15s|%-19s|\n", id, name, age, gender);
				System.out.println("+------------+-------------------------------------+--------------+------------------+");
				}
				
			}catch(Exception e) {
				System.out.print(e);
			}
		}
		
		public boolean getPatientById(int id){
			
			String query = "select * from patients where id = ?";
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
