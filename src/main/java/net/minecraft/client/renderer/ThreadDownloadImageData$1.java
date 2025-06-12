package net.minecraft.client.renderer;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.src.Config;
import org.apache.commons.io.FileUtils;

class ThreadDownloadImageData$1
extends Thread {
    ThreadDownloadImageData$1(String $anonymous0) {
        super($anonymous0);
    }

    @Override
    public void run() {
        HttpURLConnection httpurlconnection = null;
        logger.debug("Downloading http texture from {} to {}", new Object[]{ThreadDownloadImageData.this.imageUrl, ThreadDownloadImageData.this.cacheFile});
        if (ThreadDownloadImageData.this.shouldPipeline()) {
            ThreadDownloadImageData.this.loadPipelined();
        } else {
            try {
                httpurlconnection = (HttpURLConnection)new URL(ThreadDownloadImageData.this.imageUrl).openConnection(Minecraft.getMinecraft().getProxy());
                httpurlconnection.setDoInput(true);
                httpurlconnection.setDoOutput(false);
                httpurlconnection.connect();
                if (httpurlconnection.getResponseCode() / 100 != 2) {
                    if (httpurlconnection.getErrorStream() != null) {
                        Config.readAll(httpurlconnection.getErrorStream());
                    }
                    return;
                }
                try {
                    BufferedImage bufferedimage;
                    if (ThreadDownloadImageData.this.cacheFile != null) {
                        FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), ThreadDownloadImageData.this.cacheFile);
                        bufferedimage = ImageIO.read(ThreadDownloadImageData.this.cacheFile);
                    } else {
                        bufferedimage = TextureUtil.readBufferedImage(httpurlconnection.getInputStream());
                    }
                    if (ThreadDownloadImageData.this.imageBuffer != null) {
                        bufferedimage = ThreadDownloadImageData.this.imageBuffer.parseUserSkin(bufferedimage);
                    }
                    ThreadDownloadImageData.this.setBufferedImage(bufferedimage);
                }
                catch (Exception exception) {
                    logger.error("Couldn't download http texture: " + exception.getClass().getName() + ": " + exception.getMessage());
                    return;
                }
            }
            finally {
                if (httpurlconnection != null) {
                    httpurlconnection.disconnect();
                }
                ThreadDownloadImageData.this.loadingFinished();
            }
        }
    }
}
