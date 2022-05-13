package project.licenta.repository;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import project.licenta.entity.Taxes;

@Repository(forEntity = Taxes.class)
public abstract class TaxesRepository extends AbstractEntityRepository<Taxes, Long> {
}
