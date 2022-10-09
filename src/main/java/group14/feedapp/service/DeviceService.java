package group14.feedapp.service;

import group14.feedapp.exception.ResourceNotFoundException;
import group14.feedapp.model.Device;
import group14.feedapp.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceRepository repository;

    public Device createDevice(Device device) {
        Device newDevice = repository.save(device);
        return newDevice;
    }

    public List<Device> getAllDevices(String userId) {
        var user = userService.getUserById(userId);
        List<Device> devices = new ArrayList<>();

        if (user == null) {
            throw new ResourceNotFoundException("UserId " + userId);
        }

        if (user.isAdmin()) {
            devices = repository.findAll();
        }

        return devices;
    }

    public Device getDeviceById(String id) {
        Optional<Device> deviceOptional = repository.findById(id);
        return deviceOptional.orElse(null);
    }
}
