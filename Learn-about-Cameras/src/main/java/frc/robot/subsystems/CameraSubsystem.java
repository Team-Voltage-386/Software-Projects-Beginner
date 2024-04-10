// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.cscore.HttpCamera.HttpCameraKind;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.PixelFormat;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CameraSubsystem extends SubsystemBase {
    UsbCamera usbCamera;
    UsbCamera fisheyeCamera;
    HttpCamera limelightCamera;
    NetworkTableEntry cameraSelection;
    VideoSink server;

    public CameraSubsystem() {
        // Creates UsbCamera and MjpegServer [1] and connects them
        this.usbCamera = CameraServer.startAutomaticCapture("USB camera", 0);
        this.fisheyeCamera = CameraServer.startAutomaticCapture("Fisheye camera", 1);
        this.limelightCamera = new HttpCamera("limelight", "http://10.3.86.12:5800/stream.mjpg",
                HttpCameraKind.kMJPGStreamer);
        CameraServer.startAutomaticCapture(this.limelightCamera);
        this.server = CameraServer.getServer();

        this.cameraSelection = NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection");

        this.usbCamera.setVideoMode(PixelFormat.kMJPEG, 640, 480, 30);
        /*
         * FPS: 30, WxH: 640 x 480, Pixel Format: kMJPEG
         * FPS: 30, WxH: 1600 x 896, Pixel Format: kMJPEG x
         * FPS: 30, WxH: 1280 x 720, Pixel Format: kMJPEG x
         * FPS: 30, WxH: 1024 x 768, Pixel Format: kMJPEG x
         * FPS: 30, WxH: 1024 x 576, Pixel Format: kMJPEG x
         * FPS: 30, WxH: 960 x 544, Pixel Format: kMJPEG x
         * FPS: 30, WxH: 864 x 480, Pixel Format: kMJPEG x
         * FPS: 30, WxH: 848 x 480, Pixel Format: kMJPEG x
         * FPS: 30, WxH: 800 x 448, Pixel Format: kMJPEG not super
         * FPS: 30, WxH: 640 x 360, Pixel Format: kMJPEG steady at 15
         * FPS: 30, WxH: 352 x 288, Pixel Format: kMJPEG 15 fps
         * FPS: 30, WxH: 320 x 240, Pixel Format: kMJPEG x
         * FPS: 30, WxH: 1920 x 1080, Pixel Format: kMJPEG x
         * FPS: 30, WxH: 640 x 480, Pixel Format: kYUYV x
         * FPS: 5, WxH: 1600 x 896, Pixel Format: kYUYV
         * FPS: 10, WxH: 1280 x 720, Pixel Format: kYUYV
         * FPS: 10, WxH: 1024 x 768, Pixel Format: kYUYV
         * FPS: 10, WxH: 1024 x 576, Pixel Format: kYUYV
         * FPS: 10, WxH: 960 x 544, Pixel Format: kYUYV
         * FPS: 10, WxH: 864 x 480, Pixel Format: kYUYV
         * FPS: 10, WxH: 848 x 480, Pixel Format: kYUYV
         * FPS: 10, WxH: 800 x 448, Pixel Format: kYUYV
         * FPS: 30, WxH: 640 x 360, Pixel Format: kYUYV
         * FPS: 30, WxH: 352 x 288, Pixel Format: kYUYV
         * FPS: 30, WxH: 320 x 240, Pixel Format: kYUYV
         * FPS: 5, WxH: 1920 x 1080, Pixel Format: kYUYV
         */

        this.fisheyeCamera.setVideoMode(PixelFormat.kMJPEG, 320, 240, 120);

        this.setCameraSource(CameraSourceOption.FISHEYE);

        this.usbCamera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        this.fisheyeCamera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        this.limelightCamera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    }

    public void printVideoModes(VideoSource source) {
        System.out.printf("Video modes for: %s BEGIN\n", source.getName());
        for (VideoMode mode : source.enumerateVideoModes()) {
            System.out.printf("FPS: %d, WxH: %d x %d, Pixel Format: %s\n", mode.fps, mode.width, mode.height,
                    mode.pixelFormat.name());
        }
        System.out.printf("Video modes for: %s END\n", source.getName());
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public static enum CameraSourceOption {
        USB_CAMERA,
        FISHEYE,
        LIMELIGHT
    }

    public void setCameraSource(CameraSourceOption option) {
        System.out.printf("SET CAMERA SOURCE: %s\n", option.toString());
        switch (option) {
            case USB_CAMERA: {
                this.server.setSource(this.usbCamera);
                break;
            }
            case FISHEYE: {
                this.server.setSource(this.fisheyeCamera);
                break;
            }
            case LIMELIGHT: {
                this.server.setSource(this.limelightCamera);
                break;
            }
            default: {
                break;
            }
        }
    }
}
