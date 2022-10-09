package group14.feedapp;

import group14.feedapp.enums.Answer;
import group14.feedapp.exception.ResourceAlreadyExistsException;
import group14.feedapp.exception.ResourceNotFoundException;
import group14.feedapp.model.VoteCreateRequest;
import group14.feedapp.repository.PollRepository;
import group14.feedapp.repository.VoteRepository;
import group14.feedapp.service.IPollService;
import group14.feedapp.service.IVoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
public class VoteTests {
    @Autowired
    private IVoteService voteService;
    @Autowired
    private IPollService pollService;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private VoteRepository voteRepository;

//    @Test
//    @Transactional
//    void createVote_pollExists_createsVoteAndPersists() {
//        var userId = "10";
//        var pollId = "100";
//        VoteCreateRequest createRequest = new VoteCreateRequest();
//        createRequest.setPollId(pollId);
//        createRequest.setAnswer(Answer.ANSWER_A);
//
//        var before = pollRepository.findAll();
//        var ret = voteService.createVote(createRequest, userId);
//        var voteafter = voteRepository.findAll();
//        var after = pollRepository.findAll();
//
//        var poll = pollService.getPollById(pollId);
//        var newVote = poll.getVotes()
//                .stream()
//                .filter(v -> userId.equals(v.getUser().getId()))
//                .findFirst()
//                .orElseThrow();
//
//        assertEquals(newVote.getAnswer(), createRequest.getAnswer());
//        assertEquals(newVote.getUser().getId(), userId);
//        assertEquals(newVote.getPoll().getId(), pollId);
//    }
//    @Test
//    @Transactional
//    void createVote_callsTwice_throws() {
//        var userId = "10";
//        var pollId = "100";
//        VoteCreateRequest createRequest = new VoteCreateRequest();
//        createRequest.setPollId(pollId);
//        createRequest.setAnswer(Answer.ANSWER_A);
//        VoteCreateRequest secondCreateRequest = new VoteCreateRequest();
//        secondCreateRequest.setPollId(pollId);
//        secondCreateRequest.setAnswer(Answer.ANSWER_B);
//
//        voteService.createVote(createRequest, userId);
//
//        assertThrowsExactly(
//                ResourceAlreadyExistsException.class,
//                () -> voteService.createVote(createRequest, userId)
//        );
//    }
    @Test
    void createVote_pollDoesNotExists_throwsException() {
        VoteCreateRequest createRequest = new VoteCreateRequest();
        createRequest.setPollId("NOT_EXISTS");
        createRequest.setAnswer(Answer.ANSWER_A);

        assertThrowsExactly(ResourceNotFoundException.class, () -> voteService.createVote(createRequest, "10"));
    }
}
