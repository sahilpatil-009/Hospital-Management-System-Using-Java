package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class HospitalManageSys {
		
	private static final String url = "jdbc:mysql://127.0.0.1:3306/hospital";
	private static final String username = "root";
	private static final String password = "mysql";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(Exception e) {
			System.out.print(e);
		}
		Scanner scanner = new Scanner(System.in);
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			
			Patient patient = new Patient(connection, scanner);
			Doctor doctor = new Doctor(connection);
			
			while(true) {
				
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1. Add Patients");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter Your Choice:");
				int ch = scanner.nextInt();
				
				switch(ch) {
					
					case 1:
						//Add patient
						patient.addPatient();
						break;
					case 2:
						//View Patients
						patient.viewPatients();
						break;
					case 3:
						//View Doctors
						doctor.viewDoctors();
						break;
					case 4:
						//Book Appointment
						BookAppointment(patient, doctor, connection, scanner);
						break;
					case 5:
						System.out.println("ThankYou For Using Hospital Management System !");
						return;
						
					default :
						System.out.println("Invalid Innput !");
				}
			}
			
		}catch(Exception e) {
			System.out.print(e);
		}
	}
	
	public static void BookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
		System.out.println("Enter Patient Id:");
		int patientId = scanner.nextInt();
		System.out.println("Enter Doctor Id:");
		int doctorId = scanner.nextInt();
		System.out.println("Enter Appointment date (YYYY-MM-DD):");
		String appointmentDate = scanner.next();
		if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
			
			if(checkDoctorAvailability(doctorId, appointmentDate, connection)) {
				
				String appointmentQuery = "insert into appointments(patient_id, doctor_id, appointent_date) values (?,?,?)";
				try {
					
					PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
					preparedStatement.setInt(1, patientId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, appointmentDate);
					int rowsAffected = preparedStatement.executeUpdate();
					if(rowsAffected>0) {
						System.out.println("Appointment Booked");
					}else {
						System.out.println("Fail to book Appointment");
					}
				}catch(Exception e) {
					System.out.println(e);
				}
				
			}else {
				System.out.println("Doctor Not Available !");
			}
			
		}else {
			
			System.out.println("Either Doctor OR patient Dpsen't Exists !!");
			
		}
	}
	
	public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
		String query = "select count(*) from appointments where doctor_id = ? and  appointent_date = ?";
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, appointmentDate);
			
			ResultSet  resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int count = resultSet.getInt(1);
				if(count == 0)
				{
					return true;
				}else {
					return false;
				}
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return false;
	}
}
