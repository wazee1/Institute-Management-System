/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Library;

import Controller.Salary.paysheetReportServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.DBConnection;

/**
 *
 * @author jahrin
 */
@WebServlet(name = "LibraryReport", urlPatterns = {"/LibraryReport"})
public class LibraryReport extends HttpServlet {

   

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            Connection con;
            Statement statement = null;

            String year = request.getParameter("year");
            String month = request.getParameter("month");

            con = DBConnection.createConnection();
            // statement = con.createStatement();
            String query = " select l.ISBN_num \n" +
"from libraryresources l\n" +
"where l.Availability = 0\n" +
"group by l.ISBN_num";
            String reportPath = "C:\\Users\\jahrin\\Documents\\NetBeansProjects\\IMS Progress V 6\\src\\java\\reports\\library.jrxml";
            Map<String, Object> parameters = new HashMap<String, Object>();
            

////                //loading the design from the file path
            JasperDesign jasperDesign = JRXmlLoader.load(reportPath);
////                
////                //Designing the query for the report
            JRDesignQuery newQuery = new JRDesignQuery();
////                
////                //setting the query text
            newQuery.setText(query);
//////                
//////                //seeting the query for the report design
            jasperDesign.setQuery(newQuery);
//////                
//////                //compile the jasper report for the design
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//////                
//////                //creating a JasperPrint object to fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
               JasperViewer.viewReport(jasperPrint);
        //    JasperExportManager.exportReportToPdfFile(jasperPrint, "C://Users//jahrin//Documents//NetBeansProjects//Report11//web//report//report2.pdf");
//         
         //  request.getRequestDispatcher("/Paysheet.jsp").forward(request, response);
           response.sendRedirect("libraryBooksView.jsp");
        } catch (JRException ex) {
            Logger.getLogger(paysheetReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
