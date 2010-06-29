package com.ikai.urlfetchdemo;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

@SuppressWarnings("serial")
public class JavaSyncUrlFetchDemoServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		long startTime = System.currentTimeMillis();
		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		fetcher.fetchAsync(FetchHelper.makeGuestbookPostRequest("sync", "At" + startTime));		
		long totalProcessingTime = System.currentTimeMillis() - startTime;

		resp.setContentType("text/html");
		resp.getWriter().println("<h1>Synchronous fetch demo</h1>");
		resp.getWriter().println("<p>Total processing time: " + totalProcessingTime + "ms</p>");
		
	}
	
}
