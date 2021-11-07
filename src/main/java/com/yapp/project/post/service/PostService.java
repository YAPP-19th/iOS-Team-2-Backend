package com.yapp.project.post.service;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.external.s3.S3Uploader;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.post.dto.response.PostCreateResponse;
import com.yapp.project.post.dto.response.PostInfoResponse;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final S3Uploader s3Uploader;

    private final MemberRepository memberRepository;

    private final String S3DIR = "post_image";

    @Transactional
    public PostCreateResponse create(
            String title,
            int categoryCode,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String region,
            String description,
            Long ownerId,
            List<MultipartFile> postImages
    ) throws IOException {

        Member owner = memberRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        List<String> imageUrls = new ArrayList<>();
        if (postImages != null && !postImages.isEmpty()) {  //TODO: 좀 더 깔끔하게 바꿔보기
            for (var image : postImages) {
                if (image == null || image.isEmpty()) break;

                String imageUrl = s3Uploader.upload(image, S3DIR);
                imageUrls.add(imageUrl);
            }
        }

        Post post = postConverter.toPostEntity(title, categoryCode, startDate, endDate, region, description, String.join(" ", imageUrls), owner);
        Post postEntity = postRepository.save(post);

        return postConverter.toPostCreateResponse(postEntity.getId(), imageUrls, postEntity.getPostCategory(), postEntity.getCreatedDate());
    }

    @Transactional(readOnly = true)
    public Page<PostInfoResponse> findAllByPages(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);

        return postPage.map(postConverter::toPostInfoResponse);
    }

    @Transactional(readOnly = true)
    public PostInfoResponse findById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        return postConverter.toPostInfoResponse(post);
    }
}
