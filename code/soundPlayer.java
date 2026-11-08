package project;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class soundPlayer {

    private static MediaPlayer backgroundPlayer;

    // 播放背景音乐（循环）
    public static void playBackgroundMusic(String filePath) {
        try {
            Media media = new Media(new File(filePath).toURI().toString());
            backgroundPlayer = new MediaPlayer(media);
            backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE); // 无限循环
            backgroundPlayer.play();
            System.out.println("背景音乐播放中...");
        } catch (Exception e) {
            System.out.println("播放背景音乐出错: " + e.getMessage());
        }
    }

    // 播放一次性音效（点击声、开门声等）
    public static void playSoundEffect(String filePath) {
        try {
            Media media = new Media(new File(filePath).toURI().toString());
            MediaPlayer player = new MediaPlayer(media);
            player.play();
            System.out.println("播放音效: " + filePath);
        } catch (Exception e) {
            System.out.println("播放音效出错: " + e.getMessage());
        }
    }
}