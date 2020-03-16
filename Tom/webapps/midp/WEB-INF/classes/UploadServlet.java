import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
 

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

public class UploadServlet extends HttpServlet {
   
   private boolean isMultipart;
   private String filePath;
   private int maxFileSize = 5000 * 1024;
   private int maxMemSize = 5000 * 1024;
   private File file ;
   private int photoindex = 1;
   String strArray;

   public void init( ){
      // Get the file location where it would be stored.
      filePath = "C:\\Tom\\webapps\\midp\\upload\\";
   }
   
   public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, java.io.IOException {
   
      // Check that we have a file upload request
      isMultipart = ServletFileUpload.isMultipartContent(request);
      response.setContentType("text/html");
      java.io.PrintWriter out = response.getWriter( );
   
      if( !isMultipart ) {
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet upload</title>");  
         out.println("</head>");
         out.println("<body>");
         out.println("<p>No file uploaded</p>"); 
         out.println("</body>");
         out.println("</html>");
         return;
      }
  
      DiskFileItemFactory factory = new DiskFileItemFactory();
   
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
   
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File("C:\\tomcat\\PICS\\"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
   
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );

      try { 
         // Parse the request to get file items.
         List fileItems = upload.parseRequest(request);
	
         // Process the uploaded file items
         Iterator i = fileItems.iterator();

         out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet upload</title>");  
         out.println("</head>");
         out.println("<body>");
   
         while ( i.hasNext () ) {
            FileItem fi = (FileItem)i.next();
            if ( !fi.isFormField () ) {
               // Get the uploaded file parameters
               String fieldName = fi.getFieldName();
               String fileName = fi.getName();
               String contentType = fi.getContentType();
               boolean isInMemory = fi.isInMemory();
               long sizeInBytes = fi.getSize();
            
               // Write the file
               if( fileName.lastIndexOf("\\") >= 0 ) {
                  file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
               } else {
                  file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
               }
               fi.write( file ) ;
               out.println("Uploaded Filename: " + fileName + "<br>");
               out.println("Successfully Uploaded");
              // response.sendRedirect("http://localhost:8082/midp/search");
               
				//String testString = "Real-How-To";
			    //strArray = java.util.Arrays.toString(fileName.split("_"));
				String[] arrOfStr = fileName.split("_", 6);

				String photoindex_string = Integer.toString(photoindex);
				
				try {
					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
					} catch (Exception ex) { }
				
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "1");
					//errMsg += "Con";
					Statement stmt = con.createStatement();
					//rrMsg += "stmt";
					stmt.executeUpdate("INSERT INTO photos (photo_id, photo_name, photo_location, photo_time ) VALUES ('"+ photoindex_string + "','" + arrOfStr[2]+ "','" + arrOfStr[3] + arrOfStr[4] + "','" + arrOfStr[1]+"')");
					photoindex++;			  
					
					/*String sql = "select * from photos";
					ResultSet rs = stmt.executeQuery(sql);
					while(rs.next())
						System.out.println(rs.getInt(1) + "  " + rs.getInt(2) + "  " +rs.getInt(3) + "  " + rs.getInt(4) + "  "  );
					*/
					stmt.close();
					con.close();
					//errMsg += "End";

            }
         }
         out.println("</body>");
         out.println("</html>");
         } catch(Exception ex) {
            System.out.println(ex);
         }
}

      public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, java.io.IOException {

         throw new ServletException("GET method used with " +
            getClass( ).getName( )+": POST method required.");
      }
      
      
 
}