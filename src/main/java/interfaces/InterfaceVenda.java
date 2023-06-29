/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import managers.ManagerVenda;
import modelo.Paciente;
import modelo.Produto;
import servicos.ServicoPaciente;
import servicos.ServicoProduto;
import servicos.ServicoVenda;

/**
 *
 * @author paulo
 */
@Named
@ViewScoped
public class InterfaceVenda implements Serializable {

    @EJB
    private ServicoProduto servicoProduto;
    @EJB
    private ServicoVenda servicoVenda;
    @EJB
    private ServicoPaciente servicoPaciente;
    private List<Produto> estoque;
    private List<Produto> venda;
    private Produto produto;
    private Paciente paciente;
    private final ManagerVenda managerVenda = new ManagerVenda();
    private int quantidade;
    private boolean btFinalizar = false;
    private boolean formulario = false;
    private String precoTotalLabel;
    
    @PostConstruct
    public void init() {
        estoque = servicoProduto.findAll();
        venda = new ArrayList<>();
        produto = new Produto();
        paciente = new Paciente();
    }

    public void adicionaProduto() {
        venda = managerVenda.adicionaProduto(produto, venda, estoque, quantidade);
        estoque = managerVenda.getProdutosEstoque();
        btFinalizar = true;
        precoTotalLabel = managerVenda.getPrecoLabel();
    }

    public void finalizarVenda() {
        if (managerVenda.finalizaVenda(servicoPaciente, servicoProduto, servicoVenda, venda, paciente)) {
            message("Cliente encontrado!", paciente.getNome());
        } else {
            message("Cliente não encontrado!", "Este CPF não se encontra cadastrado no Banco de Dados");
            formulario = !formulario;
        }
    }

    public void message(String titulo, String detalhes) {
        FacesMessage msg = new FacesMessage(titulo, detalhes);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String navegacao(int valor) {
        switch (valor) {
            case 1:
                return "homepage?faces-redirect=true";
            case 2:
                return "";
            default:
                return "";
        }
    }

    public String getPrecoTotalLabel() {
        return precoTotalLabel;
    }

    public void setPrecoTotalLabel(String precoTotalLabel) {
        this.precoTotalLabel = precoTotalLabel;
    }
    

    public ServicoProduto getServicoProduto() {
        return servicoProduto;
    }

    public void setServicoProduto(ServicoProduto servicoProduto) {
        this.servicoProduto = servicoProduto;
    }

    public ServicoVenda getServicoVenda() {
        return servicoVenda;
    }

    public void setServicoVenda(ServicoVenda servicoVenda) {
        this.servicoVenda = servicoVenda;
    }

    public ServicoPaciente getServicoPaciente() {
        return servicoPaciente;
    }

    public void setServicoPaciente(ServicoPaciente servicoPaciente) {
        this.servicoPaciente = servicoPaciente;
    }

    public List<Produto> getEstoque() {
        return estoque;
    }

    public void setEstoque(List<Produto> estoque) {
        this.estoque = estoque;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isFormulario() {
        return formulario;
    }

    public void setFormulario(boolean formulario) {
        this.formulario = formulario;
    }

    public boolean isBtFinalizar() {
        return btFinalizar;
    }

    public void setBtFinalizar(boolean btFinalizar) {
        this.btFinalizar = btFinalizar;
    }

    public List<Produto> getVenda() {
        return venda;
    }

    public void setVenda(List<Produto> venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

}
