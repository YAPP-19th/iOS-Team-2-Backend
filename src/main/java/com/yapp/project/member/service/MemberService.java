package com.yapp.project.member.service;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.common.value.BasePosition;
import com.yapp.project.common.value.Position;
import com.yapp.project.member.dto.request.CareerRequest;
import com.yapp.project.member.dto.request.CreateInfoRequest;
import com.yapp.project.member.dto.request.ProjectRequest;
import com.yapp.project.member.dto.response.BudiMemberInfoResponse;
import com.yapp.project.member.dto.response.BudiMemberResponse;
import com.yapp.project.member.dto.response.CheckNameResponse;
import com.yapp.project.member.repository.LikeMemberRepositroy;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.entity.Project;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.member.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberConverter memberConverter;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final LikeMemberRepositroy likeMemberRepositroy;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public boolean isExistMember(long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        return member.isPresent();
    }

    @Transactional(readOnly = true)
    public String findByLoginId(String loginId) {
        Optional<Member> member = memberRepository.findMemberByLoginId(loginId);
        String memberId = "";
        if (!member.isEmpty()) {
            memberId = member.get().getLoginId();
        }
        return memberId;
    }

    @Transactional
    public Long create(String loginId) {
        Member m = memberRepository.save(memberConverter.toCreateMember(loginId));
        return m.getId();
    }

    public CheckNameResponse checkDuplicateName(String name) {
        return memberConverter.toCheckNameResponse(memberRepository.existsByNickName(name));
    }

    @Transactional
    public Long createInfo(String accessToken, CreateInfoRequest request) {
        Member member = memberRepository.findById(jwtService.getMemberId(accessToken))
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.MEMBER_NOT_FOUND));

        int score = getMemberScore(request.getProjectList(), request.getCareerList());

        Member m = memberRepository.save(memberConverter.toMemberEntity(member, request, score));

        for (ProjectRequest req : request.getProjectList())
            projectRepository.save(memberConverter.toProjectEntity(member, req));

        return m.getId();
    }

    public int getMemberScore(List<ProjectRequest> projectRequest, List<CareerRequest> careerRequest) {
        int projectPeriod = 0;
        int careerPeriod = 0;
        for (ProjectRequest p : projectRequest) {
            LocalDate startDate = p.getStartDate();
            LocalDate endDate = p.getEndDate();
            projectPeriod += (Math.round((float) ChronoUnit.DAYS.between(startDate, endDate) / 30));

        }
        for (CareerRequest p : careerRequest) {
            LocalDate startDate = p.getStartDate();
            LocalDate endDate = p.getEndDate();
            careerPeriod += Math.round((float) ChronoUnit.DAYS.between(startDate, endDate) / 30);
        }
        return (projectPeriod) * 2 + (careerPeriod) * 2;
    }

    @Transactional(readOnly = true)
    public List<BudiMemberResponse> getBudiList(String position) {
        BasePosition basePosition = BasePosition.fromEnglishName(position);

        List<Member> m = memberRepository.getMemberBybasePositionCode(basePosition.getCode());
        List<BudiMemberResponse> responses = memberConverter.toBudiMemberResponse(m);

        return responses;
    }

    @Transactional(readOnly = true)
    public List<BudiMemberResponse> getBudiPositionList(String position, String positionName) {
        BasePosition basePosition = BasePosition.fromEnglishName(position);
        int positionCode = Position.of(positionName).getCode();
        List<Member> m = memberRepository.getMemberBybasePositionCode(basePosition.getCode());
        List<Member> filter = new ArrayList<>();
        for(Member member : m){
            String positionList[] = member.getPositionCode().split(" ");
            if( Arrays.stream(positionList).anyMatch(code -> code.equals(String.valueOf(positionCode)) ) ){
                filter.add(member);
            }
        }
        List<BudiMemberResponse> responses = memberConverter.toBudiMemberResponse(filter);

        return responses;
    }

    @Transactional(readOnly = true)
    public BudiMemberInfoResponse getBudiInfo(Long id, Optional<String> accessTokenOpt) {
        Member m = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));
        List<Project> projectList = projectRepository.getAllByMemberId(id);

        boolean isLikedFromCurrentMember = false;
        if (accessTokenOpt.isPresent()) {
            long currentMemberId = jwtService.getMemberId(accessTokenOpt.get());
            isLikedFromCurrentMember = likeMemberRepositroy.existsByFromMemberIdAndToMemberId(currentMemberId, m.getId());
        }

        BudiMemberInfoResponse responses = memberConverter.toBudiMemberInfoResponse(m, projectList, isLikedFromCurrentMember);
        return responses;
    }

    @Transactional
    public void updateFcmToken(long currentMemberId, String fcmToken) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        currentMember.updateFcmTokenAndActiveStatus(fcmToken, null);
    }
}
