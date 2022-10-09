package group14.feedapp.service;

import group14.feedapp.model.Vote;
import group14.feedapp.model.VoteCreateRequest;

public interface IVoteService {
    Vote createVote(VoteCreateRequest vote, String userId);
}
