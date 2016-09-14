package com.blueSky.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class File {

	public File() throws UnknownHostException, IOException{
		Socket socket = new Socket("127.0.0.1", 8899);
		OutputStream outputStream = socket.getOutputStream(); // 
		FileInputStream fileInputStream = new FileInputStream("Users/zhangdongdong/Desktop/123.png");
		byte [] buf = new byte[1024];
		int length;
		while ((length = fileInputStream.read(buf))!=-1) {//循环读取数据
			outputStream.write(buf,0,length);
		}
		socket.shutdownInput();//关闭客户端的输出流
		InputStream inputStream = socket.getInputStream();
		
		byte [] bufMeg = new byte[1024];
		int num = inputStream.read(bufMeg);//接收服务端的信息
		String msString  =new String(bufMeg, 0, num);
		System.out.println(msString);
		fileInputStream.close();//关闭输入流对象
		socket.close();//关闭socket对象
	}
	
}
