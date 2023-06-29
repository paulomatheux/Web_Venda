/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import java.io.Serializable;
import javax.ejb.Stateless;
import modelo.Paciente;
import servicos.ServicoPaciente;

/**
 *
 * @author paulo
 */
@Stateless
public class ManagerPaciente implements Serializable {

    public boolean salvarPaciente(ServicoPaciente servico, Paciente paciente) {
        if (servico.findByCpf(paciente.getCpf()) != null) {
            return false;
        } else {
            servico.salvar(paciente);
            return true;
        }
    }

    public void editarPaciente(ServicoPaciente servico, Paciente paciente) {
        Paciente pacienteClone = servico.findByCpf(paciente.getCpf());
        if(pacienteClone != null) {
            servico.atualizar(paciente);
        } else {
            System.out.println("Verifique o paciente que está tentando editar");
        }
    }

    public Paciente pesquisarPaciente(ServicoPaciente servico, Paciente paciente) {
        return servico.findByCpf(paciente.getCpf());
    }

    public void deletarPaciente(ServicoPaciente servico, Paciente paciente) {
        servico.deletarPaciente(paciente);
    }

}
