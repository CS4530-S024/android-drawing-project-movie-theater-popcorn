// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("drawingapp");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("drawingapp")
//      }
//    }

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

static int rgb_clamp(int value) {
    if(value > 255) {
        return 255;
    }
    if(value < 0) {
        return 0;
    }
    return value;
}

static void noise(AndroidBitmapInfo* info, void* pixels){
    int counter;
    int red, green, blue;
    uint32_t* line;
    uint32_t* prevLine;
    uint32_t* nextLine;

        for (int yy = 0; yy < info->height; yy++)
        {
            line = (uint32_t*)pixels;
            if (yy - 1 >= 0)
                prevLine = (uint32_t*)((char*)pixels - info->stride);
            for (int xx = 0; xx < info->width; xx++)
            {
                if (yy < info->height)
                    nextLine = (uint32_t*)((char*)pixels + info->stride);
                //extract the RGB values from the pixel
                red = (int) ((line[xx] & 0x00FF0000) >> 16);
                green = (int)((line[xx] & 0x0000FF00) >> 8);
                blue = (int) (line[xx] & 0x00000FF );
                counter=0;

                if (yy + 1 < info->height && xx - 1 >= 0)
                {
                    red += red + ((nextLine[xx - 1]& 0x00FF0000) >> 16);
                    green += green + ((nextLine[xx - 1]& 0x0000FF00) >> 8);
                    blue += blue + (nextLine[xx - 1] & 0x000000FF);
                    counter++;
                }
                if (xx + 1 < info->width)
                {
                    red += red + ((line[xx + 1]& 0x00FF0000) >> 16);
                    green += green + ((line[xx + 1]& 0x0000FF00) >> 8);
                    blue += blue + (line[xx + 1] & 0x000000FF);
                    counter++;
                }
                if (yy + 1 < info->height && xx + 1 < info->width)
                {
                    red += red + ((nextLine[xx + 1]& 0x00FF0000) >> 16);
                    green += green + ((nextLine[xx + 1]& 0x0000FF00) >> 8);
                    blue += blue + (nextLine[xx + 1] & 0x000000FF);
                    counter++;
                }
                if (yy + 1 < info->height)
                {
                    red += red + ((nextLine[xx]& 0x00FF0000) >> 16);
                    green += green + ((nextLine[xx]& 0x0000FF00) >> 8);
                    blue += blue + (nextLine[xx] & 0x000000FF);
                    counter++;
                }
                if (xx - 1 >= 0)
                {
                    red += red + ((line[xx - 1]& 0x00FF0000) >> 16);
                    green += green + ((line[xx - 1]& 0x0000FF00) >> 8);
                    blue += blue + (line[xx - 1] & 0x000000FF);
                    counter++;
                }
                if (yy - 1 >= 0)
                {
                    red += red + ((prevLine[xx]& 0x00FF0000) >> 16);
                    green += green + ((prevLine[xx]& 0x0000FF00) >> 8);
                    blue += blue + (prevLine[xx] & 0x000000FF);
                    counter++;
                }
                line[xx] =
                        (((red/counter) << 16) & 0x00FF0000) |
                        (((green/counter) << 8) & 0x0000FF00) |
                        ((blue/counter) & 0x000000FF) | 0xFF000000;
            }
            pixels = (char*)pixels + info->stride;
        }
}



extern "C"
JNIEXPORT void JNICALL
Java_com_example_drawingapp_DrawingViewModel_staticImage(JNIEnv *env, jobject thiz, jobject bitmap) {
    AndroidBitmapInfo  info;
    int ret;
    void* pixels;
    LOGE("Before first if in cpp");
    if ((ret = AndroidBitmap_getInfo(env, bitmap, &info)) < 0) {
        LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
        return;
    }
    if (info.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
        LOGE("Bitmap format is not RGBA_8888 !");
        return;
    }

    if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixels)) < 0) {
        LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
    }

    noise(&info, pixels);

    AndroidBitmap_unlockPixels(env, bitmap);
}