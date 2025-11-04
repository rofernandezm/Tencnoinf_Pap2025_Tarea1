package turismouyapp.servlets;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import turismouyapp.webservices.OutingAndInscriptionService;
import turismouyapp.webservices.OutingAndInscriptionPortType;
import turismouyapp.webservices.DtTouristOuting;
import turismouyapp.webservices.DtUser;
import turismouyapp.webservices.RepeatedTouristOutingException;
import turismouyapp.utils.ImageManager;
import turismouyapp.utils.ImageManager.UploadFolderType;

@WebServlet("/outings/add")
@MultipartConfig
public class AddOutings extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final OutingAndInscriptionPortType outingAndInscriptionWebService;

	public AddOutings() {
		super();
		this.outingAndInscriptionWebService = new OutingAndInscriptionService().getOutingAndInscriptionPort();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);

		DtUser loggedUser = (DtUser) session.getAttribute("logged_user");
		if (loggedUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		// --- Parámetros del formulario ---
		String activityName = request.getParameter("activitySelect");
		String outingName = request.getParameter("outingName");
		String maxTouristsStr = request.getParameter("maxTourists");
		String outingPlace = request.getParameter("outingPlace");
		String outingDateStr = request.getParameter("outingDate");

		int maxTourists = 0;
		LocalDateTime outingDate = null;

		List<String> errors = new ArrayList<>();

		// --- Validaciones básicas ---
		if (activityName == null || activityName.isBlank())
			errors.add("Actividad requerida.");
		if (outingName == null || outingName.isBlank())
			errors.add("Nombre de la salida requerido.");
		if (outingPlace == null || outingPlace.isBlank())
			errors.add("Lugar de salida requerido.");
		if (outingDateStr == null || outingDateStr.isBlank())
			errors.add("Fecha y hora requeridas.");

		// Foto de perfil
		Part outingPhotoPart = request.getPart("outImage");

		String fileName = (outingPhotoPart != null && outingPhotoPart.getSize() > 0)
				? ImageManager.generateFileName(outingPhotoPart)
				: ImageManager.resolveDefaultImageName(UploadFolderType.OUTING);

		// Turistas en la inscripcion
		try {
			maxTourists = Integer.parseInt(maxTouristsStr);
			if (maxTourists <= 0)
				errors.add("La cantidad máxima de turistas debe ser mayor a 0.");
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			errors.add("Cantidad máxima de turistas inválida.");
		}

		// Fecha
		try {
			outingDate = LocalDateTime.parse(outingDateStr);
		} catch (DateTimeParseException ex) {
			ex.printStackTrace();
			errors.add("Formato de fecha inválido.");
		}

		// Si hay errores, reenviamos al form con mensajes
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/outings").forward(request, response);
			return;
		}

		DtTouristOuting newOuting = new DtTouristOuting();
		newOuting.setOutingName(outingName);
		newOuting.setMaxNumTourists(maxTourists);
		newOuting.setDeparturePoint(outingPlace);
		newOuting.setDepartureDate(outingDate.toString());
		newOuting.setDischargeDate(LocalDate.now().toString());
		newOuting.setActivityName(activityName);
		newOuting.setImageOutPath(fileName);

		try {

			outingAndInscriptionWebService.outingDataEntry(newOuting);

			// Persistir imagen
			try {
				ImageManager.persistFile(this.getServletContext(), outingPhotoPart, fileName, UploadFolderType.OUTING);
			} catch (IOException ex) {

				// Setea imagen default
				String defaultImage = ImageManager.resolveDefaultImageName(UploadFolderType.OUTING);
				outingAndInscriptionWebService.updateOutingImageName(outingName, defaultImage);
			}

			response.sendRedirect(request.getContextPath() + "/outings?status=ok&q="
					+ URLEncoder.encode(activityName, StandardCharsets.UTF_8));

		} catch (RepeatedTouristOutingException ex) {
			ex.printStackTrace();
			errors.add(ex.getMessage() != null ? ex.getMessage() : "No se pudo crear la salida turística.");
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/outings").forward(request, response);
		}

	}
}
