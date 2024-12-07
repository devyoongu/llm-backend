package com.llm.backend.api;

import com.llm.backend.domain.Member;
import com.llm.backend.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();

        // 엔티티 -> DTO 변환 :  iter을 쓰는것보다 java8 이상부터는 stream을 쓰자
        List<MemberDto> collect = findMembers.stream() //stream으로 돌리고
                .map(m -> new MemberDto(m.getName())) //dto로 변환후
                .collect(Collectors.toList()); // list로 변환한다.

        //Result json에 키셋 추가해보기
        return new Result(collect.size(), collect); // collection으로 리턴하면 리스트 형태로 넘어가기 때문에 이후에 변화 대응이 어려우므로 Result json 형태로 감싸서 리턴한다.
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }


    @PostMapping("api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("api/v2/members/{id}")
    public UpdateMemberResponse updateMemberResponse(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request
    ) {
        memberService.update(id, request.getName());
        Member findmember = memberService.findOne(id);
        return new UpdateMemberResponse(findmember.getId(), findmember.getName());
    }





    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }


    @Data
    static class CreateMemberRequest { //DTO
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }


}
