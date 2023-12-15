package ma.fatihii.aftas.service.Implmnts;

import ma.fatihii.aftas.dto.member.MemberReq;
import ma.fatihii.aftas.dto.member.MemberResp;
import ma.fatihii.aftas.exception.CustomException;
import ma.fatihii.aftas.model.Member;
import ma.fatihii.aftas.repository.MemberRepository;
import ma.fatihii.aftas.service.Intrfcs.IMember;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements IMember {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Autowired
    MemberService(MemberRepository memberRepository,ModelMapper modelMapper){
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<MemberResp> save(MemberReq memberReq) {
        try {return Optional.of(
                modelMapper.map(
                        memberRepository.save(
                                modelMapper.map(memberReq, Member.class)
                        ), MemberResp.class)
                );
            }
        catch (Exception ex){throw new CustomException("Erreur Serveur");}
    }

    @Override
    public Optional<MemberResp> update(MemberReq memberReq) {
        return Optional.empty();
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
        Optional<Member> foundMemberOptional =  memberRepository.findById(num);
        if(foundMemberOptional.isEmpty()) throw new CustomException("Member introuvable");
        return Optional.of(
                modelMapper.map(foundMemberOptional.get(),MemberResp.class)
        );
    }

    @Override
    public boolean delete(Integer num) {
        return false;
    }
}
