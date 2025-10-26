package turismouyapp.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.UUID;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

public class ImageManager {

	private ImageManager() {}
	
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

	public static String getFileHash(Part part) {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[8192];
			int bytesRead;

			try (InputStream inputStream = part.getInputStream()) {
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					md.update(buffer, 0, bytesRead);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			byte[] digest = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
