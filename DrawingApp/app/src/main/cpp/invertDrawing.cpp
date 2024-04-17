#include <jni.h>

#include <time.h>
#include <android/log.h>
#include <android/bitmap.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define  LOG_TAG    "libimageprocessing"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

int rgb_clamp(int value) {
    if(value > 255) {
        return 255;
    }
    if(value < 0) {
        return 0;
    }
    return value;
}

void invert(AndroidBitmapInfo* info, void* pixels){
    int xx, yy, red, green, blue;
    uint32_t* line;

    for(yy = 0; yy < info->height; yy++){
        line = (uint32_t*)pixels;
        for(xx =0; xx < info->width; xx++){

            //extract the RGB values from the pixel
            red = (int) ((line[xx] & 0x00FF0000) >> 16);
            green = (int)((line[xx] & 0x0000FF00) >> 8);
            blue = (int) (line[xx] & 0x00000FF );

            //manipulate each value
            red = 255 - rgb_clamp((int)(red));
            green = 255 - rgb_clamp((int)(green));
            blue = 255 - rgb_clamp((int)(blue));

            // set the new pixel back in
            line[xx] =
                    ((red << 16) & 0x00FF0000) |
                    ((green << 8) & 0x0000FF00) |
                    (blue & 0x000000FF) | 0xFF000000;
        }

        pixels = (char*)pixels + info->stride;
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_drawingapp_DrawingViewModel_invertBitmap(JNIEnv *env, jobject thiz,
                                                          jobject bitmap_drawing) {
    AndroidBitmapInfo  info;
    int ret;
    void* pixels;

    if ((ret = AndroidBitmap_getInfo(env, bitmap_drawing, &info)) < 0) {
        LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
        return ;
    }
    if (info.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
        LOGE("Bitmap format is not RGBA_8888 !");
        return ;
    }

    if ((ret = AndroidBitmap_lockPixels(env, bitmap_drawing, &pixels)) < 0) {
        LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
    }

    invert(&info, pixels);

    AndroidBitmap_unlockPixels(env, bitmap_drawing);
}