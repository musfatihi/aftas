package ma.fatihii.aftas.service.Implmnts;

import lombok.RequiredArgsConstructor;
import ma.fatihii.aftas.dto.fish.FishReq;
import ma.fatihii.aftas.dto.fish.FishResp;
import ma.fatihii.aftas.exception.NotFoundException;
import ma.fatihii.aftas.exception.ServerErrorException;
import ma.fatihii.aftas.model.Fish;
import ma.fatihii.aftas.repository.FishRepository;
import ma.fatihii.aftas.service.Intrfcs.IFish;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FishService implements IFish {

    private final FishRepository fishRepository;

    private final ModelMapper modelMapper;


    @Override
    public Optional<FishResp> save(FishReq fishReq) {
        try {return Optional.of(
                modelMapper.map(
                        fishRepository.save(
                                modelMapper.map(fishReq, Fish.class)
                        ), FishResp.class)
                );
            }
        catch (Exception ex){throw new ServerErrorException("Erreur Serveur");}
    }

    @Override
    public Optional<FishResp> update(FishReq fishReq) {

        fishRepository.findById(fishReq.getName())
                      .orElseThrow(()->new NotFoundException("Poisson introuvable"));

        try {return Optional.of(
                modelMapper.map(
                        fishRepository.save(
                                modelMapper.map(fishReq, Fish.class)
                        ), FishResp.class)
        );
        }
        catch (Exception ex){throw new ServerErrorException("Erreur Serveur");}
    }

    @Override
    public List<FishResp> findAll() {
        return List.of(
                modelMapper.map(fishRepository.findAll(),FishResp[].class)
        );
    }

    public Page<FishResp> findAllFish(Pageable pageable) {

        Page<Fish> pageFish = fishRepository.findAll(pageable);

        return new PageImpl<>(
                pageFish.getContent().stream()
                        .map(this::convertFishToFishResp)
                        .collect(Collectors.toList()),
                pageFish.getPageable(),
                pageFish.getTotalElements());

    }

    @Override
    public Optional<FishResp> findById(String name) {

        Fish foundFish =  fishRepository.findById(name)
                          .orElseThrow(()->new NotFoundException("Poisson introuvable"));

        return Optional.of(
                modelMapper.map(foundFish,FishResp.class)
        );
    }

    @Override
    public boolean delete(String name) {

        fishRepository.findById(name)
                .orElseThrow(()->new NotFoundException("Poisson introuvable"));

        fishRepository.deleteById(name);
        return true;
    }

    private FishResp convertFishToFishResp(Fish fish) {
        return modelMapper.map(fish,FishResp.class);
    }
}
