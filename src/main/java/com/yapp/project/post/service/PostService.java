package com.yapp.project.post.service;

import com.yapp.project.apply.entity.Apply;
import com.yapp.project.apply.entity.value.ApplyStatus;
import com.yapp.project.apply.repository.ApplyRepository;
import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.common.value.Position;
import com.yapp.project.common.value.RootPosition;
import com.yapp.project.likepost.repository.LikePostRepository;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.post.dto.request.PostCreateRequest;
import com.yapp.project.post.dto.request.PostUpdateRequest;
import com.yapp.project.post.dto.response.*;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.entity.RecruitingPosition;
import com.yapp.project.post.entity.value.OnlineStatus;
import com.yapp.project.post.entity.value.PostCategory;
import com.yapp.project.post.repository.PostRepository;
import com.yapp.project.post.repository.RecruitingPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;
    private final PostConverter postConverter;
    private final JwtService jwtService;

    private final MemberRepository memberRepository;
    private final RecruitingPositionRepository recruitingPositionRepository;
    private final RecruitingPositionConverter recruitingPositionConverter;
    private final LikePostRepository likePostRepository;

    @Transactional
    public PostCreateResponse create(PostCreateRequest request, String accessToken) {
        Long leaderId = jwtService.getMemberId(accessToken);

        Member leader = memberRepository.findById(leaderId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        Post post = postConverter.toPostEntity(request, leader);
        Post postEntity = postRepository.save(post);

        for (var positionDetail : request.getRecruitingPositions()) {
            var recruitingPosition = recruitingPositionConverter.toRecruitingPositionEntity(
                    positionDetail.getPositionName(),
                    positionDetail.getRecruitingNumber()
            );
            recruitingPosition.setPost(postEntity);
            recruitingPositionRepository.save(recruitingPosition);
        }

        return postConverter.toPostCreateResponse(postEntity.getId(), postEntity.getImageUrl(), postEntity.getCategoryCode(), postEntity.getCreatedDate());
    }

    @Transactional
    public void update(Long postId, PostUpdateRequest request, String accessToken) {
        Long leaderId = jwtService.getMemberId(accessToken);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        post.validateLeaderOrElseThrow(leaderId);

        post.updateInfos(
                request.getImageUrl(),
                request.getTitle(),
                PostCategory.of(request.getCategoryName()).getCategoryCode(),
                request.getStartDate(),
                request.getEndDate(),
                request.getRegion(),
                request.getDescription(),
                OnlineStatus.of(request.getOnlineInfo()).getOnlineStatusCode()
        );

        recruitingPositionRepository.deleteAllByPost(post);

        for (var positionDetail : request.getRecruitingPositions()) {
            var recruitingPosition = recruitingPositionConverter.toRecruitingPositionEntity(
                    positionDetail.getPositionName(),
                    positionDetail.getRecruitingNumber()
            );
            recruitingPosition.setPost(post);
            recruitingPositionRepository.save(recruitingPosition);
        }
    }

    @Transactional(readOnly = true)
    public Page<PostSimpleResponse> findAllByPages(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);

        return postPage.map(v -> makePostSimpleResponse(v));
    }

    @Transactional
    public PostDetailResponse findById(Long postId, Optional<String> accessTokenOptional) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        post.addViewCount();

        boolean isLiked = false;
        if (accessTokenOptional.isPresent()) { // 로그인 사용자
            Long currentMemberId = jwtService.getMemberId(accessTokenOptional.get());

            Member currentMember = memberRepository.findById(currentMemberId)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

            isLiked = likePostRepository.existsByMemberAndPost(currentMember, post);
        }

        return postConverter.toPostDetailResponse(post, post.getOwner(), isLiked);
    }

    @Transactional
    public TeamMemberResponse findTeamMembersById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        List<Apply> applies = applyRepository.findAllByPost(post);
        var response = new TeamMemberResponse();
        for (var apply : applies) {
            Member member = apply.getMember();
            response.getTeamMembers().add(
                    new TeamMemberResponse.TeamMember(
                            member.getId(),
                            member.getNickName(),
                            member.getProfileImageUrl(),
                            member.getAddress()
                    )
            );
        }

        return response;
    }

    @Transactional
    public RecruitingStatusResponse findRecruitingStatusById(Long postId) {
        var response = new RecruitingStatusResponse();
        List<RecruitingPosition> positions = recruitingPositionRepository.findAllByPostId(postId);
        for (var position : positions) {
            response.getRecruitingStatuses().add(recruitingPositionConverter.toRecruitingStatus(
                    position.getId(),
                    position.getPositionCode(),
                    Long.toString(applyRepository.countByRecruitingPositionAndApplyStatusCode(position, ApplyStatus.APPROVAL_FOR_PARTICIPATION.getApplyStatusCode()))
            ));
        }

        return response;
    }

    @Transactional
    public PostDeleteResponse deleteById(String accessToken, Long postId) {
        Long memberId = jwtService.getMemberId(accessToken);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        if (post.getOwner().getId().equals(memberId)) {  // TODO: CASCADE 설정하기
            postRepository.deleteById(postId);
            return postConverter.toPostDeleteResponse(postId);
        }

        throw new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID);
    }

    @Transactional(readOnly = true)
    public Page<PostSimpleResponse> findAllByPosition(String rootPositionName, Pageable pageable) {
        Page<Post> allByPositionCode = recruitingPositionRepository.findDistinctPostByPositionCode(RootPosition.of(rootPositionName).getRootPositionCode(), pageable);

        return allByPositionCode.map(p -> makePostSimpleResponse(p));
    }

    private PostSimpleResponse makePostSimpleResponse(Post post) {
        List<PositionAndColor> positions = new ArrayList<>();

        List<RecruitingPosition> positionDetailsByPost = recruitingPositionRepository.findAllByPostId(post.getId());
        for (var positionDetail : positionDetailsByPost) {
            positions.add(
                    new PositionAndColor(
                            Position.of(positionDetail.getPositionCode()).getPositionName(),
                            Position.getRootPosition(positionDetail.getPositionCode()).getRootPositionCode()
                    )
            );
        }

        return postConverter.toPostSimpleResponse(post, positions);
    }

}
