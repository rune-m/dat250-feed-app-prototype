package group14.feedapp.service;


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
        return pollOptional.isPresent() ? pollOptional.get() : null;
    }

    @Override
    public Poll getPollByPincode(int pincode, String userId) {
        Optional<Poll> pollOptional = repository.findByPincode(pincode);

        if (pollOptional.isPresent()) {
            Poll poll = pollOptional.get();
            User user = userService.getUserById(userId);
            if (user == null && poll.isPrivate()) {
                return null; // Can't access private polls if not logged in
            }
            return poll;
        }
        return null;
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
    public String createPoll(Poll poll) {
        String userID = poll.getUser().getId();

        if (userService.getUserById(userID) == null){

            return String.format("User with ID \'%s\' is not a registered user.", userID);
        }
        try{
            repository.save(poll);
            return "SUCCESS";
        } catch (Exception e) {
            return String.format("Could not save poll");
        }


    }

}
