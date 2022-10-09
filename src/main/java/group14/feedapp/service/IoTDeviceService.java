package group14.feedapp.service;

import group14.feedapp.model.IoTDevice;
import group14.feedapp.repository.IoTDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IoTDeviceService {

    @Autowired
    private IoTDeviceRepository repository;

    public IoTDevice getDeviceById(String id) {
        Optional<IoTDevice> deviceOptional = repository.findById(id);
        return deviceOptional.isPresent()
                ? deviceOptional.get()
                : null;
    }
}
