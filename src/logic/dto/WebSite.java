package logic.dto;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSite {

	private URI url;

	public WebSite(URI url) {
		this.url = url;
	}

	public WebSite(String strUrl) {
		try {
			this.url = new URI(strUrl);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public URI getUrl() {
		return url;
	}

	public void setUrl(URI url) {
		this.url = url;
	}
}
