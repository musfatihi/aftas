package ma.fatihii.aftas.service.Implmnts;

import lombok.RequiredArgsConstructor;
import ma.fatihii.aftas.dto.ranking.RankingReq;
import ma.fatihii.aftas.dto.ranking.RankingResp;
import ma.fatihii.aftas.exception.BadRequestException;
import ma.fatihii.aftas.exception.NotFoundException;
import ma.fatihii.aftas.exception.ServerErrorException;
import ma.fatihii.aftas.model.Competition;
import ma.fatihii.aftas.model.Ranking;
import ma.fatihii.aftas.model.compositeKeys.RankingCompositeKey;
import ma.fatihii.aftas.repository.CompetitionRepository;
import ma.fatihii.aftas.repository.RankingRepository;
import ma.fatihii.aftas.service.Intrfcs.IRanking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankingService implements IRanking {

    private final RankingRepository rankingRepository;
    private final CompetitionRepository competitionRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<RankingResp> save(RankingReq rankingReq) {
        if(!checkDateCompetition(rankingReq)) throw new BadRequestException("Participation à cette compétition n'est plus possible(Date)");
        if(!checkPlaceCompetition(rankingReq)) throw new BadRequestException("Participation à cette compétition n'est plus possible(Places)");
        try {return Optional.of(
                modelMapper.map(rankingRepository.save(
                        modelMapper.map(rankingReq, Ranking.class)
                ), RankingResp.class)
            );
        }
        catch (Exception ex){throw new ServerErrorException("Erreur Serveur");}
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

        rankingRepository.findById(rankingCompositeKey)
                         .orElseThrow(()->new NotFoundException("Place introuvable"));

        rankingRepository.deleteById(rankingCompositeKey);
        return true;
    }

    private boolean checkDateCompetition(RankingReq rankingReq){

        Competition competition = competitionRepository.findById(
                rankingReq.getRankingCompositeKey().getCompetition().getCode()
        ).orElseThrow(()->new NotFoundException("Compétition introuvable"));

        return LocalDateTime.now().plusHours(24).isBefore(
                competition.getDate().atTime(competition.getStartTime())
                );

    }


    private boolean checkPlaceCompetition(RankingReq rankingReq){

        Competition competition = competitionRepository.findById(
                rankingReq.getRankingCompositeKey().getCompetition().getCode()
        ).orElseThrow(()->new NotFoundException("Compétition introuvable"));

        Integer reservedPlaces = rankingRepository.countByRankingCompositeKeyCompetition(
                modelMapper.map(rankingReq.getRankingCompositeKey().getCompetition(),Competition.class)
        );

        return competition.getNumberOfParticipants()>reservedPlaces;
    }

    public List<RankingResp> getLeaderBoard(String code){

        competitionRepository.findById(code).
        orElseThrow(()->new NotFoundException("Competition introuvable"));

        List<Ranking> participants = rankingRepository.findByRankingCompositeKeyCompetition(
                                    competitionRepository.findById(code).get()
                                    );

        Collections.sort(participants);

        List<Ranking> leaderBoard = participants.size()>4?participants.subList(0,3):participants;

        return List.of(
                    modelMapper.map(
                            leaderBoard,
                            RankingResp[].class
                    )
        );
    }

}
