/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
public class ManagerVenda implements Serializable {

    @EJB
    private ServicoVenda servicoVenda;
    @EJB
    private ServicoProduto servicoProduto;
    @EJB
    private ServicoPaciente servicoPaciente;

    private Venda venda;
    private List<Produto> produtosEstoque;
    private List<Produto> produtosVenda;
    private Produto produto;
    private BigDecimal preco = BigDecimal.ZERO;
    private String precoLabel;
    private int quantidade;
    private boolean btFinalizar = false;
    private boolean formulario = false;
    private Paciente paciente;
    private String cpf;

    @PostConstruct
    public void init() {
        produto = new Produto();
        venda = new Venda();
        paciente = new Paciente();
        System.out.println("Paciente Instanciado");
        populaTabela();
    }

    public void populaTabela() {
        produtosEstoque = servicoProduto.findAll();
        produtosVenda = new ArrayList<>();
    }

    public void adicionaProduto() {
        if (verificaQuantidade()) {
            Produto clone = cloneProduto(produto);
            if (!produtosVenda.isEmpty()) {
                for (Produto produtos : produtosVenda) {
                    if (clone.getId() == produtos.getId()) {
                        int quantidadeVenda = produtos.getQuantidade() + quantidade;
                        produtos.setQuantidade(quantidadeVenda);
                        atualizaEstoque();
                        precoTotal(clone.getPreco(), quantidade);
                        return;
                    }
                }
                clone.setQuantidade(quantidade);
                produtosVenda.add(clone);
                atualizaEstoque();
            } else {
                clone.setQuantidade(quantidade);
                produtosVenda.add(clone);
                atualizaEstoque();
            }
            precoTotal(clone.getPreco(), quantidade);
            btFinalizar = true;
        }
    }

    public boolean identificaCliente() {
        System.out.println(paciente.getCpf());
        paciente = servicoPaciente.findByCpf(paciente.getCpf());
        if (paciente == null) {
            message("Cliente não encontrado!", "Este CPF não se encontra cadastrado no Banco de Dados");
            formulario = !formulario;
            return false;
        } else {
            message("Cliente encontrado!", paciente.getNome());
            return true;
        }
    }

    public void finalizaVenda() {
        if (!produtosVenda.isEmpty() && identificaCliente()) {
            venda.setData(new Date());
            venda.setResponsavel("Responsavel");
            venda.setPaciente(paciente);
            venda.setProdutos(produtosVenda);
            servicoVenda.salvar(venda);
            atualizaBD();
            message("Venda finalizada!", "");
        }
    }

    public void atualizaBD() {
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

    public void precoTotal(String p, int q) {
        BigDecimal valorProduto = new BigDecimal(p.replaceAll(",", "."));
        for (int x = 0; x < q; x++) {
            preco = preco.add(valorProduto);
        }
        precoLabel = String.valueOf(preco.setScale(2, RoundingMode.HALF_UP)).replaceAll("\\.", ",");
    }

    public void atualizaEstoque() {
        int index = produtosEstoque.indexOf(produto);
        int quantidadeEstoque = produtosEstoque.get(index).getQuantidade();
        produtosEstoque.get(index).setQuantidade(quantidadeEstoque - quantidade);
    }

    public void message(String titulo, String detalhes) {
        FacesMessage msg = new FacesMessage(titulo, detalhes);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean verificaQuantidade() {
        if (produto.getQuantidade() >= quantidade) {
            message("Verificar a quantidade", "A quantidade inserida é menor que a do estoque!");
            return true;
        } else {
            message("Produto inserido", "A quantidade inserida é maior que a do estoque!");
            return false;
        }
    }

    public String homePage() {
        return "homepage?faces-redirect=true";
    }

    public String getPrecoLabel() {
        return precoLabel;
    }

    public void setPrecoLabel(String precoLabel) {
        this.precoLabel = precoLabel;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public boolean isFormulario() {
        return formulario;
    }

    public void setFormulario(boolean formulario) {
        this.formulario = formulario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public ServicoVenda getServicoVenda() {
        return servicoVenda;
    }

    public void setServicoVenda(ServicoVenda servicoVenda) {
        this.servicoVenda = servicoVenda;
    }

    public boolean isBtFinalizar() {
        return btFinalizar;
    }

    public void setBtFinalizar(boolean btFinalizar) {
        this.btFinalizar = btFinalizar;
    }

    public ServicoVenda getServicovenda() {
        return servicoVenda;
    }

    public void setServicovenda(ServicoVenda servicovenda) {
        this.servicoVenda = servicovenda;
    }

    public ServicoProduto getServicoProduto() {
        return servicoProduto;
    }

    public void setServicoProduto(ServicoProduto servicoProduto) {
        this.servicoProduto = servicoProduto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public ServicoPaciente getServicoPaciente() {
        return servicoPaciente;
    }

    public void setServicoPaciente(ServicoPaciente servicoPaciente) {
        this.servicoPaciente = servicoPaciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Produto> getProdutosEstoque() {
        return produtosEstoque;
    }

    public void setProdutosEstoque(List<Produto> produtosEstoque) {
        this.produtosEstoque = produtosEstoque;
    }

    public List<Produto> getProdutosVenda() {
        return produtosVenda;
    }

    public void setProdutosVenda(List<Produto> produtosVenda) {
        this.produtosVenda = produtosVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

}
