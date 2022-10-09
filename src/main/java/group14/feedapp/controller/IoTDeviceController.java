package group14.feedapp.controller;

import group14.feedapp.model.IoTDevice;
import group14.feedapp.service.IoTDeviceService;
import group14.feedapp.service.PollService;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.IoTDeviceWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/device")
public class IoTDeviceController {

    @Autowired
    private IoTDeviceService deviceService;

    @Autowired
    private PollService pollService;

    private WebMapper mapper = new WebMapper();

    @GetMapping("/{id}")
    public ResponseEntity<IoTDeviceWeb> getDeviceById(@PathVariable("id") String id) {
        IoTDevice device = deviceService.getDeviceById(id);
        return device != null
                ? ResponseEntity.ok(mapper.MapIoTDeviceToWeb(device))
                : ResponseEntity.status(404).body(null);
    }

    @GetMapping("/connect")
    public ResponseEntity<IoTDeviceWeb> connect(@RequestBody IoTDeviceWeb newDevice) {
    }
}
