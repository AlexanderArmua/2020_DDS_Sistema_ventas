package Domain.Repositories.daos;

import db.EntidadPersistente;

import java.util.LinkedList;
import java.util.List;

public class DAOMemoria<T> implements DAO<T> {
    private List<EntidadPersistente> entidades;

    public DAOMemoria() {
        this.entidades = new LinkedList<>();
    }

    public DAOMemoria(List<EntidadPersistente> entidades){
        this.entidades = entidades;
    }

    @Override
    public List<T> buscarTodos() {
        return (List<T>) this.entidades;
    }

    @Override
    public T buscar(int id) {
        return (T) this.entidades
                .stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public void agregar(Object unObjeto) {
        this.entidades.add((EntidadPersistente) unObjeto);
    }

    @Override
    public void modificar(Object unObjeto) {

    }

    @Override
    public void eliminar(Object unObjeto) {
        this.entidades.remove(unObjeto);
    }
}