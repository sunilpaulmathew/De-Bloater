/*
 * Copyright (C) 2020-2021 sunilpaulmathew <sunil.kde@gmail.com>
 *
 * This file is part of Package Manager, a simple, yet powerful application
 * to manage other application installed on an android device.
 *
 */

package com.sunilpaulmathew.debloater.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on November 03, 2020
 */

public class CustomScripts {

    static List<String> getTomatotInvisible() {
        List<String> mData = new ArrayList<>();
        mData.add("/system/app/AntHalService/AntHalService.apk");
        mData.add("/system/app/AutoRegistration/AutoRegistration.apk");
        mData.add("/system/app/BasicDreams/BasicDreams.apk");
        mData.add("/system/app/BookmarkProvider/BookmarkProvider.apk");
        mData.add("/system/app/BTtestmode/BTtestmode.apk");
        mData.add("/system/app/BuiltInPrintService/BuiltInPrintService.apk");
        mData.add("/system/app/card/card.apk");
        mData.add("/system/app/CtsShimPrebuilt/CtsShimPrebuilt.apk");
        mData.add("/system/app/EasterEgg_O2/EasterEgg_O2.apk");
        mData.add("/system/app/EngineeringMode/EngineeringMode.apk");
        mData.add("/system/app/EngSpecialTest/EngSpecialTest.apk");
        mData.add("/system/app/GooglePrintRecommendationService/GooglePrintRecommendationService.apk");
        mData.add("/system/app/GoogleTTS/GoogleTTS.apk");
        mData.add("/system/app/LogKitSdService/LogKitSdService.apk");
        mData.add("/system/app/NFCTestMode/NFCTestMode.apk");
        mData.add("/system/app/OEMLogKit/OEMLogKit.apk");
        mData.add("/system/app/oem_tcma/oem_tcma.apk");
        mData.add("/system/app/OPBugReportLite/OPBugReportLite.apk");
        mData.add("/system/app/OPCommonLogTool/OPCommonLogTool.apk");
        mData.add("/system/app/OPLiveWallpaper/OPLiveWallpaper.apk");
        mData.add("/system/app/OPPush/OPPush.apk");
        mData.add("/system/app/PartnerBookmarksProvider/PartnerBookmarksProvider.apk");
        mData.add("/system/app/PhotosOnline/PhotosOnline.apk");
        mData.add("/system/app/PlayAutoInstallConfig/PlayAutoInstallConfig.apk");
        mData.add("/system/app/RFTuner/RFTuner.apk");
        mData.add("/system/app/SensorTestTool/SensorTestTool.apk");
        mData.add("/system/app/SoterService/SoterService.apk");
        mData.add("/system/app/Stk/Stk.apk");
        mData.add("/system/app/SeempService/SeempService.apk");
        mData.add("/system/app/talkback/talkback.apk");
        mData.add("/system/app/Traceur/Traceur.apk");
        mData.add("/system/app/WallpaperBackup/WallpaperBackup.apk");
        mData.add("/system/app/WapiCertManage/WapiCertManage.apk");
        mData.add("/system/app/WifiRfTestApk/WifiRfTestApk.apk");
        mData.add("/system/priv-app/CtsShimPrivPrebuilt/CtsShimPrivPrebuilt.apk");
        mData.add("/system/priv-app/OPCellBroadcastReceiver/OPCellBroadcastReceiver.apk");
        mData.add("/system/priv-app/TagGoogle/TagGoogle.apk");
        mData.add("/system/product/app/uimremoteclient/uimremoteclient.apk");
        mData.add("/system/product/priv-app/GoogleFeedback/GoogleFeedback.apk");
        mData.add("/product/app/uimremoteclient/uimremoteclient.apk");
        mData.add("/product/priv-app/GoogleFeedback/GoogleFeedback.apk");
        mData.add("/system/etc/usb_drivers.iso/usb_drivers.iso.apk");
        return mData;
    }

    static List<String> getTomatotLight() {
        List<String> mData = new ArrayList<>();
        mData.add("/system/app/ARCore_stub/ARCore_stub.apk");
        mData.add("/system/app/BackupRestoreRemoteService/BackupRestoreRemoteService.apk");
        mData.add("/system/app/DiracManager/DiracManager.apk");
        mData.add("/system/app/GooglePay/GooglePay.apk");
        mData.add("/system/app/HTMLViewer/HTMLViewer.apk");
        mData.add("/system/app/NVBackupUI/NVBackupUI.apk");
        mData.add("/system/app/OPScreenRecord/OPScreenRecord.apk");
        mData.add("/system/priv-app/BackupRestoreConfirmation/BackupRestoreConfirmation.apk");
        mData.add("/system/priv-app/CallLogBackup/CallLogBackup.apk");
        mData.add("/system/priv-app/DiracAudioControlService/DiracAudioControlService.apk");
        mData.add("/system/priv-app/ManagedProvisioning/ManagedProvisioning.apk");
        mData.add("/system/priv-app/OnePlusWizard/OnePlusWizard.apk");
        mData.add("/system/priv-app/OPDeviceManager/OPDeviceManager.apk");
        mData.add("/system/priv-app/OPDeviceManagerProvider/OPDeviceManagerProvider.apk");
        mData.add("/system/priv-app/SharedStorageBackup/SharedStorageBackup.apk");
        mData.add("/system/product/app/Account/Account.apk");
        mData.add("/system/product/app/atfwd/atfwd.apk");
        mData.add("/system/product/app/CalendarGoogle/CalendarGoogle.apk");
        mData.add("/system/product/app/Chrome/Chrome.apk");
        mData.add("/system/product/app/Drive/Drive.apk");
        mData.add("/system/product/app/Duo/Duo.apk");
        mData.add("/system/product/app/Gmail2/Gmail2.apk");
        mData.add("/system/product/app/Music2/Music2.apk");
        mData.add("/system/product/app/PhotoTable/PhotoTable.apk");
        mData.add("/system/product/app/QdcmFF/QdcmFF.apk");
        mData.add("/system/product/app/Videos/Videos.apk");
        mData.add("/system/product/app/YouTube/YouTube.apk");
        mData.add("/system/product/priv-app/AndroidAutoStub/AndroidAutoStub.apk");
        mData.add("/system/product/priv-app/GoogleOneTimeInitializer/GoogleOneTimeInitializer.apk");
        mData.add("/system/product/priv-app/SetupWizard/SetupWizard.apk");
        mData.add("/system/product/priv-app/Turbo/Turbo.apk");
        mData.add("/product/app/Account/Account.apk");
        mData.add("/product/app/atfwd/atfwd.apk");
        mData.add("/product/app/CalendarGoogle/CalendarGoogle.apk");
        mData.add("/product/app/Chrome/Chrome.apk");
        mData.add("/product/app/Drive/Drive.apk");
        mData.add("/product/app/Duo/Duo.apk");
        mData.add("/product/app/Gmail2/Gmail2.apk");
        mData.add("/product/app/Music2/Music2.apk");
        mData.add("/product/app/PhotoTable/PhotoTable.apk");
        mData.add("/product/app/QdcmFF/QdcmFF.apk");
        mData.add("/product/app/Videos/Videos.apk");
        mData.add("/product/app/YouTube/YouTube.apk");
        mData.add("/product/priv-app/AndroidAutoStub/AndroidAutoStub.apk");
        mData.add("/product/priv-app/GoogleOneTimeInitializer/GoogleOneTimeInitializer.apk");
        mData.add("/product/priv-app/SetupWizard/SetupWizard.apk");
        mData.add("/product/priv-app/Turbo/Turbo.apk");
        return mData;
    }

    static List<String> getTomatotExtreme() {
        List<String> mData = new ArrayList<>();
        mData.add("/system/app/Backup.apk");
        mData.add("/system/app/BluetoothMidiService/BluetoothMidiService.apk");
        mData.add("/system/app/LiveWallpapersPicker/LiveWallpapersPicker.apk");
        mData.add("/system/app/OPBackup/OPBackup.apk");
        mData.add("/system/app/OPBreathMode/OPBreathMode.apk");
        mData.add("/system/app/OPSafe/OPSafe.apk");
        mData.add("/system/app/OPYellowpage/OPYellowpage.apk");
        mData.add("/system/app/WAPPushManager/WAPPushManager.apk");
        mData.add("/system/priv-app/EmergencyInfo/EmergencyInfo.apk");
        mData.add("/system/priv-app/HotwordEnrollmentOKGoogleWCD9340/HotwordEnrollmentOKGoogleWCD9340.apk");
        mData.add("/system/priv-app/HotwordEnrollmentXGoogleWCD9340/HotwordEnrollmentXGoogleWCD9340.apk");
        mData.add("/system/priv-app/IFAAService/IFAAService.apk");
        mData.add("/system/priv-app/MusicFX/MusicFX.apk");
        mData.add("/system/priv-app/OnePlusGallery/OnePlusGallery.apk");
        mData.add("/system/priv-app/OPAod/OPAod.apk");
        mData.add("/system/priv-app/OPFaceUnlock/OPFaceUnlock.apk");
        mData.add("/system/priv-app/ProxyHandler/ProxyHandler.apk");
        mData.add("/system/priv-app/VpnDialogs/VpnDialogs.apk");
        mData.add("/system/product/app/datastatusnotification/datastatusnotification.apk");
        mData.add("/system/product/app/GoogleContactsSyncAdapter/GoogleContactsSyncAdapter.apk");
        mData.add("/system/product/app/GoogleLocationHistory/GoogleLocationHistory.apk");
        mData.add("/system/product/app/Maps/Maps.apk");
        mData.add("/system/product/app/Photos/Photos.apk");
        mData.add("/system/product/app/remoteSimLockAuthentication/remoteSimLockAuthentication.apk");
        mData.add("/system/product/app/remotesimlockservice/remotesimlockservice.apk");
        mData.add("/system/product/app/WebViewGoogle/WebViewGoogle.apk");
        mData.add("/system/product/priv-app/OPAppLocker/OPAppLocker.apk");
        mData.add("/system/product/priv-app/Velvet/Velvet/Velvet.apk");
        mData.add("/system/product/priv-app/WallpaperCropper/WallpaperCropper.apk");
        mData.add("/system/product/priv-app/Wellbeing/Wellbeing.apk");
        mData.add("/product/app/datastatusnotification/datastatusnotification.apk");
        mData.add("/product/app/GoogleContactsSyncAdapter/GoogleContactsSyncAdapter.apk");
        mData.add("/product/app/GoogleLocationHistory/GoogleLocationHistory.apk");
        mData.add("/product/app/Maps/Maps.apk");
        mData.add("/product/app/Photos/Photos.apk");
        mData.add("/product/app/remoteSimLockAuthentication/remoteSimLockAuthentication.apk");
        mData.add("/product/app/remotesimlockservice/remotesimlockservice.apk");
        mData.add("/product/app/WebViewGoogle/WebViewGoogle.apk");
        mData.add("/product/priv-app/OPAppLocker/OPAppLocker.apk");
        mData.add("/product/priv-app/Velvet/Velvet/Velvet.apk");
        mData.add("/product/priv-app/WallpaperCropper/WallpaperCropper.apk");
        mData.add("/product/priv-app/Wellbeing/Wellbeing.apk");
        return mData;
    }

    public static String getInvisibletList(Context context) {
        StringBuilder mAppList = new StringBuilder();
        String[] invisible = getTomatotInvisible().toString().substring(1, getTomatotInvisible().toString().length() - 1).split(", ");
        for (String s : invisible) {
            if (Utils.exist(s) && s.endsWith(".apk")) {
                mAppList.append(PackageTasks.getIDfromAPK(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

    public static String getLightList(Context context) {
        StringBuilder mAppList = new StringBuilder();
        String[] invisible = getTomatotInvisible().toString().substring(1, getTomatotInvisible().toString().length() - 1).split(", ");
        for (String s : invisible) {
            if (Utils.exist(s) && s.endsWith(".apk")) {
                mAppList.append(PackageTasks.getIDfromAPK(s, context)).append("\n");
            }
        }
        String[] light = getTomatotLight().toString().substring(1, getTomatotLight().toString().length() - 1).split(", ");
        for (String s : light) {
            if (Utils.exist(s) && s.endsWith(".apk")) {
                mAppList.append(PackageTasks.getIDfromAPK(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

    public static String getExtremeList(Context context) {
        StringBuilder mAppList = new StringBuilder();
        String[] invisible = getTomatotInvisible().toString().substring(1, getTomatotInvisible().toString().length() - 1).split(", ");
        for (String s : invisible) {
            if (Utils.exist(s) && s.endsWith(".apk")) {
                mAppList.append(PackageTasks.getIDfromAPK(s, context)).append("\n");
            }
        }
        String[] light = getTomatotLight().toString().substring(1, getTomatotLight().toString().length() - 1).split(", ");
        for (String s : light) {
            if (Utils.exist(s) && s.endsWith(".apk")) {
                mAppList.append(PackageTasks.getIDfromAPK(s, context)).append("\n");
            }
        }
        String[] extreme = getTomatotExtreme().toString().substring(1, getTomatotExtreme().toString().length() - 1).split(", ");
        for (String s : extreme) {
            if (Utils.exist(s) && s.endsWith(".apk")) {
                mAppList.append(PackageTasks.getIDfromAPK(s, context)).append("\n");
            }
        }
        return mAppList.toString();
    }

}