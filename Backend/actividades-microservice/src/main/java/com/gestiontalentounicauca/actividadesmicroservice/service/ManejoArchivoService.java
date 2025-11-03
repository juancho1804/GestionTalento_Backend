package com.gestiontalentounicauca.actividadesmicroservice.service;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class ManejoArchivoService {
    private static final Path UPLOAD_DIR = Paths.get("uploads");

    public String saveFile(MultipartFile file) throws IOException {
        // Crear carpeta si no existe
        if (!Files.exists(UPLOAD_DIR)) {
            Files.createDirectories(UPLOAD_DIR);
        }

        // Nombre único
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = UPLOAD_DIR.resolve(fileName);

        // Detectar tipo MIME
        String contentType = file.getContentType();

        // Si es imagen, redimensionar antes de guardar
        if (contentType != null && contentType.startsWith("image/")) {
            Thumbnails.of(file.getInputStream())
                    .size(600, 600)
                    .toFile(filePath.toFile());
        } else {
            // Guardar directamente (PDF, DOCX, etc.)
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        // Devuelvo la URL accesible (ajusta según tu servidor)
        return "/uploads/" + fileName;
    }

    public void deleteFile(String fileUrl) {
        if (fileUrl != null && !fileUrl.isEmpty()) {
            try {
                String fileName = Paths.get(fileUrl).getFileName().toString();
                Path filePath = UPLOAD_DIR.resolve(fileName);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Error al eliminar el archivo: " + fileUrl, e);
            }
        }
    }

}
