package com.capstone.models;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Text {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id;
	@Column(unique = true)
	public String name;
	@Lob
	public String context;
	
    @JsonIgnore
    @ManyToMany(mappedBy = "text")
    public Set<NGram> ngram;
    
    @ManyToOne
    public FileCleanerProfile profile;

	public Text() {
		super();
	}
	
	public Text(String name, String context, FileCleanerProfile profile) {
		super();
		this.name = name;
		this.context = context;
		this.profile = profile;
	}
	
	public Text(int id, String name, String context, FileCleanerProfile profile) {
		super();
		this.id = id;
		this.name = name;
		this.context = context;
		this.profile = profile;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<NGram> getNgram() {
		return ngram;
	}

	public void setNgram(Set<NGram> ngram) {
		this.ngram = ngram;
	}

	public FileCleanerProfile getProfile() {
		return profile;
	}

	public void setProfile(FileCleanerProfile profile) {
		this.profile = profile;
	}

	@Override
	public int hashCode() {
		return Objects.hash(context, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Text other = (Text) obj;
		return Objects.equals(context, other.context) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Text [name=" + name + ", context=" + context + "]";
	}

}
