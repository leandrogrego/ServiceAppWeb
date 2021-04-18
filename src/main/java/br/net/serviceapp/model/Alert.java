package br.net.serviceapp.model;

public class Alert {
	public String type;
	public String text;
	public Alert(String type, String text) {
		this.type = type;
		this.text = text;
	}
}
