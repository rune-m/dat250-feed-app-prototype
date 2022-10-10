package group14.feedapp.controller;

import group14.feedapp.exception.NoAccessException;
import group14.feedapp.model.Device;
import group14.feedapp.service.DeviceService;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.DeviceCreateRequest;
import group14.feedapp.web.DeviceWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    private final WebMapper mapper = new WebMapper();

    @GetMapping
    public ResponseEntity<List<DeviceWeb>> getAllDevices(@RequestHeader(required = false) String userId) {
        if (userId == null) {
            throw new NoAccessException(null);
        }

        var devices = deviceService.getAllDevices(userId);
        var mappedDevices = devices.stream()
                .map(mapper::MapDeviceToWeb)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mappedDevices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceWeb> getDeviceById(@PathVariable("id") String id) {
        Device device = deviceService.getDeviceById(id);
        return device != null
                ? ResponseEntity.ok(mapper.MapDeviceToWeb(device))
                : ResponseEntity.status(404).body(null);
    }

    @PostMapping
    public ResponseEntity<DeviceWeb> create(@RequestBody DeviceCreateRequest device) {
        Device newDevice = deviceService.createDevice(mapper.MapDeviceCreateRequestToInternal(device));
        return ResponseEntity.ok(mapper.MapDeviceToWeb(newDevice));
    }
}
