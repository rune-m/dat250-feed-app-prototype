package group14.feedapp.service;

import group14.feedapp.exception.ErrorResponse;
import group14.feedapp.model.Poll;
import group14.feedapp.model.User;
import group14.feedapp.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Locale;
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
        if (userService.getUserById(poll.getUser().getId()) == null){
            return String.format("User with ID \'%s\' is not a registered user.", poll.getUser().getId());
        }
        repository.save(poll);
        return "SUCCESS";
    }


    @Override
    public String createPoll(String pincode,
                           String question,
                           String answerA,
                           String answerB,
                           String isPrivate,
                           String startDate,
                           String endDate,
                           String isClosed,
                           String userID) {

        int pinParsed = parseInt(pincode);
        System.out.println(pincode);
        if (pinParsed < 0) {return String.format("pincode \'%s\' is not an number", pincode);}

        isPrivate = isPrivate.toLowerCase();
        boolean parseIsPrivate;
        if (isPrivate == "true"){parseIsPrivate = true;}
        else if (isPrivate == "false"){parseIsPrivate = false;}
        else {
            return String.format("isPrivate '%s' is not a boolean.", isPrivate);
        }

        LocalDateTime parsedStartDate = parseDateTime(startDate);
        if (parsedStartDate == null){return String.format("Startdate '%s' is not a valid DateTime", startDate);}

        LocalDateTime parsedEndDate = parseDateTime(endDate);
        if (parsedEndDate == null){return String.format("Startdate '%s' is not a valid DateTime", endDate);}

        boolean parsedIsClosed;
        if (isClosed == "true"){parsedIsClosed = true;}
        else if (isClosed == "false"){parsedIsClosed = false;}
        else {
            return String.format("isClosed '%s' is not a boolean.", isClosed);
        }

        int userId = parseInt(userID);
        if (userId < 0){return String.format("UserID '%s' is not an integer.", userID);}

        User user = userService.getUserById(userID);
        if (user == null){return String.format("User with ID '%s' does not exist.", userID);}

        repository.save(new Poll(pinParsed,
                                question,
                                answerA,
                                answerB,
                                parseIsPrivate,
                                parsedStartDate,
                                parsedEndDate,
                                parsedIsClosed,
                                user));

        return "SUCCESS";



    }

    @Override
    public int parseInt(String number) {
        try {
            int pinParsed = Integer.parseInt(number);
            return pinParsed;
        } catch (NumberFormatException e) {
            return -1;
        }
    }




    @Override
    public LocalDateTime parseDateTime(String dateTime) {
        LocalDateTime dt;
        try {
            dt = LocalDateTime.parse(dateTime);
        } catch (DateTimeException e) {
            return null;
        }
        return dt;
    }


}
