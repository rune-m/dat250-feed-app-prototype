package group14.feedapp.utils;

import group14.feedapp.enums.Answer;
import group14.feedapp.model.*;
import group14.feedapp.web.*;
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

    public IoTVoteCreateRequest MapIoTVoteRequestToInternal(IoTVoteCreateRequestWeb iotVoteRequest) {
        var iotVoteInternal = mapper.map(iotVoteRequest, IoTVoteCreateRequest.class);
        return iotVoteInternal;
    }

    public IoTVoteWeb MapDeviceVoteToWeb(IoTVote deviceVote) {
        var deviceVoteWeb = mapper.map(deviceVote, IoTVoteWeb.class);
        return deviceVoteWeb;
    }

    public IoTDeviceWeb MapIoTDeviceToWeb(IoTDevice device) {
        IoTDeviceWeb deviceWeb = mapper.map(device, IoTDeviceWeb.class);
        return deviceWeb;
    }

    public IoTDevice MapIoTCreateRequestToInternal(DeviceCreateRequest request) {
        IoTDevice device = mapper.map(request, IoTDevice.class);
        return device;
    }

    public ModelMapper getMapper() {
        return mapper;
    }

}
