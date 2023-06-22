/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicos;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import modelo.Produto;

/**
 *
 * @author paulo
 */
@Stateless
public class ServicoProduto extends ServicoGenerico<Produto> {

    public ServicoProduto() {
        super(Produto.class);
    }

    public Produto findProduct(Long id) {
        String jpql = "SELECT u FROM Produto u WHERE u.id = :id";
        try {
            return getEm().createQuery(jpql, Produto.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Produto findByProductName(String nome) {
        String jpql = "SELECT u FROM Produto u WHERE u.nome = :nome";
        try {
            return getEm().createQuery(jpql, Produto.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public void deletarProduto(Produto produto) {
        Object id = produto.getId();
        Produto prod = getEm().getReference(Produto.class, id);
        deletar(prod);
    }
}
