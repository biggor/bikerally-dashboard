package ca.biggor.bikerally.dashboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Bikerally_jsdataServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*		resp.getWriter().println("Morris.Donut({");
		resp.getWriter().println("element: 'donut-example',");
		resp.getWriter().println("resize: 'true',");
		resp.getWriter().println("data: [");
		resp.getWriter().println("{label: '', value: 1500000},");
		resp.getWriter().println("{label: '', value: 7666},");
		resp.getWriter().println("]");
		resp.getWriter().println("}).select(1);");
		resp.getWriter().println("");
*/
		resp.getWriter().println("Morris.Area({");
		resp.getWriter().println("element: 'area-chart',");
		resp.getWriter().println("data: [");
		resp.getWriter().println("{ y: '2006', a: 100, b: 90 },");
		resp.getWriter().println("{ y: '2007', a: 75,  b: 65 },");
		resp.getWriter().println("{ y: '2008', a: 50,  b: 40 },");
		resp.getWriter().println("{ y: '2009', a: 75,  b: 65 },");
		resp.getWriter().println("{ y: '2010', a: 50,  b: 40 },");
		resp.getWriter().println("{ y: '2011', a: 75,  b: 65 },");
		resp.getWriter().println("{ y: '2012', a: 100, b: 90 }");
		resp.getWriter().println("],");
		resp.getWriter().println("xkey: 'y',");
		resp.getWriter().println("ykeys: ['a', 'b'],");
		resp.getWriter().println("labels: ['Series A', 'Series B']");
		resp.getWriter().println("});");
		resp.getWriter().println("");

		resp.getWriter().println("document.getElementById('totalRised').innerHTML='$8000';");
		resp.getWriter().println("document.getElementById('ridersRised').innerHTML='$7000';");
		resp.getWriter().println("document.getElementById('crewRised').innerHTML='$1000';");
		resp.getWriter().println("document.getElementById('ridersRegistered').innerHTML='197';");
		resp.getWriter().println("document.getElementById('crewRegistered').innerHTML='17';");
		resp.getWriter().println("");
		
		
	
	}
}
