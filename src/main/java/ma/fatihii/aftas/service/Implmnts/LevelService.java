package ma.fatihii.aftas.service.Implmnts;

import ma.fatihii.aftas.dto.level.LevelReq;
import ma.fatihii.aftas.dto.level.LevelResp;
import ma.fatihii.aftas.exception.CustomException;
import ma.fatihii.aftas.model.Level;
import ma.fatihii.aftas.repository.LevelRepository;
import ma.fatihii.aftas.service.Intrfcs.ILevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.Optional;

@Service
public class LevelService implements ILevel {

    private final LevelRepository levelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    LevelService(LevelRepository levelRepository,ModelMapper modelMapper){
        this.levelRepository = levelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<LevelResp> save(LevelReq levelReq) {
        if(!isValidLevel(levelReq)) throw new CustomException("Infos Niveau incorrectes");

        try {return Optional.of(
                modelMapper.map(
                        levelRepository.save(
                                modelMapper.map(levelReq, Level.class)
                        ),
                        LevelResp.class)
                );
            }
        catch (Exception ex){throw new CustomException("Erreur Serveur");}

    }

    @Override
    public Optional<LevelResp> update(LevelReq levelReq) {
        return Optional.empty();
    }

    @Override
    public List<LevelResp> findAll() {
        return List.of(
                modelMapper.map(levelRepository.findAll(),LevelResp[].class)
                );
    }

    @Override
    public Optional<LevelResp> findById(Integer code) {
        Optional<Level> foundLevelOptional =  levelRepository.findById(code);
        if(foundLevelOptional.isEmpty()) throw new CustomException("Niveau introuvable");
        return Optional.of(
                modelMapper.map(foundLevelOptional.get(),LevelResp.class)
        );
    }

    @Override
    public boolean delete(Integer code) {
        return false;
    }

    private boolean isValidLevel(LevelReq levelReq){
        List<Level> levels = levelRepository.findAll();
        for(Level level:levels)
            if(level.getPoints()>=levelReq.getPoints()) return false;
        return true;
    }
}
