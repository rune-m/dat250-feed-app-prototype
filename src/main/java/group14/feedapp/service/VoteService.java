package group14.feedapp.service;

import group14.feedapp.exception.NoAccessException;
import group14.feedapp.exception.ResourceAlreadyExistsException;
import group14.feedapp.exception.ResourceNotFoundException;
import group14.feedapp.model.DeviceVote;
import group14.feedapp.model.DeviceVoteCreateRequest;
import group14.feedapp.model.User;
import group14.feedapp.model.Vote;
import group14.feedapp.model.VoteCreateRequest;
import group14.feedapp.repository.DeviceVoteRepository;
import group14.feedapp.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class VoteService implements IVoteService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private DeviceVoteRepository deviceVoteRepository;
    @Autowired
    private IPollService pollService;
    @Autowired
    private DeviceService deviceService;

    @Override
    @Transactional
    public Vote createVote(VoteCreateRequest voteRequest, User authenticatedUser) {
        var pollId = voteRequest.getPollId();
        var poll = pollService.getPollById(pollId);

        if (poll == null) {
            throw new ResourceNotFoundException("PollId " + pollId);
        }

        if (authenticatedUser == null && poll.isPrivate()) {
            throw new NoAccessException(null);
        }

        if (authenticatedUser != null
                && poll.getVotes().stream().anyMatch(_votes -> Objects.equals(authenticatedUser.getId(),
                    _votes.getUser() != null ? _votes.getUser().getId() : null))){
            throw new ResourceAlreadyExistsException("PollId " + pollId);
        }

        var vote = new Vote();
        vote.setPoll(poll);
        vote.setUser(authenticatedUser);
        vote.setAnswer(voteRequest.getAnswer());
        return voteRepository.save(vote);
    }

    @Override
    @Transactional
    public DeviceVote createDeviceVote(DeviceVoteCreateRequest request, String deviceId) {
        var pollId = request.getPollId();
        var poll = pollService.getPollById(pollId);

        if (poll == null) {
            throw new ResourceNotFoundException("PollId " + pollId);
        }

        var device = deviceService.getDeviceById(deviceId);

        if (device == null) {
            throw new ResourceNotFoundException("DeviceId " + deviceId);
        }

        var vote = new DeviceVote();
        vote.setVotingDevice(device);
        vote.setPoll(poll);
        vote.setAnswerA(request.getAnswerA());
        vote.setAnswerB(request.getAnswerB());
        return deviceVoteRepository.save(vote);
    }
}
