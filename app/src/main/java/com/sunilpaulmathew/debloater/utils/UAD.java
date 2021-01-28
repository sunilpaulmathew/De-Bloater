package com.sunilpaulmathew.debloater.utils;

import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 26, 2021
 */

public class UAD {

    private static List<String> getGoogle() {
        List<String> mData = new ArrayList<>();
        mData.add("com.android.hotwordenrollment.okgoogle");
        mData.add("com.android.hotwordenrollment.xgoogle");
        mData.add("com.android.partnerbrowsercustomizations.chromeHomepage");
        mData.add("com.android.chrome");
        mData.add("com.chrome.beta");
        mData.add("com.chrome.canary");
        mData.add("com.chrome.dev");
        mData.add("com.google.android.apps.access.wifi.consumer");
        mData.add("com.google.android.apps.adm");
        mData.add("com.google.android.apps.ads.publisher");
        mData.add("com.google.android.apps.adwords");
        mData.add("com.google.android.apps.authenticator2");
        mData.add("com.google.android.apps.blogger");
        mData.add("com.google.android.apps.books");
        mData.add("com.google.android.apps.chromecast.app");
        mData.add("com.google.android.apps.cloudprint");
        mData.add("com.google.android.apps.cultural");
        mData.add("com.google.android.apps.currents");
        mData.add("com.google.android.apps.docs");
        mData.add("com.google.android.apps.docs.editors.docs");
        mData.add("com.google.android.apps.docs.editors.sheets");
        mData.add("com.google.android.apps.docs.editors.slides");
        mData.add("com.google.android.apps.dynamite");
        mData.add("com.google.android.apps.enterprise.cpanel");
        mData.add("com.google.android.apps.enterprise.dmagent");
        mData.add("com.google.android.apps.fireball");
        mData.add("com.google.android.apps.fitness");
        mData.add("com.google.android.apps.freighter");
        mData.add("com.google.android.apps.giant");
        mData.add("com.google.android.apps.googleassistant");
        mData.add("com.google.android.apps.handwriting.ime");
        mData.add("com.google.android.apps.hangoutsdialer");
        mData.add("com.google.android.apps.inbox");
        mData.add("com.google.android.apps.kids.familylink");
        mData.add("com.google.android.apps.kids.familylinkhelper");
        mData.add("com.google.android.apps.m4b");
        mData.add("com.google.android.apps.magazines");
        mData.add("com.google.android.apps.mapslite");
        mData.add("com.google.android.apps.meetings");
        mData.add("com.google.android.apps.messaging");
        mData.add("com.google.android.apps.navlite");
        mData.add("com.google.android.apps.nbu.files");
        mData.add("com.google.android.apps.paidtasks");
        mData.add("com.google.android.apps.pdfviewer");
        mData.add("com.google.android.apps.photos");
        mData.add("com.google.android.apps.photos.scanner");
        mData.add("com.google.android.apps.plus");
        mData.add("com.google.android.apps.podcasts");
        mData.add("com.google.android.apps.restore");
        mData.add("com.google.android.apps.recorder");
        mData.add("com.google.android.apps.setupwizard.searchselector");
        mData.add("com.google.android.apps.santatracker");
        mData.add("com.google.android.apps.subscriptions.red");
        mData.add("com.google.android.apps.tachyon");
        mData.add("com.google.android.apps.tasks");
        mData.add("com.google.android.apps.translate");
        mData.add("com.google.android.apps.travel.onthego");
        mData.add("com.google.android.apps.uploader");
        mData.add("com.google.android.apps.vega");
        mData.add("com.google.android.apps.walletnfcrel");
        mData.add("com.google.android.apps.wallpaper");
        mData.add("com.google.android.apps.wellbeing");
        mData.add("com.google.android.apps.youtube.creator");
        mData.add("com.google.android.apps.youtube.gaming");
        mData.add("com.google.android.apps.youtube.kids");
        mData.add("com.google.android.apps.youtube.music");
        mData.add("com.google.android.apps.youtube.vr");
        mData.add("com.google.android.backup");
        mData.add("com.google.android.backuptransport");
        mData.add("com.google.android.calculator");
        mData.add("com.google.android.calendar");
        mData.add("com.google.android.configupdater");
        mData.add("com.google.android.feedback");
        mData.add("com.google.android.googlequicksearchbox");
        mData.add("com.google.android.instantapps.supervisor");
        mData.add("com.google.android.keep");
        mData.add("com.google.android.markup");
        mData.add("com.google.android.marvin.talkback");
        mData.add("com.google.android.onetimeinitializer");
        mData.add("com.google.android.play.games");
        mData.add("com.google.android.printservice.recommendation");
        mData.add("com.google.android.projection.gearhead");
        mData.add("com.google.android.setupwizard");
        mData.add("com.google.android.setupwizard.a_overlay");
        mData.add("com.google.android.pixel.setupwizard");
        mData.add("com.google.android.soundpicker");
        mData.add("com.google.android.street");
        mData.add("com.google.android.syncadapters.bookmarks");
        mData.add("com.google.android.syncadapters.calendar");
        mData.add("com.google.android.syncadapters.contacts");
        mData.add("com.google.android.talk");
        mData.add("com.google.android.tts");
        mData.add("com.google.android.tv.remote");
        mData.add("com.google.android.videoeditor");
        mData.add("com.google.android.videos");
        mData.add("com.google.android.voicesearch");
        mData.add("com.google.android.vr.home");
        mData.add("com.google.android.vr.inputmethod");
        mData.add("com.google.android.wearable.app");
        mData.add("com.google.android.youtube");
        mData.add("com.google.ar.core");
        mData.add("com.google.ar.lens");
        mData.add("com.google.chromeremotedesktop");
        mData.add("com.google.earth");
        mData.add("com.google.marvin.talkback");
        mData.add("com.google.samples.apps.cardboarddemo");
        mData.add("com.google.tango.measure");
        mData.add("com.google.vr.cyclops");
        mData.add("com.google.vr.expeditions");
        mData.add("com.google.vr.vrcore");
        mData.add("com.google.zxing.client.android");
        return mData;
    }

    public static String getGoogleList(Context context) {
        StringBuilder mAppList = new StringBuilder();
        String[] google = getGoogle().toString().substring(1, getGoogle().toString().length() - 1).split(", ");
        for (String s : google) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                mAppList.append(PackageTasks.getAppName(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

    public static void enableGoogle(Context context) {
        PackageTasks.initializeModule();
        StringBuilder mAppList = new StringBuilder();
        String[] google = getGoogle().toString().substring(1, getGoogle().toString().length() - 1).split(", ");
        for (String s : google) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                PackageTasks.setToDelete(PackageTasks.getAPKPath(s, context), new File(PackageTasks.getAPKPath(s, context)).getName(), context);
                mAppList.append(PackageTasks.getAPKPath(s, context)).append("\n");
                Utils.create(mAppList.toString(), PackageTasks.getModulePath() + "/uad_google");
            }
        }
    }

    public static void disableGoogle() {
        try {
            for (String line : Utils.read(PackageTasks.getModulePath() + "/uad_google").split("\\r?\\n")) {
                if (line.startsWith("/system/") && Utils.exist(PackageTasks.getModulePath() + line)) {
                    PackageTasks.revertDelete(line);
                    Utils.delete(PackageTasks.getModulePath() + "/uad_google");
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static List<String> getOnePlus() {
        List<String> mData = new ArrayList<>();
        mData.add("cn.oneplus.photos");
        mData.add("com.example.wifirftest");
        mData.add("com.fingerprints.fingerprintsensortest");
        mData.add("com.oem.autotest");
        mData.add("com.oem.logkitsdservice");
        mData.add("com.oem.nfc");
        mData.add("com.oem.oemlogkit");
        mData.add("com.oneplus.backuprestore");
        mData.add("com.oneplus.brickmode");
        mData.add("com.oneplus.bttestmode");
        mData.add("com.oneplus.card");
        mData.add("com.oneplus.factorymode");
        mData.add("com.oneplus.factorymode.specialtest");
        mData.add("com.oneplus.gamespace");
        mData.add("com.oneplus.note");
        mData.add("com.oneplus.opbugreportlite");
        mData.add("com.oneplus.soundrecorder");
        mData.add("com.tencent.soter.soterserver");
        mData.add("com.wapi.wapicertmanage");
        mData.add("net.oneplus.commonlogtool");
        mData.add("net.oneplus.forums");
        mData.add("com.oneplus.opsports");
        mData.add("net.oneplus.odm");
        mData.add("net.oneplus.odm.provider");
        mData.add("net.oneplus.provider.appcategoryprovider");
        mData.add("net.oneplus.push");
        mData.add("net.oneplus.weather");
        mData.add("net.oneplus.widget");
        return mData;
    }

    public static String getOnePlusList(Context context) {
        StringBuilder mAppList = new StringBuilder();
        String[] OnePlus = getOnePlus().toString().substring(1, getOnePlus().toString().length() - 1).split(", ");
        for (String s : OnePlus) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                mAppList.append(PackageTasks.getAppName(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

    public static void enableOnePlus(Context context) {
        PackageTasks.initializeModule();
        StringBuilder mAppList = new StringBuilder();
        String[] OnePlus = getOnePlus().toString().substring(1, getOnePlus().toString().length() - 1).split(", ");
        for (String s : OnePlus) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                PackageTasks.setToDelete(PackageTasks.getAPKPath(s, context), new File(PackageTasks.getAPKPath(s, context)).getName(), context);
                mAppList.append(PackageTasks.getAPKPath(s, context)).append("\n");
                Utils.create(mAppList.toString(), PackageTasks.getModulePath() + "/uad_oneplus");
            }
        }
    }

    public static void disableOnePlus() {
        try {
            for (String line : Utils.read(PackageTasks.getModulePath() + "/uad_oneplus").split("\\r?\\n")) {
                if (line.startsWith("/system/") && Utils.exist(PackageTasks.getModulePath() + line)) {
                    PackageTasks.revertDelete(line);
                    Utils.delete(PackageTasks.getModulePath() + "/uad_oneplus");
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static List<String> getAOSP() {
        List<String> mData = new ArrayList<>();
        mData.add("com.android.bips");
        mData.add("com.android.bluetoothmidiservice");
        mData.add("com.android.bookmarkprovider");
        mData.add("com.android.carrierdefaultapp");
        mData.add("com.android.dreams.basic");
        mData.add("com.android.dreams.phototable");
        mData.add("com.android.dreams.phototable.overlay");
        mData.add("com.android.egg");
        mData.add("com.android.galaxy4");
        mData.add("com.android.htmlviewer");
        mData.add("com.android.magicsmoke");
        mData.add("com.android.managedprovisioning");
        mData.add("com.android.musicvis");
        mData.add("com.android.noisefield");
        mData.add("com.android.phasebeam");
        mData.add("com.android.email.partnerprovider");
        mData.add("com.android.email.partnerprovider.overlay");
        mData.add("com.android.providers.partnerbookmarks");
        mData.add("com.android.runintest.ddrtest");
        mData.add("com.android.simappdialog");
        mData.add("com.android.soundrecorder");
        mData.add("com.android.stk");
        mData.add("com.android.stk2");
        mData.add("com.android.traceur");
        mData.add("com.android.wallpaper.holospiral");
        mData.add("com.android.wallpaper.livepicker");
        mData.add("com.android.wallpaper.livepicker.overlay");
        mData.add("com.android.wallpapercropper");
        mData.add("com.example.android.notepad");
        mData.add("com.android.apps.tag");
        mData.add("com.android.browser");
        mData.add("com.android.browser.provider");
        return mData;
    }

    public static String getAOSPList(Context context) {
        StringBuilder mAppList = new StringBuilder();
        String[] OnePlus = getAOSP().toString().substring(1, getAOSP().toString().length() - 1).split(", ");
        for (String s : OnePlus) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                mAppList.append(PackageTasks.getAppName(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

    public static void enableAOSP(Context context) {
        PackageTasks.initializeModule();
        StringBuilder mAppList = new StringBuilder();
        String[] OnePlus = getAOSP().toString().substring(1, getAOSP().toString().length() - 1).split(", ");
        for (String s : OnePlus) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                PackageTasks.setToDelete(PackageTasks.getAPKPath(s, context), new File(PackageTasks.getAPKPath(s, context)).getName(), context);
                mAppList.append(PackageTasks.getAPKPath(s, context)).append("\n");
                Utils.create(mAppList.toString(), PackageTasks.getModulePath() + "/uad_aosp");
            }
        }
    }

    public static void disableAOSP() {
        try {
            for (String line : Utils.read(PackageTasks.getModulePath() + "/uad_aosp").split("\\r?\\n")) {
                if (line.startsWith("/system/") && Utils.exist(PackageTasks.getModulePath() + line)) {
                    PackageTasks.revertDelete(line);
                    Utils.delete(PackageTasks.getModulePath() + "/uad_aosp");
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static boolean isSystemApp(String apkPath) {
        return apkPath.startsWith("/system/app") || apkPath.startsWith("/system/priv-app")
                || apkPath.startsWith("/system/product/app") || apkPath.startsWith("/system/vendor/app")
                || apkPath.startsWith("/system/vendor/overlay") || apkPath.startsWith("/system/product/overlay");
    }

    public static void applyScript(String path, Activity activity) {
        String script = Utils.getString("setDefault", null, activity);
        if (Utils.exist(path)) {
            if (script.equals("aosp")) {
                disableAOSP();
            } else if (script.equals("oneplus")) {
                disableOnePlus();
            } else {
                disableGoogle();
            }
        } else {
            if (script.equals("aosp")) {
                enableAOSP(activity);
            } else if (script.equals("oneplus")) {
                enableOnePlus(activity);
            } else {
                enableGoogle(activity);
            }
        }
    }

}