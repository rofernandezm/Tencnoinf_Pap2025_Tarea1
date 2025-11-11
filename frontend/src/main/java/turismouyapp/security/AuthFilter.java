package turismouyapp.security;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@WebFilter("/*")
public class AuthFilter implements Filter {

	private static final Set<String> PUBLIC_PATHS = Set.of("/login", "/iniciarSesionRegistrarse.jsp", "/");

	// Extensiones de archivos estáticos que no requieren sesión
	private static final String[] STATIC_EXT = { ".css", ".js", ".png", ".jpg", ".jpeg", ".gif", ".svg", ".ico",
			".woff", ".woff2", ".ttf", ".eot", ".map" };

	private static final String USER_LOGGED_ATTR = "logged_user"; // atributo de sesión
	private static final String USER_GUEST_ATTR = "guest_mode"; // atributo de sesión
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String ctx = request.getContextPath(); // ej: /turismouy
		String path = request.getRequestURI().substring(ctx.length()); // ej: /outings/list

		// 1) Dejar pasar si es público o estático
		if (isPublic(path) || isStatic(path)) {
			chain.doFilter(req, res);
			return;
		}

		// 2) Validar sesión para todo lo demás (toda la app)
		HttpSession session = request.getSession(false); // NO crear sesión
		boolean logged = (session != null && (session.getAttribute(USER_LOGGED_ATTR) != null || (session.getAttribute(USER_GUEST_ATTR) != null && (boolean)session.getAttribute(USER_GUEST_ATTR) == true)));

		if (!logged) {
			// URL a la que se redirige el usuario cuando no tiene sesión
			String loginUrl = request.getContextPath() + "/login";

			// Redirección con parámetro 'next' para volver después del login
			String next = URLEncoder.encode(
					request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""),
					StandardCharsets.UTF_8);

			response.sendRedirect(loginUrl + "?next=" + next);
			return;
		}

		// 3) Reforzar no-cache en contenido protegido
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		chain.doFilter(req, res);
	}

	private boolean isPublic(String path) {
		// Coincidencias exactas o por prefijo (e.g. /public/**)
		if (PUBLIC_PATHS.contains(path))
			return true;
		for (String p : PUBLIC_PATHS) {
			if (p.endsWith("/") && path.startsWith(p))
				return true;
		}
		// No filtrar recursos bajo /resources, /assets, /static
		return path.startsWith("/resources/") || path.startsWith("/assets/") || path.startsWith("/static/");
	}

	private boolean isStatic(String path) {
		for (String ext : STATIC_EXT)
			if (path.endsWith(ext))
				return true;
		return false;
	}
}
