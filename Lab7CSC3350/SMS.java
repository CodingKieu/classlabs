import java.sql.*;
import java.util.ArrayList;

public class SMS {
    
    // main program to print students with their enrolled courses from data in the MYSql 'SMS2' database
	
    public static void main(String[] args) 
	{

        String url = "jdbc:mysql://localhost:3306/SMS";
        String user = "root";
        String password = "Keto132!";
		// 'Reports()' method could have multiple report types
        Reports("enrollment", url, user, password);   
    }
    
    public static void Reports(String reportName, String url, String user, String password) 
	{
        ArrayList<Student> students = new ArrayList<>();
        
		if(reportName.toLowerCase().equals("enrollment")) {
            String sqlcommand = "SELECT student_id, firstname, lastname, classify, major "+ 
                                "FROM students " +
                                "ORDER BY student_id; ";
                                
            try (Connection myConn = DriverManager.getConnection(url, user, password)) {
                Statement myStmt = myConn.createStatement();
                ResultSet myRS = myStmt.executeQuery(sqlcommand);
                
                while (myRS.next()) {
                    Student temp = new Student();
					
					// store all values for this student in 'temp'
					temp.setStudent_id(myRS.getInt("student_id"));
					temp.setFname(myRS.getString("firstname"));
					temp.setLname(myRS.getString("lastname"));
					temp.setClassify(myRS.getString("classify"));
					temp.setMajor(myRS.getString("major"));
					
					// enrollment courses (ArrayList)
					temp.setEnrolled_courses();
					

					students.add(temp);	
                }
                myConn.close();
            
            } catch (Exception e) {
                System.out.println("ERROR " + e.getLocalizedMessage());
            } finally {
            }

            PrintEnrollments(students);
			
        } else {
            System.out.println("\n\n****ERROR**** Invalid report requested\n\n");
        }
    }
    
    public static void PrintEnrollments(ArrayList<Student> myStudents) 
	{

        System.out.println("\n\n\nStudent Enrollment Report by Khai Kieu\n");
		
        System.out.println("ID   CLASS\t MAJOR\t NAME\t\t\tEnrollments");
		
		// using an enhanced 'for loop'
        for(Student s:myStudents) {
			

			System.out.println(s.getStudent_id()+"   "+s.getClassify()+"\t "+s.getMajor()+"\t "+s.getFname()+" "+s.getLname());
			System.out.println();
			

			ArrayList<Enrollments> e1 = new ArrayList<>();
            e1.addAll(s.getEnrolled_courses());
            
			int count = 1;
			for( Enrollments eStudent:e1 ) {
				

				
				System.out.println("\t\t\t\t\t\t" + count + ") " + eStudent.getDeptID() + "-" + eStudent.getCourseID()
						+ ", HRS:" + eStudent.getCourseHours() + ", " + eStudent.getCourseName());
				count++;
            }
            System.out.println("\t\t\t\t\t\t-----------------------------------------");
			System.out.println();
        }
    }
} 
