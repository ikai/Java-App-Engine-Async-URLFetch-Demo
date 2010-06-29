package com.ikai.urlfetchdemo;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.urlfetch.HTTPHeader;
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
	
	protected void makeRequest() {
		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		try {
			URL url = new URL("http://someurl.com");			
			HTTPResponse response = fetcher.fetch(url);
			
			byte[] content = response.getContent();
			URL finalUrl = response.getFinalUrl();
			int responseCode = response.getResponseCode();
			List<HTTPHeader> headers = response.getHeaders();
			
			for(HTTPHeader header : headers) {
				String headerName = header.getName();
				String headerValue = header.getValue();
			}
			
		} catch (IOException e) {
			// new URL throws MalformedUrlException, which is impossible for us here 
		}
		
	}
	
	protected void makeAsyncRequest() {
		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		try {
			URL url = new URL("http://someurl.com");			
			Future<HTTPResponse> future = fetcher.fetchAsync(url);
			
			// Other stuff can happen here!
			
			HTTPResponse response = future.get();
			byte[] content = response.getContent();
			URL finalUrl = response.getFinalUrl();
			int responseCode = response.getResponseCode();
			List<HTTPHeader> headers = response.getHeaders();
			
			for(HTTPHeader header : headers) {
				String headerName = header.getName();
				String headerValue = header.getValue();
			}
			
		} catch (IOException e) {
			// new URL throws MalformedUrlException, which is impossible for us here 
		} catch (InterruptedException e) {
			// Exception from using java.concurrent.Future
		} catch (ExecutionException e) {
			// Exception from using java.concurrent.Future
			e.printStackTrace();
		}
		
	}	
	
}
