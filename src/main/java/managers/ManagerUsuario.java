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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Usuario;
import org.primefaces.event.RowEditEvent;
import servicos.ServicoUsuario;

/**
 *
 * @author paulo
 */
@Named
@ViewScoped
public class ManagerUsuario implements Serializable {

    @EJB
    private ServicoUsuario servico;
    private Usuario usuario;
    private boolean exibirFormulario = false;
    private List<Usuario> usuarios;
    
    @PostConstruct
    public void init() {
        usuario = new Usuario();
        populaTabela();
    }

    public void populaTabela() {
        usuarios = servico.findAll();
    }

    public void salvarUsuario() {
        if (servico.findByUsername(usuario.getNome()) != null) {
            System.out.println("Este usuário já existe!");
        } else {
            servico.salvar(usuario);
            usuario = new Usuario();
            populaTabela();
            mostrarFormulario();
        }
    }

    public String pesquisarUsuario() {
        Usuario tmp = servico.findByUsername(usuario.getNome());
        if (tmp != null && usuario.getSenha().equals(tmp.getSenha())) {
            usuarios = new ArrayList<>();
            usuarios.add(tmp);
            return "homepage?faces-redirect=true";
        }
        return null;
    }

    public String perfil() {
        return "profile?faces-redirect=true";
    }

    public void deletarUsuario(Usuario usuario) {
        servico.deletarUsuario(usuario);
        populaTabela();
    }

    public void mostrarFormulario() {
        exibirFormulario = !exibirFormulario;
    }

    public boolean isExibirFormulario() {
        return exibirFormulario;
    }

    public void setExibirFormulario(boolean exibirFormulario) {
        this.exibirFormulario = exibirFormulario;
    }

    public List<Usuario> retornaUsuarios() {
        return usuarios = servico.findAll();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ServicoUsuario getServico() {
        return servico;
    }

    public void setServico(ServicoUsuario servico) {
        this.servico = servico;
    }

}
