package com.airtel.merchant.request.filter;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
 
public class MultipleReadHttpServletRequest extends HttpServletRequestWrapper {
 
  private String requestBody;
  
  public String getRequestBody() {
	return requestBody;
}

public MultipleReadHttpServletRequest(HttpServletRequest request) throws IOException {
    super(request);
    requestBody = "";
    BufferedReader bufferedReader = request.getReader();
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      requestBody += line;
    }
  }
 
  public ServletInputStream getInputStream() throws IOException {
    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
        requestBody.getBytes());
    	return new ServletInputStream() 
     {
    
    	public int read() throws IOException {
        return byteArrayInputStream.read();
      }
 
      public boolean isReady() {
        return true;
      }
 
      public void setReadListener(ReadListener readListener) {
      }
 
      public boolean isFinished() {
        return true;
      }
    };
  }
 
  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(this.getInputStream()));
  }
}