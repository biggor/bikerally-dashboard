package ca.biggor.bikerally.dashboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class Bikerally_jsdata2Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String riderEventId = req.getParameter("riderEventId");
		if (riderEventId == null) {
			riderEventId = Bikerally_util.DEFAULT_RIDER_EVENT_ID;
		}
		String crewEventId = req.getParameter("crewEventId");
		if (crewEventId == null) {
			riderEventId = Bikerally_util.DEFAULT_CREW_EVENT_ID;
		}

		String recacheTime = "";
		Integer ridersTotalCollected = 0;
		Integer crewTotalCollected = 0;
		Integer ridersRegistered = 0;
		Integer crewRegistered = 0;
		try {
			recacheTime = Bikerally_util.getRecacheTime();
			ridersTotalCollected = Bikerally_util.getEventTotalCollected(riderEventId);
			crewTotalCollected = Bikerally_util.getEventTotalCollected(crewEventId);
			ridersRegistered = Bikerally_util.getEventParticipantCount(riderEventId);
			crewRegistered = Bikerally_util.getEventParticipantCount(crewEventId);
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
		Integer totalRaised = ridersTotalCollected + crewTotalCollected;

		resp.getWriter().println("document.getElementById('recacheTime').innerHTML='" + recacheTime + "';");
		resp.getWriter().println("document.getElementById('totalRaised').innerHTML='$" + totalRaised + "';");
		resp.getWriter().println("document.getElementById('ridersRaised').innerHTML='$" + ridersTotalCollected + "';");
		resp.getWriter().println("document.getElementById('crewRaised').innerHTML='$" + crewTotalCollected + "';");
		resp.getWriter().println("document.getElementById('ridersRegistered').innerHTML='" + ridersRegistered + "';");
		resp.getWriter().println("document.getElementById('crewRegistered').innerHTML='" + crewRegistered + "';");
		resp.getWriter().println("");

		resp.getWriter().println("Morris.Line({");
		resp.getWriter().println("element: 'dollar-chart',");
		resp.getWriter().println("data: [");
		resp.getWriter().println("{ month: '2014-07', 2010:       0, 2011:       0, 2012:       0, 2013:       0, 2014:       0, 2015:       0},");
		resp.getWriter().println("{ month: '2014-08', 2010:       0, 2011:       0, 2012:       0, 2013:     250, 2014:       0, 2015:       0},");
		resp.getWriter().println("{ month: '2014-09', 2010:       0, 2011:    2091, 2012:    1800, 2013:    6226, 2014:    1941, 2015:    1961},");
		resp.getWriter().println("{ month: '2014-10', 2010:    1099, 2011:    4306, 2012:    4429, 2013:   11090, 2014:    3556, 2015:    4723},");
		resp.getWriter().println("{ month: '2014-11', 2010:    3179, 2011:    6162, 2012:    8716, 2013:   17794, 2014:    6571, 2015:    5799},");
		resp.getWriter().println("{ month: '2014-12', 2010:    7579, 2011:   11189, 2012:   15906, 2013:   31598, 2014:   14580, 2015:   22520},");
		resp.getWriter().println("{ month: '2015-01', 2010:   15107, 2011:   19243, 2012:   22417, 2013:   50726, 2014:   29584, 2015:   29172},");
		resp.getWriter().println("{ month: '2015-02', 2010:   35200, 2011:   35005, 2012:   50878, 2013:   79943, 2014:   37416, 2015:   29172},");
		resp.getWriter().println("{ month: '2015-03', 2010:   75848, 2011:   89974, 2012:   98512, 2013:  123316, 2014:   58831, 2015:   29172},");
		resp.getWriter().println("{ month: '2015-04', 2010:  159055, 2011:  179483, 2012:  171935, 2013:  209367, 2014:  108218, 2015:   29172},");
		resp.getWriter().println("{ month: '2015-05', 2010:  304325, 2011:  300963, 2012:  297193, 2013:  341515, 2014:  181373, 2015:   29172},");
		resp.getWriter().println("{ month: '2015-06', 2010:  492703, 2011:  507372, 2012:  503545, 2013:  584419, 2014:  341587, 2015:   29172},");
		resp.getWriter().println("{ month: '2015-07', 2010:  755127, 2011:  828151, 2012:  778121, 2013:  912499, 2014:  590137, 2015:   29172},");
		resp.getWriter().println("{ month: '2015-08', 2010: 1093560, 2011: 1145619, 2012: 1188893, 2013: 1344891, 2014:  939241, 2015:   29172}");
		resp.getWriter().println("],");
		resp.getWriter().println("xkey: 'month',");
		resp.getWriter().println("ykeys: ['2010', '2011', '2012', '2013', '2014', '2015'],");
		resp.getWriter().println("labels: ['2010', '2011', '2012', '2013', '2014', '2015'],");
		resp.getWriter().println("dateFormat: function (x) { var IndexToMonth = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ]; return IndexToMonth[new Date(x).getMonth()]; },");
		resp.getWriter().println("xLabelFormat: function (x) { var IndexToMonth = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ]; return IndexToMonth[x.getMonth()]; },");
		// resp.getWriter().println("lineColors: ['#0b62a4','#7a92a3','#4da74d','#afd8f8','#edc240','#cb4b4b','#9440ed'],");
		resp.getWriter().println("lineColors: ['#afd8f8','#0b62a4','#7a92a3','#872d91','#e21836','#4da74d','#9440ed'],");
		resp.getWriter().println("pointSize: 2,");
		resp.getWriter().println("hideHover: 'auto',");
		resp.getWriter().println("resize: true,");
		resp.getWriter().println("behaveLikeLine: true");
		resp.getWriter().println("});");
		resp.getWriter().println("");

	}


}
