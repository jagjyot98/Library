package jdbc;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
public class Project1{
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{   
		Library1 lib= new Library1();
		Scanner obj=new Scanner(System.in);
	    Class.forName("com.mysql.jdbc.Driver");  	
	    
	    // Working on main database 'Library'
	    
	    do{
		System.out.println("*****Library Management System*****");
		System.out.println("1. Add Book");
		System.out.println("2. View Books");
		System.out.println("3. Issue");
		System.out.println("4. Issue History");
		System.out.println("5. Exit..");
		System.out.print("Entr your choice:");
		int ch= obj.nextInt();
		switch(ch){
		case 1:
			lib.add();
			break;
		case 2:
			lib.view();
			break;
		case 3:
			lib.iss();
			break;
		case 4:
			lib.his();
			break;
		case 5:
			System.exit(0);
			break;
		default:
			System.out.print("Invalid Choice");	
		}
	    }while(true);
     }
  }

class Library1
{   
	Scanner cin= new Scanner(System.in);
	int id,fine,bid;
	String name,athr,isdate,gnre,rtdate;

              void add()throws ClassNotFoundException, SQLException
	{
	 Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/Library","root","334");
	        System.out.print("Entr Book Id:");
	        id=cin.nextInt();
	        System.out.print("Entr Name of Book:");
            name=cin.next();
            System.out.print("Entr Name of Author:");
            athr=cin.next();
            System.out.print("Entr Genre of the Book:");
            gnre=cin.next();
	        PreparedStatement ps= con.prepareStatement("insert into Book1 values(?,?,?,?,?)");      
	        ps.setInt(1,id);
	        ps.setString(2,name);
	        ps.setString(3,athr);
	        ps.setString(4,gnre);
	        ps.setString(5,"Avail");
	        int res= ps.executeUpdate();
	        if(res>0)
	        	System.out.print("Book Added");
	        else
	        	System.out.print("Something Wrong");
	}
	
              void view()throws ClassNotFoundException, SQLException
	{
	System.out.print("\nREMEMBER the BOOK ID for issue and check STATUS for its availability !!!");
	Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/Library","root","334");
		 PreparedStatement ps= con.prepareStatement("select * from Book1");
		 ResultSet rs=ps.executeQuery();
		 while(rs.next())
		 System.out.println("\nBook Id:"+rs.getString(1)+"\n"+"Book Name:"+rs.getString(2)+"\nBook Author:"+rs.getString(3)+"\nBook Genre:"+rs.getString(4)+"\nBook Status:"+rs.getString(5));
		 
	}
	
        void iss()throws ClassNotFoundException, SQLException
        {
        Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/Library","root","334");
        System.out.print("Entr Card Id:");
        id=cin.nextInt();
        System.out.print("Entr Name:");
        name=cin.next();
        System.out.print("Entr fine:");
        fine=cin.nextInt();
        System.out.print("Entr Book Id:");
        bid=cin.nextInt();
        
        PreparedStatement pst=con.prepareStatement("select * from Book1 where Id = ?");
        pst.setInt(1,bid);
        ResultSet rs=pst.executeQuery();
      
        while(rs.next())
        System.out.println("\nBook Id:"+rs.getString(1)+"\n"+"Book Name:"+rs.getString(2)+"\nBook Author:"+rs.getString(3)+"\nBook Genre:"+rs.getString(4)+"\nBook Status:"+rs.getString(5));
		 
        System.out.print("Entr Issue Date:");
        isdate=cin.next();
        System.out.print("Entr Return Date:");
        rtdate=cin.next();
        PreparedStatement pst1=con.prepareStatement("update Book1 set Status = ? where Id = ? ");
        pst1.setString(1,"Issued");
        pst1.setInt(2,bid);
        int res=pst1.executeUpdate();
        
        PreparedStatement ps= con.prepareStatement("insert into issue values(?,?,?,?,?,?)"); 
        ps.setInt(1,id);
        ps.setString(2,name);
        ps.setInt(3,bid);
        ps.setString(4,isdate);
        ps.setString(5,rtdate);
        ps.setInt(6,fine); 
        int res1= ps.executeUpdate();
        
        if(res>0 & res1>0)
        	System.out.print("Book Issued");
        else
        	System.out.print("Something Wrong");
	}
	
        void his()throws ClassNotFoundException, SQLException
        {
       Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/Library","root","334");
       PreparedStatement pst=con.prepareStatement("select * from issue");
       ResultSet rs=pst.executeQuery();
       while(rs.next())
       System.out.println("\nIssuer CardId:"+rs.getString(1)+"\nIssuer Name:"+rs.getString(2)+"\nIssued Book Id:"+rs.getString(3)+"\nDate of Issue:"+rs.getString(4)+"\nReturn Date:"+rs.getString(5));
       }
}
