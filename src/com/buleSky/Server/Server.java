package com.buleSky.Server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.management.RuntimeErrorException;

public class Server {
	
	public Server() throws IOException{
         //
		ServerSocket serverSocket = new ServerSocket(8899);
		while (true) {
			// 调用serverSocket 的 accept 方法接收客户端的请求,得到Socket对象
			Socket socket = serverSocket.accept();
			// 每当和客户端建立socket连接后,单独开一个线程去处理和客户端的交互
			new Thread(new ServerThred(socket)).start();
		}
	
	}
	
	class ServerThred implements Runnable{

		private Socket socket;
		
		public  ServerThred(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String ip = socket.getInetAddress().getHostName(); //获取客户端的IP地址
			int count  = 1;
			try {
		///Users/zhangdongdong/Desktop
				InputStream inputStream = socket.getInputStream(); 
				File parentFile = new File("Users/zhangdongdong/Desktop/upload");//创建上传图片目录的file对象
				if (!parentFile.exists()) { // 如果该目录不存在就创建
					parentFile.mkdir();  
				}
				// 把客户端的IP地址作为上传文件的文件名
				File file = new File(parentFile,ip +"("+count+").png");
				while (file.exists()) {
					//如果文件名存在的话,则count++;
					file = new File(parentFile,ip +"("+(count++)+").png");
				}
				
				// 创建 FileOutputStream 对象
				FileOutputStream  fileOutputStream = new FileOutputStream(file);
				byte [] buf = new byte[1024];
				int length = 0;
				while ((length = inputStream.read(buf))!= -1) {
					fileOutputStream.write(buf, 0, length);
				}
				// 获取服务端的输出流
				OutputStream outputStream = socket.getOutputStream();
				outputStream.write("上传成功".getBytes());
				fileOutputStream.close();
				socket.close();
			} catch (Exception e) {
				// TODO: handle exception
				throw new RuntimeException(e);
				
			}
			
			
		}
		
		
		
	}
	

}
