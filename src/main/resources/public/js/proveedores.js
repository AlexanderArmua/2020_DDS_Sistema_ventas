async function confirmarEliminarProveedor(idProveedor) {
    const confirmacion = confirm("¿Está seguro que desea eliminar al usuario?");
    if (confirmacion) {

        await fetch(`/proveedores/${idProveedor}`, {
            method: 'DELETE'
        })
            .then(_ => location.reload())
            .catch(error => alert("Ah ocurrido un error inesperado contactese con Mauro"));
    }
}

// Para activar los tooltips en todos los componentes que tengan el atributo tooltip
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})