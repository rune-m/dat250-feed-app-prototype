package group14.feedapp.service;

import group14.feedapp.exception.NoAccessException;
import group14.feedapp.exception.ResourceNotFoundException;
import group14.feedapp.model.Poll;
import group14.feedapp.model.User;
import group14.feedapp.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollService implements IPollService {

    @Autowired
    private PollRepository repository;

    @Autowired
    private IUserService userService;

    @Override
    public Poll getPollById(String id) {
        Optional<Poll> pollOptional = repository.findById(id);
        return pollOptional.orElse(null);
    }

    @Override
    public Poll getPollByPincode(int pincode, String userId) {
        Optional<Poll> pollOptional = repository.findByPincode(pincode);

        if (pollOptional.isPresent()) {
            Poll poll = pollOptional.get();
            User user = userService.getUserById(userId);
            if (user == null && poll.isPrivate()) {
                throw new NoAccessException(userId); // Can't access private polls if not logged in
            }
            return poll;
        }
        throw new ResourceNotFoundException(String.valueOf(pincode));
    }

    @Override
    public List<Poll> getAllOngoingPolls(String userId) {
        List<Poll> allPolls = repository.findAll();

        User user = userService.getUserById(userId);

        LocalDateTime now = LocalDateTime.now();

        // Anonym: Alle aktive, åpne polls
        if (user == null) {
            return allPolls.stream()
                    .filter(poll -> !poll.isPrivate() && // is public
                                    !poll.isClosed() && // is open
                                    poll.getEndDate().isAfter(now) && // not ended
                                    poll.getStartDate().isBefore(now)) // has started
                    .collect(Collectors.toList());
        }

        // Admin: Alle
        if (user.isAdmin()) {
            return allPolls;
        }

        // Bruker: Alle aktive, åpne polls + dine egne, og alle du har stemt på.
        return allPolls.stream()
                .filter(poll -> (!poll.isPrivate() && // is public
                                !poll.isClosed() && // is open
                                poll.getEndDate().isAfter(now) && // not ended
                                poll.getStartDate().isBefore(now)) || // has started
                                poll.getUser().getId().equals(user.getId()) || // user owning poll
                                poll.getVotes().stream().anyMatch(
                                        vote -> vote.getUser().getId().equals(user.getId())
                                )) // user has voted on poll
                .collect(Collectors.toList());
    }

    @Override
    public Poll createPoll(Poll poll) {
        try{
            repository.save(poll);
            return poll;
        } catch (Exception e) {
            return null;
        }


    }

    @Override
    public void deletePoll(String pollId, String userId) {
        Optional<Poll> pollOptional = repository.findById(pollId);
        if (pollOptional.isPresent()) {
            if (pollOptional.get().getUser().getId().equals(userId)) {
                repository.deleteById(pollId);
            } else {
                throw new NoAccessException(userId);
            }
        } else {
            throw new ResourceNotFoundException(pollId);
        }
    }

}
