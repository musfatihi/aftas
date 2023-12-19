package ma.fatihii.aftas.service.Implmnts;

import lombok.RequiredArgsConstructor;
import ma.fatihii.aftas.dto.member.MemberReq;
import ma.fatihii.aftas.dto.member.MemberResp;
import ma.fatihii.aftas.exception.NotFoundException;
import ma.fatihii.aftas.exception.ServerErrorException;
import ma.fatihii.aftas.model.Member;
import ma.fatihii.aftas.repository.MemberRepository;
import ma.fatihii.aftas.service.Intrfcs.IMember;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements IMember {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;


    @Override
    public Optional<MemberResp> save(MemberReq memberReq) {
        try {return Optional.of(
                modelMapper.map(
                        memberRepository.save(
                                modelMapper.map(memberReq, Member.class)
                        ), MemberResp.class)
                );
            }
        catch (Exception ex){throw new ServerErrorException("Erreur Serveur");}
    }

    @Override
    public Optional<MemberResp> update(MemberReq memberReq) {

        memberRepository.findById(memberReq.getNum())
                .orElseThrow(()->new NotFoundException("Member introuvable"));

        Member updatedMember = modelMapper.map(memberReq,Member.class);

        try {return Optional.of(
                modelMapper.map(
                        memberRepository.save(
                        updatedMember
                        ), MemberResp.class)
        );

        }
        catch (Exception ex){throw new ServerErrorException("Erreur Serveur");}

    }

    @Override
    public List<MemberResp> findAll() {
        return List.of(
                modelMapper.map(memberRepository.findAll(), MemberResp[].class)
        );
    }

    public List<MemberResp> filter(Integer num,String name,String familyName) {
        if(num!=null){
            Optional<Member> optionalMember = memberRepository.findById(num);
            return optionalMember.map(member -> List.of(modelMapper.map(member, MemberResp.class))).orElseGet(List::of);
        }
        if(name!=null) return this.findByName(name);
        if(familyName!=null) return this.findByFamilyName(familyName);
        return findAll();
    }

    public List<MemberResp> findByName(String name) {
        return List.of(
                modelMapper.map(memberRepository.findByName(name), MemberResp[].class)
        );
    }

    public List<MemberResp> findByFamilyName(String familyName) {
        return List.of(
                modelMapper.map(memberRepository.findByFamilyName(familyName), MemberResp[].class)
        );
    }

    @Override
    public Optional<MemberResp> findById(Integer num) {
        Member foundMember =  memberRepository.findById(num)
                .orElseThrow(()->new NotFoundException("Member introuvable"));

        return Optional.of(
                modelMapper.map(foundMember,MemberResp.class)
        );
    }

    @Override
    public boolean delete(Integer num) {
        memberRepository.findById(num)
                        .orElseThrow(()->new NotFoundException("Member introuvable"));

        memberRepository.deleteById(num);
        return true;
    }
}
