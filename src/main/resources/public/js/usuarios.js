async function confirmarEliminar(idUsuario) {
    const confirmacion = confirm("¿Está seguro que desea eliminar al usuario?");
    if (confirmacion) {

        await fetch(`/usuarios/${idUsuario}`, {
            method: 'DELETE'
        })
            .then(_ => location.reload())
            .catch(error => alert("Ah ocurrido un error inesperado contactese con Mauro"));
    }
}

// Para activar los tooltips en todos los componentes que tengan el atributo tooltip
$(document).ready(function() {
    //Tooltip initialization
    $('[data-toggle="tooltip"]').tooltip()
});