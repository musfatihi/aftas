package ma.fatihii.aftas.service.Implmnts;

import lombok.RequiredArgsConstructor;
import ma.fatihii.aftas.dto.competition.CompetitionReq;
import ma.fatihii.aftas.dto.competition.CompetitionResp;
import ma.fatihii.aftas.exception.BadRequestException;
import ma.fatihii.aftas.exception.NotFoundException;
import ma.fatihii.aftas.exception.ServerErrorException;
import ma.fatihii.aftas.model.Competition;
import ma.fatihii.aftas.model.Hunting;
import ma.fatihii.aftas.model.Ranking;
import ma.fatihii.aftas.repository.CompetitionRepository;
import ma.fatihii.aftas.repository.HuntingRepository;
import ma.fatihii.aftas.repository.RankingRepository;
import ma.fatihii.aftas.service.Intrfcs.ICompetition;
import org.modelmapper.ModelMapper;
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
@RequiredArgsConstructor
public class CompetitionService implements ICompetition {

    private final CompetitionRepository competitionRepository;

    private final ModelMapper modelMapper;

    private final RankingRepository rankingRepository;

    private final HuntingRepository huntingRepository;


    //------------------------------------------------CRUD-----------------------------------
    @Override
    public Optional<CompetitionResp> save(CompetitionReq competitionReq) {

        if(!isTimeValid(competitionReq)) throw new BadRequestException("Données incorrectes");
        if(!isCompetitionDateValid(competitionReq)) throw new BadRequestException("Autre compétition est programmée pour ce jour");

        try {return Optional.of(
                modelMapper.map(
                        competitionRepository.save(modelMapper.map(
                                competitionReq, Competition.class)
                        ), CompetitionResp.class)
                );
            }
        catch (Exception ex){throw new ServerErrorException("Erreur Serveur");}
    }

    @Override
    public Optional<CompetitionResp> update(CompetitionReq competitionReq) {

        if(!isTimeValid(competitionReq)) throw new BadRequestException("Données incorrectes");

        competitionRepository.findById(competitionReq.getCode())
                                .orElseThrow(()->new NotFoundException("Competition introuvable"));

        Competition updatedCompetition = modelMapper.map(competitionReq,Competition.class);

        try {return Optional.of(
                modelMapper.map(
                        competitionRepository.save(
                                updatedCompetition
                        ), CompetitionResp.class)
        );
        }
        catch (Exception ex){throw new ServerErrorException("Erreur Serveur");}
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
        Competition foundCompetition =  competitionRepository.findById(code)
                .orElseThrow(()->new NotFoundException("Competition introuvable"));
        return Optional.of(
                modelMapper.map(foundCompetition,CompetitionResp.class)
        );
    }

    @Override
    public boolean delete(String code) {
        competitionRepository.findById(code)
                .orElseThrow(()->new NotFoundException("Competition introuvable"));
        competitionRepository.deleteById(code);
        return true;
    }

    //---------------------------------------------------------------------------------------

    public boolean closeCompetition(String code){

        Competition foundCompetition =  competitionRepository.findById(code)
                .orElseThrow(()->new NotFoundException("Competition introuvable"));

        List<Ranking> reservedPlaces = rankingRepository.findByRankingCompositeKeyCompetition(foundCompetition);

        if(reservedPlaces.isEmpty()) throw new BadRequestException("Aucun participant");

        List<Ranking> scoredReservedPlaces = new ArrayList<>();

        for(Ranking reservedPlace:reservedPlaces){

            List<Hunting> huntingList = huntingRepository.findByCompetitionAndMember(reservedPlace.getRankingCompositeKey().getCompetition(),reservedPlace.getRankingCompositeKey().getMember());

            int points = 0;

            for(Hunting hunting:huntingList){
                points+=hunting.getFish().getLevel().getPoints()*hunting.getNumberOfFish();
            }

            reservedPlace.setScore(points);

            scoredReservedPlaces.add(reservedPlace);

        }

        rankingRepository.saveAll(scoredReservedPlaces);

        rankingRepository.saveAll(rankContestants(foundCompetition));

        return true;
    }

    public List<Ranking> rankContestants(Competition competition){

        List<Ranking> contestants = rankingRepository.findByRankingCompositeKeyCompetition(competition);

        Collections.sort(contestants);

        for (int i=0;i<contestants.size();i++){
            contestants.get(i).setRank(i+1);
        }

        return contestants;
    }

    private CompetitionResp convertCompetitionToCompetitionResp(Competition competition) {
        return modelMapper.map(competition,CompetitionResp.class);
    }

    //--------------------------------------used to filter competition based on their states-----

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
                            LocalTime.now().isAfter(competition.getEndTime())
                    );
            default -> false;
        };
    }


    //--------------------------------------Validation Competition Data---------------------------------


    //--------------------------------------One Competition a day----------------------------
    private boolean isCompetitionDateValid(CompetitionReq competitionReq){
        return competitionRepository.findByDate(competitionReq.getDate()).isEmpty();
    }

    //--------------------------------------Compare End Time to Start Time

    private boolean isTimeValid(CompetitionReq competitionReq){
        return competitionReq.getEndTime().isAfter(competitionReq.getStartTime());
    }

    //--------------------------------------------------------------------------------------

}
