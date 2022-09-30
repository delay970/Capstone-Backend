package com.capstone.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class FileHandlerService {
	
	String pathPrefix = "src//main//resources//files//";
	
	public String readFile(String path) {

		try (FileInputStream fis = new FileInputStream(path);
				InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(isr)) {

			StringBuilder context = new StringBuilder();
			String temp;
			while ((temp = reader.readLine()) != null) {
				context.append(temp + " ");
				context.append("\n"); // this could be causing problems
			}
			//remove last char from 
			String result = context.toString();
			return result.trim();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean writeFile(String context, String path) {
		
		path = pathPrefix + path;
		
		try (FileOutputStream fos = new FileOutputStream(path);
				OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
				BufferedWriter writer = new BufferedWriter(osw)) {

			writer.write(context);

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String[] getFilesInFolder(String folderPath) {
		String[] paths = null;
		try (Stream<Path> p = Files.walk(Paths.get(folderPath))) {
			paths = p.filter(Files::isRegularFile).map(Path::toString).toArray(String[]::new);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return paths;
	}
	
	public String[] getFilesInFolder() {
		String[] paths = null;
		try (Stream<Path> p = Files.walk(Paths.get(pathPrefix))) {
			paths = p.filter(Files::isRegularFile).map(Path::toString).toArray(String[]::new);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return paths;
	}
	
	public boolean clearFiles() {
		String[] paths = getFilesInFolder(pathPrefix);
		for(String path: paths) {
			deleteFile(path);
		}
		return true;
	}
	
	public boolean deleteFile(String path) {
		
		try {
            Files.deleteIfExists(
                Paths.get(path));
        }
        catch (NoSuchFileException e) {
            System.out.println(
                "No such file/directory exists");
        }
        catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        }
        catch (IOException e) {
            System.out.println("Invalid permissions.");
        }
		
		return true;
	}
}
