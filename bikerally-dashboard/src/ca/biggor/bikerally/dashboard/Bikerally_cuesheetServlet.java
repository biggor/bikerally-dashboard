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
		String phone = req.getParameter("phone");
		if (phone == null) {
			phone = "647-993-HELP (4357)";
		}

		if (routeId != null) {
			Document doc = Jsoup.connect("http://ridewithgps.com/routes/" + routeId + "/cue_sheet").get();
			doc.setBaseUri("http://ridewithgps.com");

			doc.select("meta[name=keywords]").remove();
			doc.select("meta[name=description]").remove();
			doc.select("link[href=/favicon.ico]").remove();

			Elements imports = doc.select("link[href]");
			for (Element link : imports) {
				String absHref = link.attr("abs:href");
				link.attr("href", absHref);
			}

			doc.select("td:eq(1)").remove();

			doc.select("thead tr td:eq(0)").first().text("Map");
			doc.select("thead tr td:eq(1)").first().text("Km");
			doc.select("thead tr td:eq(2)").first().text("Go");
			doc.select("thead tr td:eq(3)").first().text("Notes");

			Elements trs = doc.select("tbody tr");
			int i = 1;
			for (Element tr : trs) {
				String distance = tr.getElementsByTag("td").get(3).text();
				
				String direction = tr.getElementsByTag("td").get(1).text();
				String dir = "";
				if (direction.equals("Right")) {
					dir = "R";
				}
				if (direction.equals("Left")) {
					dir = "L";
				}
				direction = dir;
				
				String note = tr.getElementsByTag("td").get(2).text().replace("Turn left onto ", "").replace("Turn left", "turn left").replace("Turn right onto ", "").replace("Turn right", "turn right").replace("Continue onto", "becomes").replace("Continue straight", "continue straight").replace("Take the", "take the").replace("Merge", "merge").replace("Slight", "slight");

				tr.getElementsByTag("td").get(0).text("" + i);
				tr.getElementsByTag("td").get(1).text(distance);
				tr.getElementsByTag("td").get(2).text(direction);
				tr.getElementsByTag("td").get(3).text(note);
				i++;
			}

			doc.select("p.print_footer").first().html("<a class='print_link' href='javascript:window.print()'>Print</a><span style='font-weight:bold;color:#f00;'>Emergency phone number: " + phone);

			resp.getWriter().println(doc);

		}
	}
}
