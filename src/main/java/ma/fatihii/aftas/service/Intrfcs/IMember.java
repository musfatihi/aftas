package ma.fatihii.aftas.service.Intrfcs;

import ma.fatihii.aftas.dto.member.MemberReq;
import ma.fatihii.aftas.dto.member.MemberResp;

import java.util.List;

public interface IMember extends GenericInterface<MemberReq,Integer, MemberResp>{
    List<MemberResp> filter(Integer num, String name, String familyName);
}
