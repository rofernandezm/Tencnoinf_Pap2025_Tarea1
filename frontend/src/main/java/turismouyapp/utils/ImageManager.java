package turismouyapp.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

public class ImageManager {

	public static String generateFileName(Part imagePart) {

		// Obtiene el nombre original (ej: "foto.png")
		String originalName = Path.of(imagePart.getSubmittedFileName()).getFileName().toString();

		// Extrae la extensión (todo después del último '.')
		String extension = "";
		int i = originalName.lastIndexOf('.');
		if (i > 0) {
			extension = originalName.substring(i); // incluye el punto, ej: ".png"
		}

		// Genera nombre único + extensión
		String fileName = UUID.randomUUID().toString() + extension;
		return fileName;
	}

	public static void persistFile(ServletContext context, Part imagePart, String imgName,
			UploadFolderType uploadFolderType) throws IOException {

		String paramName = ImageManager.resolveUploadFolderParameterName(uploadFolderType);
		String outPath = context.getInitParameter(paramName);

		File uploadDir = new File(outPath);
		if (!uploadDir.exists())
			uploadDir.mkdirs();

		if (imagePart == null || imagePart.getSize() < 1) {
			ImageManager.copyDefaultImageIfIsNeeded(context, outPath, uploadFolderType);
		} else {
			try {
				imagePart.write(outPath + File.separator + imgName);
			} catch (IOException ex) {
				ex.printStackTrace();
				throw ex; // Se captura excepcion en servlet y se debe hacer update a path en persistencia
							// (default).
			}
		}
	}

	private static void copyDefaultImageIfIsNeeded(ServletContext context, String outPath,
			UploadFolderType uploadFolderType) {

		String folderName = ImageManager.resolveUploadFolderParameterName(UploadFolderType.DEFAULT);
		String uploadPath = context.getInitParameter(folderName);
		String imageName = ImageManager.resolveDefaultImageName(uploadFolderType);
		File out_img = new File(outPath + File.separator + imageName);
		if (!out_img.exists()) {
			File default_img = new File(uploadPath + File.separator + imageName);
			try {
				Files.copy(default_img.toPath(), out_img.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String resolveUploadFolderParameterName(UploadFolderType uploadFolderType) {
		String paramName = null;

		switch (uploadFolderType) {
		case PROFILE:
			paramName = "uploadProfileFolder";
			break;
		case ACTIVITY:
			paramName = "uploadActivityFolder";
			break;
		case OUTING:
			paramName = "uploadOutingFolder";
			break;
		case DEFAULT:
			paramName = "defaultPath_img";
			break;
		default:
			throw new IllegalArgumentException("Undefined path for UploadFolderType parameter :: " + uploadFolderType);
		}
		return paramName;
	}

	public static String resolveDefaultImageName(UploadFolderType uploadFolderType) {
		String defaultImageName = null;

		switch (uploadFolderType) {
		case PROFILE:
			defaultImageName = "default_profile.jpg";
			break;
		case ACTIVITY:
			defaultImageName = "default_activity.jpg";
			break;
		case OUTING:
			defaultImageName = "default_outing.jpg";
			break;
		default:
			throw new IllegalArgumentException(
					"Undefined default image name for UploadFolderType parameter :: " + uploadFolderType);
		}
		return defaultImageName;
	}

	public enum UploadFolderType {
		PROFILE, ACTIVITY, OUTING, DEFAULT
	}
}
