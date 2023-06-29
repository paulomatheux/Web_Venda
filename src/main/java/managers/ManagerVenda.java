/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Paciente;
import modelo.Produto;
import modelo.Venda;
import servicos.ServicoPaciente;
import servicos.ServicoProduto;
import servicos.ServicoVenda;

/**
 *
 * @author paulo
 */
@Stateless
public class ManagerVenda implements Serializable {

    private Venda venda;
    private List<Produto> produtosEstoque;
    private String precoLabel;
    private Paciente p;
    
    public List<Produto> adicionaProduto(Produto produto, List<Produto> venda, List<Produto> estoque, int quantidade) {
        if (verificaQuantidade(produto, quantidade)) {
            Produto clone = cloneProduto(produto);
            if (!venda.isEmpty()) {
                for (Produto produtos : venda) {
                    if (clone.getId() == produtos.getId()) {
                        int quantidadeVenda = produtos.getQuantidade() + quantidade;
                        produtos.setQuantidade(quantidadeVenda);
                        atualizaEstoque(produto, estoque, quantidade);
                        precoTotal(clone.getPreco(), quantidade);
                        return venda;
                    }
                }
                clone.setQuantidade(quantidade);
                venda.add(clone);
                atualizaEstoque(produto, estoque, quantidade);
                return venda;
            } else {
                clone.setQuantidade(quantidade);
                venda.add(clone);
                atualizaEstoque(produto, estoque, quantidade);
            }
            precoTotal(clone.getPreco(), quantidade);
        }
        return venda;
    }

    public boolean identificaCliente(ServicoPaciente servicoPaciente, Paciente paciente) {
        p = servicoPaciente.findByCpf(paciente.getCpf());
        return paciente != null;
    }

    public boolean finalizaVenda(ServicoPaciente servicoPaciente, ServicoProduto servicoProduto, ServicoVenda servicoVenda, List<Produto> produtosVenda, Paciente paciente) {
        if (identificaCliente(servicoPaciente, paciente)) {
            venda = new Venda();
            venda.setData(new Date());
            venda.setResponsavel("Responsavel");
            venda.setPaciente(p);
            venda.setProdutos(produtosVenda);
            servicoVenda.salvar(venda);
            atualizaBD(servicoProduto);
            message("Venda finalizada!", "");
            return true;
        } else {
            return false;
        }
    }

    public void atualizaBD(ServicoProduto servicoProduto) {
        for (Produto produtos : produtosEstoque) {
            servicoProduto.atualizar(produtos);
        }
    }

    public Produto cloneProduto(Produto produto) {
        Produto produtoClone = new Produto();
        produtoClone.setNome(produto.getNome());
        produtoClone.setDescricao(produto.getDescricao());
        produtoClone.setId(produto.getId());
        produtoClone.setLote(produto.getLote());
        produtoClone.setPreco(produto.getPreco());
        produtoClone.setQuantidade(produto.getQuantidade());
        produtoClone.setValidade(produto.getValidade());
        return produtoClone;
    }

    public String precoTotal(String p, int q) {
        BigDecimal valorProduto = new BigDecimal(p.replaceAll(",", "."));
        BigDecimal preco = BigDecimal.ZERO;
        for (int x = 0; x < q; x++) {
            preco = preco.add(valorProduto);
        }
        return precoLabel = String.valueOf(preco.setScale(2, RoundingMode.HALF_UP)).replaceAll("\\.", ",");
    }

    public void atualizaEstoque(Produto produto, List<Produto> estoque, int quantidade) {
        int index = estoque.indexOf(produto);
        int quantidadeEstoque = estoque.get(index).getQuantidade();
        estoque.get(index).setQuantidade(quantidadeEstoque - quantidade);
        produtosEstoque = estoque;
    }

    public void message(String titulo, String detalhes) {
        FacesMessage msg = new FacesMessage(titulo, detalhes);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean verificaQuantidade(Produto produto, int quantidade) {
        if (produto.getQuantidade() >= quantidade) {
            message("Verificar a quantidade", "A quantidade inserida é menor que a do estoque!");
            return true;
        } else {
            message("Produto inserido", "A quantidade inserida é maior que a do estoque!");
            return false;
        }
    }

    public String getPrecoLabel() {
        return precoLabel;
    }

    public void setPrecoLabel(String precoLabel) {
        this.precoLabel = precoLabel;
    }

    public List<Produto> getProdutosEstoque() {
        return produtosEstoque;
    }

    public void setProdutosEstoque(List<Produto> produtosEstoque) {
        this.produtosEstoque = produtosEstoque;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

}
