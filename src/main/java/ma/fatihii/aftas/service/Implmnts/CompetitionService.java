package ma.fatihii.aftas.service.Implmnts;

import ma.fatihii.aftas.dto.competition.CompetitionReq;
import ma.fatihii.aftas.dto.competition.CompetitionResp;
import ma.fatihii.aftas.exception.CustomException;
import ma.fatihii.aftas.model.Competition;
import ma.fatihii.aftas.model.Hunting;
import ma.fatihii.aftas.model.Ranking;
import ma.fatihii.aftas.repository.CompetitionRepository;
import ma.fatihii.aftas.repository.HuntingRepository;
import ma.fatihii.aftas.repository.RankingRepository;
import ma.fatihii.aftas.service.Intrfcs.ICompetition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CompetitionService implements ICompetition {

    private final CompetitionRepository competitionRepository;

    private final ModelMapper modelMapper;

    private final RankingRepository rankingRepository;

    private final HuntingRepository huntingRepository;

    @Autowired
    CompetitionService(
            CompetitionRepository competitionRepository,
            RankingRepository rankingRepository,
            HuntingRepository huntingRepository,
            ModelMapper modelMapper
    ){
        this.competitionRepository = competitionRepository;
        this.rankingRepository = rankingRepository;
        this.huntingRepository = huntingRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Optional<CompetitionResp> save(CompetitionReq competitionReq) {

        if(!isTimeValid(competitionReq)) throw new CustomException("Données requete incorrectes");
        if(!isCompetitionDateValid(competitionReq)) throw new CustomException("Autre compétition est programmée pour ce jour");

        try {return Optional.of(
                modelMapper.map(
                        competitionRepository.save(modelMapper.map(
                                competitionReq, Competition.class)
                        ), CompetitionResp.class)
                );
            }
        catch (Exception ex){throw new CustomException("Erreur Serveur");}
    }

    @Override
    public Optional<CompetitionResp> update(CompetitionReq competitionReq) {
        return Optional.empty();
    }

    @Override
    public List<CompetitionResp> findAll() {
        return List.of(
                modelMapper.map(competitionRepository.findAll(),CompetitionResp[].class)
        );
    }

    public Page<CompetitionResp> findAllCompetitions(String status,Pageable pageable) {

        Page<Competition> pageCompetition = competitionRepository.findAll(pageable);

        Predicate<Competition> isStatus = competition -> isStatusCompetition(competition, status);

        if(status!=null){
            return new PageImpl<>(
                    pageCompetition.getContent().stream()
                            .filter(isStatus)
                            .map(this::convertCompetitionToCompetitionResp)
                            .collect(Collectors.toList()),
                    pageCompetition.getPageable(),
                    pageCompetition.getTotalElements()
            );
        }else{
            return new PageImpl<>(
                    pageCompetition.getContent().stream()
                            .map(this::convertCompetitionToCompetitionResp)
                            .collect(Collectors.toList()),
                    pageCompetition.getPageable(),
                    pageCompetition.getTotalElements()
            );
        }
    }

    @Override
    public Optional<CompetitionResp> findById(String code) {
        Optional<Competition> foundCompetitionOptional =  competitionRepository.findById(code);
        if(foundCompetitionOptional.isEmpty()) throw new CustomException("Competition introuvable");
        return Optional.of(
                modelMapper.map(foundCompetitionOptional.get(),CompetitionResp.class)
        );
    }

    @Override
    public boolean delete(String code) {
        Optional<Competition> foundCompetitionOptional =  competitionRepository.findById(code);
        if(foundCompetitionOptional.isEmpty()) throw new CustomException("Competition introuvable");
        competitionRepository.deleteById(code);
        return true;
    }

    public boolean closeCompetition(String code){

        Optional<Competition> foundCompetitionOptional =  competitionRepository.findById(code);

        if(foundCompetitionOptional.isEmpty()) throw new CustomException("Competition introuvable");

        List<Ranking> reservedPlaces = rankingRepository.findByRankingCompositeKeyCompetition(foundCompetitionOptional.get());

        if(reservedPlaces.isEmpty()) throw new CustomException("Aucun participant");

        List<Ranking> scoredReservedPlaces = new ArrayList<>();

        for(Ranking reservedPlace:reservedPlaces){

            List<Hunting> huntingList = huntingRepository.findByCompetitionAndMember(reservedPlace.getRankingCompositeKey().getCompetition(),reservedPlace.getRankingCompositeKey().getMember());

            Integer points = 0;

            for(Hunting hunting:huntingList){
                points+=hunting.getFish().getLevel().getPoints()*hunting.getNumberOfFish();
            }

            reservedPlace.setScore(points);

            scoredReservedPlaces.add(reservedPlace);

        }

        //-----------------------------------------------
        Collections.sort(scoredReservedPlaces);
        for (int i=0;i<scoredReservedPlaces.size();i++){
            scoredReservedPlaces.get(i).setRank(i+1);
        }
        //-----------------------------------------------

        rankingRepository.saveAll(scoredReservedPlaces);

        return true;
    }

    private CompetitionResp convertCompetitionToCompetitionResp(Competition competition) {
        return modelMapper.map(competition,CompetitionResp.class);
    }

    private boolean isStatusCompetition(Competition competition,String status) {
        return switch (status) {
            case "current" -> competition.getDate().equals(LocalDate.now()) &&
                    LocalTime.now().isAfter(competition.getStartTime()) &&
                    LocalTime.now().isBefore(competition.getEndTime());
            case "pending" -> competition.getDate().isAfter(LocalDate.now()) ||
                    (competition.getDate().equals(LocalDate.now()) &&
                            LocalTime.now().isBefore(competition.getStartTime())
                    );
            case "closed" -> competition.getDate().isBefore(LocalDate.now()) ||
                    (competition.getDate().equals(LocalDate.now()) &&
                            LocalTime.now().isAfter(competition.getStartTime())
                    );
            default -> false;
        };
    }

    private boolean isTimeValid(CompetitionReq competitionReq){
        return competitionReq.getEndTime().isAfter(competitionReq.getStartTime());
    }

    private boolean isCompetitionDateValid(CompetitionReq competitionReq){
        return competitionRepository.findByDate(competitionReq.getDate()).isEmpty();
    }

}
