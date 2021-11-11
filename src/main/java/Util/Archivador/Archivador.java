package Util.Archivador;

import Domain.Entities.Operaciones.DocumentosComerciales.DocumentoComercial;
import Domain.Repositories.daos.Entities.DocumentoComercialRepository;
import Util.Configuration.Configuration;
import org.apache.commons.io.FilenameUtils;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Archivador {

    public DocumentoComercial obtenerDocumentoDeRequest(Request request, int idOperacion, String nombreCampo, String sufijo) {
        try {
            DocumentoComercial documento = new DocumentoComercial();

            MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/tmp");
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
            final Part uploadedFile = request.raw().getPart(nombreCampo);
            if (uploadedFile.getSize() == 0) {
                return null;
            }
            String mimeType = uploadedFile.getContentType();
            String nombreArchivo = sufijo + "_" + uploadedFile.getSubmittedFileName();
            final Path path = Paths.get(Configuration.RUTA_ARCHIVOS + idOperacion + "/" + nombreArchivo);

            if(!mimeType.equals("image/png") && !mimeType.equals("image/jpeg") && !mimeType.equals("application/pdf")) {
                throw new IOException("Tipo de archivo invalido");
            }

            File file = new File(Configuration.RUTA_ARCHIVOS + idOperacion + "/" + nombreArchivo);
            file.mkdirs();

            final InputStream in = uploadedFile.getInputStream();
            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);


            documento.setNombreArchivo(nombreArchivo);
            return documento;

        } catch (Exception e) {
            return null;
        }

    }

    public HttpServletResponse downloadImage(Request request, Response response, String nombreArchivo) {
        Path path = Paths.get(Configuration.RUTA_ARCHIVOS + nombreArchivo);
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        HttpServletResponse raw = response.raw();
        response.header("Content-Disposition", "attachment; filename=" + nombreArchivo.split("/")[1]);
        response.type("application/force-download");
        try {
            raw.getOutputStream().write(data);
            raw.getOutputStream().flush();
            raw.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return raw;

    }

    public byte[] getImage(Request request, Response response, String nombreArchivo) {
        Path path = Paths.get(Configuration.RUTA_ARCHIVOS + nombreArchivo);
        byte[] data = null;

        try {
            data = Files.readAllBytes(path);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return data;
    }

    public String getExtensionFile(String nombreArchivo) {
        switch (FilenameUtils.getExtension(nombreArchivo).toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            default:
                return "";
        }
    }

    public void eliminarArchivo(String nombreArchivo) {
        File file = new File(Configuration.RUTA_ARCHIVOS + nombreArchivo);
        file.delete();
    }

}
