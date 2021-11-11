package Domain.controllers;

import Domain.Entities.Entidades.Criterio.Categoria;
import Domain.Entities.Usuarios.Usuario;

 public class UsuarioEditar {

     private Usuario revisor;
        private Boolean revisorElegido;

        public UsuarioEditar(Usuario revisor, Boolean revisorElegido) {
            this.revisor = revisor;
            this.revisorElegido = revisorElegido;
        }

    public Usuario getRevisor() {
         return revisor;
     }

     public void setRevisor(Usuario revisor) {
         this.revisor = revisor;
     }

     public Boolean getRevisorElegido() {
         return revisorElegido;
     }

     public void setRevisorElegido(Boolean revisorElegido) {
         this.revisorElegido = revisorElegido;
     }
 }



