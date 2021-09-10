# De-Bloater

![De-Bloater](app/src/main/res/mipmap-xxxhdpi/ic_launcher.png?raw=true)

[![](https://img.shields.io/badge/De--Bloater-v0.20-green)](https://play.google.com/store/apps/details?id=com.sunilpaulmathew.debloater)
![](https://img.shields.io/github/downloads/sunilpaulmathew/De-Bloater/total)
![](https://img.shields.io/github/languages/top/sunilpaulmathew/De-Bloater)
![](https://img.shields.io/github/contributors/sunilpaulmathew/De-Bloater)

### De-Bloater is an application using the power of Magisk to de-bloat unwanted applications systemless-ly.

## Download
[<img src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png"
     alt=""
     height="80">](https://play.google.com/store/apps/details?id=com.sunilpaulmathew.debloater)
     [<img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
     alt=""
     height="80">](https://f-droid.org/packages/com.sunilpaulmathew.debloater)
     [<img src="https://gitlab.com/IzzyOnDroid/repo/-/raw/master/assets/IzzyOnDroid.png"
     alt=""
     height="80">](https://apt.izzysoft.de/fdroid/index/apk/com.sunilpaulmathew.debloater)
     [<img src="https://i.ibb.co/q0mdc4Z/get-it-on-github.png"
          alt=""
          height="80">](https://github.com/sunilpaulmathew/De-Bloater/releases/download/v0.20/app-release.apk)

## Features
* Easily remove system apps from "/system", "/vendor", and "/product" directories.

## Requirement
De-Bloater requires Root Access and a fully functional Magisk environment, including modules, for proper working. As a result, De-Bloater won't work with other rooting solutions as well as in Magisk core-only mode.

## How to Use
Open the app, click the remove button on each app you want to remove. The selected apps will be removed systemless-ly after a reboot. To restore an app, either Reset Module (on the top menu) or selectively restore from the second page. Please note that a restart is necessary to get any of the changes in effect.

## How it works
The app will systemless-ly replace the selected APKs by making a Magisk module. As a result, you will see a new Module (name: De-bloater).

## Troubleshooting
In case, if you accidentally remove some important apps and your phone bootloops, please delete "/data/adb/modules/De-bloater" via recovery.

## Translations
Please help me to translate this application via [POEditor](https://poeditor.com/join/project?hash=BZS89Ev3WG). You may also translate after downloading the original language string available [here](app/src/main/res/values/strings.xml).

## Donations
If you like to appreciate my work, please consider donating to me (either via [Liberapay](https://liberapay.com/sunilpaulmathew/donate), [PayPal](https://www.paypal.me/menacherry/), [Ko-fi](https://ko-fi.com/sunilpaulmathew/), or by purchasing the [SmartPack Donation Package](https://play.google.com/store/apps/details?id=com.smartpack.donate) from playstore) as it is helpful to continue my projects more active, although it is not at all necessary.

[<img src="https://liberapay.com/assets/widgets/donate.svg"
     alt=""
     height="80">](https://liberapay.com/sunilpaulmathew/donate/)
[<img src="https://raw.githubusercontent.com/SmartPack/SmartPack.github.io/master/asset/pic005.png"
     alt=""
     height="80">](https://www.paypal.me/menacherry/)
[<img src="https://raw.githubusercontent.com/SmartPack/SmartPack.github.io/master/asset/pic010.png"
     alt=""
     height="80">](https://ko-fi.com/sunilpaulmathew/)
[<img src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png"
     alt=""
     height="80">](https://play.google.com/store/apps/details?id=com.smartpack.donate)

## Credits
* [Grarak](https://github.com/Grarak/), Code contributions (I took some code from [Kernel Adiutor](https://github.com/Grarak/KernelAdiutor/))
* [John Wu](https://github.com/topjohnwu), libsu & Magisk
* [MONSTER_PC](https://t.me/MONSTER_PC), Russian & Ukrainian Translations
* [Emre](https://t.me/xcooLwastaken), Turkish Translations
* [Firerust96](https://github.com/Firerust96), Spanish Translations
* lay4play, Italian Translations
* Axel Schaab, German Translations
* alex & Ktosspl, Polish Translations
* Many other people contributed via [POEditor](https://poeditor.com/join/project?hash=BZS89Ev3WG)

_If you think I missed your name, please let me know._

## License

    Copyright (C) 2021-2022 sunilpaulmathew <sunil.kde@gmail.com>

    De-Bloater is a free softwares: you can redistribute it and/or modify it
    under the terms of the GNU General Public License as published by the
    Free Software Foundation, either version 3 of the License, or (at
    your option) any later version.

    De-Bloater is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
    for more details.

    You should have received a copy of the GNU General Public License along
    with De-Bloater. If not, see <http://www.gnu.org/licenses/>.
