package ma.fatihii.aftas.service.Intrfcs;

import java.util.List;
import java.util.Optional;

public interface GenericInterface<Entity,Id,Resp> {
    Optional<Resp> save(Entity entity);
    Optional<Resp> update(Entity entity);
    List<Resp> findAll();
    Optional<Resp> findById(Id id);
    boolean delete(Id id);
}