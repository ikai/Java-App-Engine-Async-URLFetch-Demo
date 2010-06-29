package com.ikai.urlfetchdemo;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;

public class FetchHelper {

	protected static final String signGuestBookUrl = "http://your-guestbook.appspot.com/sign";
	
	public static HTTPRequest makeGuestbookPostRequest(String name, String content){
		HTTPRequest request = null;
		URL url;
		try {
			url = new URL(signGuestBookUrl);
			request = new HTTPRequest(url, HTTPMethod.POST);
			String body = "name=" + name + "&content=" + content;
			request.setPayload(body.getBytes());
			
		} catch (MalformedURLException e) {
			// Do nothing
		}
		return request;
	}
}
