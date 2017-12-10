package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileOperator {
	private static FileOperator instance = new FileOperator();
	private BufferedReader reader;
	private BufferedWriter writer;
	private File file;
	private FileOperator() {
		try {
			file = new File("appinfo");
			if(!file.exists()) {
				file.createNewFile();
			}
			reader = new BufferedReader(new FileReader(file));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static FileOperator getInstance() {
		return instance;
	}
	
	public void write(String data) throws IOException {
		if(writer == null) {
			writer = new BufferedWriter(new FileWriter(file));
		}
		writer.write(data);
		writer.flush();
	}
	
	public String read() {
		String s = "";
		try {
			s = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	public boolean ready() {
		try {
			return reader.ready();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
}
