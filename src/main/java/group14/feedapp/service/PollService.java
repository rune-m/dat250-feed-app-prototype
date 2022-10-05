package group14.feedapp.service;

import group14.feedapp.model.Poll;
import group14.feedapp.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PollService implements IPollService {

    @Autowired
    private PollRepository repository;

    @Override
    public Poll getPollById(String id) {
        Optional<Poll> pollOptional = repository.findById(id);
        return pollOptional.isPresent() ? pollOptional.get() : null;
    }

}
