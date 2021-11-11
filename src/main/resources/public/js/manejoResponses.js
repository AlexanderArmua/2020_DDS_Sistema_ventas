/**
 * respuesta: {
 *     errorInForm: bool,
 *     mensajeError: [string],
 *     redirect: bool,
 *     urlToRedirect: string
 * }
 */

const generic_container = "contenedor_errores_formulario";

manejo_error_json = (respuesta, toglearCargando, mostrarError, mensajeExito = "") => {
    if (!!respuesta.errorInForm) {
        mostrarError(respuesta.mensajeError);
        return;
    }
    if (!!respuesta.redirect) {
        if (mensajeExito.length > 0) {
            alert(mensajeExito);
        }
        window.location.href = respuesta.urlToRedirect;
    }
}


manejar_response = async (response, toglearCargando, toglearAlerta, mensajeExito = "") => {
    if (response.ok) {
        toglearCargando();
        const respuesta = await response.json();
        manejo_error_json(respuesta, toglearCargando, toglearAlerta, mensajeExito);
    } else {
        toglearCargando();
        toglearAlerta("Ah ocurrido un error inesperado contactese con Mauro");
    }
}

function makeulli(mensajesError) {
    let sub_ul = $('<ul/>', {"class": "list-group", "style": "padding-left: 20px"});

    $.each(mensajesError, (_, value) => {
        const sub_li = $('<li/>').html(value);
        sub_ul.append(sub_li);
    });

    return sub_ul;
}

generic_manejar_error = (id = generic_container) => {
    return (mensajesError) => {
        const contenedorError = $(`#${id}`);

        if (!!mensajesError && mensajesError.length > 0) {
            const ulList = makeulli(mensajesError);

            contenedorError.append(`<h4 class='alert-heading'>Error!</h4>`);
            contenedorError.append(ulList);
            contenedorError.show();

            $('html, body').animate({
                scrollTop: contenedorError.offset().top
            }, 500);

            return;
        }

        contenedorError.empty();
        contenedorError.hide();
    }
}

function manejar_submit(e, form, mostrar_error = generic_manejar_error(generic_container), toglearCargando = () => {
}, mensajeExito = "") {
    e.preventDefault();

    mostrar_error();

    $.ajax({
        type: form[0].method,
        url: form[0].action,
        data: form.serialize(),
        success: (data) => manejo_error_json(JSON.parse(data), toglearCargando, mostrar_error, mensajeExito)
    });
}

function iniciar_manejador_errores_por_form(formulario, manejador_submit = manejar_submit) {
    formulario.submit(function (e) {
        manejador_submit(e, $(this));
    });

    // Creamos el campo para mostrar el mensaje de error
    if ($("#generic_container").length === 0) {
        formulario.prepend(`<div id='${generic_container}' class='alert alert-danger' role='alert' style='display: none;'></div>`);
    }
}

function iniciar_manejador_errores(idFormulario, manejador_submit = manejar_submit) {
    const formulario = $(`#${idFormulario}`);

    iniciar_manejador_errores_por_form(formulario, manejador_submit);
}

function uploadDocumentacion(ruta, id, idInputFile) {
    const input = document.getElementById(idInputFile);
    const body = document.getElementById("body");
    const form = document.createElement("form");

    body.append(form);

    form.method = 'POST';
    form.action = `${ruta}/${id}`;
    form.enctype = 'multipart/form-data';

    if (input) {
        form.append(input);
    }

    iniciar_manejador_errores_por_form($(form));

    form.submit();
}
