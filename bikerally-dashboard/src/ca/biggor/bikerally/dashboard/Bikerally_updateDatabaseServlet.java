package ca.biggor.bikerally.dashboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class Bikerally_updateDatabaseServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("BikeRally - updateDatabase");
		resp.getWriter().println();
		
		Queue queue = QueueFactory.getDefaultQueue();
		
		String refreshUpdate = req.getParameter("refreshUpdate");
		if (refreshUpdate == null) {
			refreshUpdate = "false";
		} else if (!refreshUpdate.equals("true")) {
			refreshUpdate = "false";
		}

		String eventId = req.getParameter("eventId");
		if (eventId == null) {
			queue.add(TaskOptions.Builder.withUrl("/updatedatabaseworker").param("eventId", Bikerally_util.DEFAULT_RIDER_EVENT_ID).param("refreshUpdate", refreshUpdate));
			queue.add(TaskOptions.Builder.withUrl("/updatedatabaseworker").param("eventId", Bikerally_util.DEFAULT_RIDER_ONE_DAY_EVENT_ID).param("refreshUpdate", refreshUpdate));
			queue.add(TaskOptions.Builder.withUrl("/updatedatabaseworker").param("eventId", Bikerally_util.DEFAULT_CREW_EVENT_ID).param("refreshUpdate", refreshUpdate));
			queue.add(TaskOptions.Builder.withUrl("/updatedatabaseworker").param("eventId", Bikerally_util.DEFAULT_LOVEBOWL_EVENT_ID).param("refreshUpdate", refreshUpdate));
		} else {
			queue.add(TaskOptions.Builder.withUrl("/updatedatabaseworker").param("eventId", eventId).param("refreshUpdate", refreshUpdate));
		}

	}
}