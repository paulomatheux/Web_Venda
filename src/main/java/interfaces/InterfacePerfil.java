/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import managers.ManagerUsuario;
import modelo.Usuario;
import servicos.ServicoUsuario;

/**
 *
 * @author paulo
 */
@Named
@ViewScoped
public class InterfacePerfil implements Serializable {

    @EJB
    private ServicoUsuario servico;
    private ManagerUsuario manager = new ManagerUsuario();
    private Usuario usuario;
    private String numero;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }

    public void salvarUsuario() {
        if (manager.pesquisarUsuario(servico, usuario) == true) {
            System.out.println("Esse usuario já está cadastrado");
        } else {
            usuario.setEndereco(usuario.getEndereco() + ", " + numero);
            manager.salvarUsuario(servico, usuario);
        }
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

    public ServicoUsuario getServico() {
        return servico;
    }

    public void setServico(ServicoUsuario servico) {
        this.servico = servico;
    }

    public ManagerUsuario getManager() {
        return manager;
    }

    public void setManager(ManagerUsuario manager) {
        this.manager = manager;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}
