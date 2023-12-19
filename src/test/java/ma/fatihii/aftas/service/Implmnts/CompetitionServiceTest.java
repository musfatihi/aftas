package ma.fatihii.aftas.service.Implmnts;

import ma.fatihii.aftas.model.Competition;
import ma.fatihii.aftas.model.Member;
import ma.fatihii.aftas.model.Ranking;
import ma.fatihii.aftas.model.compositeKeys.RankingCompositeKey;
import ma.fatihii.aftas.repository.RankingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class CompetitionServiceTest {

    @Mock
    private RankingRepository rankingRepository;
    @InjectMocks
    private CompetitionService competitionService;

    @Test
    void rankContestants() {
        Member member1 = new Member();
        member1.setNum(1);
        Member member2 = new Member();
        member1.setNum(2);
        Member member3 = new Member();
        member1.setNum(3);

        Competition competition = new Competition();
        competition.setCode("AGA-18-12-23");

        RankingCompositeKey rankingCompositeKey1 = new RankingCompositeKey(competition,member1);
        RankingCompositeKey rankingCompositeKey2 = new RankingCompositeKey(competition,member2);
        RankingCompositeKey rankingCompositeKey3 = new RankingCompositeKey(competition,member3);

        Ranking ranking1 = new Ranking();
        ranking1.setRankingCompositeKey(rankingCompositeKey1);
        ranking1.setScore(10);

        Ranking ranking2 = new Ranking();
        ranking2.setRankingCompositeKey(rankingCompositeKey2);
        ranking2.setScore(20);

        Ranking ranking3 = new Ranking();
        ranking3.setRankingCompositeKey(rankingCompositeKey3);
        ranking3.setScore(30);

        List<Ranking> expected = new ArrayList<>();

        expected.add(ranking3);
        expected.add(ranking2);
        expected.add(ranking1);


        List<Ranking> toRank = new ArrayList<>();

        toRank.add(ranking1);
        toRank.add(ranking2);
        toRank.add(ranking3);


        when(rankingRepository.findByRankingCompositeKeyCompetition(competition)).thenReturn(toRank);

        List<Ranking> result = competitionService.rankContestants(competition);

        verify(rankingRepository).findByRankingCompositeKeyCompetition(competition);

        assertIterableEquals(expected, result);

    }
    
}