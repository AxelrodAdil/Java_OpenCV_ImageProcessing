import util.camera_util;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import javax.swing.*;

public class camera_ {
    public static void main (final String[] args) {
        // Load OpenCV
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);

        // Create panels
        final JPanel Camera = new JPanel();
        final JPanel processedFeed = new JPanel();
        final JPanel cameraFeed = new JPanel();
        final JPanel grayCamera = new JPanel();
        final JPanel blurCamera = new JPanel();
        final JPanel bilateralCamera = new JPanel();
        camera_util.createJFrame(Camera, processedFeed, cameraFeed, grayCamera, blurCamera, bilateralCamera);

        // Create video capture object (index 0 is default camera)
        final VideoCapture camera = new VideoCapture(0);

        // Start
        camera_.startFilters(Camera, processedFeed, cameraFeed, grayCamera, blurCamera, bilateralCamera, camera).run();
    }

    private static Runnable startFilters(final JPanel Camera,
                                                final JPanel processedFeed,
                                                final JPanel cameraFeed,
                                                final JPanel grayCamera,
                                                final JPanel blurCamera,
                                                final JPanel bilateralCamera,
                                                final VideoCapture camera) {
        return () -> {
            final Mat frame = new Mat();

            while (true) {
                // Read frame from camera
                camera.read(frame);

                final Mat idk = camera_util.idk(frame);
                final Mat processed = camera_util.processImage(frame);
                final Mat gray = camera_util.gray(frame);
                final Mat blur = camera_util.blur(frame);
                final Mat bilateral = camera_util.bilateral(frame);
                camera_util.markOuterContour(processed, frame);

                // Draw
                camera_util.drawImage(idk, Camera);
                camera_util.drawImage(processed, processedFeed);
                camera_util.drawImage(frame, cameraFeed);
                camera_util.drawImage(gray, grayCamera);
                camera_util.drawImage(blur, blurCamera);
                camera_util.drawImage(bilateral, bilateralCamera);
            }
        };
    }
}