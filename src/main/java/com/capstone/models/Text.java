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

	public Text() {
		super();
	}
	
	public Text(String name, String context) {
		super();
		this.name = name;
		this.context = context;
	}
	
	public Text(int id, String name, String context) {
		super();
		this.id = id;
		this.name = name;
		this.context = context;
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
