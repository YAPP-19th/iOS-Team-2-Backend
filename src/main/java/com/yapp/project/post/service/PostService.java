package com.yapp.project.post.service;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.common.value.Position;
import com.yapp.project.common.value.RootPosition;
import com.yapp.project.external.s3.S3Uploader;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.post.dto.request.PostCreateRequest;
import com.yapp.project.post.dto.response.*;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.entity.RecruitingPosition;
import com.yapp.project.post.repository.PostRepository;
import com.yapp.project.post.repository.RecruitingPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final S3Uploader s3Uploader;
    private final JwtService jwtService;

    private final MemberRepository memberRepository;
    private final RecruitingPositionRepository recruitingPositionRepository;
    private final RecruitingPositionConverter recruitingPositionConverter;

    private final String S3DIR = "post_image";

    @Transactional
    public PostCreateResponse create(PostCreateRequest request) throws IOException {
        Long leaderId = jwtService.getMemberId(request.getAccessToken());

        Member leader = memberRepository.findById(leaderId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        var postImages = request.getPostImages();
        List<String> imageUrls = new ArrayList<>();
        if (postImages != null && !postImages.isEmpty()) {  //TODO: 좀 더 깔끔하게 바꿔보기
            for (var image : postImages) {
                if (image == null || image.isEmpty()) break;

                String imageUrl = s3Uploader.upload(image, S3DIR);
                imageUrls.add(imageUrl);
            }
        }

        Post post = postConverter.toPostEntity(
                request.getTitle(),
                request.getCategoryName(),
                request.getStartDate(),
                request.getEndDate(),
                request.getRegion(),
                request.getDescription(),
                request.getOnlineInfo(),
                String.join(" ", imageUrls),
                leader
        );
        Post postEntity = postRepository.save(post);

        for(var positionDetail : request.getRecruitingPositions()){
            var recruitingPositionDetail = recruitingPositionConverter.toRecruitingPositionEntity(
                    positionDetail.getPositionName(),
                    positionDetail.getSkillName(),
                    positionDetail.getRecruitingNumber()
            );
            recruitingPositionDetail.setPost(postEntity);
            recruitingPositionRepository.save(recruitingPositionDetail);
        }

        return postConverter.toPostCreateResponse(postEntity.getId(), imageUrls, postEntity.getCategoryCode(), postEntity.getCreatedDate());
    }

    @Transactional(readOnly = true)
    public Page<PostSimpleResponse> findAllByPages(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);

        return postPage.map(v -> makePostSimpleResponse(v));
    }

    @Transactional(readOnly = true)
    public PostDetailResponse findById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        return postConverter.toPostDetailResponse(post, post.getOwner());
    }

    public TeamMemberResponse findTeamMembersById(Long postId) {
        // TODO: apply에서 status가 참여 확정인 멤버들 가져오기

        return null;
    }

    public List<RecruitingStatusResponse> findRecruitingStatusById(Long postId) {
        var responses = new ArrayList<RecruitingStatusResponse>();

        List<RecruitingPosition> positions = recruitingPositionRepository.findAllByPostId(postId);
        for(var position : positions){
            var response = recruitingPositionConverter.toRecruitingStatus(
                    position.getId(),
                    position.getPositionCode(),
                    position.getSkillCode(),
                    "3/4"  // TODO: 확정 팀원 현황 계산
            );

            responses.add(response);
        }

        return responses;
    }

    @Transactional
    public PostDeleteResponse deleteById(String token, Long postId){
        Long memberId = jwtService.getMemberId(token);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        if (post.getOwner().getId().equals(memberId)) {  // TODO: CASCADE 설정하기
            postRepository.deleteById(postId);
            return postConverter.toPostDeleteResponse(postId);
        }

        throw new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID);
    }

    @Transactional(readOnly = true)
    public Page<PostSimpleResponse> findAllByPosition(String rootPositionName, Pageable pageable){
        Page<RecruitingPosition> allByPositionCode = recruitingPositionRepository.findAllByRootPositionCode(RootPosition.of(rootPositionName).getRootPositionCode(), pageable);

        return allByPositionCode.map(rp -> makePostSimpleResponse(rp.getPost()));
    }

    private PostSimpleResponse makePostSimpleResponse(Post post) {
        List<String> positions = new ArrayList<>();

        List<RecruitingPosition> positionDetailsByPost = recruitingPositionRepository.findAllByPostId(post.getId());
        for(var positionDetail : positionDetailsByPost){
            positions.add(Position.of(positionDetail.getPositionCode()).getPositionName());
        }

        return postConverter.toPostSimpleResponse(post, positions);
    }
}
