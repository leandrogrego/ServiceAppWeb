package br.net.serviceapp.job;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import br.net.serviceapp.model.Message;

import org.json.JSONException;
import org.json.JSONObject;

public class Notifiication {
	
	private String link = "https://www.serviceapp.net.br/#newmessages/";
	private String icon = "https://www.serviceapp.net.br/images/logo.png";
	private String requestUrl = "https://api.webpushr.com/v1/notification/send/sid";
	private String webpushrKey = "00000000000000000000000000000000";
	private String webpushrAuthToken = "00000";
	private boolean success = false;
	
	public Notifiication(Message message) {		
		JSONObject params = new JSONObject();
		try {
			params.put("title", "Nova mensagem de "+message.getFrom().getName());
			params.put("message", message.getText());
			params.put("target_url", link+message.getFrom().getId());
			params.put("sid", message.getTo().getPushId());		
			params.put("icon", icon);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			System.out.println(params.toString());
			System.out.println("+++++++++++++++");
			System.out.println(request(params.toString()));
			success=true;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String request( String data ) throws URISyntaxException, IOException, InterruptedException {
		URI uri = new URI(requestUrl);
		HttpRequest request = HttpRequest.newBuilder(uri)
					.header("webpushrKey", webpushrKey)
					.header("webpushrAuthToken", webpushrAuthToken)
					.header("Content-Type", "application/json")
					.POST(BodyPublishers.ofString(data))
					.build();
		HttpResponse<?> response = HttpClient.newHttpClient()
		          .send(request, BodyHandlers.ofString());
		
		return response.headers()+"\n####################\n"+response.body();
	}
	
	public boolean success() {
		return success;
	}
	
}
