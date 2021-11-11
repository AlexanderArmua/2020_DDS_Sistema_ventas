package Domain.Repositories.daos.Entities;

import Domain.Entities.Entidades.Entidad;
import Domain.Entities.Entidades.EntidadBase;
import Domain.Entities.Entidades.EntidadJuridica;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import db.EntityManagerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntidadRepository extends Repositorio<Entidad> {
    public EntidadRepository() {
        super(new DAOHibernate<>(Entidad.class));
    }

    public List<Entidad> obtenerTodasLasEntidades() {
        return this.buscarTodos();
    }

    public List<EntidadJuridica> obtenerTodasLasEntidadesJuridicas() {
        List<Entidad> entidades = this.buscarTodos();
        List<EntidadJuridica> entidadJuridicas;

        entidadJuridicas = entidades.stream().filter(entidad -> entidad instanceof EntidadJuridica).map(entidad -> (EntidadJuridica) entidad).collect(Collectors.toList());

        return entidadJuridicas;
    }

    public List<EntidadBase> obtenerTodasLasEntidadesBases() {
        List<Entidad> entidades = this.buscarTodos();
        List<EntidadBase> entidadBases;

        entidadBases = entidades.stream().filter(entidad -> entidad instanceof EntidadBase).map(entidad -> (EntidadBase) entidad).collect(Collectors.toList());

        return entidadBases;
    }


    public List<Entidad> buscarTodosParaElUsuario (Usuario user){
        int idEntidad = user.getEntidad().getId();
        String query = "select e from Entidad e " +
                " where e.id = :idEntidad";


            try {
                return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idEntidad", idEntidad).getResultList();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }

        public EntidadJuridica buscarEntidadJuridica (Integer idEntidad){
            Entidad entidad = this.buscar(idEntidad);

            if (entidad instanceof EntidadJuridica) {
                return (EntidadJuridica) entidad;
            }

            return null;
        }
    }
