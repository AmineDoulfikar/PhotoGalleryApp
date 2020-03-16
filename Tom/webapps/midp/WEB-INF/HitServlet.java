import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.io.*;

public class HitServlet extends HttpServlet {
  private int mCount = 0;
  private int sign = 1;
  
  File aDirectory = new File("C:/Tom/webapps/midp/upload");

  String[] filesInDir = aDirectory.list();
  String filesForDisplay;
  
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    // Set response content type

	  String title = "Gallery Search";
	  
      response.setContentType("text/html");

      PrintWriter out = response.getWriter();
      out.println("<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
	  
                "<body bgcolor=\"#f0f0f0\">\n" + 
				"<h1 align=\"center\">" + title + "</h1>\n" +
				"<div align=\"center\" >\n" +
                "<form action=\"/midp/hits\" method=\"POST\">\n" +
				
                "Caption" +
				"<br />\n" +
				"<input type=\"text\" name=\"caption\" placeholder=\"Caption\">\n"   +
                "<br />\n" +
				"<br />\n" +
				
                "Time" +
				"<br />\n" +
				"<input type=\"text\" name=\"start_time\" placeholder=\"Start Time\" /> <input type=\"text\" name=\"end_time\" placeholder=\"End Time\" />\n"   +
				"<br />\n" +
				"<br />\n" +
				
				"Location" +
				"<br />\n" +
				"<input type=\"text\" name=\"start_lat\" placeholder=\"Start Latitude\" /> <input type=\"text\" name=\"end_lat\" placeholder=\"End Latitude\" />\n"  +
				"<br />\n" +
                "<input type=\"text\" name=\"start_lon\" placeholder=\"Start Longitude\" /> <input type=\"text\" name=\"end_lon\" placeholder=\"End Longitude\" />\n" +
				"<br />\n" +
				"<br />\n" +			
				"<input type=\"submit\" value=\"Search\" />\n" + 
				
                "</div>\n</form>\n</body>\n</html\n");
				
				filesForDisplay = "jokes";


  }
// Method to handle POST method request.
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {

String errMsg = "Testing";
     // Set response content type
try {
try {
Class.forName("oracle.jdbc.OracleDriver");
} catch (Exception ex) { }
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "comp7855");
errMsg += "Con";
			Statement stmt = con.createStatement();
errMsg += "stmt";
			stmt.executeUpdate("INSERT INTO staff (name, address) VALUES ('"+ request.getParameter("first_name") + "','" + request.getParameter("last_name") +"')");
			stmt.close();
			con.close();
errMsg += "End";
		} 
		catch(SQLException ex) { 
				errMsg = errMsg + "\n--- SQLException caught ---\n"; 
				while (ex != null) { 
					errMsg += "Message: " + ex.getMessage (); 
					errMsg += "SQLState: " + ex.getSQLState (); 
					errMsg += "ErrorCode: " + ex.getErrorCode (); 
					ex = ex.getNextException(); 
					errMsg += "";
				} 
		} 
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");
	  


	String title = "Current Search";
	String img = filesInDir[mCount];
	mCount = mCount + (1*sign);
	if (mCount > filesInDir.length - 1)
		mCount = filesInDir.length - 1;
	if(mCount < 0)
		mCount = 0;
	  


    String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
    out.println("<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
	  
                "<body bgcolor=\"#f0f0f0\">\n" + 
				"<h1 align=\"center\">" + title + "</h1>\n" +
				"<h2 align=\"center\">" + (mCount + 1)  + "/" + (filesInDir.length) + " Photos" + "</h2>\n" +
				"<h3 align=\"center\">" + filesForDisplay + "</h3>\n" +
				"<div align=\"center\" >\n" +
                "<form action=\"/midp/hits\" method=\"POST\">\n" +
				
                "Caption" +
				"<br />\n" +
				"<b>Caption</b>"+ request.getParameter("caption") + "\n" +
                "<br />\n" +
				"<br />\n" +
				
                "Time" +
				"<br />\n" +
				"<b>Start</b>:" + request.getParameter("start_time") + "<b>End</b>:" + request.getParameter("end_time") + "\n" +
				"<br />\n" +
				"<br />\n" +
				
				"Location" +
				"<br />\n" +
				"<b>Start Latitude</b>:"+ request.getParameter("start_lat") + "<b>  End Latitude</b>:" + request.getParameter("end_lat") + "\n" +
				"<br />\n" +
                "<b>Start Longitude</b>:" + request.getParameter("start_lon") + "<b>  End Longitude</b>:" + request.getParameter("end_lon") + "\n" +
				"<br />\n" +
				"<br />\n" +			
				"<input type=\"submit\" name=\"action\" value=\"Left\" />\n" + 
				"<input type=\"submit\" name=\"action\" value=\"Right\" />\n" +
				
				"<br />\n" +
				"<br />\n" +
				
				"<img id=\"myImg\" src=\"upload/" + filesInDir[mCount] + "\" >" +
				
                "</div>\n</form>\n</body>\n" +
				"</html\n");
				
	String action = request.getParameter("action");

	if ("Left".equals(action)) {
		sign = -1;
	} else if ("Right".equals(action)) {
		sign = 1;
	}

  }

}
