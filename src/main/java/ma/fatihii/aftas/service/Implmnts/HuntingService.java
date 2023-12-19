package ma.fatihii.aftas.service.Implmnts;


import lombok.RequiredArgsConstructor;
import ma.fatihii.aftas.dto.hunting.HuntingReq;
import ma.fatihii.aftas.dto.hunting.HuntingResp;
import ma.fatihii.aftas.exception.BadRequestException;
import ma.fatihii.aftas.exception.NotFoundException;
import ma.fatihii.aftas.exception.ServerErrorException;
import ma.fatihii.aftas.model.*;
import ma.fatihii.aftas.model.compositeKeys.RankingCompositeKey;
import ma.fatihii.aftas.repository.CompetitionRepository;
import ma.fatihii.aftas.repository.HuntingRepository;
import ma.fatihii.aftas.repository.MemberRepository;
import ma.fatihii.aftas.repository.RankingRepository;
import ma.fatihii.aftas.service.Intrfcs.IHunting;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HuntingService implements IHunting {

    private final HuntingRepository huntingRepository;
    private final CompetitionRepository competitionRepository;
    private final RankingRepository rankingRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;


    @Override
    public Optional<HuntingResp> save(HuntingReq huntingReq) {

        if(!isHuntingValid(huntingReq)) throw new BadRequestException("Donnees incorrectes");

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
            }catch (Exception ex){throw new ServerErrorException("Erreur Serveur");}
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
            } catch (Exception ex){throw new ServerErrorException("Erreur Serveur");}
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

    private boolean isHuntingValid(HuntingReq huntingReq){

        Competition competition = competitionRepository.findById(huntingReq.getCompetition().getCode()).
                orElseThrow(()->new NotFoundException("Compétition introuvable"));

        Member member = memberRepository.findById(huntingReq.getMember().getNum())
                .orElseThrow(()->new NotFoundException("Membre introuvable"));

        RankingCompositeKey rankingCompositeKey = new RankingCompositeKey();
        rankingCompositeKey.setMember(member);
        rankingCompositeKey.setCompetition(competition);

        rankingRepository.findById(rankingCompositeKey).
                orElseThrow(()->new NotFoundException("Participation introuvable"));

        return competition.getDate().equals(LocalDate.now()) &&
                competition.getStartTime().isBefore(LocalTime.now()) &&
                competition.getEndTime().isAfter(LocalTime.now());
    }
}
