# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep javax.activation
-keep class javax.activation.** { *; }

# Keep XML-related classes
-keep class javax.xml.stream.** { *; }
-keep class org.codehaus.stax2.** { *; }
-keep class com.fasterxml.jackson.dataformat.xml.** { *; }
-keep class com.ctc.wstx.** { *; }

# Keep AWT Data Transfer
-keep class java.awt.datatransfer.** { *; }

#-keep class com.sunil.app.presentation.viewmodel.restful.RestfulViewModel { *; }
