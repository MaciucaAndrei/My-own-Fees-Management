package project.licenta.service;

import project.licenta.entity.Taxes;
import project.licenta.repository.TaxesRepository;


import javax.inject.Inject;
import java.util.List;

public class TaxesService {
    @Inject
    private TaxesRepository taxesRepository;

    public Taxes save(Taxes taxes) {
        return taxesRepository.save(taxes);
    }

    public List<Taxes> findAll() {
        return taxesRepository.findAll();
    }

}
