package group14.feedapp.repository;

import group14.feedapp.model.DeviceVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceVoteRepository extends JpaRepository<DeviceVote, String> {
}
