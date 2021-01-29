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
        String[] apps = getGoogle().toString().substring(1, getGoogle().toString().length() - 1).split(", ");
        for (String s : apps) {
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
        String[] apps = getOnePlus().toString().substring(1, getOnePlus().toString().length() - 1).split(", ");
        for (String s : apps) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                mAppList.append(PackageTasks.getAppName(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

    public static void enableOnePlus(Context context) {
        PackageTasks.initializeModule();
        StringBuilder mAppList = new StringBuilder();
        String[] apps = getOnePlus().toString().substring(1, getOnePlus().toString().length() - 1).split(", ");
        for (String s : apps) {
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
        String[] apps = getAOSP().toString().substring(1, getAOSP().toString().length() - 1).split(", ");
        for (String s : apps) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                mAppList.append(PackageTasks.getAppName(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

    public static void enableAOSP(Context context) {
        PackageTasks.initializeModule();
        StringBuilder mAppList = new StringBuilder();
        String[] apps = getAOSP().toString().substring(1, getAOSP().toString().length() - 1).split(", ");
        for (String s : apps) {
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

    private static List<String> getASUS() {
        List<String> mData = new ArrayList<>();
        mData.add("com.asus.calculator");
        mData.add("com.asus.ia.asusapp");
        mData.add("com.asus.soundrecorder");
        return mData;
    }

    public static String getASUSList(Context context) {
        StringBuilder mAppList = new StringBuilder();
        String[] apps = getASUS().toString().substring(1, getASUS().toString().length() - 1).split(", ");
        for (String s : apps) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                mAppList.append(PackageTasks.getAppName(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

    public static void enableASUS(Context context) {
        PackageTasks.initializeModule();
        StringBuilder mAppList = new StringBuilder();
        String[] apps = getASUS().toString().substring(1, getASUS().toString().length() - 1).split(", ");
        for (String s : apps) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                PackageTasks.setToDelete(PackageTasks.getAPKPath(s, context), new File(PackageTasks.getAPKPath(s, context)).getName(), context);
                mAppList.append(PackageTasks.getAPKPath(s, context)).append("\n");
                Utils.create(mAppList.toString(), PackageTasks.getModulePath() + "/uad_asus");
            }
        }
    }

    public static void disableASUS() {
        try {
            for (String line : Utils.read(PackageTasks.getModulePath() + "/uad_asus").split("\\r?\\n")) {
                if (line.startsWith("/system/") && Utils.exist(PackageTasks.getModulePath() + line)) {
                    PackageTasks.revertDelete(line);
                    Utils.delete(PackageTasks.getModulePath() + "/uad_asus");
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static List<String> getCarrier() {
        List<String> mData = new ArrayList<>();
        mData.add("com.mobitv.client.tmobiletvhd");
        mData.add("com.tmobile.pr.adapt");
        mData.add("com.tmobile.pr.mytmobile");
        mData.add("com.tmobile.services.nameid");
        mData.add("com.tmobile.simlock");
        mData.add("com.tmobile.vvm.application");
        mData.add("com.whitepages.nameid.tmobile");
        mData.add("us.com.dt.iq.appsource.tmobile");
        mData.add("com.asurion.android.verizon.vms");
        mData.add("com.customermobile.preload.vzw");
        mData.add("com.LogiaGroup.LogiaDeck");
        mData.add("com.motricity.verizon.ssodownloadable");
        mData.add("com.securityandprivacy.android.verizon.vms");
        mData.add("com.telecomsys.directedsms.android.SCg");
        mData.add("com.vcast.mediamanager");
        mData.add("com.verizon.llkagent");
        mData.add("com.verizon.loginengine.unbranded");
        mData.add("com.verizon.messaging.vzmsgs");
        mData.add("com.verizon.mips.services");
        mData.add("com.verizon.obdm");
        mData.add("com.verizon.obdm_permissions");
        mData.add("com.verizon.permissions.appdirectedsms");
        mData.add("com.verizon.permissions.vzwappapn");
        mData.add("com.verizon.vzwavs");
        mData.add("com.verizontelematics.verizonhum");
        mData.add("com.vznavigator.Generic");
        mData.add("com.vzw.apnlib");
        mData.add("com.vzw.apnservice");
        mData.add("com.vzw.ecid");
        mData.add("com.vzw.hss.myverizon");
        mData.add("com.vzw.hss.widgets.infozone.large");
        mData.add("com.vzw.qualitydatalog");
        mData.add("com.motorola.mot5gmod");
        mData.add("com.motorola.vzw.mot5gmod");
        mData.add("com.motorola.vzw.pco.extensions.pcoreceiver");
        mData.add("com.motorola.vzw.phone.extensions");
        mData.add("com.motorola.vzw.cloudsetup");
        mData.add("com.motorola.ltebroadcastservices_vzw");
        mData.add("com.motorola.vzw.loader");
        mData.add("com.motorola.omadm.vzw");
        mData.add("com.motorola.vzw.provider");
        mData.add("com.motorola.visualvoicemail");
        mData.add("com.android.sprint.hiddenmenuapp");
        mData.add("com.asurion.home.sprint.vpl");
        mData.add("com.asurion.android.mobilerecovery.sprint.vpl");
        mData.add("com.asurion.android.mobilerecovery.sprint");
        mData.add("com.asurion.home.sprint");
        mData.add("com.hyperlync.Sprint.Backup");
        mData.add("com.hyperlync.Sprint.CloudBinder");
        mData.add("com.locationlabs.finder.sprint.vpl");
        mData.add("com.locationlabs.finder.sprint");
        mData.add("com.mobitv.client.sprinttvng");
        mData.add("com.mobolize.sprint.securewifi");
        mData.add("com.motorola.omadm.sprint");
        mData.add("com.sprint.android.musicplus2033");
        mData.add("com.sprint.android.musicplus2033.vpl");
        mData.add("com.sprint.ecid");
        mData.add("com.sprint.care");
        mData.add("com.sprint.ce.updater");
        mData.add("com.sprint.fng");
        mData.add("com.sprint.international.message");
        mData.add("com.sprint.psdg.sw");
        mData.add("com.sprint.ms.cdm");
        mData.add("com.sprint.ms.cnap");
        mData.add("com.sprint.safefound");
        mData.add("com.sprint.safefound.vpl");
        mData.add("com.sprint.topup");
        mData.add("com.sprint.w.installer");
        mData.add("com.sprint.w.v8");
        mData.add("com.aetherpal.attdh.se");
        mData.add("com.aetherpal.attdh.zte");
        mData.add("net.aetherpal.device");
        mData.add("com.asurion.android.mobilerecovery.att");
        mData.add("com.asurion.android.protech.att");
        mData.add("com.att.android.attsmartwifi");
        mData.add("com.att.callprotect");
        mData.add("com.att.dh");
        mData.add("com.att.dtv.shaderemote");
        mData.add("com.att.iqi");
        mData.add("com.att.mobiletransfer");
        mData.add("com.att.myWireless");
        mData.add("com.att.mobilesecurity");
        mData.add("com.att.mobile.android.vvm");
        mData.add("com.att.tv");
        mData.add("com.att.tv.watchtv");
        mData.add("com.dti.att");
        mData.add("com.locationlabs.cni.att");
        mData.add("com.matchboxmobile.wisp");
        mData.add("com.motorola.att.phone.extensions");
        mData.add("com.motorola.attvowifi");
        mData.add("com.wavemarket.waplauncher");
        mData.add("com.samsung.attvvm");
        mData.add("com.synchronoss.dcs.att.r2g");
        mData.add("com.orange.aura.oobe");
        mData.add("com.orange.mail.fr");
        mData.add("com.orange.miorange");
        mData.add("com.orange.mylivebox.fr");
        mData.add("com.orange.mysosh");
        mData.add("com.orange.orangeetmoi");
        mData.add("com.orange.owtv");
        mData.add("com.orange.tdd");
        mData.add("com.orange.update");
        mData.add("com.orange.update.OrangeUpdateApplication");
        mData.add("com.orange.vvm");
        mData.add("com.orange.wifiorange");
        mData.add("fr.orange.cineday");
        mData.add("com.sfr.android.moncompte");
        mData.add("com.sfr.android.sfrcloud");
        mData.add("com.sfr.android.sfrmail");
        mData.add("com.sfr.android.sfrplay");
        mData.add("com.sfr.android.vvm");
        mData.add("de.telekom.tsc");
        return mData;
    }

    public static String getCarrierList(Context context) {
        StringBuilder mAppList = new StringBuilder();
        String[] apps = getCarrier().toString().substring(1, getCarrier().toString().length() - 1).split(", ");
        for (String s : apps) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                mAppList.append(PackageTasks.getAppName(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

    public static void enableCarrier(Context context) {
        PackageTasks.initializeModule();
        StringBuilder mAppList = new StringBuilder();
        String[] apps = getCarrier().toString().substring(1, getCarrier().toString().length() - 1).split(", ");
        for (String s : apps) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                PackageTasks.setToDelete(PackageTasks.getAPKPath(s, context), new File(PackageTasks.getAPKPath(s, context)).getName(), context);
                mAppList.append(PackageTasks.getAPKPath(s, context)).append("\n");
                Utils.create(mAppList.toString(), PackageTasks.getModulePath() + "/uad_carrier");
            }
        }
    }

    public static void disableCarrier() {
        try {
            for (String line : Utils.read(PackageTasks.getModulePath() + "/uad_carrier").split("\\r?\\n")) {
                if (line.startsWith("/system/") && Utils.exist(PackageTasks.getModulePath() + line)) {
                    PackageTasks.revertDelete(line);
                    Utils.delete(PackageTasks.getModulePath() + "/uad_carrier");
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static List<String> getHuawei() {
        List<String> mData = new ArrayList<>();
        mData.add("com.android.keyguard");
        mData.add("com.android.hwmirror");
        mData.add("com.baidu.input_huawei");
        mData.add("com.hicloud.android.clone");
        mData.add("com.huawei.android.chr");
        mData.add("com.huawei.android.FloatTasks");
        mData.add("com.huawei.android.hsf");
        mData.add("com.huawei.android.hwpay");
        mData.add("com.huawei.android.instantonline");
        mData.add("com.huawei.android.instantshare");
        mData.add("com.huawei.android.karaoke");
        mData.add("com.huawei.android.mirrorshare");
        mData.add("com.huawei.android.pushagent");
        mData.add("com.huawei.android.remotecontroller");
        mData.add("com.huawei.android.tips");
        mData.add("com.huawei.android.totemweather");
        mData.add("com.huawei.android.totemweatherapp");
        mData.add("com.huawei.android.totemweatherwidget");
        mData.add("com.huawei.android.wfdft");
        mData.add("com.huawei.android.wfdirect");
        mData.add("com.huawei.appmarket");
        mData.add("com.huawei.arengine.service");
        mData.add("com.huawei.bd");
        mData.add("com.huawei.bluetooth");
        mData.add("com.huawei.browser");
        mData.add("com.huawei.browserhomepage");
        mData.add("com.huawei.compass");
        mData.add("com.huawei.contactscamcard");
        mData.add("com.huawei.contacts.sync");
        mData.add("com.huawei.desktop.explorer");
        mData.add("com.huawei.email");
        mData.add("com.huawei.fido.uafclient");
        mData.add("com.huawei.game.kitserver");
        mData.add("com.huawei.gameassistant");
        mData.add("com.huawei.geofence");
        mData.add("com.huawei.hwsearch");
        mData.add("com.huawei.hwid");
        mData.add("com.huawei.hiaction");
        mData.add("com.huawei.hiai");
        mData.add("com.huawei.hiassistantoversea");
        mData.add("com.huawei.hicard");
        mData.add("com.huawei.hicloud");
        mData.add("com.huawei.hifolder");
        mData.add("com.huawei.himovie.overseas");
        mData.add("com.huawei.hitouch");
        mData.add("com.huawei.hwasm");
        mData.add("com.huawei.hwblockchain");
        mData.add("com.huawei.hwdetectrepair");
        mData.add("com.huawei.hwstartupguide");
        mData.add("com.huawei.hwvoipservice");
        mData.add("com.huawei.iaware");
        mData.add("com.huawei.ihealth");
        mData.add("com.huawei.intelligent");
        mData.add("com.huawei.health");
        mData.add("com.huawei.livewallpaper.paradise");
        mData.add("com.huawei.livewallpaper.artflower");
        mData.add("com.huawei.livewallpaper.flowersbloom");
        mData.add("com.huawei.livewallpaper.mountaincloud");
        mData.add("com.huawei.livewallpaper.naturalgarden");
        mData.add("com.huawei.livewallpaper.ripplestone");
        mData.add("com.huawei.magazine");
        mData.add("com.huawei.mirror");
        mData.add("com.huawei.mirrorlink");
        mData.add("com.huawei.music");
        mData.add("com.huawei.parentcontrol");
        mData.add("com.huawei.pcassistant");
        mData.add("com.huawei.phoneservice");
        mData.add("com.huawei.scanner");
        mData.add("com.huawei.stylus.floatmenu");
        mData.add("com.huawei.synergy");
        mData.add("com.huawei.tips");
        mData.add("com.huawei.trustagent");
        mData.add("com.huawei.vassistant");
        mData.add("com.huawei.videoeditor");
        mData.add("com.huawei.vassistant");
        mData.add("com.huawei.wallet");
        mData.add("com.huawei.watch.sync");
        mData.add("com.iflytek.speechsuite");
        mData.add("com.nuance.swype.emui");
        return mData;
    }

    public static String getHuaweiList(Context context) {
        StringBuilder mAppList = new StringBuilder();
        String[] apps = getHuawei().toString().substring(1, getHuawei().toString().length() - 1).split(", ");
        for (String s : apps) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                mAppList.append(PackageTasks.getAppName(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

    public static void enableHuawei(Context context) {
        PackageTasks.initializeModule();
        StringBuilder mAppList = new StringBuilder();
        String[] apps = getHuawei().toString().substring(1, getHuawei().toString().length() - 1).split(", ");
        for (String s : apps) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                PackageTasks.setToDelete(PackageTasks.getAPKPath(s, context), new File(PackageTasks.getAPKPath(s, context)).getName(), context);
                mAppList.append(PackageTasks.getAPKPath(s, context)).append("\n");
                Utils.create(mAppList.toString(), PackageTasks.getModulePath() + "/uad_huawei");
            }
        }
    }

    public static void disableHuawei() {
        try {
            for (String line : Utils.read(PackageTasks.getModulePath() + "/uad_huawei").split("\\r?\\n")) {
                if (line.startsWith("/system/") && Utils.exist(PackageTasks.getModulePath() + line)) {
                    PackageTasks.revertDelete(line);
                    Utils.delete(PackageTasks.getModulePath() + "/uad_huawei");
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static List<String> getLG() {
        List<String> mData = new ArrayList<>();
        mData.add("com.android.LGSetupWizard");
        mData.add("com.lge.appbox.client");
        mData.add("com.lge.bnr");
        mData.add("com.lge.cic.eden.service");
        mData.add("com.lge.cloudhub");
        mData.add("com.lge.drmservice");
        mData.add("com.lge.easyhome");
        mData.add("com.lge.eltest");
        mData.add("com.lge.email");
        mData.add("com.lge.eula");
        mData.add("com.lge.eulaprovider");
        mData.add("com.lge.fmradio");
        mData.add("com.lge.friendsmanager");
        mData.add("com.lge.gallery.collagewallpaper");
        mData.add("com.lge.gallery.vr.wallpaper");
        mData.add("com.lge.gcuv");
        mData.add("com.lge.gestureanswering");
        mData.add("com.lge.gnss.airtest");
        mData.add("com.lge.gnsslogcat");
        mData.add("com.lge.gnsspostest");
        mData.add("com.lge.gnsstest");
        mData.add("com.lge.hifirecorder");
        mData.add("com.lge.hotspotlauncher");
        mData.add("com.lge.ia.task.incalagent");
        mData.add("com.lge.ia.task.smartcare");
        mData.add("com.lge.ia.task.smartsetting");
        mData.add("com.lge.iftttmanager");
        mData.add("com.lge.ime.solution.handwriting");
        mData.add("com.lge.ime.solution.text");
        mData.add("com.lge.launcher2.theme.optimus");
        mData.add("com.lge.leccp");
        mData.add("com.lge.lgaccount");
        mData.add("com.lge.lgdrm.permission");
        mData.add("com.lge.lginstallservies");
        mData.add("com.lge.lgmapui");
        mData.add("com.lge.lgsetupview");
        mData.add("com.lge.LGSetupView");
        mData.add("com.lge.lgworld");
        mData.add("com.lge.lifetracker");
        mData.add("com.lge.mirrorlink");
        mData.add("com.lge.mlt");
        mData.add("com.lge.mtalk.sf");
        mData.add("com.lge.musicshare");
        mData.add("com.lge.myplace");
        mData.add("com.lge.myplace.engine");
        mData.add("com.lge.phonemanagement");
        mData.add("com.lge.privacylock");
        mData.add("com.lge.qhelp");
        mData.add("com.lge.qhelp.application");
        mData.add("com.lge.qmemoplus");
        mData.add("com.lge.remote.lgairdrive");
        mData.add("com.lge.remote.setting");
        mData.add("com.lge.sizechangable.musicwidget.widget");
        mData.add("com.lge.sizechangable.weather");
        mData.add("com.lge.sizechangable.weather.platform");
        mData.add("com.lge.sizechangable.weather.theme.optimus");
        mData.add("com.lge.smartdoctor.webview");
        mData.add("com.lge.smartshare");
        mData.add("com.lge.smartshare.provider");
        mData.add("com.lge.smartsharepush");
        mData.add("com.lge.snappage");
        mData.add("com.lge.springcleaning");
        mData.add("com.lge.sync");
        mData.add("com.lge.video.vr.wallpaper");
        mData.add("com.lge.videoplayer");
        mData.add("com.lge.videostudio");
        mData.add("com.lge.voicecare");
        mData.add("com.lge.vrplayer");
        mData.add("com.lge.wernicke");
        mData.add("com.lge.wernicke.nlp");
        mData.add("com.lge.wfd.spmirroring.source");
        mData.add("com.lge.wfds.service.v3");
        mData.add("com.lge.wifi.p2p");
        mData.add("com.lge.wifihotspotwidget");
        mData.add("com.lge.appwidget.dualsimstatus");
        mData.add("com.lge.hiddenpersomenu");
        mData.add("com.lge.hiddenmenu");
        mData.add("com.lge.operator.hiddenmenu");
        mData.add("com.lge.servicemenu");
        mData.add("com.rsupport.rs.activity.lge.allinone");
        mData.add("com.lge.laot");
        mData.add("com.lge.lgfmservice");
        mData.add("com.lge.bnr.launcher");
        mData.add("com.lge.homeselector");
        mData.add("com.lge.music");
        mData.add("com.lge.floatingbar");
        return mData;
    }

    public static String getLGList(Context context) {
        StringBuilder mAppList = new StringBuilder();
        String[] apps = getLG().toString().substring(1, getLG().toString().length() - 1).split(", ");
        for (String s : apps) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                mAppList.append(PackageTasks.getAppName(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

    public static void enableLG(Context context) {
        PackageTasks.initializeModule();
        StringBuilder mAppList = new StringBuilder();
        String[] apps = getLG().toString().substring(1, getLG().toString().length() - 1).split(", ");
        for (String s : apps) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                PackageTasks.setToDelete(PackageTasks.getAPKPath(s, context), new File(PackageTasks.getAPKPath(s, context)).getName(), context);
                mAppList.append(PackageTasks.getAPKPath(s, context)).append("\n");
                Utils.create(mAppList.toString(), PackageTasks.getModulePath() + "/uad_lg");
            }
        }
    }

    public static void disableLG() {
        try {
            for (String line : Utils.read(PackageTasks.getModulePath() + "/uad_lg").split("\\r?\\n")) {
                if (line.startsWith("/system/") && Utils.exist(PackageTasks.getModulePath() + line)) {
                    PackageTasks.revertDelete(line);
                    Utils.delete(PackageTasks.getModulePath() + "/uad_lg");
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static List<String> getSamsung() {
        List<String> mData = new ArrayList<>();
        mData.add("android.autoinstalls.config.samsung");
        mData.add("com.aura.oobe.samsung");
        mData.add("com.cnn.mobile.android.phone.edgepanel");
        mData.add("com.enhance.gameservice");
        mData.add("com.hiya.star");
        mData.add("com.knox.vpn.proxyhandler");
        mData.add("com.mygalaxy");
        mData.add("com.mobeam.barcodeService");
        mData.add("com.samsung.app.jansky");
        mData.add("com.samsung.aasaservice");
        mData.add("com.samsung.accessory");
        mData.add("com.samsung.accessory.beansmgr");
        mData.add("com.samsung.accessory.safiletransfer");
        mData.add("com.samsung.android.aircommandmanager");
        mData.add("com.samsung.android.airtel.stubapp");
        mData.add("com.samsung.android.allshare.service.mediashare");
        mData.add("com.samsung.android.app.accesscontrol");
        mData.add("com.samsung.android.app.advsounddetector");
        mData.add("com.samsung.android.app.appsedge");
        mData.add("com.samsung.android.app.assistantmenu");
        mData.add("com.samsung.android.app.camera.sticker.facear.preload");
        mData.add("com.samsung.android.app.camera.sticker.facearframe.preload");
        mData.add("com.samsung.android.app.camera.sticker.facearavatar.preload");
        mData.add("com.samsung.android.app.clipboardedge");
        mData.add("com.samsung.android.app.cocktailbarservice");
        mData.add("com.samsung.android.app.color");
        mData.add("com.samsung.android.app.dressroom");
        mData.add("com.samsung.android.app.episodes");
        mData.add("com.samsung.android.app.filterinstaller");
        mData.add("com.samsung.app.highlightplayer");
        mData.add("com.samsung.android.app.interactivepanoramaviewer");
        mData.add("com.samsung.android.app.ledbackcover");
        mData.add("com.samsung.android.app.ledcoverdream");
        mData.add("com.samsung.android.app.omcagent");
        mData.add("com.samsung.android.app.memo");
        mData.add("com.samsung.android.app.mhswrappertmo");
        mData.add("com.samsung.android.app.mirrorlink");
        mData.add("com.samsung.android.app.news");
        mData.add("com.samsung.android.app.notes");
        mData.add("com.samsung.android.app.panel.naver.v");
        mData.add("com.samsung.android.app.pinboard");
        mData.add("com.samsung.android.app.reminder");
        mData.add("com.samsung.android.app.routines");
        mData.add("com.samsung.android.app.sbrowseredge");
        mData.add("com.samsung.android.app.settings.bixby");
        mData.add("com.samsung.android.app.simplesharing");
        mData.add("com.samsung.android.app.social");
        mData.add("com.samsung.android.app.spage");
        mData.add("com.samsung.android.app.storyalbumwidget");
        mData.add("com.samsung.android.app.talkback");
        mData.add("com.samsung.android.app.taskedge");
        mData.add("com.samsung.android.app.tips");
        mData.add("com.samsung.android.app.vrsetupwizards");
        mData.add("com.samsung.android.app.vrsetupwizardstub");
        mData.add("com.samsung.android.app.watchmanager");
        mData.add("com.samsung.android.app.watchmanagerstub");
        mData.add("com.samsung.android.app.withtv");
        mData.add("com.samsung.android.ardrawing");
        mData.add("com.samsung.android.arzone");
        mData.add("com.samsung.android.aremoji");
        mData.add("com.samsung.android.aremojieditor");
        mData.add("com.samsung.android.asksmanager");
        mData.add("com.samsung.android.authfw");
        mData.add("com.samsung.android.bbc.bbcagent");
        mData.add("com.samsung.android.bbc.fileprovider");
        mData.add("com.samsung.android.beaconmanager");
        mData.add("com.samsung.android.bixby.agent");
        mData.add("com.samsung.android.bixby.agent.dummy");
        mData.add("com.samsung.android.bixby.es.globalaction");
        mData.add("com.samsung.android.bixby.plmsync");
        mData.add("com.samsung.android.bixby.service");
        mData.add("com.samsung.android.bixby.voiceinput");
        mData.add("com.samsung.android.bixby.wakeup");
        mData.add("com.samsung.android.bixbyvision.framework");
        mData.add("com.samsung.android.calendar");
        mData.add("com.samsung.android.coreapps");
        mData.add("com.samsung.android.da.daagent");
        mData.add("com.samsung.android.dlp.service");
        mData.add("com.samsung.android.dqagent");
        mData.add("com.samsung.android.drivelink.stub");
        mData.add("com.samsung.android.dynamiclock");
        mData.add("com.samsung.android.gearoplugin");
        mData.add("com.samsung.android.easysetup");
        mData.add("com.samsung.android.email.provider");
        mData.add("com.samsung.android.emojiupdater");
        mData.add("com.samsung.android.fast");
        mData.add("com.samsung.android.fmm");
        mData.add("com.samsung.android.game.gamehome");
        mData.add("com.samsung.android.game.gametools");
        mData.add("com.samsung.android.game.gos");
        mData.add("com.samsung.android.gametuner.thin");
        mData.add("com.samsung.android.hmt.vrshell");
        mData.add("com.samsung.android.hmt.vrsvc");
        mData.add("com.samsung.android.intelligenceservice2");
        mData.add("com.samsung.android.keyguardwallpaperupdator");
        mData.add("com.samsung.android.kidsinstaller");
        mData.add("com.samsung.android.knox.attestation");
        mData.add("com.samsung.android.knox.containeragent");
        mData.add("com.samsung.android.knox.containercore");
        mData.add("com.samsung.android.knox.containerdesktop");
        mData.add("com.samsung.android.livestickers");
        mData.add("com.samsung.android.location");
        mData.add("com.samsung.android.mateagent");
        mData.add("com.samsung.android.mdecservice");
        mData.add("com.samsung.android.mdm");
        mData.add("com.samsung.android.mdx");
        mData.add("com.samsung.android.mobileservice");
        mData.add("com.samsung.android.net.wifi.wifiguider");
        mData.add("com.samsung.android.networkdiagnostic");
        mData.add("com.samsung.android.oneconnect");
        mData.add("com.samsung.android.personalpage.service");
        mData.add("com.samsung.android.providers.context");
        mData.add("com.samsung.android.rubin.app");
        mData.add("com.samsung.android.samsungpass");
        mData.add("com.samsung.android.samsungpassautofill");
        mData.add("com.samsung.android.samsungpositioning");
        mData.add("com.samsung.android.sdk.handwriting");
        mData.add("com.samsung.android.sdk.professionalaudio.utility.jammonitor");
        mData.add("com.samsung.android.sdk.professionalaudio.app.audioconnectionservice");
        mData.add("com.samsung.android.shortcutbackupservice");
        mData.add("com.samsung.android.slinkcloud");
        mData.add("com.samsung.android.smartface");
        mData.add("com.samsung.android.smartswitchassistant");
        mData.add("com.samsung.android.stickerplugin");
        mData.add("com.samsung.android.sm");
        mData.add("com.samsung.android.scloud");
        mData.add("com.samsung.android.scloud.auth");
        mData.add("com.samsung.android.scloud.sync");
        mData.add("com.samsung.android.sconnect");
        mData.add("com.samsung.android.securitylogagent");
        mData.add("com.samsung.android.service.livedrawing");
        mData.add("com.samsung.android.mfi");
        mData.add("com.samsung.android.service.peoplestripe");
        mData.add("com.samsung.android.service.travel");
        mData.add("com.samsung.android.smartcallprovider");
        mData.add("com.samsung.android.smartmirroring");
        mData.add("com.samsung.android.spayfw");
        mData.add("com.samsung.android.spay");
        mData.add("com.samsung.android.spaymini");
        mData.add("com.samsung.android.spdfnote");
        mData.add("com.samsung.android.stickercenter");
        mData.add("com.samsung.android.ststub");
        mData.add("com.samsung.android.svcagent");
        mData.add("com.samsung.android.svoice");
        mData.add("com.samsung.android.svoiceime");
        mData.add("com.samsung.android.themecenter");
        mData.add("com.samsung.android.themestore");
        mData.add("com.samsung.android.tripwidget");
        mData.add("com.samsung.android.unifiedprofile");
        mData.add("com.samsung.android.universalswitch");
        mData.add("com.samsung.android.visionarapps");
        mData.add("com.samsung.android.visioncloudagent");
        mData.add("com.samsung.android.visionintelligence");
        mData.add("com.samsung.android.voc");
        mData.add("com.samsung.android.voicewakeup");
        mData.add("com.samsung.android.weather");
        mData.add("com.samsung.android.wellbeing");
        mData.add("com.samsung.android.widgetapp.yahooedge.finance");
        mData.add("com.samsung.android.widgetapp.yahooedge.sport");
        mData.add("com.samsung.crane");
        mData.add("com.samsung.daydream.customization");
        mData.add("com.samsung.desktopsystemui");
        mData.add("com.samsung.ecomm");
        mData.add("com.samsung.enhanceservice");
        mData.add("com.samsung.faceservice");
        mData.add("com.samsung.fresco.logging");
        mData.add("com.samsung.groupcast");
        mData.add("com.samsung.helphub");
        mData.add("com.samsung.hiddennetworksetting");
        mData.add("com.samsung.ipservice");
        mData.add("com.samsung.klmsagent");
        mData.add("com.samsung.android.knox.analytics.uploader");
        mData.add("com.samsung.knox.knoxtrustagent");
        mData.add("com.samsung.knox.kss");
        mData.add("com.samsung.knox.securefolder");
        mData.add("com.samsung.knox.securefolder.setuppage");
        mData.add("com.samsung.logwriter");
        mData.add("com.samsung.mdl.radio");
        mData.add("com.samsung.mlp");
        mData.add("com.samsung.mdl.radio.radiostub");
        mData.add("com.samsung.oh");
        mData.add("com.samsung.rcs");
        mData.add("com.samsung.safetyinformation");
        mData.add("com.samsung.SMt");
        mData.add("com.samsung.storyservice");
        mData.add("com.samsung.svoice.sync");
        mData.add("com.samsung.systemui.bixby");
        mData.add("com.samsung.systemui.bixby2");
        mData.add("com.samsung.tmovvm");
        mData.add("com.samsung.ucs.agent.boot");
        mData.add("com.samsung.ucs.agent.ese");
        mData.add("com.samsung.visionprovider");
        mData.add("com.samsung.voiceserviceplatform");
        mData.add("com.samsung.vvm");
        mData.add("com.samsung.vvm.se");
        mData.add("com.sec.allsharecastplayer");
        mData.add("com.sec.android.app.apex");
        mData.add("com.sec.android.app.applinker");
        mData.add("com.sec.android.app.billing");
        mData.add("com.sec.android.app.bluetoothtest");
        mData.add("com.sec.android.app.chromecustomizations");
        mData.add("com.sec.android.app.DataCreate");
        mData.add("com.sec.android.app.desktoplauncher");
        mData.add("com.sec.android.diagmonagent");
        mData.add("com.sec.android.app.dictionary");
        mData.add("com.sec.android.app.easysetup");
        mData.add("com.sec.android.app.factorykeystring");
        mData.add("com.sec.android.app.gamehub");
        mData.add("com.sec.android.app.hwmoduletest");
        mData.add("com.sec.android.app.magnifier");
        mData.add("com.sec.android.app.mt");
        mData.add("com.sec.android.app.ocr");
        mData.add("com.sec.android.app.parser");
        mData.add("com.sec.android.app.quicktool");
        mData.add("com.sec.android.app.ringtoneBr");
        mData.add("com.sec.android.app.samsungapps");
        mData.add("com.sec.android.app.safetyassurance");
        mData.add("com.sec.android.app.sbrowser");
        mData.add("com.sec.android.app.scloud");
        mData.add("com.sec.android.app.SecSetupWizard");
        mData.add("com.sec.android.app.servicemodeapp");
        mData.add("com.sec.android.app.setupwizardlegalprovider");
        mData.add("com.sec.android.app.shealth");
        mData.add("com.sec.android.app.sns3");
        mData.add("com.sec.android.app.setupwizard");
        mData.add("com.sec.android.app.suwscriptplayer");
        mData.add("com.sec.android.app.sysscope");
        mData.add("com.sec.android.app.translator");
        mData.add("com.sec.android.app.voicenote");
        mData.add("com.sec.android.app.withtv");
        mData.add("com.sec.android.app.wlantest");
        mData.add("com.sec.android.AutoPreconfig");
        mData.add("com.sec.android.cover.ledcover");
        mData.add("com.sec.android.daemonapp");
        mData.add("com.sec.android.desktopcommunity");
        mData.add("com.sec.android.app.dexonpc");
        mData.add("com.sec.android.desktopmode.uiservice");
        mData.add("com.sec.android.easyMover");
        mData.add("com.sec.android.easyMover.Agent");
        mData.add("com.sec.android.easyonehand");
        mData.add("com.sec.android.fido.uaf.asm");
        mData.add("com.sec.android.fido.uaf.client");
        mData.add("com.sec.android.game.gamehome");
        mData.add("com.sec.android.mimage.avatarstickers");
        mData.add("com.sec.android.mimage.gear360editor");
        mData.add("com.sec.android.preloadinstaller");
        mData.add("com.sec.android.providers.security");
        mData.add("com.sec.android.provider.snote");
        mData.add("com.sec.android.RilServiceModeApp");
        mData.add("com.sec.android.service.health");
        mData.add("com.sec.android.sidesync30");
        mData.add("com.sec.android.splitsound");
        mData.add("com.sec.android.uibcvirtualsoftkey");
        mData.add("com.sec.android.widgetapp.diotek.smemo");
        mData.add("com.sec.android.widgetapp.easymodecontactswidget");
        mData.add("com.sec.android.widgetapp.samsungapps");
        mData.add("com.sec.android.widgetapp.webmanual");
        mData.add("com.sec.app.RilErrorNotifier");
        mData.add("com.sec.bcservice");
        mData.add("com.sec.downloadablekeystore");
        mData.add("com.sec.enterprise.knox.attestation");
        mData.add("com.sec.enterprise.knox.cloudmdm.smdms");
        mData.add("com.sec.enterprise.knox.shareddevice.keyguard");
        mData.add("com.sec.enterprise.mdm.services.simpin");
        mData.add("com.sec.enterprise.mdm.vpn");
        mData.add("com.sec.epdgtestapp");
        mData.add("com.sec.everglades");
        mData.add("com.sec.everglades.update");
        mData.add("com.sec.factory");
        mData.add("com.sec.factory.camera");
        mData.add("com.sec.factory.iris.usercamera");
        mData.add("com.sec.hiddenmenu");
        mData.add("com.sec.imslogger");
        mData.add("com.sec.kidsplat.installer");
        mData.add("com.sec.knox.bluetooth");
        mData.add("com.sec.knox.bridge");
        mData.add("com.sec.knox.containeragent2");
        mData.add("com.sec.knox.foldercontainer");
        mData.add("com.sec.knox.knoxsetupwizardclient");
        mData.add("com.sec.knox.packageverifier");
        mData.add("com.sec.knox.shortcutsms");
        mData.add("com.sec.knox.switcher");
        mData.add("com.sec.knox.switchknoxi");
        mData.add("com.sec.knox.switchknoxIi");
        mData.add("com.sec.location.nsflp2");
        mData.add("com.sec.mldapchecker");
        mData.add("com.sec.modem.settings");
        mData.add("com.sec.phone");
        mData.add("com.sec.readershub");
        mData.add("com.sec.smartcard.manager");
        mData.add("com.sec.spp.push");
        mData.add("com.sec.sve");
        mData.add("com.sec.yosemite.phone");
        mData.add("com.skms.android.agent");
        mData.add("com.sec.usbsettings");
        mData.add("com.monotype.android.font.samsungone");
        mData.add("com.vlingo.midas");
        mData.add("com.wsomacp");
        mData.add("com.wssnps");
        mData.add("tv.peel.samsung.app");
        return mData;
    }

    public static String getSamsungList(Context context) {
        StringBuilder mAppList = new StringBuilder();
        String[] apps = getSamsung().toString().substring(1, getSamsung().toString().length() - 1).split(", ");
        for (String s : apps) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                mAppList.append(PackageTasks.getAppName(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

    public static void enableSamsung(Context context) {
        PackageTasks.initializeModule();
        StringBuilder mAppList = new StringBuilder();
        String[] apps = getSamsung().toString().substring(1, getSamsung().toString().length() - 1).split(", ");
        for (String s : apps) {
            if (Utils.isPackageInstalled(s, context) && isSystemApp(PackageTasks.getAPKPath(s, context))) {
                PackageTasks.setToDelete(PackageTasks.getAPKPath(s, context), new File(PackageTasks.getAPKPath(s, context)).getName(), context);
                mAppList.append(PackageTasks.getAPKPath(s, context)).append("\n");
                Utils.create(mAppList.toString(), PackageTasks.getModulePath() + "/uad_samsung");
            }
        }
    }

    public static void disableSamsung() {
        try {
            for (String line : Utils.read(PackageTasks.getModulePath() + "/uad_samsung").split("\\r?\\n")) {
                if (line.startsWith("/system/") && Utils.exist(PackageTasks.getModulePath() + line)) {
                    PackageTasks.revertDelete(line);
                    Utils.delete(PackageTasks.getModulePath() + "/uad_samsung");
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
            switch (script) {
                case "aosp":
                    disableAOSP();
                    break;
                case "oneplus":
                    disableOnePlus();
                    break;
                case "asus":
                    disableASUS();
                    break;
                case "carrier":
                    disableCarrier();
                    break;
                case "huawei":
                    disableHuawei();
                    break;
                case "lg":
                    disableLG();
                    break;
                case "samsung":
                    disableSamsung();
                    break;
                default:
                    disableGoogle();
                    break;
            }
        } else {
            switch (script) {
                case "aosp":
                    enableAOSP(activity);
                    break;
                case "oneplus":
                    enableOnePlus(activity);
                    break;
                case "asus":
                    enableASUS(activity);
                    break;
                case "carrier":
                    enableCarrier(activity);
                    break;
                case "huawei":
                    enableHuawei(activity);
                    break;
                case "lg":
                    enableLG(activity);
                    break;
                case "samsung":
                    enableSamsung(activity);
                    break;
                default:
                    enableGoogle(activity);
                    break;
            }
        }
    }

}