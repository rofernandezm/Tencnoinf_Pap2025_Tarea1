package logic.dto;

import java.net.URI;

public class WebSite {

	private URI url;

	public WebSite(URI url) {
		this.url = url;
	}

	public WebSite(String strUrl) {
		this(URI.create(strUrl));
	}

	public URI getUrl() {
		return url;
	}

	public void setUrl(URI url) {
		this.url = url;
	}
}
