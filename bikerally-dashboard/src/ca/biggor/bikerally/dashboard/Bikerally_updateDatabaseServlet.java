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

		String eventId = req.getParameter("eventId");
		if (eventId == null) {
			queue.add(TaskOptions.Builder.withUrl("/updatedatabaseworker").param("eventId", Bikerally_util.DEFAULT_RIDER_EVENT_ID));
			queue.add(TaskOptions.Builder.withUrl("/updatedatabaseworker").param("eventId", Bikerally_util.DEFAULT_RIDER_ONE_DAY_EVENT_ID));
			queue.add(TaskOptions.Builder.withUrl("/updatedatabaseworker").param("eventId", Bikerally_util.DEFAULT_CREW_EVENT_ID));
		} else {
			queue.add(TaskOptions.Builder.withUrl("/updatedatabaseworker").param("eventId", eventId));
		}

	}
}