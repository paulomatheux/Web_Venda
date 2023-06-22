/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
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
public class ManagerHistorico implements Serializable {
    
    @EJB
    private ServicoProduto servicoProduto;
    @EJB
    private ServicoVenda servicoVenda;
    @EJB
    private ServicoPaciente servicoPaciente;
    
    private List<Produto> produtos;
    private List<Venda> vendas;
    private Venda venda;
    private Paciente paciente;
    
    @PostConstruct
    public void init() {
        vendas = servicoVenda.findAll();
    }
    
    public void pesquisaVenda(Venda v) {
        produtos = new ArrayList<>();
        venda = servicoVenda.findVenda(v.getId());
        for (int i = 0; i < venda.getProdutos().size(); i++) {
            produtos.add(servicoProduto.findProduct(venda.getProdutos().get(i).getId()));
        }
        paciente = servicoPaciente.findByCpf(venda.getPaciente().getCpf());
        System.out.println(paciente.getNome());
    }
    
    public String historico() {
        return "historico?faces-redirect=true";
    }
    
    public List<Produto> getProdutos() {
        return produtos;
    }
    
    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    
    public Venda getVenda() {
        return venda;
    }
    
    public void setVenda(Venda venda) {
        this.venda = venda;
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

    public ServicoPaciente getServicoPaciente() {
        return servicoPaciente;
    }

    public void setServicoPaciente(ServicoPaciente servicoPaciente) {
        this.servicoPaciente = servicoPaciente;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }
    
    public void setServicoVenda(ServicoVenda servicoVenda) {
        this.servicoVenda = servicoVenda;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
}
