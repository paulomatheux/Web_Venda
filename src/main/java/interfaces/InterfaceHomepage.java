/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import managers.ManagerPaciente;
import managers.ManagerProduto;
import modelo.Paciente;
import modelo.Produto;
import org.primefaces.event.RowEditEvent;
import servicos.ServicoPaciente;
import servicos.ServicoProduto;

/**
 *
 * @author paulo
 */
@Named
@ViewScoped
public class InterfaceHomepage implements Serializable {

    private ManagerProduto managerProduto = new ManagerProduto();
    private ManagerPaciente mPaciente = new ManagerPaciente();
    private ManagerPaciente managerPaciente;

    @EJB
    private ServicoProduto servicoProduto;
    @EJB
    private ServicoPaciente sPaciente;
    private Produto produto;
    private List<Produto> produtos;
    private boolean formulario = true;
    private Paciente paciente;
    private Map<Long, Boolean> editadoMap = new HashMap<>();

    @PostConstruct
    public void init() {
        instanciarProdutos();
        paciente = new Paciente();
    }

    public void instanciarProdutos() {
        produtos = servicoProduto.findAll();
    }

    public void novoProduto() {
        produto = new Produto();
    }

    public void salvarProduto() {
        managerProduto.salvarProduto(servicoProduto, produto);
        instanciarProdutos();
    }

    public void editarProduto(Produto produto) {
        if (managerProduto.editarProduto(servicoProduto, produto)) {
        }
    }

    public void deletarProduto() {
        managerProduto.deletarProduto(servicoProduto, produto);
        instanciarProdutos();
    }

    public void novoPaciente() {
        paciente = new Paciente();
    }

    public void salvarPaciente() {
        if (mPaciente.salvarPaciente(sPaciente, paciente)) {
            message("Cadastro realizado com Sucesso!", "Paciente cadastrado com sucesso!");
        } else {

        }
    }

    public void editarPaciente() {
        managerPaciente.editarPaciente(sPaciente, paciente);
    }

    public void deletarPaciente() {

    }

    public void pesquisarPaciente() {
        paciente = mPaciente.pesquisarPaciente(sPaciente, paciente);
        if (paciente == null) {
            message("Esse Paciente não Existe", "Paciente não encontrado no banco de dados");
        }
    }

    public void message(String titulo, String detalhes) {
        FacesMessage msg = new FacesMessage(titulo, detalhes);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit(RowEditEvent<Produto> event) {
        Produto produto = (Produto) event.getObject();
        FacesMessage msg = new FacesMessage("Produto alterado", produto.getNome());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        setEditado(produto.getId(), true);
    }

    public void onRowCancel(RowEditEvent<Produto> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getNome()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String navegacao(int valor) {
        switch (valor) {
            case 1:
                return "profile?faces-redirect=true";
            case 2:
                return "venda?faces-redirect=true";
            case 3:
                return "historico?faces-redirect=true";
            default:
                return "";
        }
    }

    public ManagerPaciente getmPaciente() {
        return mPaciente;
    }

    public void setmPaciente(ManagerPaciente mPaciente) {
        this.mPaciente = mPaciente;
    }

    public ServicoPaciente getsPaciente() {
        return sPaciente;
    }

    public void setsPaciente(ServicoPaciente sPaciente) {
        this.sPaciente = sPaciente;
    }

    public Map<Long, Boolean> getEditadoMap() {
        return editadoMap;
    }

    public void setEditadoMap(Map<Long, Boolean> editadoMap) {
        this.editadoMap = editadoMap;
    }

    public boolean isFormulario() {
        return formulario;
    }

    public void setFormulario(boolean formulario) {
        this.formulario = formulario;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public ServicoProduto getServicoProduto() {
        return servicoProduto;
    }

    public boolean isEditado(Long id) {
        return editadoMap.getOrDefault(id, false);
    }

    public void setEditado(Long id, boolean editado) {
        this.editadoMap.put(id, editado);
    }

    public void setServicoProduto(ServicoProduto servicoProduto) {
        this.servicoProduto = servicoProduto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public ManagerProduto getManagerProduto() {
        return managerProduto;
    }

    public void setManagerProduto(ManagerProduto managerProduto) {
        this.managerProduto = managerProduto;
    }

    public ManagerPaciente getManagerPaciente() {
        return managerPaciente;
    }

    public void setManagerPaciente(ManagerPaciente managerPaciente) {
        this.managerPaciente = managerPaciente;
    }

}
