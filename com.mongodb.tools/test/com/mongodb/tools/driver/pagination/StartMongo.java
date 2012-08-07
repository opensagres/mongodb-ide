package com.mongodb.tools.driver.pagination;

import java.io.File;
import java.io.IOException;

public class StartMongo {

	public static void main(String[] args) {
		
		File f = new File("D:\\MongoDB\\mongodb-win32-i386-2.0.7-rc0\\bin\\mongo.exe");
		try {
			//Process p = Runtime.getRuntime().exec("cmd.exe /c start " + f.toString());
			
			
			String[] s ={"cmd.exe", "/c",  "start", f.toString()};
			Process p = new ProcessBuilder(s).start();
			
			Thread.sleep(1000);
			p.destroy();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
