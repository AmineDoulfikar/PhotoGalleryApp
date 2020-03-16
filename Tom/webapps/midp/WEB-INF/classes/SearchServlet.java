import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class SearchServlet extends HttpServlet {
  private int mCount = -1;
  private int sign = 1;
  
 
  public static ArrayList<String> photoGallery = null;
  
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
                "<form action=\"/midp/search\" method=\"POST\">\n" +
				
                "Caption" +
				"<br />\n" +
				"<input type=\"text\" name=\"caption\" placeholder=\"Caption\">\n"   +
                "<br />\n" +
				"<br />\n" +
				
                "Time" +
				"<br />\n" +
				"<input type=\"text\" name=\"start_time\" placeholder=\"Start Time\" value=\"202003091705\" /> <input type=\"text\" name=\"end_time\" placeholder=\"End Time\" value=\"202003091705\" />\n"   +
				"<br />\n" +
				"Format of time, year/date/24-hr" + 
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
				
		
								



  }
// Method to handle POST method request.
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();
    response.setContentType("text/html");
	  
	if(request.getParameter("start_time") != null){
		File file = new File("C:/Tom/webapps/midp/upload");
		photoGallery = new ArrayList<String>();
		File[] fList = file.listFiles();
		if (fList != null) {
			for (File f : fList) {
				String filePathData[] = f.getPath().toString().split("_");
				double fromLatitudeNumber;
				double toLatitudeNumber;
				double fileLatitudeNumber;

				double fromLongitudeNumber;
				double toLongitudeNumber;
				double fileLongitudeNumber;

				try{
					fromLatitudeNumber = Float.parseFloat(request.getParameter("start_lat"));
				} catch (Exception ex) {
					fromLatitudeNumber = -180;
				}

				try{
					toLatitudeNumber = Float.parseFloat(request.getParameter("end_lat"));
				} catch (Exception ex) {
					toLatitudeNumber = 180;
				}

				try{
					fileLatitudeNumber = Float.parseFloat(filePathData[3]);
				} catch (Exception ex) {
					fileLatitudeNumber = 0;
				}

				try{
					fromLongitudeNumber = Float.parseFloat(request.getParameter("start_lon"));
				} catch (Exception ex) {
					fromLongitudeNumber = -180;
				}

				try{
					toLongitudeNumber = Float.parseFloat(request.getParameter("end_lon"));
				} catch (Exception ex) {
					toLongitudeNumber = 180;
				}

				try{
					fileLongitudeNumber = Float.parseFloat(filePathData[4]);
				} catch (Exception ex) {
					fileLongitudeNumber = 0;
				}


				try{
					if (((request.getParameter("start_time") == null && request.getParameter("end_time") == null) || (Long.parseLong(filePathData[1]) >= Long.parseLong(request.getParameter("start_time")) && Long.parseLong(filePathData[1]) <= Long.parseLong(request.getParameter("end_time"))))
							&& (request.getParameter("caption") == "" || f.getPath().contains(request.getParameter("caption")))
							&& ((request.getParameter("start_lat") == null && request.getParameter("end_lat") == null) || (fileLatitudeNumber >= fromLatitudeNumber && fileLatitudeNumber <= toLatitudeNumber))
							&& ((request.getParameter("start_lon") == null && request.getParameter("end_lon") == null) || (fileLongitudeNumber >= fromLongitudeNumber && fileLongitudeNumber <= toLongitudeNumber)))
						photoGallery.add(f.getName());
				} catch (Exception ex) {

				}
				
			}

		}
	}

	String action = request.getParameter("action");

	if ("Left".equals(action)) {
		sign = -1;
	} else if ("Right".equals(action)) {
		sign = 1;
	}
	mCount = mCount + (1*sign);




	String title = "Current Search";
	
	if (mCount > photoGallery.size() - 1)
		mCount = photoGallery.size() - 1;
	if(mCount < 0)
		mCount = 0;
	  


    String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
    
    if(photoGallery.size() != 0) {
	    out.println("<html>\n" +
	                "<head><title>" + title + "</title></head>\n" +
		  
	                "<body bgcolor=\"#f0f0f0\">\n" + 
					"<h1 align=\"center\">" + title + "</h1>\n" +
					"<h2 align=\"center\">" + (mCount + 1)  + "/" + (photoGallery.size()) + " Photos" + "</h2>\n" +
					"<div align=\"center\" >\n" +
	                "<form action=\"/midp/search\" method=\"POST\">\n" +
								
					"<input type=\"submit\" name=\"action\" value=\"Left\"  />\n" + 
					"<input type=\"button\" value=\"Search Again\" onclick=\"location.href='http://localhost:8082/midp/search';\" />\n" +
					"<input type=\"submit\" name=\"action\" value=\"Right\" />\n" +
					
					"<br />\n" +
					"<br />\n" +
					
					"<input type=\"button\" value=\"Upload\" onclick=\"location.href='http://localhost:8082/midp';\" />\n" +
					
					"<br />\n" +
					"<br />\n" +
					
					
					"<img id=\"myImg\" src=\"upload/" + photoGallery.get(mCount) + "\" >" +
					
	    
	                "</div>\n</form>\n" +
					"<h3 align=\"center\">" + photoGallery.get(mCount) + "</h3>\n" +
					"</body>\n" +
					"</html\n");
    	}else {
    		 out.println("<html>\n" +
 	                "<head><title>" + title + "</title></head>\n" +
 		  
 	                "<body bgcolor=\"#f0f0f0\">\n" + 
 					"<h1 align=\"center\">" + title + "</h1>\n" +
 					"<div align=\"center\" >\n" +
 	                "<form action=\"/midp/search\" method=\"POST\">\n" +
 								

 					"<input type=\"button\" value=\"Search Again\" onclick=\"location.href='http://localhost:8082/midp/search';\" />\n" +
 					
 					
 					"<br />\n" +
 					"<br />\n" +
 					
 					"<input type=\"button\" value=\"Upload\" onclick=\"location.href='http://localhost:8082/midp';\" />\n" +
 					
 					"<br />\n" +
 					"<br />\n" +
 					
 					
					"<h1 align=\"center\">NO PHOTOS!!! TRY AGAIN!!!</h1>\n" +
 					
 	    
 	                "</div>\n</form>\n" +
 					"</body>\n" +
 					"</html\n");
    	}
  }

}
