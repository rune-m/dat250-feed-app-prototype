package group14.feedapp.utils;

import group14.feedapp.enums.Answer;
import group14.feedapp.model.*;
import group14.feedapp.web.*;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;

import java.util.List;

public class WebMapper {

    private final ModelMapper mapper = new ModelMapper();
    private final IPollUtils pollUtils = new PollUtils();

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
                    .toList();
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

    public DeviceVoteCreateRequest MapDeviceVoteRequestToInternal(DeviceVoteCreateRequestWeb deviceVoteRequest) {
        var deviceVoteInternal = mapper.map(deviceVoteRequest, DeviceVoteCreateRequest.class);
        return deviceVoteInternal;
    }

    public DeviceVoteWeb MapDeviceVoteToWeb(DeviceVote deviceVote) {
        var deviceVoteWeb = mapper.map(deviceVote, DeviceVoteWeb.class);
        return deviceVoteWeb;
    }

    public DeviceWeb MapDeviceToWeb(Device device) {
        DeviceWeb deviceWeb = mapper.map(device, DeviceWeb.class);
        return deviceWeb;
    }

    public Device MapDeviceCreateRequestToInternal(DeviceCreateRequest request) {
        Device device = mapper.map(request, Device.class);
        return device;
    }

    public Poll MapPollCreateRequestToPoll(PollCreateRequest request){
        Poll poll = mapper.map(request, Poll.class);
        return poll;
    }

    public ModelMapper getMapper() {
        return mapper;
    }

}
