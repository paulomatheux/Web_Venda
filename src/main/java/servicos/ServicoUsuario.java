package servicos;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import modelo.Usuario;

@Stateless
public class ServicoUsuario extends ServicoGenerico<Usuario> {

    public ServicoUsuario() {
        super(Usuario.class);
    }

    public Usuario findByUsername(String nome) {
        String jpql = "SELECT u FROM Usuario u WHERE u.nome = :nome";
        try {
            return getEm().createQuery(jpql, Usuario.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public void deletarUsuario(Usuario usuario) {
        Object id = usuario.getId();
        Usuario user = getEm().getReference(Usuario.class, id);
        deletar(user);
    }
}
