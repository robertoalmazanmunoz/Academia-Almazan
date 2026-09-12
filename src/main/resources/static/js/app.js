document.addEventListener("DOMContentLoaded", function () {
    console.log("Academia - app.js cargado");

    // ELIMINAR AUTOR
    const botonesEliminar = document.querySelectorAll(".btn-eliminar-curso");
    botonesEliminar.forEach(function (boton) {
        boton.addEventListener("click", function () {
            const id = this.dataset.id;
            const nombre = this.dataset.nombre;
            Swal.fire({
                title: "¿Eliminar curso?",
                text: `¿Está seguro de eliminar el curso ${nombre}?`,
                icon: "warning",
                showCancelButton: true,
                confirmButtonText: "Sí, eliminar",
                cancelButtonText: "Cancelar",
                reverseButtons: true
            }).then((result) => {
                if (result.isConfirmed) {
                    const formulario = document.getElementById("formEliminarCurso");
                    formulario.action = `/cursos/eliminar/${id}`;
                    formulario.submit();
                }
            });
        });
    });
});