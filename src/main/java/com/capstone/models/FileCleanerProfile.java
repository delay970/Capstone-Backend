package com.capstone.models;

import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FileCleanerProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id;
	
	@Column(unique = true)
	public String name;
	
	public boolean removeHeader;
	public String[] headers;
	
	public boolean removeFooter;
	public String[] footers;
	
	public boolean removeSubstrings;
	public String[] openSequence;
	public String[] closeSequence;
	
	public boolean replaceString;
	public String[] oldString;
	public String[] newString;
	
	public boolean removeNumbers;
	
	public boolean removeCapitalization;
	
	public boolean removePunctuation;
	
	public boolean removeRomanNum;
	
	public boolean removeExtraWhitespace;

	public FileCleanerProfile(int id, String name, boolean removeHeader, String[] headers, boolean removeFooter, String[] footers,
			boolean removeSubstrings, String[] openSequence, String[] closeSequence, boolean replaceString,
			String[] oldString, String[] newString, boolean removeNumbers, boolean removeCapitalization,
			boolean removePunctuation, boolean removeRomanNum, boolean removeExtraWhitespace) {
		super();
		this.id = id;
		this.name = name;
		this.removeHeader = removeHeader;
		this.headers = headers;
		this.removeFooter = removeFooter;
		this.footers = footers;
		this.removeSubstrings = removeSubstrings;
		this.openSequence = openSequence;
		this.closeSequence = closeSequence;
		this.replaceString = replaceString;
		this.oldString = oldString;
		this.newString = newString;
		this.removeNumbers = removeNumbers;
		this.removeCapitalization = removeCapitalization;
		this.removePunctuation = removePunctuation;
		this.removeRomanNum = removeRomanNum;
		this.removeExtraWhitespace = removeExtraWhitespace;
	}

	public FileCleanerProfile() {
		super();
		
	}

	public boolean isRemoveHeader() {
		return removeHeader;
	}

	public void setRemoveHeader(boolean removeHeader) {
		this.removeHeader = removeHeader;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	public boolean isRemoveFooter() {
		return removeFooter;
	}

	public void setRemoveFooter(boolean removeFooter) {
		this.removeFooter = removeFooter;
	}

	public String[] getFooters() {
		return footers;
	}

	public void setFooters(String[] footers) {
		this.footers = footers;
	}

	public boolean isRemoveSubstrings() {
		return removeSubstrings;
	}

	public void setRemoveSubstrings(boolean removeSubstrings) {
		this.removeSubstrings = removeSubstrings;
	}

	public String[] getOpenSequence() {
		return openSequence;
	}

	public void setOpenSequence(String[] openSequence) {
		this.openSequence = openSequence;
	}

	public String[] getCloseSequence() {
		return closeSequence;
	}

	public void setCloseSequence(String[] closeSequence) {
		this.closeSequence = closeSequence;
	}

	public boolean isReplaceString() {
		return replaceString;
	}

	public void setReplaceString(boolean replaceString) {
		this.replaceString = replaceString;
	}

	public String[] getOldString() {
		return oldString;
	}

	public void setOldString(String[] oldString) {
		this.oldString = oldString;
	}

	public String[] getNewString() {
		return newString;
	}

	public void setNewString(String[] newString) {
		this.newString = newString;
	}

	public boolean isRemoveNumbers() {
		return removeNumbers;
	}

	public void setRemoveNumbers(boolean removeNumbers) {
		this.removeNumbers = removeNumbers;
	}

	public boolean isRemoveCapitalization() {
		return removeCapitalization;
	}

	public void setRemoveCapitalization(boolean removeCapitalization) {
		this.removeCapitalization = removeCapitalization;
	}

	public boolean isRemovePunctuation() {
		return removePunctuation;
	}

	public void setRemovePunctuation(boolean removePunctuation) {
		this.removePunctuation = removePunctuation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "FileCleanerProfile [id=" + id + ", name=" + name + ", removeHeader=" + removeHeader + ", headers="
				+ Arrays.toString(headers) + ", removeFooter=" + removeFooter + ", footers=" + Arrays.toString(footers)
				+ ", removeSubstrings=" + removeSubstrings + ", openSequence=" + Arrays.toString(openSequence)
				+ ", closeSequence=" + Arrays.toString(closeSequence) + ", replaceString=" + replaceString
				+ ", oldString=" + Arrays.toString(oldString) + ", newString=" + Arrays.toString(newString)
				+ ", removeNumbers=" + removeNumbers + ", removeCapitalization=" + removeCapitalization
				+ ", removePunctuation=" + removePunctuation + ", removeRomanNum=" + removeRomanNum
				+ ", removeExtraWhitespace=" + removeExtraWhitespace + "]";
	}

	public boolean isRemoveRomanNum() {
		return removeRomanNum;
	}

	public void setRemoveRomanNum(boolean removeRomanNum) {
		this.removeRomanNum = removeRomanNum;
	}

	public boolean isRemoveExtraWhitespace() {
		return removeExtraWhitespace;
	}

	public void setRemoveExtraWhitespace(boolean removeExtraWhitespace) {
		this.removeExtraWhitespace = removeExtraWhitespace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(closeSequence);
		result = prime * result + Arrays.hashCode(footers);
		result = prime * result + Arrays.hashCode(headers);
		result = prime * result + Arrays.hashCode(newString);
		result = prime * result + Arrays.hashCode(oldString);
		result = prime * result + Arrays.hashCode(openSequence);
		result = prime * result + Objects.hash(removeCapitalization, removeExtraWhitespace, removeFooter, removeHeader,
				removeNumbers, removePunctuation, removeRomanNum, removeSubstrings, replaceString);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileCleanerProfile other = (FileCleanerProfile) obj;
		return Arrays.equals(closeSequence, other.closeSequence) && Arrays.equals(footers, other.footers)
				&& Arrays.equals(headers, other.headers) && Arrays.equals(newString, other.newString)
				&& Arrays.equals(oldString, other.oldString) && Arrays.equals(openSequence, other.openSequence)
				&& removeCapitalization == other.removeCapitalization
				&& removeExtraWhitespace == other.removeExtraWhitespace && removeFooter == other.removeFooter
				&& removeHeader == other.removeHeader && removeNumbers == other.removeNumbers
				&& removePunctuation == other.removePunctuation && removeRomanNum == other.removeRomanNum
				&& removeSubstrings == other.removeSubstrings && replaceString == other.replaceString;
	}
	
	
}
