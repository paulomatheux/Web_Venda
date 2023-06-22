/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Paciente;
import servicos.ServicoPaciente;

/**
 *
 * @author paulo
 */
@Named
@ViewScoped
public class ManagerPaciente implements Serializable {

    @EJB
    private ServicoPaciente servico;
    private Paciente paciente;
    private List<Paciente> pacientes;
    private boolean trocarFormulario = true;
    private String headerText = "Pesquisar Cliente";

    @PostConstruct
    public void init() {
        paciente = new Paciente();
        populaTabela();
    }

    public void populaTabela() {
        pacientes = servico.findAll();
    }

    public void salvarPaciente() {
        if (servico.findByCpf(paciente.getCpf()) != null) {
            System.out.println("Este paciente já existe!");
        } else {
            servico.salvar(paciente);
            paciente = new Paciente();
            populaTabela();
            message("Cadastro realizado com Sucesso!", "Paciente cadastrado com sucesso!");
        }
    }

    public void message(String titulo, String detalhes) {
        FacesMessage msg = new FacesMessage(titulo, detalhes);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void novoPaciente() {
        paciente = new Paciente();
    }
    
    public void pesquisarPaciente() {
        Paciente tmp = servico.findByCpf(paciente.getCpf());
        if (tmp != null) {
            paciente = tmp;
            trocarFormulario = !trocarFormulario;
        }
    }
    
    public void cancelar() {
        paciente = new Paciente();
        trocarFormulario = !trocarFormulario;
    }

    public String perfil() {
        return "profile?faces-redirect=true";
    }

    public void deletarPaciente(Paciente paciente) {
        servico.deletarPaciente(paciente);
        populaTabela();
    }

    public ServicoPaciente getServico() {
        return servico;
    }

    public void setServico(ServicoPaciente servico) {
        this.servico = servico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public boolean isTrocarFormulario() {
        return trocarFormulario;
    }

    public void setTrocarFormulario(boolean trocarFormulario) {
        this.trocarFormulario = trocarFormulario;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

}
