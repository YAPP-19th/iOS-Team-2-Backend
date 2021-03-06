package com.yapp.project.likepost.service;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.likepost.dto.LikePostResponse;
import com.yapp.project.likepost.dto.LikeStatusResponse;
import com.yapp.project.likepost.entity.LikePost;
import com.yapp.project.likepost.repository.LikePostRepository;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikePostService {
    private final LikePostRepository likePostRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private final LikePostConverter likePostConverter;

    @Transactional(readOnly = true)
    public Page<LikePostResponse> findAll(Pageable pageable, long memberId) {
        Page<LikePost> likePostPage = likePostRepository.findAllByMemberId(pageable, memberId);

        return likePostPage.map(lp -> likePostConverter.toLikePostResponse(lp));
    }

    @Transactional
    public LikeStatusResponse switchLikeStatus(Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        boolean isLiked = false;
        if (likePostRepository.existsByMemberAndPost(member, post)) {
            LikePost likePost = likePostRepository.findByMemberAndPost(member, post)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessage.INVALID_HTTP_REQUEST));

            likePostRepository.delete(likePost);
            post.substractLikeCount();

            isLiked = false;
        }
        else{
            LikePost likePost = new LikePost(member, post);
            likePostRepository.save(likePost);
            post.addLikeCount();

            isLiked = true;
        }

        return new LikeStatusResponse(isLiked, post.getLikeCount() + 1);
    }
}
