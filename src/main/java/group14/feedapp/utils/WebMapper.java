package group14.feedapp.utils;

import group14.feedapp.enums.Answer;
import group14.feedapp.model.Poll;
import group14.feedapp.model.User;
import group14.feedapp.model.Vote;
import group14.feedapp.model.VoteCreateRequest;
import group14.feedapp.web.PollWeb;
import group14.feedapp.web.UserWeb;
import group14.feedapp.web.VoteCreateRequestWeb;
import group14.feedapp.web.VoteWeb;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class WebMapper {

    private ModelMapper mapper = new ModelMapper();
    private IPollUtils pollUtils = new PollUtils();

    public PollWeb MapPollToWeb(Poll poll) {
        return MapPollToWeb(poll, null);
    }

    public PollWeb MapPollToWeb(Poll poll, String userId) {
        PollWeb pollWeb = mapper.map(poll, PollWeb.class);
        pollWeb.setUserId(poll.getUser().getId());

        Pair<Integer, Integer> counts = pollUtils.countPollVotes(poll);
        pollWeb.setAnswerACount(counts.getLeft());
        pollWeb.setAnswerBCount(counts.getRight());

        if (userId != null) {
            List<Vote> userVotes = poll.getVotes().stream()
                    .filter(vote -> vote.getUser().getId().equals(userId))
                    .collect(Collectors.toList());
            if (userVotes.isEmpty()) {
                pollWeb.setYourAnswer(Answer.NONE);
            } else {
                pollWeb.setYourAnswer(userVotes.stream().findFirst().get().getAnswer());
            }
        }

        return pollWeb;
    }

    public UserWeb MapUserToWeb(User user) {
        UserWeb userWeb = mapper.map(user, UserWeb.class);
        return userWeb;
    }
    public VoteCreateRequest MapVoteRequestToInternal(VoteCreateRequestWeb voteRequest){
        var voteInternal = mapper.map(voteRequest, VoteCreateRequest.class);
        return voteInternal;
    }
    public VoteWeb MapVoteToWeb(Vote vote){
        VoteWeb voteWeb = mapper.map(vote, VoteWeb.class);
        return voteWeb;
    }

    public ModelMapper getMapper() {
        return mapper;
    }

}
