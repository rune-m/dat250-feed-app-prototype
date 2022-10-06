package group14.feedapp.utils;

import group14.feedapp.model.Poll;
import group14.feedapp.model.User;
import group14.feedapp.web.PollWeb;
import group14.feedapp.web.UserWeb;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;

public class WebMapper {

    private ModelMapper mapper = new ModelMapper();
    private IPollUtils pollUtils = new PollUtils();

    public PollWeb MapPollToWeb(Poll poll) {
        PollWeb pollWeb = mapper.map(poll, PollWeb.class);
        pollWeb.setUserId(poll.getUser().getId());

        Pair<Integer, Integer> counts = pollUtils.countPollVotes(poll);
        pollWeb.setAnswerACount(counts.getLeft());
        pollWeb.setAnswerBCount(counts.getRight());

        return pollWeb;
    }

    public UserWeb MapUserToWeb(User user) {
        UserWeb userWeb = mapper.map(user, UserWeb.class);
        return userWeb;
    }

}