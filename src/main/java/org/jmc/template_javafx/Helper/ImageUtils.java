package org.jmc.template_javafx.Helper;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class ImageUtils {
    public static long MB = 1024 * 1024;
    public static long MAX_IMAGE_SIZE = 1 * MB;
    public static long MAX_WIDTH = 300;
    public static long MAX_HEIGHT = 300;
    public static String DEFAULT_IMAGE = "/org/rmit/demo/Image/DEFAULT_AVT.jpg";


    public static Image byteToImage(byte[] bytes) {
        if(bytes == null || bytes.length == 0) bytes = getByte(DEFAULT_IMAGE);
        return new Image(new ByteArrayInputStream(bytes));
    }

    public static ImageView getImageView(String path){
        Image img = byteToImage(getByte(path));
        if(img == null) return new ImageView();

        ImageView imageView = new ImageView();
        imageView.setImage(img);
        imageView.setFitWidth(Math.min(MAX_WIDTH, img.getWidth()));
        imageView.setFitHeight(Math.min(MAX_HEIGHT, img.getHeight()));
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public static byte[] getByte(String path) {
        // Try to load as a file from the file system
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] fileData = new byte[(int) file.length()];
                fis.read(fileData);
                return fileData;
            } catch (IOException e) {
                e.printStackTrace();
                return new byte[0];
            }
        }

        // Try to load as a resource from the classpath
        try (InputStream is = ImageUtils.class.getResourceAsStream(path)) {
            if (is != null) {
                return is.readAllBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return empty array if both attempts fail
        return new byte[0];
    }


    public static Image imageFromPath(String imagePath){
        Image img =  byteToImage(getByte(imagePath));
        return img;
    }

    public static String openFileChooseDialog(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        fileChooser.setTitle("Choose an image");
        File file = fileChooser.showOpenDialog(stage);
        if(file == null) return DEFAULT_IMAGE;
        if(file.length() > MAX_IMAGE_SIZE){
            return DEFAULT_IMAGE;
        }

        return file.getAbsolutePath();
    }
}
