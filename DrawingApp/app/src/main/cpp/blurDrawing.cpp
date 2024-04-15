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

static void brightness(AndroidBitmapInfo* info, void* pixels, float brightnessValue){
    int xx, yy;
    const float c = 1.5f;
    int red, green, blue = 0;
    uint32_t* line;

//    for(yy = 0; yy < info->height; yy++){
//        line = (uint32_t*)pixels;
//        for(xx =0; xx < info->width; xx++){
//
//            //extract the RGB values from the pixel
//            red = (int) ((line[xx] & 0x00FF0000) >> 16);
//            green = (int)((line[xx] & 0x0000FF00) >> 8);
//            blue = (int) (line[xx] & 0x00000FF );
//
//            //manipulate each value
//            red = rgb_clamp((int)(red * brightnessValue));
//            green = rgb_clamp((int)(green * brightnessValue));
//            blue = rgb_clamp((int)(blue * brightnessValue));
//
//            // set the new pixel back in
//            line[xx] =
//                    ((red << 16) & 0x00FF0000) |
//                    ((green << 8) & 0x0000FF00) |
//                    (blue & 0x000000FF);
//        }
//
//        pixels = (char*)pixels + info->stride;
//    }


    //Blurring inspired by : https://www.youtube.com/watch?v=tvVMLIIG9i0
    for(int y = 0; y < info->height; y++)
    {
        line = (uint32_t*)pixels;
        for(int x = 0; x < info->width; x++)
        {
            red = red + c * (line[3 * (x + y * info->width)] - red);
            green = green + c * (line[3 * (x + y * info->width) + 1] - green);
            blue = blue + c * (line[3 * (x + y * info->width) + 2] - blue);
            line[line[3 * (x + y * info->width)]] = red;
            line[line[3 * (x + y * info->width) + 1]] = green;
            line[line[3 * (x + y * info->width) + 2]] = blue;
        }
    }
}


extern "C" {
JNIEXPORT void JNICALL Java_com_vm_imageprocessingndkcpp_ImageActivity_brightness(JNIEnv * env, jobject  obj, jobject bitmap, jfloat brightnessValue)
{

    AndroidBitmapInfo  info;
    int ret;
    void* pixels;

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

    brightness(&info,pixels, brightnessValue);

    AndroidBitmap_unlockPixels(env, bitmap);
}
}