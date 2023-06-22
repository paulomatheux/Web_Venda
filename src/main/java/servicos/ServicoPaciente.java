/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicos;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import modelo.Paciente;

/**
 *
 * @author paulo
 */
@Stateless
public class ServicoPaciente extends ServicoGenerico<Paciente> {

    public ServicoPaciente() {
        super(Paciente.class);
    }

    public Paciente findByCpf(String cpf) {
        String sql = "SELECT p FROM Paciente p WHERE p.cpf = :cpf";
        try {
            return getEm().createQuery(sql, Paciente.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("nenhum resultado");
            System.out.println(cpf + " ----");
            return null;
        }
    }

    public void deletarPaciente(Paciente paciente) {
        Object id = paciente.getId();
        Paciente pac = getEm().getReference(Paciente.class, id);
        deletar(pac);
    }
}
