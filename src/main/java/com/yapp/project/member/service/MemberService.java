package com.yapp.project.member.service;

import com.yapp.project.member.dto.request.CareerRequest;
import com.yapp.project.member.dto.request.CreateInfoRequest;
import com.yapp.project.member.dto.request.ProjectRequest;
import com.yapp.project.member.dto.response.BudiMemberResponse;
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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
    public Long createInfo(String accessToken, CreateInfoRequest request){
        Optional<Member> member = memberRepository.findById(jwtService.getMemberId(accessToken));
        int score = getMemberScore(request.getProjectList(), request.getCareerList());
        Member m = memberRepository.save(memberConverter.toMemberEntity(member, request, score));
        for(ProjectRequest req : request.getProjectList())
            projectRepository.save(memberConverter.toProjectEntity(member.get(), req));
        for(CareerRequest req : request.getCareerList()) {
            Career c = careerRepository.save(memberConverter.toCareerEntity(member.get(), req));
            for(ProjectRequest reqProject : req.getWorkRequestList())
                workRepository.save(memberConverter.toWorkEntity(c, reqProject));
        }

        return m.getId();
    }

    public int getMemberScore(List<ProjectRequest> projectRequest, List<CareerRequest> careerRequest){
        int projectPeriod = 0;
        int careerPeriod = 0;
        for(ProjectRequest p: projectRequest){
            LocalDate startDate = p.getStartDate();
            LocalDate endDate = p.getEndDate();
            projectPeriod += (Math.round((float)ChronoUnit.DAYS.between(startDate, endDate)/30));

        }
        for(CareerRequest p: careerRequest){
            LocalDate startDate = p.getStartDate();
            LocalDate endDate = p.getEndDate();
            careerPeriod += Math.round((float)ChronoUnit.DAYS.between(startDate, endDate)/30);
        }
        return (projectPeriod)*2 + (careerPeriod)*2;
    }

    public List<BudiMemberResponse> getBudiList(String position) {
        int positionCode = 0;
        if(position.equals("developer")){
            positionCode = 1;
        }
        else if(position.equals("planner")){
            positionCode = 2;
        }
        else if(position.equals("designer")){
            positionCode = 3;
        }

        List<Member> m = memberRepository.getMemberBybasePositionCode(positionCode);
        List<BudiMemberResponse> responses = memberConverter.toBudiMemberResponse(m);
        return responses;

    }
}
