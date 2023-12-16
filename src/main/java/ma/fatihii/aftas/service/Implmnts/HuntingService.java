package ma.fatihii.aftas.service.Implmnts;


import ma.fatihii.aftas.dto.hunting.HuntingReq;
import ma.fatihii.aftas.dto.hunting.HuntingResp;
import ma.fatihii.aftas.exception.CustomException;
import ma.fatihii.aftas.model.*;
import ma.fatihii.aftas.repository.HuntingRepository;
import ma.fatihii.aftas.service.Intrfcs.IHunting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HuntingService implements IHunting {

    private final HuntingRepository huntingRepository;
    private final ModelMapper modelMapper;

    
    @Autowired
    HuntingService(HuntingRepository huntingRepository,ModelMapper modelMapper
                   ){
        this.huntingRepository = huntingRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public Optional<HuntingResp> save(HuntingReq huntingReq) {

        Optional<Hunting> optionalHunting = huntingRepository.findByCompetitionAndMemberAndFish(
                modelMapper.map(huntingReq.getCompetition(),Competition.class),
                modelMapper.map(huntingReq.getMember(),Member.class),
                modelMapper.map(huntingReq.getFish(),Fish.class)
        );
        if(optionalHunting.isPresent()) {
            Hunting hunting = optionalHunting.get();
            hunting.setNumberOfFish(
                    huntingReq.getNumberOfFish()+hunting.getNumberOfFish()
            );

            try {
                return Optional.of(
                        modelMapper.map(huntingRepository.save(hunting),HuntingResp.class)
                );
            }catch (Exception ex){throw new CustomException("Erreur Serveur");}
        }
        else{
            try {
                return Optional.of(
                        modelMapper.map(
                                huntingRepository.save(
                                        modelMapper.map(huntingReq, Hunting.class)
                                ),
                                HuntingResp.class
                        )
                );
            } catch (Exception ex){throw new CustomException("Erreur Serveur");}
        }
    }

    @Override
    public Optional<HuntingResp> update(HuntingReq huntingReq) {
        return Optional.empty();
    }

    @Override
    public List<HuntingResp> findAll() {
        return null;
    }

    @Override
    public Optional<HuntingResp> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
