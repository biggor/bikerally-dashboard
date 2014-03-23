package ca.biggor.bikerally.dashboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@SuppressWarnings("serial")
public class Bikerally_cuesheetServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String routeId = req.getParameter("routeid");
		
		if (routeId != null) {
			Document doc = Jsoup.connect("http://ridewithgps.com/routes/" + routeId + "/cue_sheet").get();
			Elements imports = doc.select("link[href]");
	        for (Element link : imports) {
	        	link.attr("href", link.attr("abs:href"));
	        }
			
			doc.select("td:eq(1)").remove();
			doc.select("td:eq(1)").remove();
			doc.select("p.print_footer").remove();

			resp.getWriter().println(doc);
			
		}
	}
}
