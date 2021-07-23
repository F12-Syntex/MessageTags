package com.comments.main;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class Replacer {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Directory: ");
		
		String path = sc.next();
		
		File file = new File(path);
		
		while(!file.isDirectory()) {
			System.err.println(path + " is not a valid directory!");
			
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.print("Directory: ");		
			path = sc.nextLine();
			file = new File(path);
		}
		
		String replacer = "";
		
		StringBuilder text = new StringBuilder();
		
		boolean ignored = true;
		
		while(!replacer.equalsIgnoreCase("end")) {
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(ignored) {
				replacer = sc.nextLine();
				ignored = false;
			}else {
				System.out.print("add line (type end to stop): ");
				replacer = sc.nextLine();
			
				if(replacer.equalsIgnoreCase("end")) {
					break;	
				}
				text.append(replacer + "\n");
				System.out.println();	
			}
			
		}
		
		long time = System.currentTimeMillis();
		
		List<File> files = (List<File>) FileUtils.listFiles(file, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		
		for(File i : files) {

			if(!i.canWrite()) {
				System.err.println("Could not modify " + i.getName() + " [" + i.getAbsolutePath() + "]");
				continue;
			}
			
			if(!validFile(i)) {
				System.err.println(i.getName() + " [" + i.getAbsolutePath() + "] is not a text file!");
				continue;
			}
			
			try {

				String content = FileUtils.readFileToString(i, StandardCharsets.UTF_8.name());
				
				if(!content.startsWith("#")) {
					System.err.println("Could not modify " + i.getName() + " [" + i.getAbsolutePath() + "] ( DOES NOT START WITH A COMMENT )");
					continue;
				}
			
				System.out.println("Updating file " + i.getName() + " [" + i.getAbsolutePath() + "]");
				
				List<String> data = new ArrayList<String>();
				for(String o : text.toString().split("\n")) {
					data.add("#" + o);
				}
				data.add(content);
				
		        FileUtils.writeLines(i, StandardCharsets.UTF_8.name(), data);
				
				
			}catch (Exception e) {
				System.err.println("Could not modify " + i.getName() + " [" + i.getAbsolutePath() + "]");
				e.printStackTrace();
			}
			
			
		}
		
		long duration = System.currentTimeMillis() - time;
		
		System.out.println("\nAll tasks completed in " + duration + "(ms)");
		
		
		sc.close();
		
	}
	
	public void checkFilesInFolder() {
		
	}
	
	public static boolean validFile(File file) {
		
		try {
			String name = file.getName().split("\\.")[1];
			
			boolean valid = name.equalsIgnoreCase("txt") || 
							name.equalsIgnoreCase("xml") ||
							name.equalsIgnoreCase("yaml") ||
							name.equalsIgnoreCase("yml") ||
							name.equalsIgnoreCase("php") ||
							name.equalsIgnoreCase("dat");
			
			return valid;	
		}catch (Exception e) {
			return false;
		}
		
	}
	
	

}
