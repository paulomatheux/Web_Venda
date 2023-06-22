/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Produto;
import org.primefaces.event.RowEditEvent;
import servicos.ServicoProduto;

/**
 *
 * @author paulo
 */
@Named
@ViewScoped
public class ManagerProduto implements Serializable {

    @EJB
    private ServicoProduto servico;
    private List<Produto> produtos;
    private Produto produto;
    private Produto produtoSelecionado;
    private Map<Long, Boolean> linhaEditada = new HashMap<>();
    private boolean editado = false;

    @PostConstruct
    public void init() {
        produto = new Produto();
        populaTabela();
    }

    public void populaTabela() {
        produtos = servico.findAll();
    }

    public void salvarProduto() {
        if (servico.findByProductName(produto.getNome()) != null) {
            System.out.println("Este produto já está cadastrado!");
        } else {
            servico.salvar(produto);
            produto = new Produto();
            populaTabela();
        }
    }

    public void editarProduto(Produto produto) {
        produtoSelecionado = servico.findProduct(produto.getId());
        if (produtoSelecionado != null && editado) {
            servico.atualizar(produto);
            editado = false;
        } else {
            System.out.println("Verifique o produto que está tentando editar");
        }
    }

    public void novoProduto() {
        this.produto = new Produto();
    }

    public String pesquisarUsuario() {
        Produto produtoTemp = servico.findByProductName(produto.getNome());
        if (produtoTemp != null) {
            produtos = new ArrayList<>();
            produtos.add(produtoTemp);
        }
        return null;
    }
    
    public String abrirVenda(){
        System.out.println("xd");
        return "venda?faces-redirect=true";
    }

    public void deletarProduto() {
        servico.deletarProduto(produtoSelecionado);
        populaTabela();
    }

    public void onRowEdit(RowEditEvent<Produto> event) {
        FacesMessage msg = new FacesMessage("Produto alterado", String.valueOf(event.getObject().getNome()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        editado = true;
    }

    public void onRowCancel(RowEditEvent<Produto> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getNome()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean isEditado() {
        return editado;
    }

    public void setEditado(boolean editado) {
        this.editado = editado;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    public Map<Long, Boolean> getLinhaEditada() {
        return linhaEditada;
    }

    public void setLinhaEditada(Map<Long, Boolean> linhaEditada) {
        this.linhaEditada = linhaEditada;
    }

    public ServicoProduto getServico() {
        return servico;
    }

    public void setServico(ServicoProduto servico) {
        this.servico = servico;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
