package com.yapp.project.member.service;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.common.util.PositionParser;
import com.yapp.project.common.value.BasePosition;
import com.yapp.project.common.value.Position;
import com.yapp.project.member.dto.request.CareerRequest;
import com.yapp.project.member.dto.request.MemberInfoRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Long updateInfo(String accessToken, MemberInfoRequest request) {
        Member member = memberRepository.findById(jwtService.getMemberId(accessToken))
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.MEMBER_NOT_FOUND));

        int score = getMemberScore(request.getProjectList(), request.getCareerList());

        List<String> positionList = request.getPositionList()
                .stream()
                .map(v -> Position.of(v).getCode())
                .map(v -> v.toString())
                .collect(Collectors.toList());

        member.updateInfo(
                request.getMemberAddress(),
                request.getImgUrl(),
                request.getDescription(),
                BasePosition.of(request.getBasePosition()).getCode(),
                request.getNickName(),
                PositionParser.join(positionList, "-"),
                score,
                request.getPortfolioLink()
                        .stream()
                        .map(n -> String.valueOf(n))
                        .collect(Collectors.joining(" "))
        );

        projectRepository.deleteAllByMemberId(member.getId());
        for (ProjectRequest projectRequest : request.getProjectList()) {
            projectRepository.save(memberConverter.toProjectEntity(member, projectRequest));
        }

        return member.getId();
    }

    public int getMemberScore(List<ProjectRequest> projectRequest, List<CareerRequest> careerRequest) {
        int projectPeriod = 0;
        int careerPeriod = 0;
        for (ProjectRequest p : projectRequest) {
            LocalDate startDate = p.getStartDate();
            LocalDate endDate = p.getEndDate();
            projectPeriod += (Math.round((float) ChronoUnit.DAYS.between(startDate, endDate) / 30));

        }
//        for (CareerRequest p : careerRequest) {
//            LocalDate startDate = p.getStartDate();
//            LocalDate endDate = p.getEndDate();
//            careerPeriod += Math.round((float) ChronoUnit.DAYS.between(startDate, endDate) / 30);
//        }
        return (projectPeriod) * 2;
    }

    @Transactional(readOnly = true)
    public Page<BudiMemberResponse> getBudiList(Pageable pageable, String position) {
        BasePosition basePosition = BasePosition.fromEnglishName(position);

        Page<Member> budiPage = memberRepository.findAllByBasePositionCode(pageable, basePosition.getCode());

        return budiPage.map(b -> memberConverter.toBudiMemberResponse(b));
    }

    @Transactional(readOnly = true)
    public Page<BudiMemberResponse> getBudiPositionList(String basePosition, String detailPosition, Pageable pageable) {
        int positionCode = Position.of(detailPosition).getCode();
        Page<Member> budiPage = memberRepository.findAllByPositionCodeContains("-" + positionCode + "-", pageable);

        return budiPage.map(b -> memberConverter.toBudiMemberResponse(b));
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

    @Transactional
    public void deleteAllExpiredDate(LocalDateTime baseDeletionTime) {
        memberRepository.deleteAllExpired(baseDeletionTime);
    }
}
