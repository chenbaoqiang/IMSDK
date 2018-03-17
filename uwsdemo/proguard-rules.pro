# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn javax.swing.**
-dontwarn java.awt.**
-dontwarn org.springframework.**
-dontwarn demo.**
-dontwarn java.beans.**
-dontwarn java.awt.**
-dontwarn javax.swing.**
-dontwarn org.apache.**
-dontwarn com.amap.**
-dontwarn com.cmcc.**
-dontwarn com.chinamobile.**
-dontwarn com.sun.mail.**
-dontwarn ezvcard.**

#javamail

-dontwarn java.awt.**
-dontwarn java.beans.Beans
-dontwarn javax.security.**
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

##---------------Begin: proguard configuration for RcsSDK ----------
-keep class com.feinno.sdk.LogContent { *;}
-keep class org.keplerproject.luajava.** { *; }
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements android.os.Parcelable  { <fields>; }
-keepnames class * implements java.io.Serializable
##---------------End: proguard configuration for RcsSDK ----------

