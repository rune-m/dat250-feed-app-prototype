package group14.feedapp.service;

import group14.feedapp.model.DeviceVote;
import group14.feedapp.model.DeviceVoteCreateRequest;
import group14.feedapp.model.User;
import group14.feedapp.model.Vote;
import group14.feedapp.model.VoteCreateRequest;

public interface IVoteService {
    DeviceVote createDeviceVote(DeviceVoteCreateRequest request, String deviceId);
    Vote createVote(VoteCreateRequest vote, User user);
}
