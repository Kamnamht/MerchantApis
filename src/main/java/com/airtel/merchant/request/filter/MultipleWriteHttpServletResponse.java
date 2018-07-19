package com.airtel.merchant.request.filter;
//package com.airtelbank.b2bloadcash.filter;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//
//import javax.servlet.ReadListener;
//import javax.servlet.ServletInputStream;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponseWrapper;
// 
//public class MultipleWriteHttpServletResponse extends HttpServletResponseWrapper {
// 
//  private String responseBody;
//  
//  public String getRequestBody() {
//	return responseBody;
//}
//
//public MultipleWriteHttpServletResponse(HttpServletResponse response) throws IOException {
//    super(response);
//    responseBody = "";
//    PrintWriter buffereedWriter = response.getWriter();
//    buffereedWriter.write(s);
//    while ((line = buffereedWriter) != null) {
//      responseBody += line;
//    }
//  }
//
//	@Override
//	public ServletOutputStream getOutputStream() throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
// 
//  public ServletInputStream getInputStream() throws IOException {
//    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
//        responseBody.getBytes());
//    	return new ServletInputStream() 
//     {
//    
//    	public int read() throws IOException {
//        return byteArrayInputStream.read();
//      }
// 
//      public boolean isReady() {
//        return true;
//      }
// 
//      public void setReadListener(ReadListener readListener) {
//      }
// 
//      public boolean isFinished() {
//        return true;
//      }
//    };
//  }
// 
//  @Override
//  public BufferedReader getReader() throws IOException {
//    return new BufferedReader(new InputStreamReader(this.getInputStream()));
//  }
//}