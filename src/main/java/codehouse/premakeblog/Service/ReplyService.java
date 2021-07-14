package codehouse.premakeblog.Service;

import codehouse.premakeblog.dto.ReplyDTO;
import codehouse.premakeblog.entity.Chunu;
import codehouse.premakeblog.entity.Reply;

import java.util.List;

public interface ReplyService {
    Long register(ReplyDTO replyDTO); // 댓글의 등록

    List<ReplyDTO> getList(Long cno); // 특정 게시물의 댓글 목록

    void modify(ReplyDTO replyDTO); // 댓글 수정

    void remove(Long rno); // 댓글 삭제

    // ReplyDTO 를 Reply 객체로 변환 Chunu 객체의 처리가 함께 된다.
    default Reply dtoToEntity(ReplyDTO replyDTO) {
        Chunu chunu = Chunu.builder()
                .cno(replyDTO.getCno())
                .build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .chunu(chunu)
                .build();

        return reply;
    }

    // Reply 객체를 ReplyDTO 로 변환 Board 객체가 필요하지 않으므로 게시물 번호만
    default ReplyDTO entityToDTO(Reply reply) {
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();

        return dto;
    }
}
