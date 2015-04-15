package org.demo.crow.entity;

public class Eclipse {
	private String name;
	private String version;
	public Eclipse() {
		super();
	}
	public Eclipse(String name, String version) {
		super();
		this.name = name;
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
