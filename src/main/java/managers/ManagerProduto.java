/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Produto;
import servicos.ServicoProduto;

/**
 *
 * @author paulo
 */
@Stateless
public class ManagerProduto implements Serializable {

    public void salvarProduto(ServicoProduto servico, Produto produto) {
        if (servico.findByProductName(produto.getNome()) != null) {
            System.out.println("Este produto já está cadastrado!");
        } else {
            servico.salvar(produto);
        }
    }

    public void editarProduto(ServicoProduto servico, Produto produto) {
        Produto produtoSelecionado = servico.findProduct(produto.getId());
        if (produtoSelecionado != null) {
            servico.atualizar(produto);
        } else {
            System.out.println("Verifique o produto que está tentando editar");
        }
    }

    public void deletarProduto(ServicoProduto servico, Produto produto) {
        servico.deletarProduto(produto);
    }
}
