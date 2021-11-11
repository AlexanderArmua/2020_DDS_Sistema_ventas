package spark.utils;

import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Usuario;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import server.ServerRoutes;

import java.io.IOException;

public enum SisHelper implements Helper<String>{

	navbar {
		@Override
		public CharSequence apply(String arg0, Options extraParams) throws IOException {
			Usuario usuario = null;
			if (extraParams.params.length > 0) {
				usuario = extraParams.param(0);
			}


			String stringRetornar = "<nav class=\"navbar navbar-expand-sm navbar-dark bg-dark\">\n" +
					"    <a class=\"navbar-brand\" href=\"" + ServerRoutes.HOME + "\">Sistema de Control</a>\n" +
					"    <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navBarGeneral\" aria-controls=\"navBarGeneral\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
					"        <span class=\"navbar-toggler-icon\"></span>\n" +
					"    </button>\n" +
					"    <div class=\"collapse navbar-collapse\" id=\"navBarGeneral\">\n" +
					"        <ul class=\"navbar-nav mr-auto\">\n";

//Permisos para menús y botón de validar
			if(usuario.tienePermiso(PermisoUsuario.VISUALIZAR_ENTIDAD_BASE)||usuario.tienePermiso(PermisoUsuario.VISUALIZAR_ENTIDAD_JURIDICA)){
				stringRetornar+= "<li class=\"nav-item\">\n" +
						"                <a class=\"nav-link\" href=\"" + ServerRoutes.ENTIDADES + "\">Entidades</a>\n" +
						"            </li>\n";
			}

			if(usuario.tienePermiso(PermisoUsuario.VISUALIZAR_USUARIOS)){
				stringRetornar+="<li class=\"nav-item\">\n" +
						"                <a class=\"nav-link\" href=\"" + ServerRoutes.USUARIOS + "\">Usuarios</a>\n" +
						"            </li>\n";
			}

			if(usuario.tienePermiso(PermisoUsuario.VISUALIZAR_OPERACION_EGRESO) && usuario.tienePermiso(PermisoUsuario.VISUALIZAR_OPERACION_INGRESO)){
				stringRetornar+="<li class=\"nav-item dropdown\">\n" +
						"        		<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
						"          			Operaciones\n" +
						"        		</a>\n" +
						"        		<div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">\n" +
						"          			<a class=\"dropdown-item\" href=\"" + ServerRoutes.OPERACIONES_EGRESOS + "\">Egreso</a>\n" +
						"          			<a class=\"dropdown-item\" href=\"" + ServerRoutes.OPERACIONES_INGRESOS + "\">Ingreso</a>\n" +
						"       		</div>\n" +
						"      		</li>";
			}
			else{
				if(usuario.tienePermiso(PermisoUsuario.VISUALIZAR_OPERACION_EGRESO)){
					stringRetornar+="<li class=\"nav-item dropdown\">\n" +
							"        		<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
							"          			Operaciones\n" +
							"        		</a>\n" +
							"        		<div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">\n" +
							"          			<a class=\"dropdown-item\" href=\"" + ServerRoutes.OPERACIONES_EGRESOS + "\">Egreso</a>\n" +
							"       		</div>\n" +
							"      		</li>";
				}
				else{
					if(usuario.tienePermiso(PermisoUsuario. VISUALIZAR_OPERACION_INGRESO))
					{
						stringRetornar+="<li class=\"nav-item dropdown\">\n" +
								"        		<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
								"          			Operaciones\n" +
								"        		</a>\n" +
								"        		<div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">\n" +
								"          			<a class=\"dropdown-item\" href=\"" + ServerRoutes.OPERACIONES_INGRESOS + "\">Ingresos</a>\n" +
								"       		</div>\n" +
								"      		</li>";
					}
				}
			}

			if(usuario.tienePermiso(PermisoUsuario.VISUALIZAR_PRESUPUESTO)){
				stringRetornar+= "<li class=\"nav-item\">\n" +
						"                <a class=\"nav-link\" href=\"" + ServerRoutes.PRESUPUESTOS + "\">Presupuestos</a>\n" +
						"            </li>\n";
			}

			if(usuario.tienePermiso(PermisoUsuario.VISUALIZAR_PROVEEDOR)){
				stringRetornar+= "<li class=\"nav-item\">\n" +
						"                <a class=\"nav-link\" href=\"" + ServerRoutes.PROVEEDORES + "\">Proveedores</a>\n" +
						"            </li>\n";
			}

			if(usuario.tienePermiso(PermisoUsuario.VISUALIZAR_CATYCRI)){
				stringRetornar+= "<li class=\"nav-item dropdown\">\n" +
						"        		<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
						"          			Categorías\n" +
						"        		</a>\n" +
						"        		<div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">\n" +
						"          			<a class=\"dropdown-item\" href=\"" + ServerRoutes.CATEGORIAS + "\">Categorías</a>\n" +
						"          			<a class=\"dropdown-item\" href=\"" + ServerRoutes.CRITERIOS + "\">Criterios</a>\n" +
						"       		</div>\n" +
						"      		</li>";
			}

			if(usuario.tienePermiso(PermisoUsuario.EJECUTAR_VINCULADOR)){
				stringRetornar+="<li class=\"nav-item\">\n" +
						"                <a class=\"nav-link\" href=\"" + ServerRoutes.VINCULAR + "\">Vinculador</a>\n" +
						"            </li>\n";
			}

			if(usuario.tienePermiso(PermisoUsuario.EJECUTAR_VALIDADOR_TRANSPARENCIA)){
			stringRetornar+= "<li class=\"nav-item\">\n" +
					"                <a class=\"nav-link\" href=\"" + ServerRoutes.MENSAJES + "\">Bandeja de mensajes</a>\n" +
					"            </li>\n" +
					"			</ul>\n" + "</div>\n";}

//Cierre sección de menús
			stringRetornar+= "</ul>\n" + "</div>\n";

			if(usuario.tienePermiso(PermisoUsuario.EJECUTAR_VALIDADOR_TRANSPARENCIA)){
				stringRetornar+="<form class=\"form-inline my-2 my-lg-0\" style=\"margin-right: 10px\">\n" +
						"      <a class=\"btn btn-outline-info my-2 my-sm-0\" href=\"" + ServerRoutes.VALIDAR + "\">Validar</a>\n" +
						"    </form>";
			}


//Botón Desconectar
			stringRetornar+="<form class=\"form-inline my-2 my-lg-0\">\n" +
					"      <a class=\"btn btn-outline-danger my-2 my-sm-0\" href=\"" + ServerRoutes.LOGOUT + "\">Desconectar</a>\n" +
					"    </form>" +
					"</nav>";

			return new Handlebars.SafeString(stringRetornar);

/*
			return new Handlebars.SafeString(
					"<nav class=\"navbar navbar-expand-sm navbar-dark bg-dark\">\n" +
					"    <a class=\"navbar-brand\" href=\"" + ServerRoutes.HOME + "\">Sistema de Control</a>\n" +
					"    <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navBarGeneral\" aria-controls=\"navBarGeneral\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
					"        <span class=\"navbar-toggler-icon\"></span>\n" +
					"    </button>\n" +
					"    <div class=\"collapse navbar-collapse\" id=\"navBarGeneral\">\n" +
					"        <ul class=\"navbar-nav mr-auto\">\n" +
					"            <li class=\"nav-item\">\n" +
					"                <a class=\"nav-link\" href=\"" + ServerRoutes.USUARIOS + "\">Usuarios</a>\n" +
					"            </li>\n" +
					"		<li class=\"nav-item dropdown\">\n" +
					"        		<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
					"          			Operaciones\n" +
					"        		</a>\n" +
					"        		<div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">\n" +
					"          			<a class=\"dropdown-item\" href=\"" + ServerRoutes.OPERACIONES_EGRESOS + "\">Egreso</a>\n" +
					"          			<a class=\"dropdown-item\" href=\"" + ServerRoutes.OPERACIONES_INGRESOS + "\">Ingreso</a>\n" +
					"       		</div>\n" +
					"      		</li>" +
					"			<li class=\"nav-item\">\n" +
					"                <a class=\"nav-link\" href=\"" + ServerRoutes.MENSAJES + "\">Bandeja</a>\n" +
					"            </li>\n"+
							"  <li class=\"nav-item dropdown\">\n" +
							"        <a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
							"          Presupuestos\n" +
							"        </a>\n" +
							"        <div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">\n" +
							"          <a class=\"dropdown-item\" href=\"" + ServerRoutes.PRESUPUESTOS + "\">Presupuestos</a>\n" +
							"          <a class=\"dropdown-item\" href=\"" + ServerRoutes.PROVEEDORES + "\">Proveedores</a>\n" +
							"        </div>\n" +
					"			<li class=\"nav-item dropdown\">\n" +
					"        		<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
					"          			Categorías\n" +
					"        		</a>\n" +
					"        		<div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">\n" +
					"          			<a class=\"dropdown-item\" href=\"" + ServerRoutes.CATEGORIAS + "\">Categorías</a>\n" +
					"          			<a class=\"dropdown-item\" href=\"" + ServerRoutes.CRITERIOS + "\">Criterios</a>\n" +
					"       		</div>\n" +
					"      		</li>" +
					"            <li class=\"nav-item\">\n" +
					"                <a class=\"nav-link\" href=\"" + ServerRoutes.VINCULAR + "\">Vinculador</a>\n" +
					"            </li>\n" +
					"        </ul>\n" +
					"    </div>\n" +
					"	 <form class=\"form-inline my-2 my-lg-0\" style=\"margin-right: 10px\" >\n" +
					"      <a class=\"btn btn-outline-info my-2 my-sm-0\" href=\"" + ServerRoutes.VALIDAR + "\">Validar</a>\n" +
					"    </form>" +
					"	 <form class=\"form-inline my-2 my-lg-0\">\n" +
					"      <a class=\"btn btn-outline-danger my-2 my-sm-0\" href=\"" + ServerRoutes.LOGOUT + "\">Desconectar</a>\n" +
					"    </form>" +
					"</nav>" +
					"");
		}
	},

	navbarAdmin {
		@Override
		public CharSequence apply(String arg0, Options arg1) throws IOException {
			return new Handlebars.SafeString(
					"<nav class=\"navbar navbar-expand-sm navbar-dark bg-dark\">\n" +
							"    <a class=\"navbar-brand\" href=\"" + ServerRoutes.HOME + "\">Sistema de Control</a>\n" +
							"    <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navBarGeneral\" aria-controls=\"navBarGeneral\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
							"        <span class=\"navbar-toggler-icon\"></span>\n" +
							"    </button>\n" +
							"    <div class=\"collapse navbar-collapse\" id=\"navBarGeneral\">\n" +
							"        <ul class=\"navbar-nav mr-auto\">\n" +
							"            <li class=\"nav-item active\">\n" +
							"                <a class=\"nav-link\" href=\"" + ServerRoutes.ENTIDADES + "\">Entidades</a>\n" +
							"            </li>\n" +
							"        </ul>\n" +
							"    </div>\n" +
							"	 <form class=\"form-inline my-2 my-lg-0\" style=\"margin-right: 10px\" >\n" +
							"      <a class=\"btn btn-outline-info my-2 my-sm-0\" href=\"" + ServerRoutes.VALIDAR + "\">Validar</a>\n" +
							"    </form>" +
							"	 <form class=\"form-inline my-2 my-lg-0\">\n" +
							"      <a class=\"btn btn-outline-danger my-2 my-sm-0\" href=\"" + ServerRoutes.LOGOUT + "\">Desconectar</a>\n" +
							"    </form>" +
							"</nav>" +
							"");
					"");*/
		}
	},

	head {
		@Override
		public CharSequence apply(String titulo, Options arg1) throws IOException {
			return new Handlebars.SafeString(
					"<!-- Required meta tags -->\n" +
							"    <meta charset=\"utf-8\">\n" +
							"	 <meta name=\"Description\" content=\"Gestión de Eventos para la UTN.\">\n" +
							"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
							"	\n" +
							"    <!-- favico -->\n" +
							"    <link rel=\"icon\" type=\"image/x-icon\" href=\"/favicon.ico\">\n" +
							"	<link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.22/css/jquery.dataTables.css\">"+
							"	 <script src=\"/js/scriptsgenerales.js\"></script>" +
							"    \n" +
							"    <title>" + titulo + "</title>");
		}
	},

	footer {
		@Override
		public CharSequence apply(String _, Options arg1) throws IOException {
		return new Handlebars.SafeString(
					"<footer style=\"text-align: center position: absolute\">" +
							"<div style=\"text-align: center\" class=\"container\">"+
								"<p>© Grupo 7 2020-♾️</p>" +
							"</div>"+
					"</footer>");
		}
	},

	generalLibs {
		@Override
		public CharSequence apply(String _, Options arg1) throws IOException {
			return new Handlebars.SafeString(
					"<script src=\"/libs/jquery-3.5.1.slim.min.js\"></script>" +
							"<script src=\"/js/manejoResponses.js\"></script>" +
							"<script src=\"/libs/popper.min.js\"></script>" +
							"<link rel=\"stylesheet\" href=\"/libs/bootstrap.min.css\">" +
							"<script src=\"/libs/bootstrap.min.js\"></script>"+
							"<script src=\"/libs/datatables.js\"></script>"
			);
		}
	},

	selectorSimpleHelper {
		@Override
		public CharSequence apply(String idSelect, Options extraParams) throws IOException {
			Object idSeleccionado = extraParams.param(0);
			return new Handlebars.SafeString(
					"$(document).ready(() => {\n" +
							"        if (!!'" + idSeleccionado + "') {\n" +
							"            const options = document.getElementById(\"" + idSelect + "\").options;\n" +
							"            for (let i = 0; i < options.length; i++) {\n" +
							"                if (options[i].value === '" + idSeleccionado + "'){\n" +
							"                    options[i].selected = true;\n" +
							"                    break;\n" +
							"                }\n" +
							"            }\n" +
							"        }\n" +
							"    });"
			);
		}
	},

	selectorMultipleHelper {
		@Override
		public CharSequence apply(String idSelect, Options extraParams) throws IOException {
			Object idsSeleccionados = extraParams.param(0); // Deberia ser una lista de enteros
			return new Handlebars.SafeString(
					"$(document).ready(() => {\n" +
							"        if (!!" + idsSeleccionados + " && + " + idsSeleccionados + ".length > 0) {\n" +
							"            const options = document.getElementById(\"" + idSelect + "\").options;\n" +
							"            for (let i = 0; i < options.length; i++) {\n" +
							"                if (" + idsSeleccionados + ".includes(parseInt(options[i].value, 10))){\n" +
							"                    options[i].selected = true;\n" +
							"                }\n" +
							"            }\n" +
							"        }\n" +
							"    });"
			);
		}
	},

	compararIgual {
		@Override
		public CharSequence apply(String valorComparativo, Options extraParams) throws IOException {
			Object valor = extraParams.param(0);
			if(valor != null && valorComparativo != null) {
				return new Handlebars.SafeString(
						valor.toString().toLowerCase().equals(valorComparativo.toLowerCase()) ? extraParams.fn() : extraParams.inverse()
				);
			} else return extraParams.inverse();
		}
	},

	controlDireccion {
		@Override
		public CharSequence apply(String idSelectPais, Options extraParams) throws IOException {
			Object idSelectProvincia = extraParams.param(0);
			Object idSelectCiudad = extraParams.param(1);

			return new Handlebars.SafeString(
					"<script type=\"text/javascript\">\n" +
							"    const selectPais = $(\"select#" + idSelectPais + "\");\n" +
							"    const selectProvincia = $(\"select#" + idSelectProvincia + "\");\n" +
							"    const selectCiudad = $(\"select#" + idSelectCiudad +"\");\n" +

							"    const RUTA_PAIS = \"" + ServerRoutes.DIRECCIONES_PAISES_API + "\";\n" +
							"    const RUTA_PROVINCIA = \"" + ServerRoutes.DIRECCIONES_PROVINCIAS_API + "\";\n" +
							"    const RUTA_CIUDAD = \"" + ServerRoutes.DIRECCIONES_CIUDADES_API + "\";\n" +
							"    const ID_RUTA = \":" + ServerRoutes.PARAM_ID + "\";\n" +

							"    obtenerID = (select) => parseInt(select.val() !== null ? select.val() : select.attr(\"value\"), 10);\n" +

							"    llenarSelect = (campo, idComparar, selectToAppend) => {\n" +
							"        const option = $(`<option value=\"${campo.id}\">${campo.nombre}</option>`);\n" +
							"        if (campo.id === idComparar) {\n" +
							"            option.prop('selected', true);\n" +
							"        }\n" +
							"        option.appendTo(selectToAppend);\n" +
							"    }\n" +
							"\n" +
							"    obtenerPaises = () => {\n" +
							"        const idPais = obtenerID(selectPais);\n" +
							"\n" +
							"        fetch(RUTA_PAIS)\n" +
							"                .then(response => response.json())\n" +
							"                .then(result => {\n" +
							"                    result.forEach((pais) => llenarSelect(pais, idPais, selectPais));\n" +
							"                    changePais();\n" +
							"                });\n" +
							"    }\n" +
							"\n" +
							"    changePais = () => {\n" +
							"        const idPais = obtenerID(selectPais);\n" +
							"        const idProvincia = obtenerID(selectProvincia);\n" +
							"        selectProvincia.empty();\n" +
							"\n" +
							"        fetch(RUTA_PROVINCIA.replace(ID_RUTA, String(idPais)))\n" +
							"                .then(response => response.json())\n" +
							"                .then(result => {\n" +
							"                    if (result.length === 0) {\n" +
							"                        selectCiudad.empty();\n" +
							"                        return;\n" +
							"                    }\n" +
							"                    result.forEach((provincia) => llenarSelect(provincia, idProvincia, selectProvincia));\n" +
							"                    changeProvincia();\n" +
							"                });\n" +
							"    }\n" +
							"\n" +
							"    changeProvincia = () => {\n" +
							"        const idProvincia = obtenerID(selectProvincia);\n" +
							"        const idCiudad = obtenerID(selectCiudad);\n" +
							"        selectCiudad.empty();\n" +
							"\n" +
							"        fetch(RUTA_CIUDAD.replace(ID_RUTA, String(idProvincia)))\n" +
							"                .then(response => response.json())\n" +
							"                .then(result => result.forEach((ciudad) => llenarSelect(ciudad, idCiudad, selectCiudad)));\n" +
							"    }\n" +
							"\n" +
							"    obtenerPaises();\n" +
							"</script>"
			);
		}
	}

}
