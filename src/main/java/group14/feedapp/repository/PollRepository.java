package group14.feedapp.repository;

import group14.feedapp.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, String> {

    @Query("SELECT p FROM Poll p WHERE p.pincode = :pincode")
    Optional<Poll> findByPincode(int pincode);

}
