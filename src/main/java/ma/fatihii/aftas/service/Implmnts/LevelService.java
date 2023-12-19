package ma.fatihii.aftas.service.Implmnts;

import lombok.RequiredArgsConstructor;
import ma.fatihii.aftas.dto.level.LevelReq;
import ma.fatihii.aftas.dto.level.LevelResp;
import ma.fatihii.aftas.exception.BadRequestException;
import ma.fatihii.aftas.exception.NotFoundException;
import ma.fatihii.aftas.exception.ServerErrorException;
import ma.fatihii.aftas.model.Level;
import ma.fatihii.aftas.repository.LevelRepository;
import ma.fatihii.aftas.service.Intrfcs.ILevel;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LevelService implements ILevel {

    private final LevelRepository levelRepository;
    private final ModelMapper modelMapper;


    @Override
    public Optional<LevelResp> save(LevelReq levelReq) {

        if(!isValidLevel(levelReq)) throw new BadRequestException("Infos Niveau incorrectes");

        try {return Optional.of(
                modelMapper.map(
                        levelRepository.save(
                                modelMapper.map(levelReq, Level.class)
                        ),
                        LevelResp.class)
                );
            }
        catch (Exception ex){throw new ServerErrorException("Erreur Serveur");}

    }

    @Override
    public Optional<LevelResp> update(LevelReq levelReq) {

        levelRepository.findById(levelReq.getCode()).
        orElseThrow(()->new NotFoundException("Niveau introuvable"));

        if(!isUpdateValidLevel(levelReq)) throw new BadRequestException("Infos Niveau incorrectes");

        try {return Optional.of(
                modelMapper.map(
                        levelRepository.save(
                                modelMapper.map(levelReq, Level.class)
                        ),
                        LevelResp.class)
        );
        }
        catch (Exception ex){throw new ServerErrorException("Erreur Serveur");}
    }

    @Override
    public List<LevelResp> findAll() {
        return List.of(
                modelMapper.map(levelRepository.findAllByOrderByCode(),LevelResp[].class)
                );
    }

    @Override
    public Optional<LevelResp> findById(Integer code) {
        Level foundLevel =  levelRepository.findById(code)
                        .orElseThrow(()->new NotFoundException("Niveau introuvable"));
        return Optional.of(
                modelMapper.map(foundLevel,LevelResp.class)
        );
    }

    @Override
    public boolean delete(Integer code) {

        levelRepository.findById(code)
                        .orElseThrow(()->new NotFoundException("Niveau introuvable"));

        levelRepository.deleteById(code);

        return true;
    }


    private boolean isValidLevel(LevelReq levelReq){
        List<Level> levels = levelRepository.findAll();
        for(Level level:levels)
            if(levelReq.getCode().equals(level.getCode()) ||
               levelReq.getCode()>level.getCode() && levelReq.getPoints()<=level.getPoints() ||
               levelReq.getCode()<level.getCode() && levelReq.getPoints()>=level.getPoints())
                return false;

        return true;
    }

    private boolean isUpdateValidLevel(LevelReq levelReq){
        List<Level> levels = levelRepository.findAll();
        for(Level level:levels)
            if(levelReq.getCode()>level.getCode() && levelReq.getPoints()<=level.getPoints() ||
               levelReq.getCode()<level.getCode() && levelReq.getPoints()>=level.getPoints())
                return false;

        return true;
    }

}
