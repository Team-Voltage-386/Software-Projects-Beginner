# Camera Tuning

## Basics
Connecting to a camera is relatively easy. You can see the use of 3 different cameras in CameraSubsystem. The first is a USB connected camera of okish quality. The second is a fisheye camera with a wider lens. The third is a Limelight camera which we also typically use for object and april tag detection.

For the usb cameras (the first and second), you just use `CameraServer.startAutomaticCapture` and can pass arguments like the name and id. Those cameras start with a pre-configured video mode which includes a pixel format, width, height, and frames-per-second (fps). You can print these out using the function `printVideoModes` in `CameraSubsystem.java`.

The Limelight camera communicates over HTTP traffic, so its constructor starts a little different as you can see in `CameraSubsystem`. In the end, you still call `CameraServer.startAutomaticCapture`. Limelight has its own configuration through its web portal for its video mode.

## Tuning USB cameras
The next goal for a camera is to find the best video mode settings to use. You first can enumerate the whole list that the camera supports using `printVideoModes`. Here's some example video modes:
```
FPS: 30, WxH: 640 x 480, Pixel Format: kMJPEG
FPS: 30, WxH: 1600 x 896, Pixel Format: kMJPEG
FPS: 30, WxH: 1280 x 720, Pixel Format: kMJPEG
FPS: 30, WxH: 1024 x 768, Pixel Format: kMJPEG
FPS: 30, WxH: 1024 x 576, Pixel Format: kMJPEG
FPS: 30, WxH: 960 x 544, Pixel Format: kMJPEG
FPS: 30, WxH: 864 x 480, Pixel Format: kMJPEG
...
FPS: 30, WxH: 640 x 360, Pixel Format: kYUYV
FPS: 30, WxH: 352 x 288, Pixel Format: kYUYV
FPS: 30, WxH: 320 x 240, Pixel Format: kYUYV
FPS: 5, WxH: 1920 x 1080, Pixel Format: kYUYV
```

The documentation suggests you stick with the kMJPEG pixel format. You can then experiment with the FPS, width, and height values per row to find the best fit. Here's how I tested them:

1. Set the video mode of the camera using `setVideoMode`, for example: `this.usbCamera.setVideoMode(PixelFormat.kMJPEG, 640, 480, 30);`.

2. Open Shuffleboard. On the left, there's an expandable side-bar `>>`. Click to open the side-bar. Under `Sources`, there's an expandable list of `CameraServer`. Expand the list and drag in the row with the corresponding camera (in this case `USB camera`) onto Shuffleboard.

3. From there, you can tune the output display. The documentation suggests that the best performance will come by matching the video mode you specified in Step 1. This didn't seem to work for me. Instead, I would increase compression to ~20 and then mess with the width and height resolution values to experiment and find the highest FPS. Make sure you `Apply Settings` each time you change the settings.

4. Continue experimenting until you find the best FPS for that video mode. Return to 1 until you've tested all the video modes.

## Switching Between Cameras
The goal of this project was to have a way to switch between multiple cameras. One use case was for a driver who wanted to swap between camera-views on the robot. This is pretty straightforward. The documentation will describe how operationally you can think of cameras as sources and the display in Shuffleboard as a sink. The default sink is `CameraServer.getServer()` stored in `this.server` in `CameraSubsystem.java`. Using the function `setCameraSource`, you can swap what camera source is directed to the camera sink. 

The documentation also describes that the camera library is pretty aggressive about reducing bandwidth especially when there isn't a sink connected to a camera source. To avoid the small downtime for a camera to startup when switching between cameras often, we use `setConnectionStrategy(ConnectionStrategy.kKeepOpen)` on each camera to keep the cameras online. This makes it much smoother to switch between cameras.

Lastly, we bound each camera to a different button in `RobotContainer.java`. When you press the respective button, the output of the `USB Camera` in Shuffleboard will change to match the currently selected camera.

## Tuning take-aways
* I found that the higher resolutions would have much lower FPS. This makes sense because for a higher source width and height, there's more data to send and the bandwidth is only so large.

* On-board processing such as with a rasberry pi would make it much more reasonable to use the higher resolutions because you wouldn't have to send the video over the network.

* I found that 320x240 at 120 FPS and ~20 compression was best for the current project.