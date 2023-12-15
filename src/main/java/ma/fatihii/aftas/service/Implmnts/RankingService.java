package ma.fatihii.aftas.service.Implmnts;

import ma.fatihii.aftas.dto.ranking.RankingReq;
import ma.fatihii.aftas.dto.ranking.RankingResp;
import ma.fatihii.aftas.exception.CustomException;
import ma.fatihii.aftas.model.Competition;
import ma.fatihii.aftas.model.Ranking;
import ma.fatihii.aftas.model.compositeKeys.RankingCompositeKey;
import ma.fatihii.aftas.repository.CompetitionRepository;
import ma.fatihii.aftas.repository.RankingRepository;
import ma.fatihii.aftas.service.Intrfcs.IRanking;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RankingService implements IRanking {

    private final RankingRepository rankingRepository;
    private final CompetitionRepository competitionRepository;
    private final ModelMapper modelMapper;

    @Autowired

    RankingService(RankingRepository rankingRepository,
                   CompetitionRepository competitionRepository,
                   ModelMapper modelMapper){
        this.rankingRepository = rankingRepository;
        this.competitionRepository = competitionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<RankingResp> save(RankingReq rankingReq) {
        if(!checkDateCompetition(rankingReq)) throw new CustomException("Participation à cette compétition n'est plus possible(Date)");
        if(!checkPlaceCompetition(rankingReq)) throw new CustomException("Participation à cette compétition n'est plus possible(Places)");
        try {return Optional.of(
                modelMapper.map(rankingRepository.save(
                        modelMapper.map(rankingReq, Ranking.class)
                ), RankingResp.class)
            );
        }
        catch (Exception ex){throw new CustomException("Erreur Serveur");}
    }

    @Override
    public Optional<RankingResp> update(RankingReq rankingReq) {
        return Optional.empty();
    }

    @Override
    public List<RankingResp> findAll() {
        return null;
    }

    @Override
    public Optional<RankingResp> findById(RankingCompositeKey rankingCompositeKey) {
        return Optional.empty();
    }

    @Override
    public boolean delete(RankingCompositeKey rankingCompositeKey) {
        return false;
    }

    private boolean checkDateCompetition(RankingReq rankingReq){

        Optional<Competition> competitionOptional = competitionRepository.findById(
                rankingReq.getRankingCompositeKey().getCompetition().getCode()
        );

        if(competitionOptional.isEmpty()) throw new CustomException("Compétition introuvable");

        return LocalDateTime.now().plus(24, ChronoUnit.HOURS).isBefore(
                competitionOptional.get().getDate().atTime(competitionOptional.get().getStartTime())
                );

    }


    private boolean checkPlaceCompetition(RankingReq rankingReq){

        Optional<Competition> competitionOptional = competitionRepository.findById(
                rankingReq.getRankingCompositeKey().getCompetition().getCode()
        );

        Integer reservedPlaces = rankingRepository.countByRankingCompositeKeyCompetition(
                modelMapper.map(rankingReq.getRankingCompositeKey().getCompetition(),Competition.class)
        );

        return competitionOptional.get().getNumberOfParticipants()>reservedPlaces;
    }

    public List<RankingResp> getLeaderBoard(String code){
        if(competitionRepository.findById(code).isEmpty()) throw new CustomException("Competition introuvable");

        List<Ranking> participants = rankingRepository.findByRankingCompositeKeyCompetition(
                                    competitionRepository.findById(code).get()
                                    );
        Collections.sort(participants);
        List<Ranking> leaderBoard = new ArrayList<>();

        leaderBoard = participants.size()>4?participants.subList(0,3):participants;

        return List.of(
                    modelMapper.map(
                            leaderBoard,
                            RankingResp[].class
                    )
        );
    }

}
