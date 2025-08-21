package logica;

import java.net.URI;


public class WebSite {
	
	private URI url;

	public WebSite(URI url) {
		this.url = url;
	}

	public URI getUrl() {
		return url;
	}

	public void setUrl(URI url) {
		this.url = url;
	}
}
