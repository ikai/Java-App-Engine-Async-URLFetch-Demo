package com.ikai.urlfetchdemo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

@SuppressWarnings("serial")
public class GetTwitterFeedServlet extends HttpServlet {
	
	protected static String IKAI_TWITTER_RSS = "http://twitter.com/statuses/user_timeline/14437022.rss";
		
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		boolean isSyncRequest = true;
		
		if(req.getParameter("async") != null) {
			isSyncRequest = false;
		}
		
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<h1>Twitter feed fetch demo</h1>");
		
		long startTime = System.currentTimeMillis();
		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();			
		URL url = new URL(IKAI_TWITTER_RSS);
		
		if(isSyncRequest) {
			out.println("<h2>Synchronous fetch</h2>");			
			for(int i = 0; i < 20; i++) {
				HTTPResponse response = fetcher.fetch(url);
				printResponse(out, response);
			}
		} else {
			out.println("<h2>Asynchronous fetch</h2>");			
			ArrayList<Future<HTTPResponse>> asyncResponses = new ArrayList<Future<HTTPResponse>>();
			for(int i = 0; i < 20; i++) {
				Future<HTTPResponse> responseFuture = fetcher.fetchAsync(url);
				asyncResponses.add(responseFuture);
			}
			
			for(Future<HTTPResponse> future : asyncResponses){
				HTTPResponse response;
				try {
					response = future.get();
					printResponse(out, response);					
				} catch (InterruptedException e) {
					// Guess you would do something here
				} catch (ExecutionException e) {
					// Guess you would do something here
				}
			}
			
		}
						
		long totalProcessingTime = System.currentTimeMillis() - startTime;
		out.println("<p>Total processing time: " + totalProcessingTime + "ms</p>");		
	}

	private void printResponse(PrintWriter out, HTTPResponse response) {
		out.println("<p>");
		out.println("Response: " + new String(response.getContent()));
		out.println("</p>");		
	}
	

	
		
}
