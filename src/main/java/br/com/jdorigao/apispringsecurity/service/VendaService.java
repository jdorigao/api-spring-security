package br.com.jdorigao.apispringsecurity.service;

import br.com.jdorigao.apispringsecurity.entity.Venda;
import br.com.jdorigao.apispringsecurity.repository.VendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VendaService {

    private final VendaRepository repository;

    public Venda salvar(Venda venda) {
        return repository.save(venda);
    }

    public List<Venda> buscarPorCnpj(String cnpj) {
        return repository.findAllByCnpj(cnpj);
    }
}
