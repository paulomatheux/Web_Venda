/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
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
public class ManagerHistorico implements Serializable {
    
    private List<Produto> produtos;
    private Venda venda;
    private Paciente paciente;
    
    public void pesquisaVenda(ServicoVenda sVenda, ServicoProduto sProduto, ServicoPaciente sPaciente, Venda v) {
        venda = sVenda.findVenda(v.getId());
        produtos = new ArrayList<>();
        for (int i = 0; i < venda.getProdutos().size(); i++) {
            produtos.add(sProduto.findProduct(venda.getProdutos().get(i).getId()));
        }
        paciente = sPaciente.findByCpf(venda.getPaciente().getCpf());
        System.out.println(paciente.getNome());
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

    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
}
