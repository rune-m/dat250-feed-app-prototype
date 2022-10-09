package group14.feedapp.service;

import group14.feedapp.exception.ResourceAlreadyExistsException;
import group14.feedapp.exception.ResourceNotFoundException;
import group14.feedapp.model.User;
import group14.feedapp.model.Vote;
import group14.feedapp.model.VoteCreateRequest;
import group14.feedapp.repository.PollRepository;
import group14.feedapp.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteService implements IVoteService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private IPollService pollService;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public Vote createVote(VoteCreateRequest voteRequest, User authenticatedUser) {
        var pollId = voteRequest.getPollId();
        var poll = pollService.getPollById(pollId);
        if (poll == null) {
            throw new ResourceNotFoundException("PollId " + pollId);
        }
        if (poll.getVotes().stream().anyMatch(_votes -> authenticatedUser.getId().equals(_votes.getUser().getId()))){
            throw new ResourceAlreadyExistsException("PollId " + pollId);
        }
        var vote = new Vote();
        vote.setPoll(poll);
        vote.setUser(authenticatedUser);
        vote.setAnswer(voteRequest.getAnswer());
        return voteRepository.save(vote);
    }
}
