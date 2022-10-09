package group14.feedapp.model;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@UuidGenerator(name = "iotDeviceIdGenerator")
public class Device {
    @Id
    @GeneratedValue(generator = "iotDeviceIdGenerator")
    private String id;

    private int pincode;

    private String name;

    @OneToMany(mappedBy = "votingDevice", cascade = CascadeType.PERSIST)
    private Set<DeviceVote> votes = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DeviceVote> getVotes() {
        return votes;
    }

    public void setVotes(Set<DeviceVote> votes) {
        this.votes = votes;
    }
}
