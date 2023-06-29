/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import managers.ManagerHistorico;
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
@Named
@ViewScoped
public class InterfaceHistorico implements Serializable {

    @EJB
    private ServicoVenda servicoVenda;
    @EJB
    private ServicoProduto servicoProduto;
    @EJB
    private ServicoPaciente servicoPaciente;
    private ManagerHistorico managerHistorico = new ManagerHistorico();
    private List<Produto> produtosVenda;
    private List<Venda> vendas;
    private Venda venda;
    private Paciente paciente;

    @PostConstruct
    public void init() {
        vendas = servicoVenda.findAll();
        venda = new Venda();
    }

    public void pesquisarVenda(Venda v) {
        managerHistorico.pesquisaVenda(servicoVenda, servicoProduto, servicoPaciente, v);
        venda = managerHistorico.getVenda();
        produtosVenda = managerHistorico.getProdutos();
        paciente = managerHistorico.getPaciente();

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

    public ServicoVenda getServicoVenda() {
        return servicoVenda;
    }

    public void setServicoVenda(ServicoVenda servicoVenda) {
        this.servicoVenda = servicoVenda;
    }

    public ServicoProduto getServicoProduto() {
        return servicoProduto;
    }

    public void setServicoProduto(ServicoProduto servicoProduto) {
        this.servicoProduto = servicoProduto;
    }

    public ServicoPaciente getServicoPaciente() {
        return servicoPaciente;
    }

    public void setServicoPaciente(ServicoPaciente servicoPaciente) {
        this.servicoPaciente = servicoPaciente;
    }

    public ManagerHistorico getManagerHistorico() {
        return managerHistorico;
    }

    public void setManagerHistorico(ManagerHistorico managerHistorico) {
        this.managerHistorico = managerHistorico;
    }

    public List<Produto> getProdutosVenda() {
        return produtosVenda;
    }

    public void setProdutosVenda(List<Produto> produtosVenda) {
        this.produtosVenda = produtosVenda;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

}
