package spark.utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// El front recibe este Json luego de cada CREAR o UPDATE o donde se desee
public class JsonResponse {

    /**
     * {
     *      "redirect": bool,
     *      "url_redirect": string,
     *      "error_form": bool,
     *      "mensaje_error": [string]
     * }
     */

    // Hay que realizar un redirect luego de subir el formulario
    private Boolean redirect;

    // URL a la cual se debe realizar el redirect
    private String urlToRedirect;

    // Hay un error en el formulario
    private Boolean errorInForm;

    // Lista de mensajes de error
    private List<String> mensajeError = new ArrayList<>();

    private String idRetorno;

    public JsonResponse() {}

    public JsonResponse(Boolean redirect, String urlToRedirect, Boolean errorInForm, String mensajeError) {
        this.redirect = redirect;
        this.urlToRedirect = urlToRedirect;
        this.errorInForm = errorInForm;
        if (mensajeError != null) {
            this.addMensajeError(mensajeError);
        }
    }

    public static String buildJson(Boolean redirect, String urlToRedirect, Boolean errorInForm, String mensajeError) {
        JsonResponse jsonResponse = new JsonResponse(redirect, urlToRedirect, errorInForm, mensajeError);

        Gson gson = new Gson();

        return gson.toJson(jsonResponse);
    }

    public static String buildJson(Boolean redirect, String urlToRedirect, Boolean errorInForm, String mensajeError, String idRetorno) {
        JsonResponse jsonResponse = new JsonResponse(redirect, urlToRedirect, errorInForm, mensajeError);
        jsonResponse.setIdRetorno(idRetorno);

        Gson gson = new Gson();

        return gson.toJson(jsonResponse);
    }


    public static String buildJson(Boolean redirect, String urlToRedirect) {
        return buildJson(redirect, urlToRedirect, false, null);
    }

    public String buildJson() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

    public Boolean getRedirect() {
        return redirect;
    }

    public void setRedirect(Boolean redirect) {
        this.redirect = redirect;
    }

    public String getUrlToRedirect() {
        return urlToRedirect;
    }

    public void setUrlToRedirect(String urlToRedirect) {
        this.urlToRedirect = urlToRedirect;
    }

    public Boolean getErrorInForm() {
        return errorInForm;
    }

    public void setErrorInForm(Boolean errorInForm) {
        this.errorInForm = errorInForm;
    }

    public List<String> getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(List<String> mensajeError) {
        this.mensajeError = mensajeError;
    }

    public void addMensajeError(String ...mensajeError) {
        Collections.addAll(this.mensajeError, mensajeError);
    }

    public String getIdRetorno() {
        return idRetorno;
    }

    public void setIdRetorno(String idRetorno) {
        this.idRetorno = idRetorno;
    }
}
