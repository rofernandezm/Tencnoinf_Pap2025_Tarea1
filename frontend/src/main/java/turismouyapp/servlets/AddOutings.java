package turismouyapp.servlets;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.factory.FactoryUyTourism;
import turismouyapp.core.interfaces.ITouristOutingAndInscriptionController;

@WebServlet("/outings/add")
@MultipartConfig
public class AddOutings extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ITouristOutingAndInscriptionController itoaic;

    public AddOutings() {
        super();
        FactoryUyTourism factory = FactoryUyTourism.getInstance();
        this.itoaic = factory.getITouristOutingAndInscriptionController();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        // --- Parámetros del formulario ---
        String activityName = request.getParameter("activitySelect");
        String outingName = request.getParameter("outingName");
        String maxTouristsStr = request.getParameter("maxTourists");
        String outingPlace = request.getParameter("outingPlace");
        String outingDateStr = request.getParameter("outingDate");

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
             String rawPath_out = getServletContext().getInitParameter("uploadOutingFolder");
             String rawPath_def = getServletContext().getInitParameter("defaultPath_img");

             // Reemplaza la variable ${catalina.base} por su valor real
             String catalinaToken = "${catalina.base}";
             String catalinaBase = System.getProperty("catalina.base");
             String outPath = rawPath_out.replace(catalinaToken, catalinaBase);
             String defPath = rawPath_def.replace(catalinaToken, catalinaBase);

             File uploadDir = new File(outPath);
             if (!uploadDir.exists())
                 uploadDir.mkdirs();

             String imagePath = "default_outing.jpg";

             if (outingPhotoPart != null && outingPhotoPart.getSize() > 0) {

                 // Obtiene el nombre original (ej: "foto.png")
                 String originalName = Path.of(outingPhotoPart.getSubmittedFileName()).getFileName().toString();

                 // Extrae la extensión (todo después del último '.')
                 String extension = "";
                 int i = originalName.lastIndexOf('.');
                 if (i > 0) {
                     extension = originalName.substring(i); // incluye el punto, ej: ".png"
                 }

                 // Genera nombre único + extensión
                 String fileName = UUID.randomUUID().toString() + extension;

                 // Guardar el archivo en el servidor
                 outingPhotoPart.write(outPath + File.separator + fileName);

                 // Guardar la ruta relativa
                 imagePath = fileName;

             } else {

                 File out_img = new File(outPath + File.separator + imagePath);
                 if (!out_img.exists()) {
                     File default_img = new File(defPath + File.separator + imagePath);
                     try {
                         Files.copy(default_img.toPath(), out_img.toPath(), StandardCopyOption.REPLACE_EXISTING);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
             }

        int maxTourists = 0;
        try {
            maxTourists = Integer.parseInt(maxTouristsStr);
            if (maxTourists <= 0)
                errors.add("La cantidad máxima de turistas debe ser mayor a 0.");
        } catch (Exception e) {
            errors.add("Cantidad máxima de turistas inválida.");
        }

        LocalDateTime outingDate = null;
        try {
            outingDate = LocalDateTime.parse(outingDateStr);
        } catch (Exception e) {
            errors.add("Formato de fecha inválido.");
        }

        // Si hay errores, reenviamos al form con mensajes
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/outings").forward(request, response);
            return;
        }

        try {
            DtUser loggedUser = (DtUser) session.getAttribute("logged_user");
            if (loggedUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            DtTouristOuting newOuting = new DtTouristOuting(outingName, maxTourists, outingPlace, outingDate, LocalDate.now(), activityName, imagePath);
            itoaic.outingDataEntry(newOuting);

            response.sendRedirect(request.getContextPath() + "/outings?status=ok&q="
                    + URLEncoder.encode(activityName, StandardCharsets.UTF_8));

        } catch (Exception ex) {
            errors.add(ex.getMessage() != null ? ex.getMessage() : "No se pudo crear la salida turística.");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/outings").forward(request, response);
        }

    }


}
