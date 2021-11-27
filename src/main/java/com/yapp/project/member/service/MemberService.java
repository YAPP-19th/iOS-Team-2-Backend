package com.yapp.project.member.service;
import com.yapp.project.member.dto.request.CareerRequest;
import com.yapp.project.member.dto.request.CreateInfoRequest;
import com.yapp.project.member.dto.request.ProjectRequest;
import com.yapp.project.member.dto.response.CheckNameResponse;
import com.yapp.project.member.entity.Career;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.CareerRepository;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.member.repository.ProjectRepository;
import com.yapp.project.member.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberConverter memberConverter;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final CareerRepository careerRepository;
    private final WorkRepository workRepository;
    private final JwtService jwtService;

    public String findByLoginId(String loginId){
        Optional<Member> member = memberRepository.findMemberByLoginId(loginId);
        String memberId = "";
        if(!member.isEmpty()){
            memberId = member.get().getLoginId();
        }
        return memberId;
    }
    @Transactional
    public Long create(String loginId){
        Member m = memberRepository.save(memberConverter.toCreateMember(loginId));
        return m.getId();
    }

    public CheckNameResponse checkDuplicateName(String name){
        return memberConverter.toCheckNameResponse(memberRepository.existsByNickName(name));
    }

    @Transactional
    public Long createInfo(CreateInfoRequest request){
        Optional<Member> member = memberRepository.findById(jwtService.getMemberId(request.getAccessToken()));
        //TODO: 사용자 레벨
        Member m = memberRepository.save(memberConverter.toSignUpResponseMember(member, request));
        for(ProjectRequest req : request.getProjectList())
            projectRepository.save(memberConverter.toSignUpResponseProject(member.get(), req));
        for(CareerRequest req : request.getCareerList()) {
            Career c = careerRepository.save(memberConverter.toSignUpResponseCareer(member.get(), req));
            for(ProjectRequest reqProject : req.getWorkRequestList())
                workRepository.save(memberConverter.toSignUpResponseWork(c, reqProject));
        }

        return m.getId();
    }


}
