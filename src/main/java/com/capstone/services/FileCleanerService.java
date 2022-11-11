package com.capstone.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.models.FileCleanerProfile;
import com.capstone.repos.FileCleanerProfileRepo;

@Service
public class FileCleanerService {

	@Autowired
	FileHandlerService fileHandlerService;

	@Autowired
	FileCleanerProfileRepo fileCleanerProfileRepo;

	public Optional<FileCleanerProfile> getDefaultProfile() {
		Optional<FileCleanerProfile> profile = fileCleanerProfileRepo.findByName("default");
		if(profile.isPresent()) {
			return profile;
		}
		
		return createDefaultProfile();
		
	}
	
	public List<FileCleanerProfile> getAllProfiles() {
		List<FileCleanerProfile> profiles = fileCleanerProfileRepo.findByOrderById();
		return profiles;
	}
	
	public FileCleanerProfile updateProfile(FileCleanerProfile profile){
		Optional<FileCleanerProfile> p = fileCleanerProfileRepo.findByName(profile.name);
		if(!p.isPresent()) {
			return fileCleanerProfileRepo.save(profile);
		}
		
		FileCleanerProfile temp = p.get();
		profile.id = temp.id;
		return fileCleanerProfileRepo.save(profile);
	}
	
	public FileCleanerProfile createProfile(FileCleanerProfile profile){
		return fileCleanerProfileRepo.save(profile);
	}
	
	public Optional<FileCleanerProfile> createDefaultProfile() {
		FileCleanerProfile profile = new FileCleanerProfile(
				0,
				"default",
				true, new String[] { "---" }, //header
				false, null, //footer
				true, new String[] { "*[", "/", "(", "[" }, new String[] { "]*", "/", ")", "]" }, //delete
				true, new String[] { "-" }, new String[] { " " }, //replace
				true, //numbers
				true, //Cap
				true, //punc
				true, //RN
				true); // white space
	
		fileCleanerProfileRepo.save(profile);
		
		return fileCleanerProfileRepo.findByName("default");
	}

	public String cleanContext(String context, FileCleanerProfile profile) {

		if(profile.isRemoveHeader()) {
			context = removeHeader(context, profile.getHeaders());
		}
		
		if(profile.isRemoveFooter()) {
			context = removeFooter(context, profile.getFooters());
		}

		if(profile.isRemoveSubstrings()) {
			context = removeSubstrings(context, profile.getOpenSequence(), profile.getCloseSequence());
		}
		
		if(profile.isReplaceString()) {
			context = replaceString(context, profile.getOldString(), profile.getNewString());
		}

		if(profile.isRemoveNumbers()) {
			context = removeNumbers(context);
		}
		
		if(profile.isRemoveCapitalization()) {
			context = removeCapitalization(context);
		}

		if(profile.isRemovePunctuation()) {
			context = removePunctuation(context);
		}
		
		if(profile.isRemoveRomanNum()) {
			context = removeRomanNum(context);
		}

		if(profile.isRemoveExtraWhitespace()) {
			context = removeExtraWhitespace(context);
		}

		// saveCleanedFile(context, "temp.txt");
		// saveCleanedFile(context, path);
		return context;
	}

	private String removeHeader(String context, String[] headers) {

		for(String header : headers) {
			int index = context.lastIndexOf(header); // remove everything above each header

			if (index != -1) {
				context = context.substring(index + header.length());
			}
		}

		return context;
	}
	
	private String removeFooter(String context, String[] footers) {

		for(String footer : footers) {
			int index = context.indexOf(footer); // remove everything after each footer

			if (index != -1) {
				context = context.substring(0, index);
			}
		}

		return context;
	}

	private String removeSubstrings(String context, String[] openSequence, String[] closeSequence) {

		StringBuilder contextBuilder = new StringBuilder(context);
		for (int i = 0; i < openSequence.length; i++) {
			String open = openSequence[i];
			String close = closeSequence[i];

			int index = contextBuilder.indexOf(open);
			while (index != -1) {
				int index2 = contextBuilder.indexOf(close, index + 1);
				// index+1 ensures that if open == close it will look
				// for the next char and not have the same index
				if(index2 == -1) {
					break; //makes sure that they're is a close character after the open character
				}
				contextBuilder.delete(index, index2 + close.length());
				index = contextBuilder.indexOf(open);
			}

		}

		return contextBuilder.toString();
	}
	
	private String replaceString(String context, String[] oldStrings, String[] newStrings) {
		for (int i =0; i< oldStrings.length; i++) {
			context = context.replaceAll(oldStrings[i], newStrings[i]);
		}
		return context;
	}

	private String removePunctuation(String context) {
		return context.replaceAll("\\p{IsPunctuation}", "");
	}

	private String removeNumbers(String context) {

		return context.replaceAll("\\d", "");

	}

	private String removeRomanNum(String context) {
		return context.replaceAll("\\s+([ivxl])+\\s", "");

	}

	private String removeCapitalization(String context) {
		return context.toLowerCase();

	}

	private String removeExtraWhitespace(String context) {
		context = context.replaceAll("\\s", " ");
		return context.trim().replaceAll(" +", " ");

	}

	private void saveCleanedFile(String context, String path) {

		String[] pathParts = path.replaceAll("\\\\", "/").split("/");
		String writePath = "Cleaned/" + pathParts[pathParts.length - 1];

		fileHandlerService.writeFile(context, writePath);
	}

}
