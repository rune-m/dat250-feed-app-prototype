package group14.feedapp.controller;

import group14.feedapp.exception.ResourceNotFoundException;
import group14.feedapp.model.IoTDevice;
import group14.feedapp.service.IoTDeviceService;
import group14.feedapp.service.PollService;
import group14.feedapp.service.UserService;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.DeviceCreateRequest;
import group14.feedapp.web.IoTDeviceWeb;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/device")
public class IoTDeviceController {

    @Autowired
    private IoTDeviceService deviceService;

    @Autowired
    private UserService userService;

    private WebMapper mapper = new WebMapper();

    @GetMapping
    public ResponseEntity<List<IoTDeviceWeb>> getAllDevices(@RequestHeader String userId) {
        var devices = deviceService.getAllDevices(userId);
        var mappedDevices = devices.stream()
                .map(device -> mapper.MapIoTDeviceToWeb(device))
                .collect(Collectors.toList());
        return mappedDevices.size() > 0 ? ResponseEntity.ok(mappedDevices) : ResponseEntity.status(401).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IoTDeviceWeb> getDeviceById(@PathVariable("id") String id) {
        IoTDevice device = deviceService.getDeviceById(id);
        return device != null
                ? ResponseEntity.ok(mapper.MapIoTDeviceToWeb(device))
                : ResponseEntity.status(404).body(null);
    }

    @PostMapping
    public ResponseEntity<IoTDeviceWeb> create(@RequestBody DeviceCreateRequest device) {
        IoTDevice newDevice = deviceService.createDevice(mapper.MapIoTCreateRequestToInternal(device));
        return ResponseEntity.ok(mapper.MapIoTDeviceToWeb(newDevice));
    }
}
