package group14.feedapp.service;

import group14.feedapp.exception.ResourceNotFoundException;
import group14.feedapp.model.IoTDevice;
import group14.feedapp.repository.IoTDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IoTDeviceService {

    @Autowired
    private UserService userService;

    @Autowired
    private IoTDeviceRepository repository;

    public IoTDevice createDevice(IoTDevice device) {
        IoTDevice newDevice = repository.save(device);
        return newDevice;
    }

    public List<IoTDevice> getAllDevices(String userId) {
        var user = userService.getUserById(userId);
        List<IoTDevice> devices = new ArrayList<>();

        if (user == null) {
            throw new ResourceNotFoundException("UserId " + userId);
        }

        if (user.isAdmin()) {
            devices = repository.findAll();
        }

        return devices;
    }

    public IoTDevice getDeviceById(String id) {
        Optional<IoTDevice> deviceOptional = repository.findById(id);
        return deviceOptional.isPresent()
                ? deviceOptional.get()
                : null;
    }
}
